package com.oneapplab.shopnow.interfaces;


import com.oneapplab.shopnow.baseDataContainer.BaseDataContainer;

/**
 * Created by haider on 09-02-2017.
 */

public interface OnDashboardActivityToFragmentCommunication {

    BaseDataContainer getComingData();
    void toolbarTitleClicked(boolean anchor);
    boolean shouldDoNormalOperationOnBackPressed();
}
