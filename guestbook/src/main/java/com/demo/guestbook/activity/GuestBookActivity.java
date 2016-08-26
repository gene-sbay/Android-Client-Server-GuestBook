package com.demo.guestbook.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo.guestbook.R;
import com.demo.guestbook.databinding.ActivityMainBinding;
import com.demo.guestbook.model.mapper.GuestEntryMapper;
import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.model.view.GuestEntryViewModel;
import com.demo.guestbook.remote.FirebaseEndPoint;
import com.firebase.client.Firebase;

/**
 * Launcher Activity displaying two EditTexts. Both EditTexts point to same class object. Since we
 * have used 2 way authentication, once we update the text in either of EditText, it will automatically
 * be reflected in another EditText.
 */

public class GuestBookActivity extends AppCompatActivity {

    private GuestEntryViewModel mGuestEntryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        initDataBinding();
    }

    private void initDataBinding() {

        mGuestEntryViewModel = new GuestEntryViewModel();

        // No need to add a setContentView(), we will use DataBinding to set the contentView
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Binding SimplePOJO class with the DataBinding Adapter
        binding.setGuestEntryViewModel(mGuestEntryViewModel);

        Button submitBtn = (Button) findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFirebase();
            }
        });
    }

    private void updateFirebase() {
        String message = "Saving: " + mGuestEntryViewModel.toString();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        GuestEntryMapper guestEntryMapper = new GuestEntryMapper();
        GuestEntry guestEntry = guestEntryMapper.map(mGuestEntryViewModel);

        FirebaseEndPoint firebaseEndPoint = new FirebaseEndPoint();
        firebaseEndPoint.connect();
        firebaseEndPoint.save(guestEntry);
    }
}
