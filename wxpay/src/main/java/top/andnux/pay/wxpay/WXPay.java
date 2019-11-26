package top.andnux.pay.wxpay;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import top.andnux.pay.core.IPay;
import top.andnux.pay.core.Pay;

public class WXPay implements IPay<WXPayInfo> {

    private static WXPay sWxPay;
    private IWXAPI mWXApi;
    private boolean initializated;
    private WXPayInfo mPayInfo;
    private Pay.IPayCallback mCallback;

    public static WXPay getInstance() {
        if (sWxPay == null) {
            synchronized (WXPay.class) {
                if (sWxPay == null) {
                    sWxPay = new WXPay();
                }
            }
        }
        return sWxPay;
    }

    public IWXAPI getWXApi() {
        return mWXApi;
    }

    private void initWXApi(Context context, String appId) {
        mWXApi = WXAPIFactory.createWXAPI(context.getApplicationContext(), appId);
        mWXApi.registerApp(appId);
        initializated = true;
    }


    @Override
    public void pay(Activity activity, WXPayInfo payInfo, Pay.IPayCallback payCallback) {
        mPayInfo = payInfo;
        mCallback = payCallback;
        if (mPayInfo == null || TextUtils.isEmpty(mPayInfo.getAppid()) || TextUtils.isEmpty(mPayInfo.getPartnerid())
                || TextUtils.isEmpty(mPayInfo.getPrepayId()) || TextUtils.isEmpty(mPayInfo.getPackageValue()) ||
                TextUtils.isEmpty(mPayInfo.getNonceStr()) || TextUtils.isEmpty(mPayInfo.getTimestamp()) ||
                TextUtils.isEmpty(mPayInfo.getSign())) {
            if (payCallback != null) {
                payCallback.failed(WXErrCodeEx.CODE_ILLEGAL_ARGURE,
                        WXErrCodeEx.getMessageByCode(WXErrCodeEx.CODE_ILLEGAL_ARGURE));
            }
            return;
        }

        if (!initializated) {
            initWXApi(activity.getApplicationContext(), mPayInfo.getAppid());
        }
        if (!check()) {
            if (payCallback != null) {
                payCallback.failed(WXErrCodeEx.CODE_UNSUPPORT, WXErrCodeEx.getMessageByCode(WXErrCodeEx.CODE_UNSUPPORT));
            }
            return;
        }
        PayReq req = new PayReq();
        req.appId = mPayInfo.getAppid();
        req.partnerId = mPayInfo.getPartnerid();
        req.prepayId = mPayInfo.getPrepayId();
        req.packageValue = mPayInfo.getPackageValue();
        req.nonceStr = mPayInfo.getNonceStr();
        req.timeStamp = mPayInfo.getTimestamp();
        req.sign = mPayInfo.getSign();
        mWXApi.sendReq(req);
    }

    /**
     * 支付回调响应
     */
    public void onResp(int errorCode, String errorMsg) {
        if (mCallback == null) {
            return;
        }
        if (errorCode == BaseResp.ErrCode.ERR_OK) {
            mCallback.success();
        } else if (errorCode == BaseResp.ErrCode.ERR_COMM) {
            mCallback.failed(errorCode, errorMsg);
        } else if (errorCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            mCallback.cancel();
        } else {
            mCallback.failed(errorCode, errorMsg);
        }
        mCallback = null;
    }

    /**
     * 检测是否支持微信支付
     */
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }
}
