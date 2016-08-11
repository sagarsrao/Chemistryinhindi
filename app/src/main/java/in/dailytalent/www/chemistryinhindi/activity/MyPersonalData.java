package in.dailytalent.www.chemistryinhindi.activity;

import android.content.Context;

public class MyPersonalData {
    private static final String DB_NAME = "chemistry.db";
    static Context ctx;

    public static String dbname() {
        return DB_NAME;
    }
}
