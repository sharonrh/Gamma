package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MakananHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "gamma.db"; // entar sesuain
	private static final int SCHEMA_VERSION = 1;

	public MakananHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// db.execSQL("CREATE TABLE makanan (nama TEXT, jenisKelamin TEXT, noKTP TEXT, tempatLahir TEXT, tglLahir TEXT, noTLP TEXT PRIMARY KEY, alamat TEXT, foto TEXT, agen TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public Cursor getAll() {
		return (getReadableDatabase().rawQuery("SELECT * FROM Makanan", null));
	}
}
