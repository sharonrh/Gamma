package model;

public class Artikel {
	private int id;
	private String nama;
	private String url;
	private String deskripsi;
	
	public Artikel(int id, String nama, String url, String deskripsi) {
		super();
		this.id = id;
		this.nama = nama;
		this.url = url;
		this.deskripsi = deskripsi;
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDeskripsi() {
		return deskripsi;
	}
	
	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	
}
