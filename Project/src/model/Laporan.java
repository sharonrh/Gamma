package model;

public class Laporan {
	
	private String tinggiBadan;
	private String beratBadan;
	
	public Laporan(String tinggiBadan, String beratBadan) {
		this.tinggiBadan = tinggiBadan;
		this.beratBadan = beratBadan;
	}
	public String getTinggiBadan() {
		return tinggiBadan;
	}
	public void setTinggiBadan(String tinggiBadan) {
		this.tinggiBadan = tinggiBadan;
	}
	public String getBeratBadan() {
		return beratBadan;
	}
	public void setBeratBadan(String beratBadan) {
		this.beratBadan = beratBadan;
	}
	
	public String toString(){
		return tinggiBadan;
	}
	
}
