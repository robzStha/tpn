package com.official.trialpassnepal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SlowhandJr. on 4/4/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_tpn.db";
    public static final int DB_VER = 8;
    Context context;
    public static final String TBL_CATEGORY = "tpn_category";
    public static final String TBL_DRIVING_CENTERS = "tpn_driving_centers";
    public static final String TBL_QUESTIONS = "tpn_questions";
    public static final String TBL_QUESTION_IMAGES = "tpn_question_images";
    public static final String TBL_QUESTION_OPTIONS = "tpn_question_options";
    public static final String TBL_SUB_ANSWERS = "tpn_sub_answers";
    public static final String TBL_REMINDER = "tpn_reminder";

    //
    // Table structure for table tpn_category
    //

    String QRY_TBL_CATEGORY = "CREATE TABLE IF NOT EXISTS tpn_category (" +
            "catid INTEGER," +
            "categoryName tinytext NOT NULL," +
            "categoryDescription tinytext NOT NULL," +
            "published tinyint(1) NOT NULL DEFAULT '1')";

    String QRY_TBL_DRIVING_CENTERS = "CREATE TABLE IF NOT EXISTS tpn_driving_centers (" +
            "  dcid INTEGER," +
            "  dcName tinytext NOT NULL," +
            "  dcProprietor varchar(50) NOT NULL," +
            "  dcContactno varchar(50)," +
            "  latitude float NOT NULL," +
            "  longitude float NOT NULL," +
            "  published tinyint(4) NOT NULL DEFAULT '1')";

    String QRY_TBL_QUESTIONS = "CREATE TABLE IF NOT EXISTS tpn_questions (" +
            "  qid INTEGER," +
            "  question text NOT NULL," +
            "  qtype tinyint(1) NOT NULL DEFAULT '0'," +
            "  published tinyint(1) NOT NULL DEFAULT '1')";

    String QRY_TBL_QUESTION_IMAGES = "CREATE TABLE IF NOT EXISTS tpn_question_images (" +
            "  qid INTEGER," +
            "  catid INTEGER NOT NULL," +
            "  imagePath varchar(50) NOT NULL" +
            ")";

    String QRY_TBL_QUESTION_OPTIONS = "CREATE TABLE IF NOT EXISTS tpn_question_options (" +
            "  optionsid INTEGER NOT NULL," +
            "  qid INTEGER NOT NULL," +
            "  ansOptions tinytext NOT NULL," +
            "  validOption tinyint(1) NOT NULL" +
            ")";

    String QRY_TBL_SUB_ANSWERS = "CREATE TABLE IF NOT EXISTS tpn_sub_answers (" +
            "  qid INTEGER NOT NULL," +
            "  answer text NOT NULL," +
            "  PRIMARY KEY (qid)" +
            ")";

    String QRY_TBL_REMINDER = "CREATE TABLE IF NOT EXISTS "+TBL_REMINDER+ "(" +
            " id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " title text NOT NULL," +
            " date text NOT NULL," +
            " fullDate text NOT NULL, " +
            " notificationId INT" +
            ")";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Creating database");
        db.execSQL(QRY_TBL_CATEGORY);
        db.execSQL(QRY_TBL_DRIVING_CENTERS);
        db.execSQL(QRY_TBL_QUESTION_IMAGES);
        db.execSQL(QRY_TBL_QUESTION_OPTIONS);
        db.execSQL(QRY_TBL_QUESTIONS);
        db.execSQL(QRY_TBL_SUB_ANSWERS);
        db.execSQL(QRY_TBL_REMINDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TBL_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_DRIVING_CENTERS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_QUESTION_IMAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_QUESTION_OPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_SUB_ANSWERS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REMINDER);
        onCreate(db);

    }
}
