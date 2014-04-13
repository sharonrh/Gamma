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
	private static final String hewani = "hewani";
	private static final String seafood = "seafood";
	private static final String kacang = "kacang";
	private static final String terakhir = "terakhirDipilih";
	
	public DatabaseHandlerMakanan(Context context) {
		super(context, namaDB, null, versiDB);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String buatTabelMakanan = "CREATE TABLE " + tabelMakanan + "("
				+ namaMakanan + " TEXT PRIMARY KEY," + jlhKalori + " INTEGER,"
				+ jlhProtein + " REAL," + jlhLemak + " REAL," + jlhKarbo
				+ " REAL," + jlhKalsium + " REAL," + rating + " INTEGER,"
				+ persentase + " REAL," + jenis + " TEXT" + hewani + " INTEGER," + seafood + " INTEGER," + kacang + " INTEGER," + terakhir + " INTEGER" + ")";
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
		int h = 0, s = 0, k = 0;
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
		if (makanan.isHewani()) {
			h = 1;
		} 
		values.put(hewani, h);

		if (makanan.isSeafood()) {
			s = 1;
		} 
		values.put(seafood, s);
		
		if (makanan.isKacang()) {
			k = 1;
		} 
		values.put(kacang, k);
		values.put(terakhir, makanan.getTerakhir());

		// Inserting Row
		db.insert(tabelMakanan, null, values);
		db.close(); // Closing database connection
	}

	// Getting single makanan
	Makanan getMakanan(String nama) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelMakanan, new String[] { namaMakanan,
				jlhKalori, jlhProtein, jlhLemak, jlhKarbo, jlhKalsium, rating,
				persentase, jenis, hewani, seafood, kacang, terakhir }, namaMakanan + "=?",
				new String[] { String.valueOf(nama) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Makanan makanan = new Makanan(cursor.getString(0),
				Integer.parseInt(cursor.getString(1)),
				Double.parseDouble(cursor.getString(2)),
				Double.parseDouble(cursor.getString(3)),
				Double.parseDouble(cursor.getString(4)),
				Double.parseDouble(cursor.getString(5)),
				Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)), cursor.getString(8), 100);

		// retrieve data alegi
		int h = Integer.parseInt(cursor.getString(9));
		int s = Integer.parseInt(cursor.getString(10));
		int k = Integer.parseInt(cursor.getString(11));
		
		if (h == 1) {
			makanan.setHewani(true);
		}
		if (s == 1) {
			makanan.setSeafood(true);
		}
		if (k == 1) {
			makanan.setKacang(true);
		}
		makanan.setTerakhir(Integer.parseInt(cursor.getString(12)));

		// return makanan
		return makanan;
	}

	// Getting All Contacts
	public ArrayList<Makanan> getAllMakanan() {
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
				makanan.setKalori(Integer.parseInt(cursor.getString(1)));
				makanan.setProtein(Double.parseDouble(cursor.getString(2)));
				makanan.setKarbohidrat(Double.parseDouble(cursor.getString(3)));
				makanan.setLemak(Double.parseDouble(cursor.getString(4)));
				makanan.setKalsium(Double.parseDouble(cursor.getString(5)));
				makanan.setPersentase(Integer.parseInt(cursor.getString(6)));
				makanan.setRating(Integer.parseInt(cursor.getString(7)));
				makanan.setJenisMakanan(cursor.getString(8));

				// retrieve data alegi
				int h = Integer.parseInt(cursor.getString(9));
				int s = Integer.parseInt(cursor.getString(10));
				int k = Integer.parseInt(cursor.getString(11));
				
				if (h == 1) {
					makanan.setHewani(true);
				}
				if (s == 1) {
					makanan.setSeafood(true);
				}
				if (k == 1) {
					makanan.setKacang(true);
				}
				makanan.setTerakhir(Integer.parseInt(cursor.getString(12)));

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
		int h = 0, s = 0, k = 0;
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
		if (makanan.isHewani()) {
			h = 1;
		} 
		values.put(hewani, h);

		if (makanan.isSeafood()) {
			s = 1;
		} 
		values.put(seafood, s);
		
		if (makanan.isKacang()) {
			k = 1;
		} 
		values.put(kacang, k);
		values.put(terakhir, makanan.getTerakhir());
		
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

	// Baca data dari csv
	public ArrayList<Makanan> bacaFile() {
		ArrayList<Makanan> mk = new ArrayList<>();
		InputStreamReader is = new InputStreamReader(getAssets().open("data_makanan.csv"));

        BufferedReader reader = new BufferedReader(is);
        reader.readLine(); //baca header
        String line;
        
        while ((line = reader.readLine()) != null) {
        	String[] temp = line.split(",");
        	boolean isHewani = false, isSeafood = false, isKacang = false;
        	if (temp[9].charAt(0) == 'Y') {
        		isHewani = true;
        	}
        	if (temp[10].charAt(0) == 'Y') {
        		isSeafood = true;
        	}
        	if (temp[11].charAt(0) == 'Y') {
        		isKacang = true;
        	}
        	Makanan ma = new Makanan(temp[0], Integer.parseInt(temp[1]), Double.parseDouble(temp[2]), Double.parseDouble(temp[3]), Double.parseDouble(temp[4]), Double.parseDouble(temp[5]), Integer.parseInt(temp[6]), Integer.parseInt(temp[7]), temp[8], isHewani, isSeafood, isKacang, Integer.parseInt(temp[12]));
        	mk.add(ma);
        }

        return mk;
	}

}