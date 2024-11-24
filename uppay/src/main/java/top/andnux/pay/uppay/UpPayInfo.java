package top.andnux.pay.uppay;

import top.andnux.pay.core.IPayInfo;

public class UpPayInfo implements IPayInfo {

    private String tn;

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }
}