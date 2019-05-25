package com.example.masrawynet.masrawywi_fi;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import java.net.URLEncoder;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    LinearLayout title , linearEdtxt;
    Animation downToUp , upToDown ;
    EditText EduserName , EdPassword;
    TextView mostafa;
    Button login;
   // String username , password;
   // Typeface matthew;
//   checkuser checkuser;
//

//    BluetoothSocket bluetoothSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (LinearLayout) findViewById(R.id.line1Title);
        linearEdtxt = (LinearLayout) findViewById(R.id.linearEdtxt);
        EduserName = (EditText)findViewById(R.id.eduser);
        EdPassword = (EditText) findViewById(R.id.edpass);
        downToUp = AnimationUtils.loadAnimation(this,R.anim.down);
        upToDown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        mostafa = (TextView) findViewById(R.id.mostafa);
        //set animation Titile Linear
        title.setAnimation(downToUp);
        // set animation Username and Passwor Edit text
        linearEdtxt.setAnimation(upToDown);
        // login Method
      //  FindBlt();
//        checkuser = new checkuser();
        login = (Button)findViewById(R.id.BtnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  username = EduserName.getText().toString();
                String password = EdPassword.getText().toString();
               // checkuser.execute(username,password);
                new checkuser().execute(username,password);
            }
        });

    }
//    public void login(View view){
//      String  username = EduserName.getText().toString();
//      String password = EdPassword.getText().toString();
//        checkuser.execute(username,password);
//    }


    public class checkuser extends AsyncTask<String , Void , String> {
        String balance = "";
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            if(s.equals(" Login Successful ....")){
                Intent intent = new Intent(MainActivity.this , CardActivity.class);
                balance = MainActivity.this.EduserName.getText().toString();
                intent.putExtra("username",balance);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
             //   System.out.print(balance);
            }
            else {
                MainActivity.this.EdPassword.setText("");
                MainActivity.this.EduserName.setText("");

            }

        }

        @Override
        protected String doInBackground(String... params) {
            String user = params[0];
            String pass = params[1];
            String result = "";

            try {
                String connstr = "http://10.0.0.13/login.php";
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")
                        +"&&"+URLEncoder.encode("Secrat","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
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

}
