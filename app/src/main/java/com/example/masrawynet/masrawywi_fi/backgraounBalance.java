package com.example.masrawynet.masrawywi_fi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.RequiresPermission;

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

public class backgraounBalance extends AsyncTask<String,Void,String> {
    String reslut = "";
    AlertDialog.Builder builder;
    Context context;

    public backgraounBalance(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(context);
        builder.setTitle("الاستعلام عن الرصيد");
        builder.setCancelable(true);
        builder.setNegativeButton("خروج", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.cancel();
            }
        });

    }

    @Override
    protected void onPostExecute(String s) {
            builder.setMessage("رصيدك الحالي :   "+ s);
            AlertDialog dialog = builder.create();
            dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        String idusr = strings[0];
        String connstr = "http://10.0.0.13/balance.php";
        try {
            URL url = new URL(connstr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data = URLEncoder.encode("idusr","UTF-8")+"="+URLEncoder.encode(idusr,"UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            outputStream.close();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1"));
            String line = "";
            while ((line = reader.readLine()) != null){
                reslut += line;
            }
            return reslut ;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reslut;
    }
}
