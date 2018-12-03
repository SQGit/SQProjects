package com.sqindia.avcdemo;

/**
 * 
 * @author anfer
 * 
 */
public class Model {

	private String sNo;
	private String product;
	private String highlightStatus;
	private String treadSize;

	public Model(String sNo, String product, String highlightStatus, String treadSize) {
		this.sNo = sNo;
		this.product = product;
		this.highlightStatus = highlightStatus;
		this.treadSize = treadSize;
	}

	public Model(String sNo, String product) {
		this.sNo = sNo;
		this.product = product;
		this.highlightStatus = highlightStatus;
		this.treadSize = treadSize;
	}



	public String getsNo() {
		return sNo;
	}

	public String getProduct() {
		return product;
	}

	public String getHighlightStatus() {
		return highlightStatus;
	}

	public String getTreadSize() {
		return treadSize;
	}

}
