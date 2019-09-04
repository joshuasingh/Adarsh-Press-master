package com.example.testproject;

import android.widget.Spinner;

public class Items {

    private String ItemName;
    private String Unit;
    private String SUnit;
    private String CostPrice;
    private String GST;
    private String TotalCP;
    private String WholesalePrice;
    private String RetailPrice;
    private String spnr1;
    private String spnr2;
    private String pUid;


    public Items() {
    }



    public Items(String itemName, String unit, String sUnit, String costPrice, String gst, String totalCP, String WholesalePrice, String RetailPrice, String pUid) {
        ItemName = itemName;
        Unit = unit;

        CostPrice = costPrice;
        GST= gst;
        TotalCP = totalCP;
        this.WholesalePrice = WholesalePrice;
        this.RetailPrice = RetailPrice;
    }

    public String getpUid() {
        return pUid;
    }

    public void setpUid(String pUid) {
        this.pUid = pUid;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }



    public String getCostPrice() {
        return CostPrice;
    }


    public void setCostPrice(String costPrice) {
        CostPrice = costPrice;
    }



    public String getTotalCP() {
        return TotalCP;
    }

    public void setTotalCP(String totalCP) {
        TotalCP = totalCP;
    }

    public String getWholesalePrice() {
        return WholesalePrice;
    }

    public void setWholesalePrice(String WholesalePrice) {
        this.WholesalePrice = WholesalePrice;
    }

    public String getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(String RetailPrice) {
        this.RetailPrice = RetailPrice;
    }

    public String getSUnit() {
        return SUnit;
    }

    public void setSUnit(String SUnit) {
        this.SUnit = SUnit;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }


}

