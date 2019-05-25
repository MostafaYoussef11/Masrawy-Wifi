package com.example.masrawynet.masrawywi_fi;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class FDataBackground extends AsyncTask<String,Void,String> {
    String reslut = "";

//    public String getReslut() {
//        return reslut;
//    }

    @Override
    protected void onPostExecute(String s) {
        this.reslut = s ;
    }

    @Override
    protected String doInBackground(String... strings) {
        String connstr =strings[0];
        String id = strings[1];
        URL url = null;
        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();
            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            String line ="";
            while ((line = reader.readLine()) != null)
            {
                reslut += line;
            }
            reader.close();
            ips.close();
            http.disconnect();
            return reslut;
        } catch (MalformedURLException e) {
            reslut = e.getMessage();
        } catch (IOException e) {
            reslut = e.getMessage();
        }


        return reslut;
    }
}
