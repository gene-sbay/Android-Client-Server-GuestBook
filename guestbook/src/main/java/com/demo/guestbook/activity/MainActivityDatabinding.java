package com.demo.guestbook.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo.guestbook.R;
import com.demo.guestbook.databinding.ActivityMainBinding;
import com.demo.guestbook.pojo.GuestEntry;

/**
 * Launcher Activity displaying two EditTexts. Both EditTexts point to same class object. Since we
 * have used 2 way authentication, once we update the text in either of EditText, it will automatically
 * be reflected in another EditText.
 */

public class MainActivityDatabinding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final GuestEntry guestEntry = new GuestEntry();

        // No need to add a setContentView(), we will use DataBinding to set the contentView
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Binding SimplePOJO class with the DataBinding Adapter
        binding.setGuestEntry(guestEntry);

        Button submitBtn = (Button) findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), guestEntry.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
