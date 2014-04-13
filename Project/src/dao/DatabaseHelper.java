package dao;

import java.util.ArrayList;

import model.Laporan;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "gamma.db"; // entar sesuain
	private static final int SCHEMA_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE laporan (_id INTEGER PRIMARY KEY AUTOINCREMENT,berat REAL, tinggi REAL);");
		db.execSQL("CREATE TABLE profil (_id INTEGER PRIMARY KEY AUTOINCREMENT,nama TEXT, tinggi REAL, berat REAL, jenisKelamin TEXT, vegetarian INTEGER, alergiTelur INTEGER, alergiMakananLaut INTEGER, alergiKacang INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public Cursor getAll() {
		return (getReadableDatabase().rawQuery("SELECT * FROM laporan", null));
	}

	public boolean insertLaporan(String berat, String tinggi) {
		ContentValues cv = new ContentValues();

		System.out.println(berat + ", " + tinggi);
		double brt = Double.parseDouble(berat);
		double tgi = Double.parseDouble(tinggi);

		System.out.println(brt + ", " + tgi);
		cv.put("berat", brt);
		cv.put("tinggi", tgi);

		return getWritableDatabase().insert("laporan", "berat", cv) > 0;
	}

	public boolean insertProfil(String nama, String tinggi, String berat,
			String jenisKelamin, int vegetarian, int alergiTelur,
			int alergiMakananLaut, int alergiKacang) {
		ContentValues cv = new ContentValues();

		System.out.println(berat + ", " + tinggi);
		double brt = Double.parseDouble(berat);
		double tgi = Double.parseDouble(tinggi);

		System.out.println(brt + ", " + tgi);
		cv.put("nama", nama);
		cv.put("tinggi", tgi);
		cv.put("berat", brt);
		cv.put("jenisKelamin", jenisKelamin);
		cv.put("vegetarian", vegetarian);
		cv.put("alergiTelur", alergiTelur);
		cv.put("alergiMakananLaut", alergiMakananLaut);
		cv.put("alergiKacang", alergiKacang);

		return getWritableDatabase().insert("profil", "nama", cv) > 0;
	}

	public ArrayList<Laporan> getDataLaporan() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { "berat", "tinggi" };

		Cursor c = getReadableDatabase().query("laporan", columns, null, null,
				null, null, null);
		ArrayList<Laporan> result = new ArrayList<Laporan>();

		int iBerat = c.getColumnIndex("berat");
		int iTinggi = c.getColumnIndex("tinggi");

		System.out.println("i " + iBerat);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Laporan l = new Laporan(iBerat, iTinggi, 0);
			result.add(l);
		}
		return result;
	}

	public ArrayList<String> getDataProfil() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { "nama", "tinggi", "berat",
				"jenisKelamin", "vegetarian", "alergiTelur",
				"alergiMakananLaut", "alergiKacang" };

		Cursor c = getReadableDatabase().query("profil", columns, null, null,
				null, null, null);
		ArrayList<String> result = new ArrayList<String>();

		int iNama = c.getColumnIndex("nama");
		int iTinggi = c.getColumnIndex("tinggi");
		int iBerat = c.getColumnIndex("berat");
		int iJenisKelamin = c.getColumnIndex("jenisKelamin");
		int iVegetarian = c.getColumnIndex("vegetarian");
		int iAlergiTelur = c.getColumnIndex("alergiTelur");
		int iAlergiMakananLaut = c.getColumnIndex("alergiMakananLaut");
		int iAlergiKacang = c.getColumnIndex("alergiKacang");

		System.out.println("i " + iBerat);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result.add(c.getString(iNama) + "," + c.getString(iTinggi) + ","
					+ c.getString(iBerat) + "," + c.getString(iJenisKelamin)
					+ "," + c.getString(iVegetarian) + ","
					+ c.getString(iAlergiTelur) + ","
					+ c.getString(iAlergiMakananLaut) + ","
					+ c.getString(iAlergiKacang));
		}
		return result;
	}

}
