package com.bhochhi.rules.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {

	private String client;
	private String competitor;
	private String productCategory;

	private String location;

	private String action1;
	private String action2;
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getCompetitor() {
		return competitor;
	}
	public void setCompetitor(String competitor) {
		this.competitor = competitor;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAction1() {
		return action1;
	}
	public void setAction1(String action1) {
		this.action1 = action1;
	}
	public String getAction2() {
		return action2;
	}
	public void setAction2(String action2) {
		this.action2 = action2;
	}


}