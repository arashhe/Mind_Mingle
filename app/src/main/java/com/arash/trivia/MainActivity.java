package com.arash.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arash.trivia.controller.AppController;
import com.arash.trivia.data.AnswerListAsyncResponce;
import com.arash.trivia.data.Repository;
import com.arash.trivia.databinding.ActivityMainBinding;
import com.arash.trivia.model.Question;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String MESSAGE_ID = "message_prefs";
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    List<Question> questionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID , MODE_PRIVATE);
        currentQuestionIndex = sharedPreferences.getInt("questionNumber" , 0);
        questionList = new Repository().getQuestions(new AnswerListAsyncResponce() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                binding.questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());

                updateCounter(questionArrayList);

                //Log.i("TAG", "1"+questionArrayList);
            }
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               nextQuestion();
            }
        });
        binding.buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                nextQuestion();
            }
        });
        binding.buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                nextQuestion();
            }
        });



        //Log.i("TAG", "1");
        Cat c = new Cat();
        c.age = 10;
        c.name = "YO YO";
        c.makeNoise();

    }

    private void nextQuestion() {
        currentQuestionIndex = (currentQuestionIndex +1 ) % questionList.size();
        //currentQuestionIndex = (currentQuestionIndex +1 );
        updateQuestion();
    }

    private void checkAnswer(boolean userChooseCorrect) {
        boolean answer = questionList.get(currentQuestionIndex).isAnswerTrue();
        int snackMessageId = 0 ;
        if(answer == userChooseCorrect){
            snackMessageId = R.string.correct_answer;
            fadeAnimation();
        }else{
            snackMessageId = R.string.incorrect;
            shakeAnimation();
        }
        Snackbar.make(binding.cardView , snackMessageId , Snackbar.LENGTH_SHORT).show();

    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.textViewOutOf.setText(String.format(getString(R.string.text_formatted)
                , currentQuestionIndex, questionArrayList.size()));
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        binding.questionTextView.setText(question);
        updateCounter((ArrayList<Question>) questionList);
    }
    private void fadeAnimation (){

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f , 0.0f);
        alphaAnimation.setDuration(200);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                binding.questionTextView.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                binding.questionTextView.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void shakeAnimation(){

        Animation shake = AnimationUtils.loadAnimation(MainActivity.this , R.anim.shake_animation);
        binding.cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("questionNumber", currentQuestionIndex);
        editor.apply();


    }
}