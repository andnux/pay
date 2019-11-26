package top.andnux.pay.core;

import android.app.Activity;

public class Pay {

    public static <T extends IPayInfo> void pay(IPay<T> payWay,
                                                Activity mActivity,
                                                T payinfo,
                                                IPayCallback callback){
        payWay.pay(mActivity, payinfo, callback);
    }

    public interface IPayCallback {

        void success();

        void failed(int code, String message);

        void cancel();
    }
}