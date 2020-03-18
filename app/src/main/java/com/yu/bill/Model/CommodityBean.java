package com.yu.bill.Model;

/**
 * CREATED BY DY ON 2020/1/14.
 * TIME BY 15:56.
 * 商品
 **/
public class CommodityBean {
    /**
     * 商品种类
     */
    String CommodityType;
    /**
     * 商品名称
     */
    String CommodityName;
    /**
     * 购买时间
     */
    String CommodityTime;
    /**
     * 购买金额
     */
    String CommodityMoney;
    /**
     * 商品类型
     */
    boolean CommodityFlag;

    /**
     * @param commodityName 商品名称
     * @param commodityTime 购买时间
     * @param commodityMoney 购买金额
     * @param CommodityType 商品种类
     */
    public CommodityBean(String commodityName, String commodityTime, String commodityMoney,String CommodityType ) {
        this.CommodityName = commodityName;
        this.CommodityTime = commodityTime;
        this.CommodityMoney = commodityMoney;
        this.CommodityType = CommodityType;

    }

    public CommodityBean() {

    }

    public boolean isCommodityFlag() {
        return CommodityFlag;
    }

    public void setCommodityFlag(boolean commodityFlag) {
        CommodityFlag = commodityFlag;
    }

    public String getCommodityType() {
        return CommodityType;
    }

    public void setCommodityType(String commodityType) {
        CommodityType = commodityType;
    }

    public String getCommodityName() {
        return CommodityName;
    }

    public void setCommodityName(String commodityName) {
        CommodityName = commodityName;
    }

    public String getCommodityTime() {
        return CommodityTime;
    }

    public void setCommodityTime(String commodityTime) {
        CommodityTime = commodityTime;
    }

    public String getCommodityMoney() {
        return CommodityMoney;
    }

    public void setCommodityMoney(String commodityMoney) {
        CommodityMoney = commodityMoney;
    }

}
