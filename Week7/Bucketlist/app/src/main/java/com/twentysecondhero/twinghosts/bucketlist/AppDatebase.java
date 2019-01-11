package com.twentysecondhero.twinghosts.bucketlist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Bucket.class}, version = 1)
public abstract class AppDatebase extends RoomDatabase {

    public abstract BucketDAO bucketDAO();

    private final static String NAME_DATABASE = "bucket_db";
    private static AppDatebase sInstance; //Static instance

    public static AppDatebase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatebase.class, NAME_DATABASE).build();
        }

        return sInstance;
    }
}

