package com.demo.guestbook.ui.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.demo.guestbook.R;

import java.util.Calendar;

public class DatePickerHelper {

    public static final int DATE_DIALOG_ID = 999;

    private Activity mActivity;

    private TextView mTextViewDisplayDate;
    private Button mButtonChangeDate;

    private int mYear;
    private int mMonth;
    private int mDay;

    public DatePickerHelper(Activity activity, int dateDisplayTextViewId) {
        mActivity = activity;
        mTextViewDisplayDate = (TextView) mActivity.findViewById(R.id.textviewDate);
    }

    public DatePickerDialog getDatePickerDialog() {
        return new DatePickerDialog(mActivity, datePickerListener, mYear, mMonth, mDay);
    }

    // display current date
    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    public void addListenerOnButton() {

        mButtonChangeDate = (Button) mActivity.findViewById(R.id.btnChangeDate);

        mButtonChangeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mActivity.showDialog(DATE_DIALOG_ID);
            }
        });

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;

            mTextViewDisplayDate.setText(
                    new StringBuilder().append(mMonth + 1)
                                            .append("-").append(mDay)
                                            .append("-").append(mYear));
        }
    };

    public TextView getTextViewDisplayDate() {
        return mTextViewDisplayDate;
    }
}
