package com.demo.guestbook.ui.list;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.guestbook.activity.AddGuestLogActivity;
import com.demo.guestbook.databinding.ListGuestCardBinding;
import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.util.Const;

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

    private Activity mActivity;
    private List<GuestEntry> mGuestEntries;
    private boolean mIsEditAllowed = false;

    public GuestListRecyclerViewAdapter(Activity activity, List<GuestEntry> guestEntries, boolean isEditAllowed) {
        mActivity = activity;
        mGuestEntries = guestEntries;
        mIsEditAllowed = isEditAllowed;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public GuestEntryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final ListGuestCardBinding binding = ListGuestCardBinding.inflate(layoutInflater);

        if ( ! mIsEditAllowed ) {
            binding.editButton.setVisibility(View.GONE);
        }
        else {
            binding.editButton.setVisibility(View.VISIBLE);
            binding.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String guestEntryId = binding.getGuestEntry().getId();
                    Intent intent = new Intent(mActivity, AddGuestLogActivity.class);
                    intent.putExtra(Const.Extra.GUEST_ENTRY_ID, guestEntryId);
                    mActivity.startActivity(intent);
                }
            });
        }
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

