package com.twentysecondhero.twinghosts.bucketlist;

public interface BucketChangedListener {
    /**
     * onchange for each checkBox item in RecyclerView
     * @param position
     * @param isChecked
     */
    void onBucketCheckBoxChanged(int position, boolean isChecked);
}
