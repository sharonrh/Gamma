package controller;

import java.util.List;

import model.Notifikasi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import dao.*;

public class ResetController {
	private DatabaseHandler dbh;

	public ResetController(Context c) {
		dbh = new DatabaseHandler(c);
	}

	public void reset(Context c) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		dbh.resetProgress(c, db);
		db.close();
	}	
}
