package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.net.HttpResult;

public interface LikesView extends IBaseView {

    void onSaveLikes(HttpResult httpResult);

}
