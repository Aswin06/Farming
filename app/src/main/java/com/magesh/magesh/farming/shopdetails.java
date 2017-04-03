package com.magesh.magesh.farming;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by magesh on 07-02-2017.
 */
public class shopdetails extends AppCompatActivity {
TextView shopname,ownername,address,city,state,emailid,password,mobile;
String shopname1,ownername1,address1,city1,state1,emailid1,password1,mobile1;
String name,shopemail;
    HttpPost httppost;
    Button seeds;
    InputStream is = null;
    StringBuffer buffer;
    public ProgressDialog pDialog;

    private ListView listView;
    String result="";
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
String seedname,price1;
    int code=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopdetails);
        shopname=(TextView)findViewById(R.id.textView69);
        ownername=(TextView)findViewById(R.id.textView71);
        address=(TextView)findViewById(R.id.textView73);
        city=(TextView)findViewById(R.id.textView75);
        state=(TextView)findViewById(R.id.textView77);
        emailid=(TextView)findViewById(R.id.textView79);
        mobile=(TextView)findViewById(R.id.textView81);
        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        shopemail=intent.getExtras().getString("email");
        new Thread(new Runnable() {
            public void run() {
                login1();
            }
        }).start();
        seeds=(Button)findViewById(R.id.button47);
        seeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(shopdetails.this,seedsavail.class);
                intent.putExtra("name",name);
                intent.putExtra("email",emailid1);
                startActivity(intent);
            }
        });

    }
    public  String login1(){
        try{
            httpclient=new DefaultHttpClient();
            httppost= new HttpPost("http://192.168.43.66/android/shopdetails.php/");
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("shopname5",name));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("log_tag", "connection success");
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection"+ e.toString());
            Toast.makeText(getApplicationContext(), "Connection fail", Toast.LENGTH_SHORT).show();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

            result = sb.toString();
            Log.e("log_tag", result);

        } catch (Exception e) {
            Log.e("log_tag", "Error converting result" + e.toString());
            Toast.makeText(getApplicationContext(), "Input reading fail", Toast.LENGTH_SHORT).show();

        }
        try {
            JSONArray jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length() - 1; i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                shopname1= String.valueOf(json_data.getString("shopname"));
                ownername1= String.valueOf(json_data.getString("ownername"));
                address1= String.valueOf(json_data.getString("address"));
                city1= String.valueOf(json_data.getString("city"));
                state1= String.valueOf(json_data.getString("state"));
                emailid1= String.valueOf(json_data.getString("email"));
                mobile1= String.valueOf(json_data.getString("mobile"));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shopname.setText(shopname1);
                        ownername.setText(ownername1);
                        address.setText(address1);
                        city.setText(city1);
                        state.setText(state1);
                        emailid.setText(emailid1);
                        mobile.setText(mobile1);
                    }
                });}
        }
        catch (JSONException e) {
            Log.e("log_tag", "Error parsing data" + e.toString());
            Toast.makeText(getApplicationContext(), "JsonArray fail", Toast.LENGTH_SHORT).show();
        }
return null;    }
}
