package top.andnux.pay.core;

import android.app.Activity;

public interface IPay<T extends IPayInfo> {

    void pay(Activity activity,
             T payInfo,
             Pay.IPayCallback payCallback);
}