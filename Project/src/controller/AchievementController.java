package controller;

import android.content.Context;
import dao.HandlerAchievement;

public class AchievementController {

	private HandlerAchievement db;

	public AchievementController(Context c) {
		db = HandlerAchievement.getInstance(c);
	}

	public void setReceived(String name) {
	//	db.updateAchievement(name);
	}
}
