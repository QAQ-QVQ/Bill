package com.yu.bill.Model;

/**
 * CREATED BY DY ON 2020/1/14.
 * TIME BY 14:56.
 **/
public class BillBean {
    public BillBean(String daily, String food, String fashion, String payment, String trafficTrip, String housingProperty, String electronicEquipment, String movieEntertainment, String other) {
        Daily = daily;
        Food = food;
        Fashion = fashion;
        Payment = payment;
        TrafficTrip = trafficTrip;
        HousingProperty = housingProperty;
        ElectronicEquipment = electronicEquipment;
        MovieEntertainment = movieEntertainment;
        Other = other;
    }

    /**
     * 生活日用
     */
    String Daily;
    /**
     * 餐饮美食
     */
    String Food;
    /**
     * 服饰美容
     */
    String Fashion;
    /**
     * 日常缴费
     */
    String Payment;
    /**
     * 交通出行
     */
    String TrafficTrip;
    /**
     * 住房物业
     */
    String HousingProperty;
    /**
     * 电子设备
     */
    String ElectronicEquipment;
    /**
     * 电影娱乐
     */
    String MovieEntertainment;
    /**
     * 其他
     */
    String Other;

    public String getDaily() {
        return Daily;
    }

    public void setDaily(String daily) {
        Daily = daily;
    }

    public String getFood() {
        return Food;
    }

    public void setFood(String food) {
        Food = food;
    }

    public String getFashion() {
        return Fashion;
    }

    public void setFashion(String fashion) {
        Fashion = fashion;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getTrafficTrip() {
        return TrafficTrip;
    }

    public void setTrafficTrip(String trafficTrip) {
        TrafficTrip = trafficTrip;
    }

    public String getHousingProperty() {
        return HousingProperty;
    }

    public void setHousingProperty(String housingProperty) {
        HousingProperty = housingProperty;
    }

    public String getElectronicEquipment() {
        return ElectronicEquipment;
    }

    public void setElectronicEquipment(String electronicEquipment) {
        ElectronicEquipment = electronicEquipment;
    }

    public String getMovieEntertainment() {
        return MovieEntertainment;
    }

    public void setMovieEntertainment(String movieEntertainment) {
        MovieEntertainment = movieEntertainment;
    }

    public String getOther() {
        return Other;
    }

    public void setOther(String other) {
        Other = other;
    }


}
