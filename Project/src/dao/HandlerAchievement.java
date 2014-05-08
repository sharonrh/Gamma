package dao;

import java.util.ArrayList;
import java.util.List;

import model.Achievement;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HandlerAchievement extends DatabaseHandler {

    private static final String KEY_ID = "nama";
    private static HandlerAchievement sInstance;

    // Nama tabel yang akan dibuat
    private static final String tabelAchievement = "achievement";

    // Kolom tabel achievement
    private static final String nama = "nama";
    private static final String deskripsi = "deskripsi";
    private static final String terkunci = "terkunci";
    private static final String progress = "progress";
    private static final String pathLogo = "pathLogo";

    public static HandlerAchievement getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new HandlerAchievement(context);
        }
        return sInstance;
    }

    private HandlerAchievement(Context context) {
        super(context);
    }

    // Adding new achievement
    public void tambahAchievement(Achievement achievement) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(nama, achievement.getNama());
        values.put(terkunci, achievement.isGet());
        values.put(deskripsi, achievement.getDeskripsi());
        values.put(progress, achievement.getProgress());
        values.put(pathLogo, achievement.getPathLogo());

        db.insert(tabelAchievement, null, values);
        db.close();
    }

    // Getting single achievement
    public Achievement getAchievement(String nama) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tabelAchievement, new String[] { nama,
                        terkunci, deskripsi, progress, pathLogo },
                nama + "=?", new String[] { String.valueOf(nama) },
                null, null, null, null);

        Achievement achievement = null;
        if (cursor.moveToFirst()) {
            achievement = new Achievement(cursor.getString(0),
                    false, cursor.getString(2), Double.parseDouble(cursor.getString(3)),
                    cursor.getString(4));
            if (Integer.parseInt(cursor.getString(1)) == 1) {
                achievement.setGet(true);
            }
        }
        cursor.close();
        db.close();
        return achievement;
    }

    // Getting All Achievement
    public List<Achievement> getAllAchievement() {
        List<Achievement> listAchievement = new ArrayList<Achievement>();
        String selectQuery = "SELECT  * FROM " + tabelAchievement;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Achievement achievement = new Achievement();
                achievement.setNama(cursor.getString(0));
                if (Integer.parseInt(cursor.getString(1)) == 1) {
                    achievement.setGet(true);
                }
                achievement.setDeskripsi(cursor.getString(2));
                achievement.setProgress(Double.parseDouble(cursor.getString(3)));
                achievement.setPathLogo(cursor.getString(4));

                listAchievement.add(achievement);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listAchievement;
    }

    // Updating single achievement
    public int updateAchievement (Achievement achievement) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if (achievement.getProgress() == 1.0) {
            achievement.setGet(true);
        }
        values.put(terkunci, achievement.isGet());
        values.put(progress, achievement.getProgress());

        // updating row
        return db.update(tabelAchievement, values, KEY_ID + " = ?",
                new String[]{String.valueOf(achievement.getNama())});
    }

}
