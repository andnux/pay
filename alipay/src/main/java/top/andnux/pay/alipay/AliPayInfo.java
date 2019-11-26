package top.andnux.pay.alipay;

import top.andnux.pay.core.IPayInfo;

public class AliPayInfo implements IPayInfo {

    private String orderInfo;

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

}