package com.official.trialpassnepal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SlowhandJr. on 4/4/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_tpn.db";
    public static final int DB_VER = 5;
    Context context;
    public static final String TBL_CATEGORY = "tpn_category";
    public static final String TBL_DRIVING_CENTERS = "tpn_driving_centers";
    public static final String TBL_QUESTIONS = "tpn_questions";
    public static final String TBL_QUESTION_IMAGES = "tpn_question_images";
    public static final String TBL_QUESTION_OPTIONS = "tpn_question_options";
    public static final String TBL_SUB_ANSWERS = "tpn_sub_answers";

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

//    String QRY_INSERT_TBL_QUESTIONS = "INSERT INTO tpn_questions (qid, question, qtype, published, created, edited) VALUES" +
//            "(1, 'प्राथमिक उपचार भन्नाले के बुझिन्छ ?', 1, 1, '1144830654', '1448306549')," +
//            "(2, 'सवारी चालक अनुमतिप्रत्र म्याद सकिएको कति दिन भित्र नबिकरण गर्नुपर्छ ?', 1, 1, '1448306549', '1448306549')," +
//            "(3, 'सवारी चालक अनुमतिपत्र प्राप्त नगरेको व्यक्तिले सवारी चलाएमा के हुन्छ ?', 1, 1, '1448306549', '1448306549')," +
//            "(4, 'क्लचको काम के हो ?', 1, 1, '1448306549', '1448306549')," +
//            "(5, 'सवारी प्रदूषण भनेको के हो ?', 1, 1, '1448306549', '1448306549')," +
//            "(6, 'जेब्राक्रसिङ केलाई भनिन्छ ?', 1, 1, '1448306549', '1448306549')," +
//            "(7, 'सवारी चालक अनुमतिपत्र साथमा नराखी सवारी चलाएमा कतिसम्म जरिवाना हुन सक्छ ?', 1, 1, '1448306549', '1448306549')," +
//            "(8, 'तीन कुने आकारको ट्राफिक संकेतलाई के भनिन्छ ?', 1, 1, '1448306549', '1448306549')," +
//            "(9, 'रातो स्टिकर भएको सवारी नेपालको कुन – कुन भागमा चलाउन पाउँदैन ?', 1, 1, '1448306549', '1448306549')," +
//            "(10, 'सवारी साधन दुर्घटना भएको देखेमा के गर्ने ? कुनै चार तरिका लेख्नुहोस् |', 1, 1, '1448306549', '1448306549')," +
//            "(11, 'गाडीको कागजपत्र कहिले नबिकरण गराइसक्नुपर्छ ?', 0, 1, '1448306549', '1448306549')," +
//            "(12, 'बा १ स कुन सवारी साधन हो ?', 0, 1, '1448306549', '1448306549')," +
//            "(13, 'सवारी चलाउँदा के सेवन गर्नु हुँदैन ?', 0, 1, '1448306549', '1448306549')," +
//            "(14, 'पहेंलो नम्बर प्लेटले कस्तो सवारीलाई जनाउँछ ?', 0, 1, '1448306549', '1448306549')," +
//            "(15, 'कार्बुरेटर कुन इन्जिनमा हुन्छ ?', 0, 1, '1448306549', '1448306549')," +
//            "(16, 'जेब्रा क्रसमा .............................', 0, 1, '1448306549', '1448306549')," +
//            "(17, 'बिग्रेको सवारी चलाएमा के हुन्छ ?', 0, 1, '1448306549', '1448306549')," +
//            "(18, 'पहिरो गएको बेलामा के गर्ने ?', 0, 1, '1448306549', '1448306549')," +
//            "(19, 'तलकामध्ये कुन गियर बढी शक्तिशालि हुन्छ ?', 0, 1, '1448306549', '1448306549')," +
//            "(20, 'यस्तो ट्राफिक चिन्हको अर्थ के हो ?', 0, 1, '1448306549', '1448306549')," +
//            "(21, 'यस्तो ट्राफिक चिन्हको अर्थ के हो ?', 1, 1, '1448306549', '1448306549')," +
//            "(22, 'यस्तो ट्राफिक चिन्हको अर्थ के हो ?', 1, 1, '1448306549', '1448306549')," +
//            "(23, 'यस्तो ट्राफिक चिन्हको अर्थ के हो ?', 1, 1, '1448306549', '1448306549')," +
//            "(24, 'यस्तो ट्राफिक चिन्हको अर्थ के हो ?', 1, 1, '1448306549', '1448306549')," +
//            "(25, 'यस्तो ट्राफिक चिन्हको अर्थ के हो ?', 1, 1, '1448306549', '1448306549')";
//
//    String QRY_INSERT_TBL_CATEGORY = "INSERT INTO tpn_category (catid, categoryName, categoryDescription, published, created, edited) VALUES" +
//            "(1, 'प्रतिबन्धात्मक चिन्हहरु', 'प्रतिबन्धात्मक चिन्हहरु', 1, '1448306549', '1448306549')," +
//            "(2, 'सचेतात्मक चिन्हहरु', 'सचेतात्मक चिन्हहरु', 1, '1448306549', '1448306549')," +
//            "(3, 'सूचनामूलक चिन्हहरु', 'सूचनामूलक चिन्हहरु', 1, '1448306549', '1448306549')";
//
//    String QRY_INSERT_TBL_QUESTION_IMAGES = "INSERT INTO tpn_question_images (qid, catid, imagePath) VALUES" +
//            "(20, 1, 'trafficsigns/regulatory/stop-and-go.png')," +
//            "(21, 2, 'trafficsigns/warning/chaubato.png')," +
//            "(22, 2, 'trafficsigns/warning/dhisko.png')," +
//            "(23, 3, 'trafficsigns/information/dead-end.png')," +
//            "(24, 3, 'trafficsigns/information/parking.png')," +
//            "(25, 3, 'trafficsigns/information/bus-bisauni.png')";
//
//    String QRY_INSERT_TBL_QUESTION_OPTIONS = "INSERT INTO tpn_sub_answers (qid, answer) VALUES" +
//            "(1, 'अस्पताल पुर्याउनु अघि दुर्घटना भएकै ठाउँमा घाइतेलाई दिइने उपचारलाई प्राथमिक उपचार भनिन्छ |')," +
//            "(2, 'सवारी चालक अनुमतिपत्रको म्याद सकिएको ९० दिन भित्र |')," +
//            "(3, 'सवारी चालक अनुमतिपत्र प्राप्त नगरेको व्यक्तिले सवारी चलाएमा रु. ५०० देखि रु. २००० सम्म जरिवाना हुन्छ |')," +
//            "(4, 'क्लचको काम इन्जिन र गियर बक्सको सम्बन्ध जोड्ने र छुट्याउन')," +
//            "(5, 'सवारीबाट निस्कने ध्वनि तथा धुँवा नै सवारी प्रदूषण हो')," +
//            "(6, 'पैदल यात्रुलै सडक पार गर्नको लागि सडकमा कोरिएका रेखाहरुलाई जेब्राक्रसिङ भनिन्छ ')," +
//            "(7, 'रु. २५ देखि रु. २०० रुपैयाँसम्म जरिवाना हुन सक्छ |')," +
//            "(8, 'यस्ता प्रकारका ट्राफिक संकेतलाई सचेतात्मक ट्राफिक संकेत भनिन्छ')," +
//            "(9, 'रिङरोडभित्र चलाउन पाउँदैन')," +
//            "(10, '१. घाइतेको उद्धार गर्ने \\r\\n२. नजिकको प्रहरीमा खबर गर्ने \\r\\n३. प्राथमिक उपचार गर्ने \\r\\n४. घाइतेको आफन्तहरुलाई खबर गर्ने \\r\\n')," +
//            "(21, 'चौबाटो (अगाडी शाखा सडक)')," +
//            "(22, 'बाटोमा ढिस्को आउँदैछ')," +
//            "(23, 'बाटोको अन्त')," +
//            "(24, 'पार्किङ गर्ने ठाउँ')," +
//            "(25, 'बस बिसौनी')";
//
//    String QRY_INSERT_TBL_DRIVING_CENTERS = "INSERT INTO tpn_driving_centers (dcid, dcName, dcProprietor, dcAddress, dcContactno, latitude, longitude, published, created, edited) VALUES" +
//            "(1, 'श्री इन्द्रायणी ड्राइभिङ सेन्टर', 'नातिबाबु श्रेष्ठ', 'जोरपाटी', '०१-४९११५२५, ९८४००५१९१९', 27.7331, 85.3809, 1, '1448306549', '1448306549')," +
//            "(2, 'ब्लु मुन मोटर ड्राइभिङ सेन्टर', 'नवराज सत्याल', 'तिनकुने', '०१-४९११५२५', 27.6864, 85.3494, 1, '1448306549', '1448306549')," +
//            "(3, 'श्री विश्वकर्मा ड्राइभिङ सेन्टर', 'दिनेश मानन्धर', 'कालिमाटी', '९८४१२३६२९१', 27.6989, 85.2939, 1, '1448306549', '1448306549')," +
//            "(4, 'कान्ति भैरव ड्राइभिङ सेन्टर', 'अनिल गजुरेल', 'जयबागेश्वरी', '९८५१०१३५१७', 27.6857, 85.3473, 1, '1448306549', '1448306549')," +
//            "(5, 'एन.बी. ड्राइभिङ ट्रेनिङ सेन्टर', 'श्याम श्रेष्ठ', 'कोटेश्वर', '९८४१२४६६०३', 27.7116, 85.3418, 1, '1448306549', '1448306549')," +
//            "(6, 'हरि ओम मोटर ड्राइभिङ प्रा. लि.', 'शिव गौतम', 'तिनकुने', '९८४९६२३८५७', 27.6789, 85.3495, 1, '1448306549', '1448306549')";
//
//    String QRY_INSERT_TBL_SUB_ANSWERS = "INSERT INTO tpn_question_options (optionsid, qid, ansOptions, validOption) VALUES" +
//            "(1, 11, '(क) म्याद नागेको ९० दिन भित्र', 1)," +
//            "(2, 11, '(ख) ३० दिनभित्र', 0)," +
//            "(3, 11, '(ग) जहिले पनि', 0)," +
//            "(4, 11, '(घ) म्याद ननाघिकन', 0)," +
//            "(5, 12, '(क) निजि टेम्पो', 1)," +
//            "(6, 12, '(ख) निजि मोटरसाइकल', 0)," +
//            "(7, 12, '(ग) निजि कार', 0)," +
//            "(8, 12, '(घ) निजि माइक्रोबस', 0)," +
//            "(9, 13, '(क) लागु औषध', 0)," +
//            "(10, 13, '(ख) धुम्रपान', 0)," +
//            "(11, 13, '(ग) मदिरा सेवन', 0)," +
//            "(12, 13, '(घ) माथिका सबै', 1)," +
//            "(13, 14, '(क) संस्थानको', 1)," +
//            "(14, 14, '(ख) ट्राफिकको', 0)," +
//            "(15, 14, '(ग) पर्यटनको', 0)," +
//            "(16, 14, '(घ) निजि', 0)," +
//            "(17, 15, '(क) डिजेल इन्जिनर', 0)," +
//            "(18, 15, '(ख) पेट्रोल इन्जिन', 1)," +
//            "(19, 15, '(ग) मोबिल इन्जिन', 0)," +
//            "(20, 15, '(घ) ग्यास इन्जिन', 0)," +
//            "(21, 16, '(क) सवारीलाई स्पिड गर्ने', 0)," +
//            "(22, 16, '(ख) ओभरटेक गर्ने', 0)," +
//            "(23, 16, '(ग) गाडी स्लो गर्ने', 1)," +
//            "(24, 16, '(घ) हेडलाइट बाल्ने', 0)," +
//            "(25, 17, '(क) केहि पनि हुँदैन', 0)," +
//            "(26, 17, '(ख) पैसाको हिफाजत हुन्छ', 0)," +
//            "(27, 17, '(ग) खतरा', 1)," +
//            "(28, 17, '(घ) ट्राफिकले समात्छ', 0)," +
//            "(29, 18, '(क) पहिरो गएको ठाउँमा गाडी लैजाने', 0)," +
//            "(30, 18, '(ख) गाडीलाई सुरक्षित स्थानमा लैजाने', 1)," +
//            "(31, 18, '(ग) गाडीलाई तीव्र गतिमा चलाउने', 0)," +
//            "(32, 18, '(घ) माथिका सबै', 0)," +
//            "(33, 19, '(क) १ नं. गियर', 1)," +
//            "(34, 19, '(ख) ३ नं. गियर', 0)," +
//            "(35, 19, '(ग) ५ नं. गियर', 0)," +
//            "(36, 19, '(घ) ब्याक गियर', 0)," +
//            "(37, 20, '(क) खतरा', 1)," +
//            "(38, 20, '(ख) साँघुरो पुल', 0)," +
//            "(39, 20, '(ग) दोहोरोबाटो', 0)," +
//            "(40, 20, '(घ) कुनै पनि होइन', 0)";

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

//        db.execSQL(QRY_INSERT_TBL_CATEGORY);
//        db.execSQL(QRY_INSERT_TBL_DRIVING_CENTERS);
//        db.execSQL(QRY_INSERT_TBL_QUESTION_IMAGES);
//        db.execSQL(QRY_INSERT_TBL_QUESTION_OPTIONS);
//        db.execSQL(QRY_INSERT_TBL_QUESTIONS);
//        db.execSQL(QRY_INSERT_TBL_SUB_ANSWERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TBL_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_DRIVING_CENTERS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_QUESTION_IMAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_QUESTION_OPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_SUB_ANSWERS);
        onCreate(db);

    }
}
