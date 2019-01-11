package com.twentysecondhero.twinghosts.bucketlist;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BucketChangedListener {

    private List<Bucket> mBuckets;
    private RecyclerView mBucketRecyclerView;
    private BucketAdapter mAdapter;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBucketRecyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                1,
                LinearLayoutManager.VERTICAL
        );
        mBucketRecyclerView.setLayoutManager(mLayoutManager);

        mBuckets = new ArrayList<>();

        mMainViewModel = new MainViewModel(getApplicationContext());
        mMainViewModel.getBuckets().observe(this, new Observer<List<Bucket>>() {
            @Override
            public void onChanged(@Nullable List<Bucket> buckets) {
                mBuckets = buckets;
                updateUI();
            }
        });

        handleSwipe(mAdapter, mBucketRecyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBucketItem.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBucketCheckBoxChanged(int position, boolean isChecked) {
        Bucket bucket = mBuckets.get(position);
        bucket.setChecked(isChecked);
        mBuckets.set(position, bucket);
        mMainViewModel.update(mBuckets.get(position));
    }

    /**
     * Update the UI with RecyclerView.
     */
    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new BucketAdapter(mBuckets, this);
            mBucketRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mBuckets);
        }
    }

    /**
     * If items are swiped left delete the item.
     *
     * @param mAdapter
     * @param mGeoRecyclerView
     */
    private void handleSwipe(final BucketAdapter mAdapter, RecyclerView mGeoRecyclerView) {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
        ) {
            @Override
            public boolean onMove(
                    RecyclerView recyclerView,
                    RecyclerView.ViewHolder viewHolder,
                    RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mMainViewModel.delete(mBuckets.get(position));
                Toast.makeText(
                        MainActivity.this,
                        R.string.bucket_deleted,
                        Toast.LENGTH_SHORT
                ).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mGeoRecyclerView);
    }
}
