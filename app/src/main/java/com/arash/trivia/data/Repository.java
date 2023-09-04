package com.arash.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.arash.trivia.controller.AppController;
import com.arash.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    ArrayList <Question> questionArrayList = new ArrayList<>();
//    String url = "https://jsonplaceholder.typicode.com/todos";
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";


    public List<Question> getQuestions(final AnswerListAsyncResponce  callBack) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("TAG", "2.0");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Question question = new Question(response.getJSONArray(i).getString(0)
                                ,response.getJSONArray(i).getBoolean(1));

                        questionArrayList.add(question);

                        //Log.d( "onResponse: ",""+questionArrayList);


                        //Log.i(String.valueOf(i), "onResponse: ");
//                        Log.i("TAG", "onResponse: "+response.getJSONArray(i).getString(0));
//                        Log.i("TAG", "onResponse: "+response.getJSONArray(i).getBoolean(1));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(callBack != null){
                    callBack.processFinished(questionArrayList);
                }
                //Log.d("TAG" , ""+response.toString());
                //Log.i("TAG", "2");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TAG", "3");
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);


        return questionArrayList;
    }
}
