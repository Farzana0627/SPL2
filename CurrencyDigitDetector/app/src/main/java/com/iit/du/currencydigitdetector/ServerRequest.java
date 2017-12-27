package com.iit.du.currencydigitdetector;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.google.api.client.http.HttpResponse;
import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by naush on 26-Apr-16.
 */
public class ServerRequest {

    ProgressDialog dialog;
    public static final int CONNECTION_TIMEOUT = 6000;
    public static final String Server_Address = "http://10.255.6.83/";

    public ServerRequest(Context context){

        dialog = new ProgressDialog(context);
        dialog.setCancelable(true);
        dialog.setTitle("Loading");
        dialog.setMessage("Please Wait!");
    }

    public void storeUserDataInBackground(User user, GetUserCallBack callBack){
        dialog.show();
        new StoreUserDataAsyncTask(user, callBack).execute();
    }

    public void storeLogDataAsyncTask(Data data, User user, GetDataCallBack callBack){
        dialog.show();
        new StoreLogdataAsyncTask(data, user, callBack).execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallBack callBack){

        dialog.show();
        new FetchUserDataAsyncTask(user, callBack).execute();
    }

    public void fetchLogDataInBackground(Data data, User user,GetDataCallBack callBack){
        dialog.show();
        new fetchLogDataAsynctask(data, user, callBack).execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void>{

        User user;
        GetUserCallBack userCallBack;

        public StoreUserDataAsyncTask(User user, GetUserCallBack callBack){
            this.user = user;
            this.userCallBack = callBack;
        }
        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("userName", user.userId));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            dataToSend.add(new BasicNameValuePair("email", user.email));

            HttpParams httpRequestParam = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParam);
            HttpPost post = new HttpPost(Server_Address + "cdd/" +"Resister.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            }catch (Exception e){
                    e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            userCallBack.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public class StoreLogdataAsyncTask extends AsyncTask<Void, Void, Void>{

        Data data;
        User getuserData;
        GetDataCallBack getDataCallBack;
        public StoreLogdataAsyncTask(Data data, User user, GetDataCallBack callBack) {
            this.data = data;
            this.getuserData = user;
            this.getDataCallBack = callBack;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("userName", getuserData.userId));
            dataToSend.add(new BasicNameValuePair("logName", data.logName));
            dataToSend.add(new BasicNameValuePair("date", data.date.toString()));
            dataToSend.add(new BasicNameValuePair("amount", ("" + data.amount)));

            HttpParams httpRequestParam = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParam);
            HttpPost post = new HttpPost(Server_Address + "cdd/" +"StoreData.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            getDataCallBack.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public class fetchLogDataAsynctask extends AsyncTask<Void, Void, Data>{

        Data data;
        User getuserData;
        GetDataCallBack getDataCallBack;

        public fetchLogDataAsynctask(Data data, User user, GetDataCallBack callBack) {
            this.data = data;
            this.getuserData = user;
            this.getDataCallBack = callBack;
        }

        @Override
        protected Data doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("userName", getuserData.userId));
            dataToSend.add(new BasicNameValuePair("logName", data.logName));

            HttpParams httpRequestParam = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParam);
            HttpPost post = new HttpPost(Server_Address + "cdd/" +"FetchLogData.php");

            Data returnedData = null;
            try {

                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                org.apache.http.HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.length() == 0) {
                    returnedData = null;
                } else {

                    String date = jsonObject.getString("date");
                    String amount = jsonObject.getString("amount");

                    returnedData = new Data(data.logName, date, amount);
                }
            }
            catch (Exception e){
                    e.printStackTrace();
                }

            return returnedData;
        }

        @Override
        protected void onPostExecute(Data data) {
            dialog.dismiss();
            getDataCallBack.done(data);
            super.onPostExecute(data);
        }

        public Data returnData() {
            return data;
        }
    }

    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {

        User user;
        GetUserCallBack userCallBack;

        public FetchUserDataAsyncTask(User user, GetUserCallBack callBack) {
            this.user = user;
            this.userCallBack = callBack;
        }

        @Override
        protected User doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("userName", user.userId));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpRequestParam = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParam);
            HttpPost post = new HttpPost(Server_Address +"cdd/"+"FetchUserData.php");

            User returnedUser = null;
            try {

                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                org.apache.http.HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.length() == 0){
                    returnedUser = null;
                }
                else {

                    String email = jsonObject.getString("Email");
                    returnedUser = new User(user.userId, user.password, email);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            dialog.dismiss();
            userCallBack.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }


}
