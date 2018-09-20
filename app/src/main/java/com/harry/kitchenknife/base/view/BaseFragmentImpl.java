package com.harry.kitchenknife.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.harry.kitchenknife.base.presenter.BasePresenter;

/**
 * Created by Harry on 2018/4/17.
 */
public abstract class BaseFragmentImpl<P extends BasePresenter> extends Fragment implements IBaseView {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = bindPresenter();
        if (mPresenter != null) {
            mPresenter.bindView(this);//presenter与view层绑定
        }
    }

    /**
     * 绑定presenter
     * @return presenter
     */
    protected abstract P bindPresenter();

    /**
     * 当View销毁的时候, presenter层与view层解绑
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPresenter != null) {
            mPresenter.unBindView();
        }
    }
}
