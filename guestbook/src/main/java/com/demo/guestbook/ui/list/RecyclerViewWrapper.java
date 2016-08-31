package com.demo.guestbook.ui.list;

import android.support.v7.widget.RecyclerView;

import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.model.sharedprefs.AppStateDao;

import java.util.List;

public class RecyclerViewWrapper {

    private RecyclerView mRecyclerView;

    public void setLocalListRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public void resetLocalListRecyclerView(GuestListRecyclerViewAdapter adapter) {

        // hasn't been initialized yet
        if (mRecyclerView == null) {
            return;
        }

        List<GuestEntry> guestEntries = AppStateDao.getAppState().getLocalGuestEntries();
        adapter = new GuestListRecyclerViewAdapter(guestEntries);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.invalidate();
    }
}

