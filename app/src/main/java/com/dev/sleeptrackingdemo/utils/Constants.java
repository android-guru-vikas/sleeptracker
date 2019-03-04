package com.dev.sleeptrackingdemo.utils;

public class Constants {
    public static final String KEY_FONT_TYPE = "DROIDSERIF.ttf";
    public static final String KEY_APP_DB_PATH = "/data/data/com.dev.sleeptrackingdemo/databases/sleep_data";
    public static final String KEY_APP_DB_NAME = "sleep_data";
    public static final String KEY_APP_DB_QUERY = "Select * from sensor_data";
//    public static final String KEY_APP_DB_QUERY = "Select * from sensor_data WHERE timestamp >= DATETIME('now', '-3 day');";
    public static final String KEY_APP_DB_DELETE_QUERY = "DELETE FROM sensor_data WHERE sensor_data <= date('now','-2 minute')";
    public static final String KEY_APP_DATE_FORMAT= "yyyy/MM/dd HH:mm";
}
