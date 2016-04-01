package com.bestseller.poi.entity;

public class TrafficSource {
	private String shopCode;
	private String dayId;
	private String itsdNum;
	private String itsd;
	private String trafficSourse;
	private String tsDetail;
	private Long uv;
	private Long pv;
	private Integer bookMark;
	private Integer sCart;
	private Double lossRate;
	private Double coValue;
	private Long ccQty;
	private Double salesValue;
	private Integer pcQty;
	public TrafficSource() {
		super();
	}
	public TrafficSource(String shopCode, String dayId, String itsdNum,
			String itsd, String trafficSourse, String tsDetail,
			Long uv, Long pv, Integer bookMark,
			Integer sCart, Double lossRate, Double coValue, Long ccQty,
			Double salesValue, Integer pcQty) {
		super();
		this.shopCode = shopCode;
		this.dayId = dayId;
		this.itsdNum = itsdNum;
		this.itsd = itsd;
		this.trafficSourse = trafficSourse;
		this.tsDetail = tsDetail;
		this.uv = uv;
		this.pv = pv;
		this.bookMark = bookMark;
		this.sCart = sCart;
		this.lossRate = lossRate;
		this.coValue = coValue;
		this.ccQty = ccQty;
		this.salesValue = salesValue;
		this.pcQty = pcQty;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getDayId() {
		return dayId;
	}
	public void setDayId(String dayId) {
		this.dayId = dayId;
	}
	public String getItsdNum() {
		return itsdNum;
	}
	public void setItsdNum(String itsdNum) {
		this.itsdNum = itsdNum;
	}
	public String getItsd() {
		return itsd;
	}
	public void setItsd(String itsd) {
		this.itsd = itsd;
	}
	public String getTrafficSourse() {
		return trafficSourse;
	}
	public void setTrafficSourse(String trafficSourse) {
		this.trafficSourse = trafficSourse;
	}
	public String getTsDetail() {
		return tsDetail;
	}
	public void setTsDetail(String tsDetail) {
		this.tsDetail = tsDetail;
	}
	public Long getUv() {
		return uv;
	}
	public void setUv(Long uv) {
		this.uv = uv;
	}
	public Long getPv() {
		return pv;
	}
	public void setPv(Long pv) {
		this.pv = pv;
	}
	public Integer getBookMark() {
		return bookMark;
	}
	public void setBookMark(Integer bookMark) {
		this.bookMark = bookMark;
	}
	public Integer getsCart() {
		return sCart;
	}
	public void setsCart(Integer sCart) {
		this.sCart = sCart;
	}
	public Double getLossRate() {
		return lossRate;
	}
	public void setLossRate(Double lossRate) {
		this.lossRate = lossRate;
	}
	public Double getCoValue() {
		return coValue;
	}
	public void setCoValue(Double coValue) {
		this.coValue = coValue;
	}
	public Long getCcQty() {
		return ccQty;
	}
	public void setCcQty(Long ccQty) {
		this.ccQty = ccQty;
	}
	public Double getSalesValue() {
		return salesValue;
	}
	public void setSalesValue(Double salesValue) {
		this.salesValue = salesValue;
	}
	public Integer getPcQty() {
		return pcQty;
	}
	public void setPcQty(Integer pcQty) {
		this.pcQty = pcQty;
	}
	
	
}
