package com.oneapplab.shopnow.dashboard;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.oneapplab.shopnow.MessageBoxService.MessageBoxService;
import com.oneapplab.shopnow.R;
import com.oneapplab.shopnow.baseDataContainer.BaseDataContainer;
import com.oneapplab.shopnow.baseFragment.BaseFragment;
import com.oneapplab.shopnow.enums.FragmentEnum;
import com.oneapplab.shopnow.fragmentHelper.FragmentMessageContainer;
import com.oneapplab.shopnow.fragmentHelper.StringContainer;
import com.oneapplab.shopnow.fragments.DashboardFragment;
import com.oneapplab.shopnow.fragments.UserProfileFragment;
import com.oneapplab.shopnow.interfaces.OnDashboardActivityToFragmentCommunication;
import com.oneapplab.shopnow.interfaces.OnFragmentToDashboardInteractionListener;
import com.oneapplab.shopnow.utilService.GlobalConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class DashboardActivity extends AppCompatActivity implements ExpandableListView.OnGroupClickListener,
        ExpandableListView.OnChildClickListener, OnFragmentToDashboardInteractionListener {


    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListView drawerList;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private LinearLayout mainView;
    private LinearLayout navHeader;
    private FrameLayout fragmentContainer;

    private Context context;
    private String newurl;

    private View.OnClickListener showUserProfileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            openDesiredFragment(new FragmentMessageContainer(
                    FragmentEnum.UserProfile,
                    FragmentEnum.Dashboard,
                    null,
                    false, null));

        }
    };
    private AppCompatCheckedTextView toolBarTitle;
    private Handler handler;
    private String TAG = getClass().getSimpleName();
    private View.OnClickListener toolBarTitleOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppCompatCheckedTextView appCompatCheckedTextView = ((AppCompatCheckedTextView) v);
            switch (comingFrom) {
                case Dashboard:
                    boolean isChecked = appCompatCheckedTextView.isChecked();
                    onDashboardActivityToFragmentCommunication.toolbarTitleClicked(isChecked);
                    if (isChecked) {
                        appCompatCheckedTextView.setChecked(false);
                        toolBarRightImageAdjacentTitle.setImageResource(R.drawable.expand_arrow);
                    } else {
                        appCompatCheckedTextView.setChecked(true);
                        toolBarRightImageAdjacentTitle.setImageResource(R.drawable.collapse_menu);
                    }
                    break;
                case UserProfile:
                    break;
            }
        }
    };
    private AppCompatImageView toolBarRightImageAdjacentTitle;
    private OnDashboardActivityToFragmentCommunication onDashboardActivityToFragmentCommunication;
    private FragmentMessageContainer defaultFragmentMessageContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        MessageBoxService.sharedInstance().setContext(this);


        setContentView(R.layout.activity_dashboard);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);



        setSupportActionBar(mToolbar);
        initializeViews();

        navHeader.setOnClickListener(showUserProfileClickListener);

        toolBarTitle.setOnClickListener(toolBarTitleOnClickListener);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        setSupportActionBar(mToolbar);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        initDrawer();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);



        if (savedInstanceState == null) {
            addDefaultFragmentInPlace();
        } else {
            fragmentMessageContainerList = savedInstanceState.getParcelableArrayList(GlobalConstant.getInstance().getFragmentMessageConatinerKey());

        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainerList);

    }

    private void initDrawer() {
        navHeader = (LinearLayout) findViewById(R.id.nav_header_container);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerList = (ExpandableListView) findViewById(R.id.list_slidermenu);

        prepareListData();

        drawerList.setAdapter(new NavigationViewAdapter(this, listDataHeader, listDataChild));

        drawerList.setOnChildClickListener(this);
        drawerList.setOnGroupClickListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                mToolbar.setTranslationX(slideOffset * drawerView.getWidth());

                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        Resources res = getResources();
        String[] headers = res.getStringArray(R.array.nav_drawer_labels);
        listDataHeader = Arrays.asList(headers);

        List<String> home, orders, notifications, settings, custSupport, signOut;
        String[] shome, sorders, snotifications, ssettings, scustSupport , ssignOut;

        shome = res.getStringArray(R.array.elements_home);
        home = Arrays.asList(shome);

        sorders = res.getStringArray(R.array.elements_orders);
        orders = Arrays.asList(sorders);

        snotifications = res.getStringArray(R.array.elements_notifications);
        notifications = Arrays.asList(snotifications);

        ssettings = res.getStringArray(R.array.elements_settings);
        settings = Arrays.asList(ssettings);

        scustSupport = res.getStringArray(R.array.elements_custSupport);
        custSupport = Arrays.asList(scustSupport);

        ssignOut = res.getStringArray(R.array.elements_signOut);
        signOut = Arrays.asList(ssignOut);

        listDataChild.put(listDataHeader.get(0), home);
        listDataChild.put(listDataHeader.get(1), orders);
        listDataChild.put(listDataHeader.get(2), notifications);
        listDataChild.put(listDataHeader.get(3), settings);
        listDataChild.put(listDataHeader.get(4), custSupport);
        listDataChild.put(listDataHeader.get(5), signOut);

    }

    private void initializeViews() {
        navHeader = (LinearLayout) findViewById(R.id.nav_header_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mainView = (LinearLayout) findViewById(R.id.container_body);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragmentContainer = (FrameLayout) findViewById(R.id.content_dashboard_framelayout_fragmentcontainer);
        toolBarTitle = (AppCompatCheckedTextView) mToolbar.findViewById(R.id.actionbar_layout_textview);

    }

    private void addDefaultFragmentInPlace() {
        defaultFragmentMessageContainer = new FragmentMessageContainer(
                FragmentEnum.Dashboard,
                FragmentEnum.Dashboard,
                null,
                false,
                null
        );
        Bundle bundle = new Bundle();
        bundle.putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), defaultFragmentMessageContainer);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        DashboardFragment dashboardFragment = new DashboardFragment();
        dashboardFragment.setArguments(bundle);
        fragmentTransaction.add(fragmentContainer.getId(), dashboardFragment, FragmentEnum.Dashboard.toString());
        fragmentTransaction.addToBackStack(FragmentEnum.Dashboard.toString());
        fragmentTransaction.commit();
        fragmentMessageContainerList.add(defaultFragmentMessageContainer);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (onDashboardActivityToFragmentCommunication.shouldDoNormalOperationOnBackPressed()) {
                int currentFragment = fragmentMessageContainerList.size() - 1;
                if (currentFragment > 0) {
                    FragmentMessageContainer fragmentMessageContainer = fragmentMessageContainerList.get(currentFragment - 1);
                    BaseDataContainer baseDataContainer = onDashboardActivityToFragmentCommunication.getComingData();
                    if (baseDataContainer != null) {
                        fragmentMessageContainer.setComeback(true);
                        fragmentMessageContainer.setComingData(baseDataContainer);
                    }
                    fragmentMessageContainerList.remove(currentFragment);
                    setFragmentBack(fragmentMessageContainer);
                } else {
                    finish();
                }
            }
        }
    }

    private void setFragmentBack(FragmentMessageContainer fragmentMessageContainer) {
        switch (fragmentMessageContainer.getGoingTo()) {
            case Dashboard:
                setToolbarVisible();
                break;
            case UserProfile:
                setToolbarInvisible();
                break;

        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BaseFragment baseFragment = (BaseFragment) getFragmentManager().findFragmentByTag(fragmentMessageContainer.getGoingTo().toString());
        if (baseFragment != null) {
            Bundle bundle = baseFragment.getArguments();
            if (bundle != null) {
                bundle.putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainer);
            } else {
                bundle = new Bundle();
                bundle.putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainer);
                baseFragment.setArguments(bundle);
            }
        }
        fragmentTransaction.replace(fragmentContainer.getId(), baseFragment, fragmentMessageContainer.getGoingTo().toString());
        fragmentTransaction.commit();
    }



    FragmentEnum comingFrom;

    @Override
    public void setToolbarTitle(final String s, final FragmentEnum comingFromm) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                comingFrom = comingFromm;
                toolBarTitle.setOnClickListener(null);
                toolBarTitle.setClickable(false);
                switch (comingFromm) {
                    case Dashboard:
                        toolBarTitle.setText(s);
                        toolBarTitle.setClickable(true);
                        toolBarTitle.setChecked(true);
                        toolBarTitle.setOnClickListener(toolBarTitleOnClickListener);
                        break;
                    case UserProfile:


                }
            }
        });
    }

    @Override
    public void setOnDashboardActivityToFragmentCommunication(OnDashboardActivityToFragmentCommunication onDashboardActivityToFragmentCommunication) {
        this.onDashboardActivityToFragmentCommunication = onDashboardActivityToFragmentCommunication;
    }



    @Override
    public void openDesiredFragment(FragmentMessageContainer fragmentMessageContainer) {
        setToolbarVisible();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainer);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BaseFragment baseFragment = (BaseFragment) getFragmentManager().findFragmentByTag(fragmentMessageContainer.getGoingTo().toString());
        if (baseFragment == null) {
            switch (fragmentMessageContainer.getGoingTo()) {
                case Dashboard:
                    break;

                case UserProfile:
                    baseFragment = new UserProfileFragment();
                    break;

            }
            if (baseFragment != null) {
                baseFragment.setArguments(bundle);
                fragmentTransaction.replace(fragmentContainer.getId(), baseFragment, fragmentMessageContainer.getGoingTo().toString());
            }
        } else {
            baseFragment.getArguments().putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainer);
            fragmentTransaction.replace(fragmentContainer.getId(), baseFragment, fragmentMessageContainer.getGoingTo().toString());
        }

        fragmentTransaction.addToBackStack(fragmentMessageContainer.getGoingTo().toString());
        maintainFragmentStack(fragmentMessageContainer);
        fragmentTransaction.commit();
    }

    public void setToolbarVisible() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mToolbar.setVisibility(View.VISIBLE);
            }
        });
    }

    ArrayList<FragmentMessageContainer> fragmentMessageContainerList = new ArrayList<>();

    private void maintainFragmentStack(FragmentMessageContainer fragmentMessageContainer) {
        switch (fragmentMessageContainer.getGoingTo()) {
            case Dashboard:
                break;
            case UserProfile:
               break;
        }
    }


    @Override
    public void setToolbarInvisible() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mToolbar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void backButtonClicked() {
        handler.post(new Runnable() {
            public void run() {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


        Bundle mBundle = new Bundle();
        mDrawerLayout.closeDrawer(GravityCompat.START);
        drawerList.collapseGroup(groupPosition);
        switch (childPosition) {

            case 0:

                break;

            case 2:

                break;

        }
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (groupPosition) {
            case 0:
                hamburgerRedirection(FragmentEnum.Dashboard, null);
                break;
            case 1:
                hamburgerRedirection(FragmentEnum.OneTimePassword, new FragmentMessageContainer(
                        FragmentEnum.OneTimePassword,
                        FragmentEnum.Dashboard,
                        new StringContainer("Order List"),
                        false,
                        null
                ));
                break;

            case 5:
                DashboardActivity.this.finish();
                break;

        }

        return false;
    }

    private void hamburgerRedirection(FragmentEnum anEnum, FragmentMessageContainer fragmentMessageContainer) {
        fragmentMessageContainerList.clear();
        switch (anEnum) {
            case Dashboard:
                addDefaultFragmentInPlace();
                break;

        }

    }






}
