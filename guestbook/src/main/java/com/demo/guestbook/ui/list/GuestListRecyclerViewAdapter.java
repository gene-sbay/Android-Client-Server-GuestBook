package com.demo.guestbook.ui.list;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.guestbook.databinding.ListGuestCardBinding;
import com.demo.guestbook.model.pojo.GuestEntry;

import java.util.List;

public class GuestListRecyclerViewAdapter extends RecyclerView.Adapter<GuestListRecyclerViewAdapter.GuestEntryViewHolder> {

    public class GuestEntryViewHolder extends RecyclerView.ViewHolder {

        private ListGuestCardBinding mBinding;

        GuestEntryViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public ListGuestCardBinding getBinding() {
            return mBinding;
        }
    }

    List<GuestEntry> mGuestEntries;
    Activity mActivity;

    public GuestListRecyclerViewAdapter(Activity activity, List<GuestEntry> guestEntries){
        mActivity = activity;
        mGuestEntries = guestEntries;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public GuestEntryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ListGuestCardBinding binding = ListGuestCardBinding.inflate(layoutInflater);
        return new GuestEntryViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(GuestEntryViewHolder guestEntryViewHolder, int i) {
        final GuestEntry guestEntry = mGuestEntries.get(i);
        guestEntryViewHolder.getBinding().setGuestEntry(guestEntry);
    }

    @Override
    public int getItemCount() {
        return mGuestEntries.size();
    }
}

