package com.demo.guestbook.ui.list;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.guestbook.databinding.ListGuestCardBinding;
import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.util.TheApp;

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

    private List<GuestEntry> mGuestEntries;

    public GuestListRecyclerViewAdapter(List<GuestEntry> guestEntries){
        mGuestEntries = guestEntries;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public GuestEntryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final ListGuestCardBinding binding = ListGuestCardBinding.inflate(layoutInflater);
        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = binding.getGuestEntry().getId();
                Toast.makeText(TheApp.getAppContext(), "My Edit with id: " + id, Toast.LENGTH_SHORT).show();
            }
        });
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

