package Kurs;

public class Rates {
	private String Currency;
	private String Code;
	private double Mid;
	private double Bid;
	private double Ask;
	
	public String getCurrency() {
		return Currency;
	}
	public void setCurrency(String currency) {
		Currency = currency;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public double getMid() {
		return Mid;
	}
	public void setMid(double mid) {
		Mid = mid;
	}
	public double getBid() {
		return Bid;
	}
	public void setBid(double bid) {
		Bid = bid;
	}
	public double getAsk() {
		return Ask;
	}
	public void setAsk(double ask) {
		Ask = ask;
	}

}
