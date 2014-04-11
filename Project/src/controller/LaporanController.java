package controller;

import java.util.ArrayList;

import android.content.Context;
import dao.DatabaseHelper;
import model.Laporan;

public class LaporanController {
	
	private DatabaseHelper helper;
	
	public LaporanController(Context c) {

		helper = new DatabaseHelper(c);
	}
	
	public boolean addLaporan(String berat, String tinggi){
		boolean cek = helper.insertLaporan(berat, tinggi);
			
		return cek;
	}
	
	public ArrayList<Laporan> getListLaporan(){
		return helper.getDataLaporan();
	}
}
