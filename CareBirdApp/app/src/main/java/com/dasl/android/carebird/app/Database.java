package com.dasl.android.carebird.app;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 5/17/2014.
 */
public class Database {
    private static final String BASE_URL = "https://caredb.herokuapp.com";
    public static User me;

    /** login will accept a User object and will check the username and password with the webserver.
     * Upon a successful user/pass combination, the User object held by the Database class will be updated
     * with a SessionToken. No additional manipulation will be required by android developers.*/
    public static Status login(User user) throws IOException{

        me = user;

        //HttpPost post = new HttpPost(BASE_URL);
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(BASE_URL + "/login.php");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("UName", me.getUserName()));
        urlParameters.add(new BasicNameValuePair("Pass", me.getPassword()));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        //Gson token = new Gson();
        /*
        if(response.getEntity() != null){
            //token.toJson(EntityUtils.toString(response.getEntity(),"UTF-8"));
            //Log.v("carebird", token.toString());
            System.out.println(EntityUtils.toString(response.getEntity(),"UTF-8"));
        }
        else
            Log.v("carebird1", "data is null");
        */

        String responseString=(EntityUtils.toString(response.getEntity(),"UTF-8"));

        responseString=responseString.substring(1,responseString.length()-1);
        me.setToken(responseString);


        return new Status(response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
    }

    /**login will use the User labeled 'me' instantiated above */
    public static Status login() throws IOException{
        return login(me);
    }

    /** This method is intended to be called by the CareReceiver's phone after generating a
     *  PermissionToken when it generates a QRCode.
     *  addCareGiver will accept a PermissionToken generated by the careReceiver's phone.
     *  This will be sent to the webServer with the SessionToken of whoever is currently logged in
     *  where they will live for a short time waiting for a careGiver to come along and correctly
     *  guess the PermissionToken.*/
    public static Status addCareGiver(String permissionToken)throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(BASE_URL + "/addCareGiver.php");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("SessionToken", me.getToken()));
        urlParameters.add(new BasicNameValuePair("PermissionToken", permissionToken));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        //responseString is either "true" or "false"
        String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");
        System.out.println("ResponseString: "+responseString);
        return new Status(response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
    }

    /** This method is intended to be called by the CareGiver's phone after receiving a
     *  PermissionToken from the CareReceiver's phone.
     *  addCareReceiver will accept a PermissionToken generated by the careReceivers phone.
     *  This will be sent to the webServer with the SessionToken of whoever is currently logged in
     *  where they will look for a matching PermissionToken.
     *  When the Tokens match, the webservice will create the relationship
     */
    public static Status addCareReceiver(String permissionToken)throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(BASE_URL + "/addCareReceiver.php");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("SessionToken", me.getToken()));
        urlParameters.add(new BasicNameValuePair("PermissionToken", permissionToken));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        //responseString is either "true" or "false"
        String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");

        return new Status(response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
    }

    /** addUser will accept a User object and will check for the existence of that user in the Database
     * Upon successful account creation, a new user will be added to the users table on the webservice.
     * The new user will automatically be logged in*/
    public static Status addUser(User newUser)throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(BASE_URL + "/addUser.php");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("UName", newUser.getUserName()));
        urlParameters.add(new BasicNameValuePair("Pass", newUser.getPassword()));
        urlParameters.add(new BasicNameValuePair("FName", newUser.getFirstName()));
        urlParameters.add(new BasicNameValuePair("LName", newUser.getLastName()));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        //responseString is either "true" or "false"
        String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");
        if(!responseString.equals("false")){
            login(newUser);
        }

        return new Status(response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
    }

    /** This method accepts a ReminderSchedule object and
     * assumes the reminder is intended for the currently logged in user */
    public static Status addReminderSchedule(ReminderSchedule reminder)throws IOException{
        return addReminderSchedule(reminder,me);
    }

    /** This method accepts a ReminderSchedule object and
     * assumes the reminder is intended for the passed User object (a careReceiver)
     * When it speaks with the webserver it will also use the currently logged in user (a careGiver)
     * So that the webServer can check the Permissions*/
    public static Status addReminderSchedule(ReminderSchedule reminder, User user)throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(BASE_URL + "/addReminderSchedule.php");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("CareGiverSessionToken", me.getToken()));
        urlParameters.add(new BasicNameValuePair("CareReceiverSessionToken", user.getToken()));
        urlParameters.add(new BasicNameValuePair("name", String.valueOf(reminder.getName()) ));
        urlParameters.add(new BasicNameValuePair("minute", String.valueOf(reminder.getMinute()) ));
        urlParameters.add(new BasicNameValuePair("hour", String.valueOf(reminder.getHour()) ));
        //urlParameters.add(new BasicNameValuePair("interval", String.valueOf(reminder.getInterval()) ));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        //responseString is either "true" or "false"
        String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");

        return new Status(response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
    }


    /** This method will return an arraylist of ReminderSchedules associated with the currently logged in user */
    public static ArrayList<ReminderSchedule> getReminderSchedules()throws IOException{
        return getReminderSchedules(me);
    }

    /** This method will return an arraylist of ReminderSchedules associated with the passed User object
     * When it speaks with the webserver it will also use the currently logged in user (a careGiver)
     * So that the webServer can check the Permissions*/
    public static ArrayList<ReminderSchedule> getReminderSchedules(User careReceiver)throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(BASE_URL + "/getReminderSchedules.php");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("SessionToken", me.getToken()));
        urlParameters.add(new BasicNameValuePair("CRName", careReceiver.getUserName()));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        // This string needs to be converted with gson into an ArrayList<ReminderSchedules>
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        if (!responseString.equals("false")) {
            ReminderSchedule[] reminders = new Gson().fromJson(responseString, ReminderSchedule[].class);
            //new Gson().fromJson(responseString, ReminderSchedule[].class);
            //User[] careGivers = new Gson().fromJson(responseString, User[].class);
            ArrayList<ReminderSchedule> temp = new ArrayList<ReminderSchedule>();
            for(ReminderSchedule reminder:reminders)
                temp.add(reminder);
            return temp;
        }
        return new ArrayList<ReminderSchedule>();
    }

    /** This method will return an arraylist of User objects
     *  representing the careReceiver's careGivers*/
    public static ArrayList<User> getCareGivers(User careReceiver)throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(BASE_URL + "/getCareGivers.php");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        if(me.getToken().equals(""))
            login();
        urlParameters.add(new BasicNameValuePair("SessionToken", me.getToken()));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        // This string needs to be converted with gson into an ArrayList<CareGivers>
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        if(!responseString.equals("false")) {
            //return new Gson().fromJson(responseString, new TypeToken<ArrayList<User>>() {}.getType());
            System.out.println(responseString);
            User[] careGivers = new Gson().fromJson(responseString, User[].class);
            ArrayList<User> temp = new ArrayList<User>();
            for(User careGiver:careGivers)
                temp.add(careGiver);
            return temp;
        }else
            return new ArrayList<User>();
    }

    /** This method will return an arraylist of User objects
     *  representing the careGiver's careReceivers*/
    public static ArrayList<User> getCareReceivers(User careGiver)throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(BASE_URL + "/getCareReceivers.php");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("SessionToken", me.getToken()));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        // This string needs to be converted with gson into an ArrayList<ReminderSchedules>
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        if(responseString != "false") {
            return new Gson().fromJson(responseString
                    , new TypeToken<ArrayList<User>>() {}.getType());
        }
        return new ArrayList<User>();
    }


    /** METHODS BELOW THIS LINE DO NOT HAVE AN EQUIVALENT ON THE WEBSERVER
     *  THEY ARE PROTOTYPES BASED ON EXPECTED FUNCTIONALITY
     */

    /** This is the same concept as addReminderSchedule, only it will remove the ReminderSchedule */
    public static Status removeReminderSchedule(ReminderSchedule reminder)throws IOException{
        return removeReminderSchedule(reminder,me);
    }

    /** This is the same concept as addReminderSchedule, only it will remove the ReminderSchedule */
    public static Status removeReminderSchedule(ReminderSchedule reminder, User careReceiver)throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(BASE_URL + "/addCareReceiver.php");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("SessionToken", me.getToken()));
        urlParameters.add(new BasicNameValuePair("CRName", careReceiver.getUserName()));
        urlParameters.add(new BasicNameValuePair("name", String.valueOf(reminder.getName())));
        urlParameters.add(new BasicNameValuePair("minute", String.valueOf(reminder.getMinute()) ));
        urlParameters.add(new BasicNameValuePair("hour", String.valueOf(reminder.getHour()) ));
        //urlParameters.add(new BasicNameValuePair("interval", String.valueOf(reminder.getInterval()) ));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        String jsonResponseString = EntityUtils.toString(response.getEntity(),"UTF-8");

        return new Status(response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
    }
}
