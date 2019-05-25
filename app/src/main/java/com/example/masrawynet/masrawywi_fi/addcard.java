package com.example.masrawynet.masrawywi_fi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

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
import java.net.URLConnection;
import java.net.URLEncoder;



public class addcard extends AsyncTask<String , Void , String> {
//    AlertDialog dialog ;
//
    Context context ;
    public addcard( Context context) {
        this.context = context ;
    }

    @Override
    protected void onPreExecute() {
//        dialog = new AlertDialog.Builder(context).create();
//        dialog.setTitle("جاري الاستقبال ... ");
    }

    @Override
    protected void onPostExecute(String s) {
//            dialog.setMessage(s);
//            dialog.show();
        if(s.equals("insert Successful ....")){
            Toast.makeText(context, "جاري الاستقبال ...", Toast.LENGTH_SHORT).show();

        }
        else{

            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected String doInBackground(String... params) {
        String id = params[0];
        String numcard = params[1];
        String CardType = params[2];
        String num_hour =  params[3];
        String result = "";

        try {
            String connstr = "http://10.0.0.13/addcard.php";
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
           String data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")
             +"&&"+URLEncoder.encode("numcard","UTF-8")+"="+URLEncoder.encode(numcard,"UTF-8")
             +"&&"+URLEncoder.encode("price","UTF-8")+"="+URLEncoder.encode(CardType,"UTF-8")
             +"&&"+URLEncoder.encode("numhour","UTF-8")+"="+URLEncoder.encode(num_hour,"UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();
            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            String line ="";
            while ((line = reader.readLine()) != null)
            {
                result += line;
            }
            reader.close();
            ips.close();
            http.disconnect();
            return result;
        } catch (MalformedURLException e) {
                result = e.getMessage();
        } catch (IOException e) {
                result = e.getMessage();
        }

        return result;
    }
}

