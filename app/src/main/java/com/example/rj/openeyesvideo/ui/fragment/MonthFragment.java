package com.example.rj.openeyesvideo.ui.fragment;

/**
 * Created by rj on 2017/12/26.
 */

public class MonthFragment extends BaseHotFragment {
    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void getHotData() {
        mPresenter.getHotData("monthly");
    }
}
