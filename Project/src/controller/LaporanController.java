package controller;

import java.util.ArrayList;

import android.content.Context;
import dao.DatabaseHelper;
import model.Laporan;

public class LaporanController {
	
	private LaporanDatabaseDummy listLaporan;
	private DatabaseHelper helper;
	public LaporanController(Context c) {
		listLaporan = new LaporanDatabaseDummy();
		helper = new DatabaseHelper(c);
	}
	
	public boolean addLaporan(String berat, String tinggi){
		return helper.insert(berat, tinggi);
	}
	
	public ArrayList<Laporan> getListLaporan(){
		return helper.getData();
	}
}
