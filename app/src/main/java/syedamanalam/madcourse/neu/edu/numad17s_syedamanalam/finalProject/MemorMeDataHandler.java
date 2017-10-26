package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.HashMap;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.commDemo.User;

/**
 * Created by chandrimaghosh on 4/11/17.
 */

public class MemorMeDataHandler extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MemorMe.db";
    public static final String TABLE_NAME = "ActivityLogging";
    public static final String TABLE2_NAME = "UserDetail";
    public static final String TABLE3_NAME = "RealtimeActivityLogging";

    //column names
    public static final String ACTIVITYLOGGING_COLUMN_ID = "id";
    public static final String ACTIVITYLOGGING_COLUMN_TIME = "timestamp";
    public static final String ACTIVITYLOGGING_COLUMN_ACTIVITY = "activity";
    public static final String ACTIVITYLOGGING_COLUMN_CONFIDENCESCORE = "confidence_score";
    public static final String ACTIVITYLOGGING_COLUMN_LOCATION = "location";



    //column names
    public static final String USER_DETAIL_COLUMN_ID = "id";
    public static final String USER_DETAIL_COLUMN_NAME = "user_name";
    public static final String USER_DETAIL_COLUMN_OFFICELOCATION = "office_location";
    public static final String USER_DETAIL_COLUMN_HOMELOCATION = "home_location";
    public static final String USER_DETAIL_STARTTIME= "start_time";
    public static final String USER_DETAIL_ENDTIME= "end_time";


    private HashMap hp;

    public MemorMeDataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ACTIVITYLOGGING_COLUMN_ID + " INTEGER PRIMARY KEY," + ACTIVITYLOGGING_COLUMN_ACTIVITY + " TEXT,"
                + ACTIVITYLOGGING_COLUMN_CONFIDENCESCORE + " INTEGER ," + ACTIVITYLOGGING_COLUMN_TIME + " TEXT,"+ ACTIVITYLOGGING_COLUMN_LOCATION + " TEXT" +")";
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE2_NAME + "("
                + USER_DETAIL_COLUMN_ID + " INTEGER PRIMARY KEY," + USER_DETAIL_COLUMN_NAME + " TEXT,"
                + USER_DETAIL_COLUMN_OFFICELOCATION + " TEXT ," +USER_DETAIL_STARTTIME+ " INTEGER,"+USER_DETAIL_ENDTIME+ " INTEGER,"+ USER_DETAIL_COLUMN_HOMELOCATION + " TEXT" +")";

        String CREATE_CONTACTS_REALLTABLE = "CREATE TABLE " + TABLE3_NAME + "("
                + ACTIVITYLOGGING_COLUMN_ID + " INTEGER PRIMARY KEY," + ACTIVITYLOGGING_COLUMN_ACTIVITY + " TEXT,"
                + ACTIVITYLOGGING_COLUMN_CONFIDENCESCORE + " INTEGER ," + ACTIVITYLOGGING_COLUMN_TIME + " TEXT,"+ ACTIVITYLOGGING_COLUMN_LOCATION + " TEXT" +")";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CONTACTS_REALLTABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
    }


    //Adding new ActivityStamp
    void addActicityStamp(ActivityStamp activityStamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACTIVITYLOGGING_COLUMN_ACTIVITY, activityStamp.getActivityDetail()); //
        values.put(ACTIVITYLOGGING_COLUMN_TIME, activityStamp.getActivityTimeStamp()); //
        values.put(ACTIVITYLOGGING_COLUMN_LOCATION,activityStamp.getLocationCloseTo());//location
        values.put(ACTIVITYLOGGING_COLUMN_CONFIDENCESCORE,activityStamp.getActivityConfidence());
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
    //Adding new ActivityStamp
    void addActicityStampReal(ActivityStamp activityStamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACTIVITYLOGGING_COLUMN_ACTIVITY, activityStamp.getActivityDetail()); //
        values.put(ACTIVITYLOGGING_COLUMN_TIME, activityStamp.getActivityTimeStamp()); //
        values.put(ACTIVITYLOGGING_COLUMN_LOCATION,activityStamp.getLocationCloseTo());//location
        values.put(ACTIVITYLOGGING_COLUMN_CONFIDENCESCORE,activityStamp.getActivityConfidence());
        // Inserting Row
        db.insert(TABLE3_NAME, null, values);
        db.close(); // Closing database connection
    }

    //Adding new ActivityStamp
    void addUser(UserClass user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_DETAIL_COLUMN_ID, user.getId()); //
        values.put(USER_DETAIL_COLUMN_NAME, user.getName()); //
        values.put(USER_DETAIL_COLUMN_HOMELOCATION,user.getHomeLocation());//location
        values.put(USER_DETAIL_COLUMN_OFFICELOCATION,user.getOfficeLocation());
        // Inserting Row
        db.insert(TABLE2_NAME, null, values);
        db.close(); // Closing database connection
    }


    public int getActivityStampCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();
        return count;
    }
    public int getUserClassCount() {
        String countQuery = "SELECT  * FROM " + TABLE2_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();
        return count;
    }

    //getAllActivities
    public List<ActivityStamp> getAllContacts() {
        List<ActivityStamp> contactList = new ArrayList<ActivityStamp>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ActivityStamp activityStamp = new ActivityStamp();
                activityStamp.setId(Integer.parseInt(cursor.getString(0)));
                activityStamp.setActivityDetail(cursor.getString(1));
                activityStamp.setActivityTimeStamp(cursor.getString(3));
                activityStamp.setLocationCloseTo(cursor.getString(4));
                // Adding contact to list
                contactList.add(activityStamp);

            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    //getAllActivities
    public List<UserClass> getAllUsers() {
        List<UserClass> userList = new ArrayList<UserClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE2_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserClass user=new UserClass();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setStart(cursor.getInt(2));

                // Adding contact to list
                userList.add(user);

            } while (cursor.moveToNext());
        }

        // return userList
        return userList;
    }


    //getAllActivities
    public List<ActivityStamp> getAllRealData() {
        List<ActivityStamp> contactList = new ArrayList<ActivityStamp>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE3_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ActivityStamp activityStamp = new ActivityStamp();
                activityStamp.setId(Integer.parseInt(cursor.getString(0)));
                activityStamp.setActivityDetail(cursor.getString(1));
                activityStamp.setActivityTimeStamp(cursor.getString(3));
                activityStamp.setLocationCloseTo(cursor.getString(4));
                // Adding contact to list
                contactList.add(activityStamp);

            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

}
