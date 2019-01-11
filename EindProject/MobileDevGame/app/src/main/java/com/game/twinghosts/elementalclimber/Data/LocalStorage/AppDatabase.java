package com.game.twinghosts.elementalclimber.Data.LocalStorage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.game.twinghosts.elementalclimber.Data.HiScores.HiScore;

@Database(entities = {HiScore.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract HiScoreDAO hiScoreDAO();

    private final static String DB_NAME = "hi_score_db";
    private static AppDatabase dbInstance;

    public static AppDatabase getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries().build();
        }

        return dbInstance;
    }
}