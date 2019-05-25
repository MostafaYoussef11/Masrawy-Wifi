package com.example.masrawynet.masrawywi_fi;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.Set;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;


public class CardActivity extends AppCompatActivity {
    Button Card3 , Card5 , Card10 , balnceBtn , Card15 , Card25 , Card50 , Card20 ,Card30 , Card100 ;
    String Price , num_hour , number_card , userid , Downstream;
    int imagePrint;
    PrinterBluetooth print ;
    FDataBackground fDataBackground;
    //BluetoothDevice bluetoothDevice;
    TextView textView , Balance;
    boolean isCreate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Card3 = (Button) findViewById(R.id.card3);
        Card5 = (Button) findViewById(R.id.card5);
        Card15 = (Button) findViewById(R.id.card15);
        Card10 = (Button) findViewById(R.id.card10);
        Card25 = (Button) findViewById(R.id.card25);
        Card50 = (Button) findViewById(R.id.card50);
        Card20 = (Button) findViewById(R.id.card20);
        Card30 = (Button) findViewById(R.id.card30);
        Card100 = (Button) findViewById(R.id.card100);
        balnceBtn = (Button) findViewById(R.id.balance);
        textView = (TextView) findViewById(R.id.mylable);
        print = new PrinterBluetooth(CardActivity.this);
        new PrinterBluetooth(this).findBT();
        userid = getIntent().getStringExtra("username").toString();

        new newBalance(textView).execute(userid);
        Card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double blance = Double.parseDouble(textView.getText().toString());
                if(blance < 3.50){
                    Toast.makeText(CardActivity.this, "رصيدك الحالي غير كافي ", Toast.LENGTH_SHORT).show();
                }
                else {
                    new cheacid().execute(3);
                  //  print = new PrinterBluetooth(CardActivity.this);
                    new newBalance(textView).execute(userid);
                }

            }
        });
        Card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double blance = Double.parseDouble(textView.getText().toString());
                if(blance < 5.00){
                    Toast.makeText(CardActivity.this, "رصيدك الحالي غير كافي ", Toast.LENGTH_SHORT).show();
                }
                else {
                    new cheacid().execute(5);
                  //  print = new PrinterBluetooth(CardActivity.this);
                    new newBalance(textView).execute(userid);
                }
            }
        });
        Card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double blance = Double.parseDouble(textView.getText().toString());
                if(blance < 10.00){
                    Toast.makeText(CardActivity.this, "رصيدك الحالي غير كافي ", Toast.LENGTH_SHORT).show();
                }
                else {
                    new cheacid().execute(10);
                    //       print = new PrinterBluetooth(CardActivity.this);
                    new newBalance(textView).execute(userid);
                }

            }
        });
        Card15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double blance = Double.parseDouble(textView.getText().toString());
                if(blance < 15.00){
                    Toast.makeText(CardActivity.this, "رصيدك الحالي غير كافي ", Toast.LENGTH_SHORT).show();
                }
                else {
                    new cheacid().execute(15);
             //       print = new PrinterBluetooth(CardActivity.this);
                    new newBalance(textView).execute(userid);
                }

            }
        });
        Card25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double blance = Double.parseDouble(textView.getText().toString());
                if(blance < 25.00){
                    Toast.makeText(CardActivity.this, "رصيدك الحالي غير كافي ", Toast.LENGTH_SHORT).show();
                }
                else {
                    new cheacid().execute(25);
                    //       print = new PrinterBluetooth(CardActivity.this);
                    new newBalance(textView).execute(userid);
                }

            }
        });
        Card20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double blance = Double.parseDouble(textView.getText().toString());
                if(blance < 20.00){
                    Toast.makeText(CardActivity.this, "رصيدك الحالي غير كافي ", Toast.LENGTH_SHORT).show();
                }
                else {
                    new cheacid().execute(20);
                    //       print = new PrinterBluetooth(CardActivity.this);
                    new newBalance(textView).execute(userid);
                }

            }
        });

        Card30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double blance = Double.parseDouble(textView.getText().toString());
                if(blance < 30.00){
                    Toast.makeText(CardActivity.this, "رصيدك الحالي غير كافي ", Toast.LENGTH_SHORT).show();
                }
                else {
                    new cheacid().execute(30);
                    //       print = new PrinterBluetooth(CardActivity.this);
                    new newBalance(textView).execute(userid);
                }

            }
        });



        Card50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double blance = Double.parseDouble(textView.getText().toString());
                if(blance < 50.00){
                    Toast.makeText(CardActivity.this, "رصيدك الحالي غير كافي ", Toast.LENGTH_SHORT).show();
                }
                else {
                    new cheacid().execute(50);
                    //       print = new PrinterBluetooth(CardActivity.this);
                    new newBalance(textView).execute(userid);
                }

            }
        });
        Card100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double blance = Double.parseDouble(textView.getText().toString());
                if(blance < 100.00){
                    Toast.makeText(CardActivity.this, "رصيدك الحالي غير كافي ", Toast.LENGTH_SHORT).show();
                }
                else {
                    new cheacid().execute(100);
                    //       print = new PrinterBluetooth(CardActivity.this);
                    new newBalance(textView).execute(userid);
                }

            }
        });
        balnceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new backgraounBalance(CardActivity.this).execute(userid);
            }
        });
    }

 public class cheacid extends AsyncTask<Integer,Void,String>{
        int typeCard = 0 ;
        AlertDialog.Builder builder;

        @Override
        protected void onPreExecute() {
            Toast.makeText(CardActivity.this,"جاري الارسال ..", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(String s) {
            if(isCreate) {
                Toast.makeText(CardActivity.this, "جاري الاستقبال ..", Toast.LENGTH_LONG).show();
                builder = new AlertDialog.Builder(CardActivity.this);
                builder.setTitle("طباعة الكارت");

                // set dialog message
                builder
                        .setMessage("هل ترغب في طباعة الكرت ")
                        .setCancelable(false);
                builder.setPositiveButton("طباعة", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        print.findBT();
                        try {
                            print.openBT();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            print.sendData();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.show();

            }


        }

        @Override
     protected String doInBackground(Integer... params) {
            typeCard = params[0];

            String reslut = "";
            String connstr = "http://10.0.0.13/checkid.php";
            URL url = null;
            try {

                url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setDoInput(true);
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
                reslut +=  inserdCardDb(reslut,typeCard);
                isCreate = true ;
               // itemsCard = new String[]{reslut,}
                return reslut;
            } catch (MalformedURLException e) {
                reslut = e.getMessage();
            } catch (IOException e) {
                reslut = e.getMessage();
            }


            return reslut;
        }

    }
    public String inserdCardDb (String id , int type){
        String resultadd = "";
        String connstr = "";
        switch (type){
            case 3 : connstr ="http://10.0.0.13/addcard3.php";break;
            case 5 : connstr ="http://10.0.0.13/addcard5.php";break;
            case 10 : connstr ="http://10.0.0.13/addcard10.php";break;
            case 15 : connstr ="http://10.0.0.13/addcard15.php";break;
            case 25 : connstr ="http://10.0.0.13/addcard25.php";break;
            case 20 : connstr ="http://10.0.0.13/addcard20.php";break;
            case 30 : connstr ="http://10.0.0.13/addcard30.php";break;
            case 50 : connstr ="http://10.0.0.13/addcard50.php";break;
            case 100 : connstr ="http://10.0.0.13/addcard100.php";break;
        }
        try {
            int num1 , num2 , num3  = 0;
            // Create card
            Random part1 = new Random();
            num1 = part1.nextInt(10000);
            Random part2 = new Random();
            num2 = part2.nextInt(10000);
            if(String.valueOf(num2).length() >= 4 ){
                num2 = Integer.parseInt(String.valueOf(num2).substring(1));
            }
            Random part3 = new Random();
            num3 = part3.nextInt(10000);
            number_card = ""+num1 + num2 + num3 ;
            //insert
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
            OutputStream ops = http.getOutputStream();
            //userid
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")
                    +"&&"+URLEncoder.encode("numcard","UTF-8")+"="+URLEncoder.encode(number_card,"UTF-8")
                    +"&&"+URLEncoder.encode("userid","UTF-8")+"="+URLEncoder.encode(userid,"UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();
            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            String line ="";
            while ((line = reader.readLine()) != null)
            {
                resultadd += line;
            }
            reader.close();
            ips.close();
            http.disconnect();
            String typecard = "/tool/user-manager/user/create-and-activate-profile " ;
            switch (type){
                case 3 : typecard = typecard + "customer='"+userid +"' profile=3h user="+number_card; num_hour = "3 Hour - 1 GB" ; Price = "3.50 EGP";Downstream = "1024 Kbps";imagePrint=R.drawable.crd3;
                    break;
                case 5: typecard =  typecard +"customer='"+userid +"' profile=12h user="+number_card; num_hour = "12 Hour - 2 GB" ; Price = "5.00 EGP";Downstream = "1024 Kbps";imagePrint=R.drawable.crd5;
                    break;
                case 15 : typecard = typecard +"customer='"+userid +"' profile=week user="+number_card; num_hour = "7 Day - 5 GB" ; Price = "15.00 EGP";Downstream = "2048 Kbps";imagePrint=R.drawable.crd15;
                    break;
                case 10 : typecard = typecard +"customer='"+userid +"' profile=3day user="+number_card; num_hour = "3 Day - 4 GB" ; Price = "10.00 EGP";Downstream = "1024 Kbps";imagePrint=R.drawable.crd10;
                    break;
                case 25 : typecard = typecard +"customer='"+userid +"' profile=2week user="+number_card; num_hour = "15 Day - 10 GB" ; Price = "25.00 EGP";Downstream = "2048 Kbps";imagePrint=R.drawable.crd25;
                    break;
                case 20 : typecard = typecard +"customer='"+userid +"' profile=1M5G user="+number_card; num_hour = "30 Day - 5 GB" ; Price = "20.00 EGP";Downstream = "2048 Kbps";imagePrint=R.drawable.crd20;
                    break;
                case 30 : typecard = typecard +"customer='"+userid +"' profile=1M10G user="+number_card; num_hour = "30 Day - 10 GB" ; Price = "30.00 EGP";Downstream = "2048 Kbps";imagePrint=R.drawable.crd30;
                    break;
                case 50 : typecard = typecard +"customer='"+userid +"' profile=1M30 user="+number_card; num_hour = "30 Day - 30 GB" ; Price = "50.00 EGP";Downstream = "3 Mbps";imagePrint=R.drawable.crd50;
                    break;
                case 100 : typecard = typecard +"customer='"+userid +"' profile=Masrawy user="+number_card; num_hour = "30 Day - 50 GB" ; Price = "100.00 EGP";Downstream = "6 Mbps";imagePrint=R.drawable.crd100;
                    break;
            }
            String add = "/tool/user-manager/user/add username=" + number_card + " customer='"+userid+"' caller-id-bind-on-first-use=yes shared-users=1" ;
            print.setId_card(id);
            print.setNumCard(num1 + "  " + num2 + "  " + num3);
            print.setNumhour(num_hour);
            print.setPric(Price);
            print.setImagePrint(imagePrint);
            print.setDownstream(Downstream);
            print.setNumberCard(number_card);
            ApiConnection con = ApiConnection.connect("10.0.0.1"); // connect to router
            con.setTimeout(5000); // set command timeout to 5 seconds
            con.login("admin","");
            con.execute(add + "\n");
            Thread.sleep(2000);
            con.execute(typecard);
            return resultadd;

        } catch (MalformedURLException e) {
            return resultadd;
        } catch (UnsupportedEncodingException e) {
            return resultadd;
        } catch (IOException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            return e.getMessage();
        } catch (MikrotikApiException e) {
            return e.getMessage();
        }


    }






}
