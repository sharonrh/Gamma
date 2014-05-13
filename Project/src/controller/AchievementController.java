package controller;

import android.content.Context;
import dao.HandlerAchievement;
import model.Achievement;

public class AchievementController {

	private HandlerAchievement handlerAchievement;

	public AchievementController(Context c) {
		handlerAchievement = HandlerAchievement.getInstance(c);
	}

	public void setReceived(String name) {
	//	db.updateAchievement(name);
	}

    public HandlerAchievement getHandler () {
        return handlerAchievement;
    }

    public boolean cekAchievement (String achievement) {
        return handlerAchievement.getAchievement(achievement).isGet();
    }

    public boolean tambahProgress (String achievement) {
        Achievement ach = handlerAchievement.getAchievement(achievement);
        ach.addProgress();
        handlerAchievement.updateAchievement(ach);
        return cekAchievement(achievement);
    }
}
