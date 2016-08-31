package com.demo.guestbook.model.validator;

import com.demo.guestbook.R;
import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.util.Const;
import com.demo.guestbook.util.Logr;
import com.demo.guestbook.util.TheApp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * With more time, I would add this solution with inline error messages
 *
     EditText firstName = (EditText)findViewById(R.id.first_name);
     if( firstName.getText().toString().length() == 0 )
        firstName.setError( "First name is required!" );

    Additionally, could do this with listeners for the event when user lives an input field,
    and then do validation there
 */
public class GuestEntryValidator {

    private final static String ZIPCODE_FORMAT_ERROR = "Please enter a valid zip code";

    private List<String> errorMessages;

    private GuestEntry mGuestEntry;

    public GuestEntryValidator(GuestEntry guestEntry) {
        mGuestEntry = guestEntry;
    }

    public boolean isValid() {

        errorMessages = new ArrayList<>();

        runValidation();

        boolean isValid = errorMessages.size() == 0;

        return isValid;
    }

    public String getErrorMessageString() {

        StringBuffer singleMessageBuffer = new StringBuffer();

        for (String message : errorMessages) {
            singleMessageBuffer.append(message + "\n");
        }

        return singleMessageBuffer.toString();
    }

    private void runValidation() {

        validateNotNullAndNameLengths();
        validateZipCode();
    }

    private void validateZipCode() {

        Pattern pattern = Pattern.compile(Const.Regex.ZIPCODE);
        Matcher matcher = pattern.matcher(mGuestEntry.getZipcode());
        boolean isValidZipCode = matcher.matches();
        if ( ! isValidZipCode ) {
            errorMessages.add(ZIPCODE_FORMAT_ERROR);
        }
    }

    private void validateNameLength(String fieldName, String value) {

        // for simpler code here, null is expected have been checked
        if (value.length() < GuestEntry.NAME_MINIMUM_CHARS) {
            String errMsg = fieldName + " is too short.  It must be at least " + GuestEntry.NAME_MINIMUM_CHARS + " characters";
            errorMessages.add(errMsg);
        }
    }

    public void validateNotNullAndNameLengths() {

        try {
            for (Field field : mGuestEntry.getClass().getDeclaredFields()) {
                field.setAccessible(true); // You might want to set modifier to public first.
                String fieldName = field.getName();
                Object value = field.get(mGuestEntry);
                String message = fieldName + "=" + value;

                if (value instanceof String) {

                    if (value != null && ! value.toString().isEmpty()) {
                        Logr.d("validateNotNullAndNameLengths is good : " + message);
                    }
                    else {
                        errorMessages.add(fieldName + " is missing");
                        continue;
                    }

                    if (fieldName.endsWith(GuestEntry.NAME_FIELD_SUFFIX)) {
                        validateNameLength(fieldName, value.toString());
                    }

                    if (fieldName.equals("birthday")) {
                        String placeHolderString = TheApp.findString(R.string.please_set);
                        if ( placeHolderString.equals(value.toString()) ) {
                            errorMessages.add("Please select a birthday to save your Entry");
                        }
                    }
                }
            }
        }
        catch (IllegalAccessException iaEx) {
            iaEx.printStackTrace();
            String message = "Unexpected error validating the object. Ex: " + iaEx.getMessage();
            Logr.e(message);
            errorMessages.add(message);
        }
    }
}
