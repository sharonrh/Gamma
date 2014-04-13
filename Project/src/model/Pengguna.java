package model;
import java.util.ArrayList;

public class Pengguna {

	private String nama;
	private int umur;
	private double berat;
	private double tinggi;
	private double target;
	private char gender;
	private int gayaHidup; //1 - ringan, 2 - sedang, dst
	private boolean isAlergiKacang;
	private boolean isAlergiSeafood;
	private boolean isVegetarian;
	
	public Pengguna(String nama, int umur, double berat, double tinggi,
			double target, char gender, int gayaHidup, boolean isAlergiKacang,
			boolean isAlergiSeafood, boolean isVegetarian) {
		this.nama = nama;
		this.umur = umur;
		this.berat = berat;
		this.tinggi = tinggi;
		this.target = target;
		this.gender = gender;
		this.gayaHidup = gayaHidup;
		this.isAlergiKacang = isAlergiKacang;
		this.isAlergiSeafood = isAlergiSeafood;
		this.isVegetarian = isVegetarian;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public int getUmur() {
		return umur;
	}

	public void setUmur(int umur) {
		this.umur = umur;
	}

	public double getBerat() {
		return berat;
	}

	public void setBerat(int berat) {
		this.berat = berat;
	}

	public double getTinggi() {
		return tinggi;
	}

	public void setTinggi(int tinggi) {
		this.tinggi = tinggi;
	}

	public double getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public int getGayaHidup() {
		return gayaHidup;
	}

	public void setGayaHidup(int gayaHidup) {
		this.gayaHidup = gayaHidup;
	}

	public boolean isAlergiKacang() {
		return isAlergiKacang;
	}

	public void setAlergiKacang(boolean isAlergiKacang) {
		this.isAlergiKacang = isAlergiKacang;
	}


	public boolean isAlergiSeafood() {
		return isAlergiSeafood;
	}

	public void setAlergiSeafood(boolean isAlergiSeafood) {
		this.isAlergiSeafood = isAlergiSeafood;
	}

	public boolean isVegetarian() {
		return isVegetarian;
	}

	public void setVegetarian(boolean isVegetarian) {
		this.isVegetarian = isVegetarian;
	}

}
