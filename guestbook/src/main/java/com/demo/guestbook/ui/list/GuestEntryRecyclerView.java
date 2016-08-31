package com.demo.guestbook.ui.list;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.demo.guestbook.model.pojo.GuestEntry;

import java.util.List;

public abstract class GuestEntryRecyclerView {

    abstract public List<GuestEntry> getGuestEntries();
    abstract public boolean isEditAllowed();

    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private GuestListRecyclerViewAdapter mAdapter;

    protected GuestEntryRecyclerView(Activity activity) {
        mActivity = activity;
    }

    public void setRecyclerView(RecyclerView recyclerView) {

        mRecyclerView = recyclerView;

        mAdapter = getAdapter();

        LinearLayoutManager llm = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void resetLocalListRecyclerView() {

        // hasn't been initialized yet
        if (mRecyclerView == null) {
            return;
        }

        mAdapter = getAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.invalidate();
    }

    private GuestListRecyclerViewAdapter getAdapter() {

        List<GuestEntry> guestEntries = getGuestEntries();
        return new GuestListRecyclerViewAdapter(guestEntries, isEditAllowed());
    }
}

