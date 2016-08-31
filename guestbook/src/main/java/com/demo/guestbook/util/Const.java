package com.demo.guestbook.util;

import com.demo.guestbook.R;

public class Const {

    public final static int APP_THEME = R.array.grape;

    public class Field {

        public final static String ID = "id";
    }

    public class Regex {

        public final static String ZIPCODE = "^[0-9]{5}(?:-[0-9]{4})?$";
    }

    public class Extra {

        public final static String GUEST_ENTRY_ID = "GUEST_ENTRY_ID";
    }

    public class Dates {

        public final static int DATE_PICKER_START_YEAR_OFFSET = 20;
    }

    public class Tabs {

        public final static int LOCAL_ENTRIES = 1;
    }

    public class SharedPrefs {

        public final static String PREF__APP_STATE = "app_state";
    }

    public class Firebase {

        public final static String PROJECT_URL = "https://guest-bookly.firebaseio.com";
        public final static String GUEST_LOG = "guest_log";
        public final static String GUEST_LOG_URL = PROJECT_URL + "/" + GUEST_LOG;
    }
}
