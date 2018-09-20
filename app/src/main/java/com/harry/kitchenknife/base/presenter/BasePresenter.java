package com.harry.kitchenknife.base.presenter;

import com.harry.kitchenknife.base.view.IBaseView;

/**
 * Created by Harry on 2018/4/17.
 */
public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    protected V view;

    @Override
    public void bindView(V view) {
        this.view = view;
    }

    @Override
    public void unBindView() {
        if (view != null) {
            view = null;
        }
    }
}
