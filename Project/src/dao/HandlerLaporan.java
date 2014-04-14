package dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.Laporan;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HandlerLaporan extends DatabaseHandler{

	private static HandlerLaporan sInstance;
	private static final String KEY_ID = "id";

	// Nama tabel yang akan dibuat
	private static final String tabelLaporan = "laporan";

	// Kolom tabel laporan
	private static final String id = "ID";
	private static final String waktu = "waktu";
	private static final String berat = "berat";
	private static final String tinggi = "tinggi";

	public static HandlerLaporan getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new HandlerLaporan(context);
		}
		return sInstance;
	}

	public HandlerLaporan(Context context) {
		super(context);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 * untuk tabel Laporan
	 */

	public boolean tambahLaporan(long waktuInput, String beratInput,
			String tinggiInput) {
		SQLiteDatabase db = this.getWritableDatabase();

		System.out.println("size: " + getLaporanCount());
		ContentValues values = new ContentValues();
		values.put(waktu, waktuInput);
		values.put(berat, beratInput);
		values.put(tinggi, tinggiInput);

		Cursor cursor = db.rawQuery("SELECT waktu  FROM " + tabelLaporan, null);

		// pastiin dia ga kosong
		if (cursor.moveToLast()) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(waktuInput);
			Calendar cl = Calendar.getInstance();
			c.setTimeInMillis(Long.parseLong(cursor.getString(0)));
			// kalo tanggalnya sama, update data yang lama
			if (c.get(Calendar.YEAR) == cl.get(Calendar.YEAR)
					&& c.get(Calendar.MONTH) == cl.get(Calendar.MONTH)
					&& c.get(Calendar.DATE) == cl.get(Calendar.DATE)) {
				return db.update(tabelLaporan, values, KEY_ID + " = ?",
						new String[] { String.valueOf(cursor.getCount()) }) > 0;
			}
		}
		return db.insert(tabelLaporan, null, values) > 0;
	}

	// Getting single laporan
	public Laporan getLaporan(String nama) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelLaporan, new String[] { id, waktu, berat,
				tinggi }, KEY_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Laporan laporan = new Laporan(Long.parseLong(cursor.getString(1)),
				Double.parseDouble(cursor.getString(2)),
				Double.parseDouble(cursor.getString(3)));

		return laporan;
	}

	// Getting All Laporan
	public List<Laporan> getAllLaporan() {
		List<Laporan> laporanList = new ArrayList<Laporan>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + tabelLaporan;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Laporan laporan = new Laporan();
				laporan.setWaktu(Long.parseLong(cursor.getString(1)));
				laporan.setBeratBadan(Double.parseDouble(cursor.getString(2)));
				laporan.setTinggiBadan(Double.parseDouble(cursor.getString(3)));

				// Adding laporan to list
				laporanList.add(laporan);
			} while (cursor.moveToNext());
		}

		// return laporan list
		return laporanList;
	}

	// Deleting single laporan
	public void deleteLaporan(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tabelLaporan, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	// Getting laporan count
	public int getLaporanCount() {
		String countQuery = "SELECT  * FROM " + tabelLaporan;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		// cursor.close();

		// return count
		return cursor.getCount();
	}

}
