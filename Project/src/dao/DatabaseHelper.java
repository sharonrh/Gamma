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

		db.execSQL("CREATE TABLE laporan (_id INTEGER PRIMARY KEY AUTOINCREMENT, DOUBLE berat, DOUBLE tinggi);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public Cursor getAll() {
		return (getReadableDatabase().rawQuery("SELECT * FROM laporan", null));
	}
	
	public boolean insert(String berat, String tinggi) {
		ContentValues cv=new ContentValues();
		double brt = Double.parseDouble(berat);
		double tgi = Double.parseDouble(tinggi);
		
		cv.put("berat", brt);
		cv.put("tinggi", tgi);
		
		return getWritableDatabase().insert("laporan", "berat", cv)>0;
	}
	
	public ArrayList<Laporan> getData() {
	    // TODO Auto-generated method stub
	    String[]columns=new String[]{"berat", "tinggi"};
	    Cursor c =getReadableDatabase().query("laporan", columns, null, null, null, null, null);
	    ArrayList<Laporan> result = new ArrayList<Laporan>();
	    
	    double iBerat = c.getColumnIndex("berat");
	    double iTinggi= c.getColumnIndex("tinggi");

	    for(c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
	    	Laporan l = new Laporan(""+iBerat ,""+iTinggi);	
	    	result.add(l);
	    }
	    return result;
	}
}
