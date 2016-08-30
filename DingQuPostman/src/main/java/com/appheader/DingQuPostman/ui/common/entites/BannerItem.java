package com.appheader.DingQuPostman.ui.common.entites;

import java.io.Serializable;

/**
 * Created by qinzhipeng on 16/4/test_two.
 */
public class BannerItem implements Serializable {
    private static final long serialVersionUID = 2528352798273436260L;

    private String picResId;
    private String destination;
    private String paramsStr;
    private int type;
    private String refId;
    // private String content;
    private String title;
    private int retailPrice;
    private int salesPrice;
    private int discount;
    private String id;
    private String showPic;

    public BannerItem() {

    }

    public BannerItem(String id, String title, String showPic, int retailPrice,
                      int salesPrice, int discount) {
        this.id = id;
        this.title = title;
        this.picResId = showPic;
        this.retailPrice = retailPrice;
        this.salesPrice = salesPrice;
        this.discount = discount;
    }

    public BannerItem(String picResId, String destination, String paramsStr,
                      int type, String refId) {
        this.picResId = picResId;
        this.destination = destination;
        this.paramsStr = paramsStr;
        this.type = type;
        this.refId = refId;
    }

    public String getPicResId() {
        return picResId;
    }

    public void setPicResId(String picResId) {
        this.picResId = picResId;
    }

	/*
	 * public String getContent() { return content; }
	 *
	 * public void setContent(String content) { this.content = content; }
	 */

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getParamsStr() {
        return paramsStr;
    }

    public void setParamsStr(String paramsStr) {
        this.paramsStr = paramsStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
    }

    public int getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(int salesPrice) {
        this.salesPrice = salesPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShowPic() {
        return showPic;
    }

    public void setShowPic(String showPic) {
        this.showPic = showPic;
    }


}
