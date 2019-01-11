package com.twentysecondhero.twinghosts.bucketlist;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BucketRepository {
    private AppDatebase mAppDatabase;
    private BucketDAO mBucketDao;
    private LiveData<List<Bucket>> mBuckets;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public BucketRepository (Context context) {
        mAppDatabase = AppDatebase.getInstance(context);
        mBucketDao = mAppDatabase.bucketDAO();
        mBuckets = mBucketDao.getAllBuckets();
    }

    public LiveData<List<Bucket>> getAllBuckets() {
        return mBuckets;
    }

    public void insert(final Bucket bucket) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBucketDao.insertBuckets(bucket);
            }
        });
    }

    public void update(final Bucket bucket) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBucketDao.updateBuckets(bucket);
            }
        });
    }

    public void delete(final Bucket bucket) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBucketDao.deleteBuckets(bucket);
            }
        });
    }
}