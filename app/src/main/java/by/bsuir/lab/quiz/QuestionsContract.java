package by.bsuir.lab.quiz;

import android.provider.BaseColumns;

public final class QuestionsContract {

    public QuestionsContract() {}

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String[] OPTIONS = {COLUMN_OPTION1, COLUMN_OPTION2, COLUMN_OPTION3, COLUMN_OPTION4};
        public static final String COLUMN_ANSWER_NUMBER = "answer_number";
    }
}
