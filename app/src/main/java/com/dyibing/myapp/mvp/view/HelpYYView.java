package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.WelfareBean;
import com.dyibing.myapp.net.HttpResult;

public interface HelpYYView extends IBaseView {

    void onDonationForestCoin(HttpResult httpResult);

    void onWelfareRankTenList(WelfareBean welfareBean);

}
