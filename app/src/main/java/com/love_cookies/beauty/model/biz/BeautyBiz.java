package com.love_cookies.beauty.model.biz;

import com.google.gson.Gson;
import com.love_cookies.beauty.app.BeautyApplication;
import com.love_cookies.beauty.config.AppConfig;
import com.love_cookies.beauty.model.bean.BeautyBean;
import com.love_cookies.beauty.model.biz.interfaces.IBeautyBiz;
import com.love_cookies.cookie_library.interfaces.CallBack;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by xiekun on 2016/7/22 0022.
 *
 * 获取美女图片的逻辑
 */
public class BeautyBiz implements IBeautyBiz {

    private Gson gson = new Gson();

    /**
     * 获取数据
     * @param page
     * @param callBack
     */
    @Override
    public void getBeauty(int page, final CallBack callBack) {
        RequestParams requestParams = new RequestParams(AppConfig.API + page);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BeautyBean beautyBean = gson.fromJson(result, BeautyBean.class);
                for (int i = 0; i < beautyBean.getResults().size(); i++) {
                    beautyBean.getResults().get(i).setBackgroundColor(BeautyApplication.colorList[i % BeautyApplication.colorList.length]);
                }
                callBack.onSuccess(beautyBean);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onFailed(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
