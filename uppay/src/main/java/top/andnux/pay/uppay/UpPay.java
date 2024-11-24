package top.andnux.pay.uppay;

import android.app.Activity;

import com.unionpay.UPPayAssistEx;

import top.andnux.pay.core.IPay;
import top.andnux.pay.core.Pay;

public class UpPay implements IPay<UpPayInfo> {

    private Activity mActivity;
    private UpPayInfo alipayInfoImpli;
    private Pay.IPayCallback sPayCallback;

    @Override
    public void pay(Activity activity, UpPayInfo payInfo, Pay.IPayCallback payCallback) {
        this.mActivity = activity;
        this.alipayInfoImpli = payInfo;
        sPayCallback = payCallback;
        /*****************************************************************
         * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
         *****************************************************************/
        UPPayAssistEx.startPay(activity, null, null, payInfo.getTn(), "01");
    }
}