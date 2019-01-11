package com.twentysecondhero.twinghosts.bucketlist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "bucket")
public class Bucket {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "checked")
    private boolean mChecked;
    @ColumnInfo(name = "title")
    private String mTitle;
    @ColumnInfo(name = "description")
    private String mDescription;

    public Bucket(boolean mChecked, String mTitle, String mDescription) {
        this.mChecked = mChecked;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean mChecked) {
        this.mChecked = mChecked;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}

