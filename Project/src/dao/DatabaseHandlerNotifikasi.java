package dao;

import java.util.ArrayList;
import java.util.List;

import model.Notifikasi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//code source from here : http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

public class DatabaseHandlerNotifikasi extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int versiDB = 1;

	// Database Name
	private static final String namaDB = "gamma.db";
	private static final String KEY_ID = "id";

	// Nama tabel yang akan dibuat
	private static final String tabelNotifikasi = "notifikasi";

	// Kolom tabel notifikasi
	private static final String id = "id";
	private static final String nama = "nama";
	private static final String waktu = "waktu";
	private static final String pesan = "pesan";

	public DatabaseHandlerNotifikasi(Context context) {
		super(context, namaDB, null, versiDB);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String buatTabelNotifikasi = "CREATE TABLE " + tabelNotifikasi + "("
				+ id + " INTEGER PRIMARY KEY AUTOINCREMENT," + nama + " TEXT,"
				+ waktu + " INTEGER," + pesan + " TEXT" + ")";
		db.execSQL(buatTabelNotifikasi);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + tabelNotifikasi);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new notifikasi
	void tambahNotifikasi(Notifikasi notifikasi) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(nama, notifikasi.getNama());
		values.put(waktu, notifikasi.getWaktu());
		values.put(pesan, notifikasi.getPesan());

		// Inserting Row
		db.insert(tabelNotifikasi, null, values);
		db.close(); // Closing database connection
	}

	// Getting single notifikasi
	Notifikasi getNotifikasi(String nama) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelNotifikasi, new String[] { id, nama,
				waktu, pesan }, KEY_ID + "=?",
				new String[] { String.valueOf(nama) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Notifikasi notifikasi = new Notifikasi(cursor.getString(1),
				Long.parseLong(cursor.getString(2)), cursor.getString(3));

		// return notifikasi
		return notifikasi;
	}

	// Getting All Notifications
	public List<Notifikasi> getAllNotifikasi() {
		List<Notifikasi> notifikasiList = new ArrayList<Notifikasi>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + tabelNotifikasi;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Notifikasi notifikasi = new Notifikasi();
				notifikasi.setNama(cursor.getString(1));
				notifikasi.setWaktu(Long.parseLong(cursor.getString(2)));
				notifikasi.setPesan(cursor.getString(3));

				// Adding notifikasi to list
				notifikasiList.add(notifikasi);
			} while (cursor.moveToNext());
		}

		// return notifikasi list
		return notifikasiList;
	}

	// Deleting single notifikasi
	public void deleteContact(Notifikasi notifikasi) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tabelNotifikasi, KEY_ID + " = ?",
				new String[] { String.valueOf(nama) });
		db.close();
	}

	// Getting notifikasis Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + tabelNotifikasi;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}