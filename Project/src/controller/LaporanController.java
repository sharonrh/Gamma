package controller;

import java.util.List;

import model.Laporan;
import android.content.Context;
import android.text.format.Time;
import dao.HandlerLaporan;

public class LaporanController {

	private HandlerLaporan db;
	private int sisaHari = 0;

	public LaporanController(Context c) {
		db = HandlerLaporan.getInstance(c);
	}

	public boolean addLaporan(String berat, String tinggi) {
		Time t = new Time();
		t.setToNow();
		long l = t.toMillis(false);
		return db.tambahLaporan(l, berat, tinggi);
	}

	public List<Laporan> getListLaporan() {
		return db.getAllLaporan();
	}
	
	public Laporan getLaporanTerbaru() {
		return db.getLaporanTerbaru();	
	}
	
	public Laporan getLaporanPertama(){
		return db.getLaporanPertama();	
	}
	
	public int getDurasiHari(){
		
		Laporan laporanTerakhir = getLaporanTerbaru();
		Laporan laporanPertama = getLaporanPertama();
		
		Long end = laporanTerakhir.getWaktu();
		Long begin = laporanPertama.getWaktu();
		
		Long diff = end - begin;
		long seconds = diff / 1000;
	    long minutes = seconds / 60;
	    long hours = minutes / 60;
	    
	    System.out.println("jam " + hours);
	    long days = hours / 24;
	    
	    return (int) days;
	}
	
	public boolean cekDurasiIsiLaporan(){
		
		Laporan laporanPertama = getLaporanPertama();
		
		Long end = System.currentTimeMillis();
		Long begin = laporanPertama.getWaktu();
		
		Long diff = end - begin;
		long seconds = diff / 1000;
	    long minutes = seconds / 60;
	    long hours = minutes / 60;
	    
	    System.out.println("jam " + hours);
	    long days = hours / 24;
	    System.out.println("hari " + days);
	    setSisaHari(7 - (int)days);
	    return days > 7;
	}

	public int getSisaHari() {
		return sisaHari;
	}

	public void setSisaHari(int sisaHari) {
		this.sisaHari = sisaHari;
	}
	
}
