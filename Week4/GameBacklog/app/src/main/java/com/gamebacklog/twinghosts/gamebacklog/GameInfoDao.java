package com.gamebacklog.twinghosts.gamebacklog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GameInfoDao {

    @Query("SELECT * FROM gameInfo")
    List<GameInfo> getAllGames();

    @Insert
    void insertGames(GameInfo games);

    @Delete
    void deleteGames(GameInfo games);

    @Update
    void updateGames(GameInfo games);
}
