package com.oneapplab.shopnow.interfaces;


import com.oneapplab.shopnow.enums.FragmentEnum;
import com.oneapplab.shopnow.fragmentHelper.FragmentMessageContainer;

/**
 * Created by haider on 09-02-2017.
 */

public interface OnFragmentToDashboardInteractionListener {
    void setToolbarTitle(String s, FragmentEnum comingFrom);

    void setOnDashboardActivityToFragmentCommunication(OnDashboardActivityToFragmentCommunication onDashboardActivityToFragmentCommunication);

    void openDesiredFragment(FragmentMessageContainer fragmentMessageContainer);

    void setToolbarInvisible();

    void backButtonClicked();

    void setToolbarVisible();
}
