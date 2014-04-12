package dao;

import java.util.ArrayList;
import java.util.List;

import model.Makanan;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//code source from here : http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

public class DatabaseHandlerMakanan extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int versiDB = 1;

	// Database Name
	private static final String namaDB = "gamma.db";
	private static final String KEY_ID = "id";
	
	// Nama tabel yang akan dibuat
	private static final String tabelMakanan = "makanan";

	// Kolom tabel makanan
	private static final String namaMakanan = "namaMakanan";
	private static final String jlhKalori = "kalori";
	private static final String jlhProtein = "protein";
	private static final String jlhLemak = "lemak";
	private static final String jlhKarbo = "karbohidrat";
	private static final String jlhKalsium = "kalsium";
	private static final String persentase = "persentase";
	private static final String rating = "rating";
	private static final String jenis = "jenis";

	public DatabaseHandlerMakanan(Context context) {
		super(context, namaDB, null, versiDB);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String buatTabelMakanan = "CREATE TABLE " + tabelMakanan + "("
				+ namaMakanan + " TEXT PRIMARY KEY," + jlhKalori + " REAL,"
				+ jlhProtein + " REAL," + jlhLemak + " REAL," + jlhKarbo
				+ " REAL," + jlhKalsium + " REAL," + rating + " INTEGER,"
				+ persentase + " REAL," + jenis + " TEXT" + ")";
		db.execSQL(buatTabelMakanan);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + tabelMakanan);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new makanan
	void tambahMakanan(Makanan makanan) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(namaMakanan, makanan.getNama());
		values.put(jlhKalori, makanan.getKalori());
		values.put(jlhProtein, makanan.getProtein());
		values.put(jlhLemak, makanan.getLemak());
		values.put(jlhKarbo, makanan.getKarbohidrat());
		values.put(jlhKalsium, makanan.getKalsium());
		values.put(rating, makanan.getRating());
		values.put(persentase, makanan.getPersentase());
		values.put(jenis, makanan.getJenisMakanan());

		// Inserting Row
		db.insert(tabelMakanan, null, values);
		db.close(); // Closing database connection
	}

	// Getting single makanan
	Makanan getMakanan(String nama) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelMakanan, new String[] { namaMakanan,
				jlhKalori, jlhProtein, jlhLemak, jlhKarbo, jlhKalsium, rating,
				persentase, jenis }, namaMakanan + "=?",
				new String[] { String.valueOf(nama) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Makanan makanan = new Makanan(cursor.getString(0),
				Double.parseDouble(cursor.getString(1)),
				Double.parseDouble(cursor.getString(2)),
				Double.parseDouble(cursor.getString(3)),
				Double.parseDouble(cursor.getString(4)),
				Double.parseDouble(cursor.getString(5)),
				Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor
						.getString(7)), cursor.getString(8), 0);
		// return makanan
		return makanan;
	}

	// Getting All Contacts
	public List<Makanan> getAllMakanan() {
		List<Makanan> makananList = new ArrayList<Makanan>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + tabelMakanan;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(Double.parseDouble(cursor.getString(1)));
				makanan.setProtein(Double.parseDouble(cursor.getString(2)));
				makanan.setKarbohidrat(Double.parseDouble(cursor.getString(3)));
				makanan.setLemak(Double.parseDouble(cursor.getString(4)));
				makanan.setKalsium(Double.parseDouble(cursor.getString(5)));
				makanan.setPersentase(Integer.parseInt(cursor.getString(6)));
				makanan.setRating(Integer.parseInt(cursor.getString(7)));
				makanan.setJenisMakanan(cursor.getString(8));
				// Adding makanan to list
				makananList.add(makanan);
			} while (cursor.moveToNext());
		}

		// return makanan list
		return makananList;
	}

	// Updating single makanan
	public int updateContact(Makanan makanan) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(namaMakanan, makanan.getNama());
		values.put(jlhKalori, makanan.getKalori());
		values.put(jlhProtein, makanan.getProtein());
		values.put(jlhLemak, makanan.getLemak());
		values.put(jlhKarbo, makanan.getKarbohidrat());
		values.put(jlhKalsium, makanan.getKalsium());
		values.put(rating, makanan.getRating());
		values.put(persentase, makanan.getPersentase());
		values.put(jenis, makanan.getJenisMakanan());

		// updating row
		return db.update(tabelMakanan, values, KEY_ID + " = ?",
				new String[] { String.valueOf(makanan.getNama()) });
	}

	// Deleting single makanan
	public void deleteContact(Makanan makanan) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tabelMakanan, KEY_ID + " = ?",
				new String[] { String.valueOf(makanan.getNama()) });
		db.close();
	}

	// Getting makanans Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + tabelMakanan;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}