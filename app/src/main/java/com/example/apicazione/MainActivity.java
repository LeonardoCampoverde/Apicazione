package com.example.apicazione;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private ArrayList <CardItems> films;


    // creating variables for our edit text
    private EditText courseIDEdt;

    // creating variable for button
    private Button getCourseDetailsBtn;

    // creating variable for card view and text views.
    private CardView courseCV;
    private TextView titolo, descrizione, durata;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         lista= findViewById(R.id.list_view);

        CustomAdapter customAdapter = new CustomAdapter(
                MainActivity.this, films
        );

        // initializing all our variables.
        getCourseDetailsBtn = findViewById(R.id.idBtnGetCourse);
        courseIDEdt = findViewById(R.id.idEdtCourseId);


        // adding click listener for our button.
        getCourseDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if the id text field is empty or not.
                if (TextUtils.isEmpty(courseIDEdt.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Introduci il titolo del film", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling method to load data.
                //getCourseDetails(courseIDEdt.getText().toString());
                omdapi(courseIDEdt.getText().toString().replace(" ","+"));
            }
        });
    }


    public void setAdapter(ArrayList<CardItems> list){
        CustomAdapter c = new CustomAdapter(MainActivity.this,list);
        lista.setAdapter(c);


    }

    public void omdapi(String req){
        String api="3df18acb";
        String url = "http://www.omdbapi.com/?apikey="+api+"&t="+req;


        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                films = new ArrayList<CardItems>();
                JSONObject fulljsonObject = null;





                try {
                    //jsonObject= arr.getJSONObject(i);

                    fulljsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    Log.d("MIAO","ENTRATO DO WHILE ON RESPONSE");
                    try {
                        Log.d("MIAO","ENTRATO TRY");




                        // on below line we are checking if the response is null or not.
                        if (fulljsonObject.getString("Title") == "null") {
                            Log.d("MIAO","STRINGA TITOLO NULL");
                            // displaying a toast message if we get error
                        } else {

                            // if we get the data then we are setting it in our text views in below line.
                            Log.d("MIAO", "PRENDENDO DATI");
                            String stringaTitolo = "TITOLO: " + fulljsonObject.getString("Title");
                            String stringaDescrizione ="DESCRIZIONE: " + fulljsonObject.getString("Plot");
                            String stringaDurata="DURATA: " + fulljsonObject.getString("Runtime") + " minuti";
                            films.add(new CardItems(stringaTitolo,stringaDescrizione,stringaDurata));
                            Log.d("MIAO","TITOLO:"+stringaTitolo);
                        }

                        // on below line we are displaying
                        // a success toast message.


                    } catch (JSONException e) {
                        Log.d("MIAO","JSON EXEPCION"+e);
                        e.printStackTrace();
                    }

                setAdapter(films);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(MainActivity.this, "Fail to get course" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key and value pair to our parameters.

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
        Log.d("MIAO","RICHIESTA INVIATA");
    }



    private void getCourseDetails(String titoloReq) {
        Log.d("MIAO","COURSE DETAILS");

        InetAddress ip = null;
        try {
            ip = Inet4Address.getByName("192.168.43.178");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // url to post our data
        String url = "http://"+ip.toString()+"/distributed/index.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MIAO","RICHIESTA OTTENUTA RISPOSTA");
                films = new ArrayList<CardItems>();
                    int i =0;
                    int length=0;
                JSONObject fulljsonObject = null;





                try {
                    //jsonObject= arr.getJSONObject(i);

                    fulljsonObject = new JSONObject(response);
                    length = fulljsonObject.length()-2;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                do {
                    Log.d("MIAO","ENTRATO DO WHILE ON RESPONSE");
                        try {
                            JSONObject jsonObject = fulljsonObject.getJSONObject(Integer.toString(i));

                            // on below line passing our response to json object.


                            Log.d("MIAO","SETTATA LENGTH: "+length);
                            // on below line we are checking if the response is null or not.
                            if (jsonObject.getString("titolo") == "null") {
                                Log.d("MIAO","STRINGA TITOLO NULL");
                                // displaying a toast message if we get error
                                if(i==0)
                                    Toast.makeText(MainActivity.this, "NESSUN FILM TROVATO", Toast.LENGTH_SHORT).show();
                            } else {

                                // if we get the data then we are setting it in our text views in below line.
                                String stringaTitolo = "TITOLO: " + jsonObject.getString("titolo");
                                String stringaDescrizione ="DESCRIZIONE: " + jsonObject.getString("descrizione");
                                String stringaDurata="DURATA: " + jsonObject.getString("durata") + " minuti";
                                films.add(new CardItems(stringaTitolo,stringaDescrizione,stringaDurata));
                                Log.d("MIAO",stringaTitolo);
                            }

                            // on below line we are displaying
                            // a success toast message.


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        i++;
                    Log.d("MIAO","FINITO CICLO: "+ i + " MASSIMO: "+length);
                    }while(i<length);
                setAdapter(films);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(MainActivity.this, "Fail to get course" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key and value pair to our parameters.
                params.put("titolo", titoloReq);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
        Log.d("MIAO","RICHIESTA INVIATA");
    }



}
