package com.example.apicazione;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // creating variables for our edit text
    private EditText courseIDEdt;

    // creating variable for button
    private Button getCourseDetailsBtn;

    // creating variable for card view and text views.
    private CardView courseCV;
    private TextView titolo, descrizione, durata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing all our variables.
        titolo = findViewById(R.id.idTVCourseName);
        descrizione = findViewById(R.id.idTVCourseDescription);
        durata = findViewById(R.id.idTVCourseDuration);
        getCourseDetailsBtn = findViewById(R.id.idBtnGetCourse);
        courseIDEdt = findViewById(R.id.idEdtCourseId);
        courseCV = findViewById(R.id.idCVCOurseItem);

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
                getCourseDetails(courseIDEdt.getText().toString());
            }
        });
    }

    private void getCourseDetails(String titoloReq) {
        InetAddress ip = null;
        try {
            ip = Inet4Address.getByName("192.168.0.153");
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
                try {
                    // on below line passing our response to json object.
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are checking if the response is null or not.
                    if (jsonObject.getString("titolo") == "null") {
                        // displaying a toast message if we get error
                        Toast.makeText(MainActivity.this, "FILM NON TROVATO", Toast.LENGTH_SHORT).show();
                    } else {
                        // if we get the data then we are setting it in our text views in below line.
                        titolo.setText("TITOLO: "+jsonObject.getString("titolo"));
                        descrizione.setText("DESCRIZIONE: " +jsonObject.getString("descrizione"));
                        durata.setText("DURATA: "+jsonObject.getString("durata")+" minuti");
                        courseCV.setVisibility(View.VISIBLE);
                    }
                    // on below line we are displaying
                    // a success toast message.
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    }
}
