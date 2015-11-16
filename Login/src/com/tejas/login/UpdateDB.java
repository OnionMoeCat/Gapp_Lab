package com.tejas.login;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by u0923408 on 11/6/2015.
 */
public class UpdateDB {
    //php Login scrip location:
    private static final String GETMEDS_URL = "http://155.101.206.136/webservice/getmeds.php";


    public UpdateDB(){

    }

    public boolean fetchAndUpdate(Context context){
        //Get the username for current patient.
        String currentUser = LoginManager.currentUser;

        HashMap params = new HashMap();
        params.put("patient", currentUser);

        //ArrayList<HashMap<String, String>> mMedsList = new ArrayList<HashMap<String, String>>();
        JSONArray mMedsList = null;

        Log.d("Request!", "Starting");
        //Attempting to get information.
        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(GETMEDS_URL, "POST", params);
        try{
            int success = json.getInt("success");

            if(success == 1)
            {
                Log.d("Got Data Successfully!", json.toString());
                // Init DB stuff.
                MedListDBHelper MLDBHelper = new MedListDBHelper(context);
                SQLiteDatabase db = MLDBHelper.getWritableDatabase();

                mMedsList = json.getJSONArray("meds");
                ContentValues values = new ContentValues();

                for (int i =0; i < mMedsList.length(); i++)
                {
                    JSONObject jObj = mMedsList.getJSONObject(i);

                    values.put(MedListContract.MedList.COLUMN_MED_NAME, jObj.getString("med"));
                    values.put(MedListContract.MedList.COLUMN_AMOUNT, jObj.getString("amount"));
                    values.put(MedListContract.MedList.COLUMN_UNIT, jObj.getString("unit"));
                    values.put(MedListContract.MedList.COLUMN_ROUTE, jObj.getString("route"));
                    values.put(MedListContract.MedList.COLUMN_FREQUENCY, jObj.getString("frequency"));
                    values.put(MedListContract.MedList.COLUMN_INSTRUCTIONS, jObj.getString("instructions"));
                    values.put(MedListContract.MedList.COLUMN_UPDATED, jObj.getString("updated"));
                }

                long newRowId = db.insert(MedListContract.MedList.TABLE_NAME, null, values);
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return true;
    }
}
