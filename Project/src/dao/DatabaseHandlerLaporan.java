package dao;

import java.util.ArrayList;

import model.Laporan;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//code source from here : http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

public class DatabaseHandlerLaporan extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int versiDB = 1;

	// Database Name
	private static final String namaDB = "gamma.db";
	private static final String KEY_ID = "id";
	
	// Nama tabel yang akan dibuat
	private static final String tabelLaporan = "laporan";

	// Kolom tabel laporan
	private static final String id = "ID";
	private static final String waktu = "waktu";
	private static final String berat = "berat";
	private static final String tinggi = "tinggi";


	public DatabaseHandlerLaporan(Context context) {
		super(context, namaDB, null, versiDB);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String buatTabelLaporan = "CREATE TABLE " + tabelLaporan + "("
				+ id + " INTEGER PRIMARY KEY AUTOINCREMENT," + waktu + " INTEGER,"
				+ berat + " REAL," + tinggi + " REAL" + ")";
		db.execSQL(buatTabelLaporan);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + tabelLaporan);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new laporan
	void tambahLaporan(Laporan laporan) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(waktu, laporan.getWaktu());
		values.put(berat, laporan.getBeratBadan());
		values.put(tinggi, laporan.getTinggiBadan());

		// Inserting Row
		db.insert(tabelLaporan, null, values);
		db.close(); // Closing database connection
	}

	// Getting single laporan
	Laporan getLaporan(String nama) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelLaporan, new String[] { id, waktu, berat, tinggi }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Laporan laporan = new Laporan(Long.parseLong(cursor.getString(1)),
				Double.parseDouble(cursor.getString(2)),
				Double.parseDouble(cursor.getString(3));

		// return laporan
		return laporan;
	}

	// Getting All Contacts
	public List<Laporan> getAllLaporan() {
		List<Laporan> laporanList = new ArrayList<Laporan>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + tabelLaporan;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Laporan laporan = new Laporan();
				laporan.setWaktu(Long.parseLong(cursor.getString(1));
				laporan.setBeratBadan(Double.parseDouble(cursor.getString(2)));
				laporan.setTinggiBadan(Double.parseDouble(cursor.getString(3);

				// Adding laporan to list
				laporanList.add(laporan);
			} while (cursor.moveToNext());
		}

		// return laporan list
		return laporanList;
	}

	// Updating single laporan
	public int updateContact(Laporan laporan) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(waktu, laporan.getWaktu());
		values.put(berat, laporan.getBeratBadan());
		values.put(tinggi, laporan.getTinggiBadan());
		
		// updating row
		return db.update(tabelLaporan, values, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	// Deleting single laporan
	public void deleteContact(Laporan laporan) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tabelLaporan, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	// Getting laporans Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + tabelLaporan;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}