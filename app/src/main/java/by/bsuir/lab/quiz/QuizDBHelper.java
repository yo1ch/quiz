package by.bsuir.lab.quiz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import by.bsuir.lab.quiz.QuestionsContract.*;
import by.bsuir.lab.quiz.ResultsContract.*;

import java.util.ArrayList;
import java.util.Collections;

public class QuizDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "quiz";
    public static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
        //onUpgrade(this.getWritableDatabase(), 0, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NUMBER + " INTEGER )";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        final String SQL_CREATE_RESULTS_TABLE = "CREATE TABLE " +
                ResultsTable.TABLE_NAME + " ( " +
                ResultsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ResultsTable.COLUMN_NAME + " TEXT, " +
                ResultsTable.COLUMN_CORRECT_ANSWERS + " INTEGER )";

        db.execSQL(SQL_CREATE_RESULTS_TABLE);

        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ResultsTable.TABLE_NAME);
        onCreate(db);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        for (int i = 0; i < QuestionsTable.OPTIONS.length; i++)
            cv.put(QuestionsTable.OPTIONS[i], question.getAnswers().get(i).getAnswer());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public void addResult(Result result) {
        ContentValues cv = new ContentValues();
        cv.put(ResultsTable.COLUMN_NAME, result.getName());
        cv.put(ResultsTable.COLUMN_CORRECT_ANSWERS, result.getCorrectAnswers());
        db.insert(ResultsTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Много ли людей выживает после удара молнии?", "Большинство", "Почти никто", "Больше половины", "Меньше половины");
        addQuestion(q1);
        Question q2 = new Question("Кто первый запатентовал телефон?", "Александр Белл","Томас Эдисон","Альберт Эйнштейн","Стивен Хокинг");
        addQuestion(q2);
        Question q3 = new Question("Олимпийское кольцо какого цвета соответствует Европе?", "Синего", "Красного","Желтого","Черного");
        addQuestion(q3);
        Question q4 = new Question("В какой из этих категорий Нобелевская премия не присуждается?", "Математика", "Литература","Медицина","Химия");
        addQuestion(q4);
        Question q5 = new Question("Спутником какой планеты является Харон?", "Плутон", "Венера","Марс","Юпитер");
        addQuestion(q5);
        Question q6 = new Question("Какая птица умеет летать задом наперед?", "Колибри", "Киви","Малая качурка","Косатка");
        addQuestion(q6);
        Question q7 = new Question("Каким животным разрешен вход в масульманскую мечеть?", "Кошка", "Собака","Мышь","Баран");
        addQuestion(q7);
        Question q8 = new Question("Какой океан ранее назывался Гиперборейским?", "Северный Ледовитый океан", "Тихий","Индийский","Южный");
        addQuestion(q8);
        Question q9 = new Question("Где убили Юлия Цезаря?", "В сенате", "Дома","На площади","На корабле");
        addQuestion(q9);
        Question q10 = new Question("Сколько костей в ухе?", "3", "5","12","8");
        addQuestion(q10);
    }

    @SuppressLint("Range")
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionsList = new ArrayList<>();
        String[] Projection = {
                QuestionsTable._ID,
                QuestionsTable.COLUMN_QUESTION,
                QuestionsTable.COLUMN_OPTION1,
                QuestionsTable.COLUMN_OPTION2,
                QuestionsTable.COLUMN_OPTION3,
                QuestionsTable.COLUMN_OPTION4,
                QuestionsTable.COLUMN_ANSWER_NUMBER,
        };
        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question(
                    c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)),
                    c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)),
                    c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)),
                    c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)),
                    c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                questionsList.add(question);
            } while (c.moveToNext());
        }
        c.close();

        return questionsList;
    }

    @SuppressLint("Range")
    public ArrayList<Result> getAllResults() {
        ArrayList<Result> resultsList = new ArrayList<>();
        String[] Projection = {
                ResultsTable._ID,
                ResultsTable.COLUMN_NAME,
                ResultsTable.COLUMN_CORRECT_ANSWERS,
        };
        Cursor c = db.query(
                ResultsTable.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Result result = new Result();
                result.setName(c.getString(c.getColumnIndex(ResultsTable.COLUMN_NAME)));
                result.setCorrectAnswers(c.getInt(c.getColumnIndex(ResultsTable.COLUMN_CORRECT_ANSWERS)));
                resultsList.add(result);
            } while (c.moveToNext());
        }
        c.close();

        Collections.sort(resultsList);

        return resultsList;
    }
}
