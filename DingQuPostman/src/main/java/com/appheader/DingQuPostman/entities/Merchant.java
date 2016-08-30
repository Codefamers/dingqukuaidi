package com.appheader.DingQuPostman.entities;
import java.io.Serializable;
import java.util.List;

public class Merchant implements Serializable {

	private static final long serialVersionUID = 138142241104427291L;

	private String id;

	private String name;

	private List<String> telephones;

	private int hasMaintainService;

	private List<String> extraServices;

	private String address;
	

	private String picID;

	private int manualPrice;

	private int level;

	private int orderCount;

	private List<Double> location;

	private String distance;

	private int state;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPicID() {
		return picID;
	}

	public void setPicID(String picID) {
		this.picID = picID;
	}

	
	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getManualPrice() {
		return manualPrice;
	}

	public void setManualPrice(int manualPrice) {
		this.manualPrice = manualPrice;
	}

	

	public int getHasMaintainService() {
		return hasMaintainService;
	}

	public void setHasMaintainService(int hasMaintainService) {
		this.hasMaintainService = hasMaintainService;
	}

	

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public List<String> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<String> telephones) {
		this.telephones = telephones;
	}

	public List<String> getExtraServices() {
		return extraServices;
	}

	public void setExtraServices(List<String> extraServices) {
		this.extraServices = extraServices;
	}

	public List<Double> getLocation() {
		return location;
	}

	public void setLocation(List<Double> location) {
		this.location = location;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
