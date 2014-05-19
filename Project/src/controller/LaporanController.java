package controller;

import java.util.List;

import dao.HandlerAchievement;
import model.Achievement;
import model.Laporan;
import model.Pengguna;
import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import dao.HandlerLaporan;

public class LaporanController {

	private HandlerLaporan db;
    private ProfilController profilController;
    private AchievementController achievementController;
    private Context context;
	private int sisaHari = 0;

	public LaporanController(Context c) {
		db = HandlerLaporan.getInstance(c);
        profilController = new ProfilController(c);
        achievementController = new AchievementController(c);
        context = c;
	}

	public boolean addLaporan(String berat, String tinggi) {
		Time t = new Time();
		t.setToNow();
		long l = t.toMillis(false);
		updateAchievement(berat);
		return db.tambahLaporan(l, berat, tinggi);
	}

    public void updateAchievement(String berat) {
        double beratTarget = profilController.getProfil().getTarget();
        double beratAwal = profilController.getProfil().getBerat();
        double beratSekarang = Double.parseDouble(berat);
        int progress = (int) (((beratSekarang - beratAwal) / (beratTarget - beratAwal)) * 100);
        Log.i("Progress ", progress + "");
        if (progress < 0) {
            progress = 0;
        }
        // retrieve achievement
        Achievement starter = achievementController.getHandler().getAchievement("Starter");
        Achievement halfway = achievementController.getHandler().getAchievement("Halfway");
        Achievement finisher = achievementController.getHandler().getAchievement("Finisher");

        // update nilai progress
        boolean cek1 = starter.isGet();
        boolean cek2 = halfway.isGet();
        boolean cek3 = finisher.isGet();

        starter.setProgress(progress);
        halfway.setProgress(progress);
        finisher.setProgress(progress);

        // simpan kembali ke db
        achievementController.getHandler().updateAchievement(starter);
        achievementController.getHandler().updateAchievement(halfway);
        achievementController.getHandler().updateAchievement(finisher);

        // cek kembali untuk menampilkan toast
        if (starter.isGet() == true && cek1 == false) {
            Toast.makeText(context, "Selamat! Anda telah mendapat achievement Starter!", Toast.LENGTH_SHORT).show();
        }
        if (halfway.isGet() == true && cek2 == false) {
            Toast.makeText(context, "Selamat! Anda telah mendapat achievement Halfway!", Toast.LENGTH_SHORT).show();
        }
        if (finisher.isGet() == true && cek3 == false) {
            Toast.makeText(context, "Selamat! Anda telah mendapat achievement Finisher!", Toast.LENGTH_SHORT).show();
        }
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
		
		//ambil waktu start
		Pengguna pengguna = profilController.getProfil();
		
		System.out.println(laporanTerakhir);
		if(laporanTerakhir != null || pengguna != null){
			Long end = laporanTerakhir.getWaktu();
			Long begin = pengguna.getStartTime();
			
			Long diff = end - begin;
			long seconds = diff / 1000;
		    long minutes = seconds / 60;
		    long hours = minutes / 60;
		    
		    System.out.println("jam " + hours);
		    long days = hours / 24;
		    
		    return (int) days;
		}
		else {
			return 0;
		}
		
	}
	
	public boolean cekDurasiIsiLaporan(){
		
		Laporan laporanPertama = getLaporanPertama();
		
		if(laporanPertama != null){
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
		else {
			return true;
		}
		
	}

	public int getSisaHari() {
		return sisaHari;
	}

	public void setSisaHari(int sisaHari) {
		this.sisaHari = sisaHari;
	}
	
}
