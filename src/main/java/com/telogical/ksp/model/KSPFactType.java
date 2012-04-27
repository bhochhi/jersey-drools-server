package com.telogical.ksp.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class KSPFactType {

	private String client;
	private String competitor;
	private String productCategory;

	private String competitorProductSubCategory;
	private String competitorProductType;
	private String competitorAttribute1;
	private String competitorAttribute2;

	private String clientProductSubCategory;
	private String clientProductType;
	private String clientAttribute1;
	private String clientAttribute2;

	private String locationType;
	private String location;

	private String kspTitle;
	private String kspBody;
	private String kspBullets;
	private String kspPriority;

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getKspBody() {
		return kspBody;
	}

	public void setKspBody(String kspBody) {
		this.kspBody = kspBody;
	}

	public String getCompetitorProductType() {
		return competitorProductType;
	}

	public void setCompetitorProductType(String competitorProductType) {
		this.competitorProductType = competitorProductType;
	}

	public String getClientAttribute2() {
		return clientAttribute2;
	}

	public void setClientAttribute2(String clientAttribute2) {
		this.clientAttribute2 = clientAttribute2;
	}

	public String getClientAttribute1() {
		return clientAttribute1;
	}

	public void setClientAttribute1(String clientAttribute1) {
		this.clientAttribute1 = clientAttribute1;
	}

	public String getCompetitorProductSubCategory() {
		return competitorProductSubCategory;
	}

	public void setCompetitorProductSubCategory(
			String competitorProductSubCategory) {
		this.competitorProductSubCategory = competitorProductSubCategory;
	}

	public String getKspTitle() {
		return kspTitle;
	}

	public void setKspTitle(String kspTitle) {
		this.kspTitle = kspTitle;
	}

	public String getKspPriority() {
		return kspPriority;
	}

	public void setKspPriority(String kspPriority) {
		this.kspPriority = kspPriority;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getCompetitor() {
		return competitor;
	}

	public void setCompetitor(String competitor) {
		this.competitor = competitor;
	}

	public String getCompetitorAttribute2() {
		return competitorAttribute2;
	}

	public void setCompetitorAttribute2(String competitorAttribute2) {
		this.competitorAttribute2 = competitorAttribute2;
	}

	public String getKspBullets() {
		return kspBullets;
	}

	public void setKspBullets(String kspBullets) {
		this.kspBullets = kspBullets;
	}

	public String getCompetitorAttribute1() {
		return competitorAttribute1;
	}

	public void setCompetitorAttribute1(String competitorAttribute1) {
		this.competitorAttribute1 = competitorAttribute1;
	}

	public String getClientProductSubCategory() {
		return clientProductSubCategory;
	}

	public void setClientProductSubCategory(String clientProductSubCategory) {
		this.clientProductSubCategory = clientProductSubCategory;
	}

	public String getClientProductType() {
		return clientProductType;
	}

	public void setClientProductType(String clientProductType) {
		this.clientProductType = clientProductType;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

}