package com.dasl.android.carebird.app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * Created by Brian on 5/17/2014.
 */
public class Database {
    private static final String BASE_URL = "https://caredb.herokuapp.com";

    public class User {
        private String userName;
        private String password;
        private String firstName;
        private String lastName;
        private transient  String token;

        public User(String userName, String password, String firstName, String lastName){
            this.userName = userName;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public void setToken(String token){
            this.token = token;
        }
        public String getUserName(){
            return this.userName;
        }
        public String getPassword(){
            return this.password;
        }
        public String getFirstName(){
            return this.firstName;
        }
        public String getLastName(){
            return this.lastName;
        }
        public String getToken(){
            return this.token;
        }
    }

    public static Status login (User user) throws IOException{
        //HttpPost post = new HttpPost(BASE_URL);
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(BASE_URL + "/login.php");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("UName", user.getUserName()));
        urlParameters.add(new BasicNameValuePair("Pass", user.getPassword()));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        return new Status(response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
    }
}
