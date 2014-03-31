package model;

public class Pengguna {

	private int id;
	private String nama;
	private int umur;
	private int berat;
	private int tinggi;
	private int target;
	private char gender;
	private boolean isAlergiKacang;
	private boolean isAlergiTelur;
	private boolean isAlergiSeafood;
	private boolean isVegetarian;
	
	public Pengguna(int id, String nama, int umur, int berat, int tinggi,
			int target, char gender, boolean isAlergiKacang,
			boolean isAlergiTelur, boolean isAlergiSeafood, boolean isVegetarian) {
		super();
		this.id = id;
		this.nama = nama;
		this.umur = umur;
		this.berat = berat;
		this.tinggi = tinggi;
		this.target = target;
		this.gender = gender;
		this.isAlergiKacang = isAlergiKacang;
		this.isAlergiTelur = isAlergiTelur;
		this.isAlergiSeafood = isAlergiSeafood;
		this.isVegetarian = isVegetarian;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getBerat() {
		return berat;
	}

	public void setBerat(int berat) {
		this.berat = berat;
	}

	public int getTinggi() {
		return tinggi;
	}

	public void setTinggi(int tinggi) {
		this.tinggi = tinggi;
	}

	public int getTarget() {
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

	public boolean isAlergiKacang() {
		return isAlergiKacang;
	}

	public void setAlergiKacang(boolean isAlergiKacang) {
		this.isAlergiKacang = isAlergiKacang;
	}

	public boolean isAlergiTelur() {
		return isAlergiTelur;
	}

	public void setAlergiTelur(boolean isAlergiTelur) {
		this.isAlergiTelur = isAlergiTelur;
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
