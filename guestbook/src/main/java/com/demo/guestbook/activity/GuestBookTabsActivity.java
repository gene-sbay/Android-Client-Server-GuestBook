package com.demo.guestbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.demo.guestbook.R;
import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.model.sharedprefs.AppStateDao;
import com.demo.guestbook.ui.list.GuestListRecyclerViewAdapter;
import com.demo.guestbook.ui.list.RecyclerViewWrapper;
import com.demo.guestbook.util.Const;
import com.demo.guestbook.util.TheApp;

import java.util.List;

public class GuestBookTabsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private RecyclerViewWrapper mLocalRecyclerViewWrapper;
    // private RecyclerView mLocalListRecyclerView;
    private RecyclerView mGlobalListRecyclerView;

    private GuestListRecyclerViewAdapter mAdapter;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Default theme should be set before content view is added
        FlatUI.setDefaultTheme(Const.APP_THEME);

        setContentView(R.layout.tab_activity_guest_book);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mLocalRecyclerViewWrapper = new RecyclerViewWrapper();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_entry_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GuestBookTabsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadAddGuestLogActivity();
                    }
                });

            }
        });

    }

    private void loadAddGuestLogActivity() {
        loadAddGuestLogActivity(null);
    }

    private void loadAddGuestLogActivity(String guestEntryId) {
        Intent intent = new Intent(GuestBookTabsActivity.this, AddGuestLogActivity.class);

        if (guestEntryId != null) {
            intent.putExtra(Const.Extra.GUEST_ENTRY_ID, guestEntryId);
        }

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mLocalRecyclerViewWrapper.resetLocalListRecyclerView(mAdapter);
    }

    public GuestListRecyclerViewAdapter getAdapter() {

        if (mAdapter == null) {
            List<GuestEntry> guestEntries = AppStateDao.getAppState().getLocalGuestEntries();
            mAdapter = new GuestListRecyclerViewAdapter(guestEntries);
        }

        return mAdapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tab_menu_guest_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clean_up_firebase) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class GuestBookTabsFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public GuestBookTabsFragment() {
        }


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static GuestBookTabsFragment newInstance(int sectionNumber) {
            GuestBookTabsFragment fragment = new GuestBookTabsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {

            int section = getArguments().getInt(ARG_SECTION_NUMBER);

            if (section == Const.Tabs.LOCAL_ENTRIES) {
               return getLocalListRootView(inflater, container);
            }

            View rootView = inflater.inflate(R.layout.tab_fragment_guest_book, container, false);

            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, section));

            return rootView;
        }

        private View getLocalListRootView(LayoutInflater inflater, ViewGroup container) {

            View rootView = inflater.inflate(R.layout.fragment_recycler_list, container, false);

            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.listRecyclerView);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(llm);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(((GuestBookTabsActivity) getActivity()).getAdapter());

            ((GuestBookTabsActivity) getActivity())
                    .mLocalRecyclerViewWrapper
                    .setLocalListRecyclerView(recyclerView);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a GuestBookTabsFragment (defined as a static inner class below).
            return GuestBookTabsFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Local";
                case 1:
                    return "Global";
            }
            return null;
        }
    }
}




//
