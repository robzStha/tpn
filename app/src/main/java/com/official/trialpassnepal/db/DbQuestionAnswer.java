package com.official.trialpassnepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.official.trialpassnepal.objects.AnswerObject;
import com.official.trialpassnepal.objects.CategoryObject;
import com.official.trialpassnepal.objects.Question;
import com.official.trialpassnepal.objects.QuestionImage;
import com.official.trialpassnepal.objects.QuestionObject;
import com.official.trialpassnepal.objects.QuestionOption;
import com.official.trialpassnepal.objects.SubAnswer;
import com.official.trialpassnepal.objects.SubQstnAnsObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SlowhandJr. on 4/4/16.
 */
public class DbQuestionAnswer {

    SQLiteDatabase db;
    DbHelper dbHelper;

    Context context;

    public DbQuestionAnswer(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() {
        db = dbHelper.getWritableDatabase();
    }

    private void close() {
        db.close();
    }

    public ArrayList<QuestionObject> getQuestion(String offset, String limit) {
        String query = "Select * from tpn_category where published=1 limit " + offset + "," + limit;
        open();
        Cursor cursor = db.rawQuery(query, null);
        return loadQuestions(cursor);
    }

    public ArrayList<SubQstnAnsObject> getSubQstnAns(){
        String query = "Select tpn_questions.qid, tpn_questions.question, tpn_sub_answers.answer from tpn_questions  left join tpn_sub_answers on tpn_questions.qid = tpn_sub_answers.qid where tpn_questions.published = 1 and tpn_questions.qtype= 1";
        open();
        Cursor cursor = db.rawQuery(query, null);
        return loadSubQuestionAns(cursor);
    }

    private ArrayList<SubQstnAnsObject> loadSubQuestionAns(Cursor cursor) {
        ArrayList<SubQstnAnsObject> subQstnAnsObjects = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            SubQstnAnsObject subQstnAnsObject = new SubQstnAnsObject();
            subQstnAnsObject.setQid(Integer.parseInt(cursor.getString(cursor.getColumnIndex("qid"))));
            subQstnAnsObject.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
            subQstnAnsObject.setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
//            System.out.println("Cat id: " + questionObj.catId + " cat Name: " + questionObj.catName + " cat Desc: " + questionObj.catDesc);
            subQstnAnsObjects.add(subQstnAnsObject);
            cursor.moveToNext();
        }
        return subQstnAnsObjects;
    }

    public ArrayList<QuestionObject> getQuestions(String type) {
        String query = "Select tpn_questions.qid, tpn_questions.question, tpn_question_images.imagePath from tpn_questions left join tpn_question_images on tpn_questions.qid = tpn_question_images.qid where tpn_questions.published = 1 and tpn_questions.qtype= " + type+" Order by RANDOM() limit 5";
//        select * from tpn_questions 0;

        open();
        Cursor cursor = db.rawQuery(query, null);
        return loadQuestions(cursor);
    }

    public ArrayList<QuestionObject> getQuestions() {
        String query = "Select tpn_questions.qid, tpn_questions.question, tpn_question_images.imagePath from tpn_questions left join tpn_question_images on tpn_questions.qid = tpn_question_images.qid where tpn_questions.published = 1 and tpn_questions.qtype= 0";
//        select * from tpn_questions 0;

        open();
        Cursor cursor = db.rawQuery(query, null);
        return loadQuestions(cursor);
    }

    public ArrayList<AnswerObject> getQuestionOptions(int id) {
        String query = "Select * from tpn_question_options where qid = " + id;
        open();
        Cursor cursor = db.rawQuery(query, null);
        return loadQuestionOptions(cursor);
    }

    private ArrayList<AnswerObject> loadQuestionOptions(Cursor cursor) {
        ArrayList<AnswerObject> alAnswerObj = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AnswerObject answerObject = new AnswerObject();
            answerObject.setOptionsid(Integer.parseInt(cursor.getString(cursor.getColumnIndex("optionsid"))));
            answerObject.setAnswer(cursor.getString(cursor.getColumnIndex("ansOptions")));
            String temp = cursor.getString(cursor.getColumnIndex("validOption"));
            Boolean btemp = temp.equals("0")? false:true;
            answerObject.setCorrectAnswer(btemp);
//            System.out.println("Cat id: " + questionObj.catId + " cat Name: " + questionObj.catName + " cat Desc: " + questionObj.catDesc);
            alAnswerObj.add(answerObject);
            cursor.moveToNext();
        }
        close();
        return alAnswerObj;
    }

    private ArrayList<QuestionObject> loadQuestions(Cursor cursor) {

        ArrayList<QuestionObject> alQuestionObj = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            QuestionObject questionObj = new QuestionObject();
            questionObj.setQid(Integer.parseInt(cursor.getString(cursor.getColumnIndex("qid"))));
            questionObj.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
            questionObj.setImgLoc(cursor.getString(cursor.getColumnIndex("imagePath")));
//            System.out.println("Cat id: " + questionObj.catId + " cat Name: " + questionObj.catName + " cat Desc: " + questionObj.catDesc);
            alQuestionObj.add(questionObj);
            cursor.moveToNext();
        }
        close();
        return alQuestionObj;

    }


    public void insertQuestions(ArrayList<Question> questions) {
        open();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        try {
            for (Question question : questions) {
                contentValues.put("qid", question.getQid());
                contentValues.put("question", question.getQuestion());
                contentValues.put("qtype", question.getQtype());
                contentValues.put("published", question.getPublished());
                db.insert("tpn_questions", null, contentValues);
                contentValues.clear();
                System.out.println("Alquestaaa: " + question.getQuestion() + " - qid: " + question.getQid());
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        close();
    }

    public void insertQuestionOptions(ArrayList<QuestionOption> questionOptions){
        open();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        try {
            for (QuestionOption questionOption : questionOptions) {
                contentValues.put("qid", questionOption.getQid());
                contentValues.put("optionsid", questionOption.getOptionsid());
                contentValues.put("ansOptions", questionOption.getAnsOptions());
                contentValues.put("validOption", questionOption.getValidOption());
                db.insert("tpn_question_options", null, contentValues);
                contentValues.clear();
                System.out.println("Options: " + questionOption.getAnsOptions() + " - qid: " + questionOption.getQid());
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        close();
    }

    public void insertQuestionImages(ArrayList<QuestionImage> questionImages){
        open();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        try {
            for (QuestionImage questionImage : questionImages) {
                contentValues.put("qid", questionImage.getQid());
                contentValues.put("catid", questionImage.getCatid());
                contentValues.put("imagePath", questionImage.getImagePath());
                db.insert("tpn_question_images", null, contentValues);
                contentValues.clear();
                System.out.println("Options: " + questionImage.getImagePath() + " - qid: " + questionImage.getQid());
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        close();
    }

    public void truncate() {
        open();
        db.delete("tpn_questions", null, null);
        db.delete("tpn_question_options", null, null);
        db.delete("tpn_question_images", null, null);
        db.delete("tpn_sub_answers", null, null);
        close();
    }

    public void insertSubAnswers(ArrayList<SubAnswer> subAnswers) {
        open();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        try {
            for (SubAnswer subAnswer : subAnswers) {
                contentValues.put("qid", subAnswer.getQid());
                contentValues.put("answer", subAnswer.getAnswer());
                db.insert("tpn_sub_answers", null, contentValues);
                contentValues.clear();
                System.out.println("Options: " + subAnswer.getAnswer() + " - qid: " + subAnswer.getQid());
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        close();
    }
}
