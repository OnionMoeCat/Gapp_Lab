package com.tejas.login;

import android.provider.BaseColumns;

/**
 * Created by u0923408 on 11/4/2015.
 */
public final class MedListContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MedListContract() {}

    //Inner class that defines the table contents.
    public static abstract class MedList implements BaseColumns{
        public static final String TABLE_NAME = "medicationlist";
        public static final String COLUMN_MED_NAME = "medname";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_ROUTE = "route";
        public static final String COLUMN_FREQUENCY = "frequency";
        public static final String COLUMN_INSTRUCTIONS = "instructions";
        public static final String COLUMN_UPDATED = "updated";
    }
}
