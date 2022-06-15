package com.example.credenciais.Async;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONParserAsyncTask extends AsyncTask<Void, Void, String> {
        private final TextView mtextView;
        private final String mUrl;

        public JSONParserAsyncTask(TextView textView, String url) {
            mtextView = textView;
            mUrl = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            return getJSONTemp(mUrl);
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
            mtextView.setText(strings);
        }

        private String getJSONTemp(String url) {
            HttpURLConnection c = null;
            try {
                URL u = new URL(url);
                c = (HttpURLConnection) u.openConnection();
                c.connect();
                int status = c.getResponseCode();
                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();

                        return jsonParseGetTemp(sb.toString());
                }

            } catch (Exception ex) {
                return ex.toString();
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        //disconnect error
                    }
                }
            }
            return "Falha";
        }

        private String jsonParseGetTemp(String result) throws JSONException {
            JSONObject jObject = new JSONObject(result);
            JSONObject jObject2 = jObject.getJSONObject("current_weather");
            String temperatura = jObject2.getString("temperature");
            return temperatura + " ºC";
        }


}
