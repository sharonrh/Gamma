package controller;

import java.util.ArrayList;

import model.Laporan;

public class LaporanDatabaseDummy {
	
	private ArrayList<Laporan> list;

	public LaporanDatabaseDummy() {

		this.list = new ArrayList<Laporan>();
	}

	public ArrayList<Laporan> getList() {
		return list;
	}
	
	public boolean addLaporan(Laporan laporan){
		return list.add(laporan);
	}
}
