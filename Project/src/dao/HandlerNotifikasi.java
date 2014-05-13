package dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.Notifikasi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class HandlerNotifikasi extends DatabaseHandler {

	private static final String KEY_ID = "id";

	// Nama tabel yang akan dibuat
	private static final String tabelNotifikasi = "notifikasi";

	// Kolom tabel notifikasi
	private static final String id = "id";
	private static final String nama = "nama";
	private static final String waktu = "waktu";
	private static final String pesan = "pesan";

	public HandlerNotifikasi(Context context) {
		super(context);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations untuk tabel Notifikasi
	 */
	public boolean tambahNotifikasi(Notifikasi notifikasi) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(nama, notifikasi.getNama());
		values.put(waktu, notifikasi.getWaktu());
		values.put(pesan, notifikasi.getPesan());

		// Inserting Row
		boolean cek = db.insert(tabelNotifikasi, null, values) > 0;
		db.close();
		return cek;
	}

	// Getting single notifikasi
	Notifikasi getNotifikasi(String nama) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelNotifikasi, new String[] { id, nama,
				waktu, pesan }, KEY_ID + "=?",
				new String[] { String.valueOf(nama) }, null, null, null, null);

		Notifikasi notifikasi = null;
		if (cursor.moveToFirst()) {
			notifikasi = new Notifikasi(cursor.getString(1), cursor.getLong(2),
					cursor.getString(3), cursor.getInt(4) == 1 ? true : false);
		}
		cursor.close();
		db.close();

		return notifikasi;
	}

	// Getting All Notifications
	public List<Notifikasi> getAllNotifikasi() {
		List<Notifikasi> notifikasiList = new ArrayList<Notifikasi>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + tabelNotifikasi;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Notifikasi notifikasi = new Notifikasi();
				notifikasi.setNama(cursor.getString(1));
				notifikasi.setWaktu(cursor.getLong(2));
				notifikasi.setPesan(cursor.getString(3));

				// Adding notifikasi to list
				notifikasiList.add(notifikasi);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return notifikasiList;
	}

	// Deleting single notifikasi
	public void deleteNotifikasi(Notifikasi notifikasi) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tabelNotifikasi, KEY_ID + " = ?",
				new String[] { String.valueOf(nama) });
		db.close();
	}

	public boolean updateNotif(String namaNotif, long time, boolean selected) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues args = new ContentValues();
		String str = "" + time;
		args.put(waktu, str);
		
		if(selected){
			args.put("selected", 1);
		}
		else {
			args.put("selected", 0);
		}
		
		boolean b = db.update(tabelNotifikasi, args, nama + "=" + "'"
				+ namaNotif + "'", null) > 0;
		db.close();

		return b;
	}
	public boolean updateNotif(String namaNotif, long time) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues args = new ContentValues();
		String str = "" + time;
		args.put(waktu, str);
		
		
		
		boolean b = db.update(tabelNotifikasi, args, nama + "=" + "'"
				+ namaNotif + "'", null) > 0;
		db.close();

		return b;
	}

	// Getting notifikasis Count
	public int getNotifikasiCount() {
		String countQuery = "SELECT  * FROM " + tabelNotifikasi;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		db.close();

		return count;
	}
}
