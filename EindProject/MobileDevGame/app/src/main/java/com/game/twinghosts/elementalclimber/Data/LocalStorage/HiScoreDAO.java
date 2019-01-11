package com.game.twinghosts.elementalclimber.Data.LocalStorage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.Update;

import com.game.twinghosts.elementalclimber.Data.HiScores.HiScore;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface HiScoreDAO {

    @Query("SELECT * FROM hiScore")
    List<HiScore> getAllHiScoresOrderedByDesc();

    @Insert
    void insertGames(HiScore hiScore);

    @Delete
    void deleteGames(HiScore hiScore);

    @Update
    void updateGames(HiScore hiScore);
}
