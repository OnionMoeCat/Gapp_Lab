package com.tejas.login;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.unity3d.player.UnityPlayer;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

public class LoginManager extends BroadcastReceiver {
	
    private ProgressDialog pDialog;
    private static final String LOGIN_URL = "http://155.101.206.136/webservice/login.php";

    //JSON element ids from response of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    
    JSONParser jsonParser = new JSONParser();
    
    public static String currentUser;

	public static void Login(String unityClass, String username, String password)
	{
		Activity currentActivity = UnityPlayer.currentActivity;
        Intent intent = new Intent(currentActivity, LoginManager.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("unityClass", unityClass);
        currentActivity.sendBroadcast(intent);	       
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		String unityClass = intent.getStringExtra("unityClass");
    	String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        new AttemptLogin(context, unityClass).execute(username, password);        
	}

	
    class AttemptLogin extends AsyncTask<String, String, String> {

    	private Context m_context;
    	private String m_unityClass;
    	
    	public AttemptLogin(Context context, String unityClass)
    	{
    		m_context = context;
    		m_unityClass = unityClass;
    	}    	
    	
        @Override
        protected String doInBackground(String... args){
            int success;

            try{
                //Building Parameters
                HashMap params = new HashMap();
                params.put("username", args[0]);
                params.put("password", args[1]);               

                
                Log.d("params", params.toString());
                Log.d("username : password ", args[0] + " : " + args[1]);
                
                Log.d("Request!", "Starting");
                //Attempting login & getting response
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                
                //Log.d("Login result", json.toString());

                success = json.getInt(TAG_SUCCESS);
                if(success == 1){
                    Log.d("Login Successful", json.toString());

                    currentUser = args[0];
                    
                    HashMap params2 = new HashMap();
                    params2.put("patient", args[0]);
                    
                    JSONObject json2 = jsonParser.makeHttpRequest("http://155.101.206.136/webservice/getmeds.php", "POST", params2);
                    Log.d("Json2", json2.toString());
                    JSONArray mMedsList = json2.getJSONArray("meds");
                    JSONObject json3 = mMedsList.getJSONObject(0);
                    Log.d("Json3", json3.toString());
                    UpdateDB updateDB = new UpdateDB();
                    updateDB.fetchAndUpdate(m_context);
                    
                    return json3.toString();
                    //return json.toString();
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch(JSONException e){
                e.printStackTrace();
            }

            return null;
        }
        
        protected  void onPostExecute(String i_file){
        	
            Class<?> unityClassActivity = null;
    		try {
    			unityClassActivity = Class.forName(m_unityClass);
    		} catch (ClassNotFoundException e) {
    			e.printStackTrace();
    		}
    		UnityPlayer.UnitySendMessage("JavaMessageReceiver", "JavaMessage", i_file);
            Intent intent = new Intent(m_context, unityClassActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            m_context.startActivity(intent);
        }
    }
}
