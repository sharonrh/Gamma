package dao;

import java.util.ArrayList;
import java.util.List;

import model.Achievement;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    private static final String requirement = "requirement";
    private static final String pathLogo = "pathLogo";

    public static HandlerAchievement getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new HandlerAchievement(context);
        }
        return sInstance;
    }

    public HandlerAchievement(Context context) {
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
        values.put(requirement, achievement.getRequirement());
        values.put(pathLogo, achievement.getPathLogo());

        db.insert(tabelAchievement, null, values);
        db.close();
    }

    // Getting single achievement
    public Achievement getAchievement(String namaAchievement) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tabelAchievement, new String[] { nama,
                        terkunci, deskripsi, progress, requirement, pathLogo },
                nama + "=?", new String[] { String.valueOf(namaAchievement) },
                null, null, null, null);

        Achievement achievement = null;
        if (cursor.moveToFirst()) {
            achievement = new Achievement(cursor.getString(0),
                    false, cursor.getString(2), cursor.getInt(3), cursor.getInt(4),
                    cursor.getString(5));
            if (Integer.parseInt(cursor.getString(1)) == 1) {
                achievement.setGet(true);
            }
        }
        cursor.close();
        db.close();
        return achievement;
    }

    // Getting All Achievement
    public ArrayList<Achievement> getAllAchievement() {
        ArrayList<Achievement> listAchievement = new ArrayList<Achievement>();
        String selectQuery = "SELECT  * FROM " + tabelAchievement;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.i("Info", "Coba baca db");
        if (cursor.moveToFirst()) {
            do {
                Achievement achievement = new Achievement();
                achievement.setNama(cursor.getString(0));
                Log.i("Nama achievement : ", ""+ achievement.getNama());
                if (Integer.parseInt(cursor.getString(1)) == 1) {
                    achievement.setGet(true);
                }
                achievement.setDeskripsi(cursor.getString(2));
                achievement.setProgress(cursor.getInt(3));
                achievement.setRequirement(cursor.getInt(4));
                achievement.setPathLogo(cursor.getString(5));

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
        int isGet = 0;
        if (achievement.isGet()) {
            isGet = 1;
            Log.i("Done", "Harusnya nilai isGet jadi 1");
        }
        values.put(terkunci, isGet);
        values.put(progress, achievement.getProgress());
        values.put(pathLogo, achievement.getPathLogo());
        // updating row
        return db.update(tabelAchievement, values, KEY_ID + " = ?",
                new String[]{String.valueOf(achievement.getNama())});
    }
    
}
