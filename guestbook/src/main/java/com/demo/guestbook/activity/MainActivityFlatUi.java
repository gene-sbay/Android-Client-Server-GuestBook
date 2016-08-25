package com.demo.guestbook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatEditText;
import com.demo.guestbook.R;

import java.util.ArrayList;

public class MainActivityFlatUi extends AppCompatActivity {

    private final int APP_THEME = R.array.blood;

    private ArrayList<FlatEditText> flatEditTexts = new ArrayList<FlatEditText>();
    private ArrayList<FlatButton> flatButtons = new ArrayList<FlatButton>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // converts the default values to dp to be compatible with different screen sizes
        FlatUI.initDefaultValues(this);

        // Default theme should be set before content view is added
        FlatUI.setDefaultTheme(APP_THEME);

        setContentView(R.layout.activity_main_flatui);

        // Getting action bar background and applying it
        getSupportActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, APP_THEME, false, 2));

        flatEditTexts.add((FlatEditText) findViewById(R.id.edittext_firstname));
        flatButtons.add((FlatButton) findViewById(R.id.button_white));
    }

    public void onChangeThemeButtonClicked(View v) {
        FlatButton button = (FlatButton) v;
        changeTheme(button.getAttributes().getTheme());
    }

    private void changeTheme(int colorReference) {

        for (FlatEditText view : flatEditTexts) {
            view.getAttributes().setTheme(colorReference, getResources());
        }

        for (FlatButton view : flatButtons) {
            view.getAttributes().setTheme(colorReference, getResources());
        }

        // if you are using standard action bar (not compatibility library) use this
        // getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, colorReference, false));

        // if you are using ActionBar of Compatibility library, get drawable and set it manually
        getSupportActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, colorReference, false));

        setTitle("FlatUI Sample App");
    }

}

