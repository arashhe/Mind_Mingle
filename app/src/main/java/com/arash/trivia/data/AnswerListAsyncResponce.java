package com.arash.trivia.data;

import com.arash.trivia.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponce {
    void processFinished(ArrayList<Question> questionArrayList);
}
