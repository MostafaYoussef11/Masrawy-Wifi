package com.example.masrawynet.masrawywi_fi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ganesh.intermecarabic.Arabic864;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class PrinterBluetooth {
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
  // ProgressDialog dialog;
    Activity  activity;
    //Context context;
    QRGEncoder qrgEncoder;
    public PrinterBluetooth( Activity activity ) {
     //  this.context = context;
        this.activity = activity;

    }


    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public void setDatePrint(String datePrint) {
        this.datePrint = datePrint;
    }

    public void setNumCard(String numCard) {
        this.numCard = numCard;
    }

    public void setPric(String pric) {
        this.pric = pric;
    }

    public void setNumhour(String numhour) {
        this.numhour = numhour;
    }

    public void setNumberCard(String numberCard) {
        NumberCard = numberCard;
    }

    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    String id_card , numCard , pric , numhour , datePrint , NumberCard , Downstream;
    int imagePrint;

    public void setDownstream(String downstream) {
        Downstream = downstream;
    }

    public void setImagePrint(int imagePrint) {
        this.imagePrint = imagePrint;
    }

    void findBT() {
        //dialog = new ProgressDialog(context);
        //dialog.show();
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter == null) {
              // dialog.setMessage("No bluetooth adapter available");
               // Toast.makeText(context, "No bluetooth adapter available", Toast.LENGTH_SHORT).show();
            }

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                    .getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // MP300 is the name of the bluetooth printer device07-28 13:20:10.946  10461-10461/com.example.printer E/sex﹕ C4:73:1E:67:29:6C
                    /*07-28 13:20:10.946  10461-10461/com.example.printer E/sex﹕ E8:99:C4:FF:B1:AC
                    07-28 13:20:10.946  10461-10461/com.example.printer E/sex﹕ 0C:A6:94:35:11:27*/

                    /*Log.e("sex",device.getName());*/
                    if (device.getName().equals("printer001")) {
                        /*Log.e("sex1",device.getAddress());*/
                        mmDevice = device;
                        break;
                    }
                }
            }
         //   dialog.setMessage("Bluetooth Device Found");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    public void openBT() throws IOException {
        try {
            findBT();
            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            //dialog.setMessage("Bluetooth Opened");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                             //   myLabel.setText(data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void sendData() throws IOException {
       // String PLogo ="";
       // String Pid ="";
       // String Pdate = "";
        //String Ptime = "";
        //String Pprice = "" ;
        //String nHour = "";
        //String PnumCard = "";
        try {
            String line = "\n";
            String ThreeLine = "\n \n \n ";
            String astrics = "******************************";
            String id = " Id_Card     : "+id_card;
            String Logo = "*       Masrawy Wi-Fi        *";
            String date = " Date        : "+ new SimpleDateFormat("dd - MM - yyyy",Locale.ENGLISH).format(new Date());
            SimpleDateFormat sdf =  new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+02:00"));
            String time = " Time        : " + sdf.format(new Date());
            String nprice= " Price       : "+pric;
            String nhor = " The session : " + numhour;
            String dwonst = " Downstream  : "+Downstream;
            byte[] bb3 = new byte[]{0x1B,0x21,0x10};
            byte[] left = new byte[]{0x1B, 'a', 0x00};
            byte[] center = new byte[]{0x1b, 'a', 0x01};
            byte[] mFormat = new byte[]{27, 33, 0};
            printPhoto(R.drawable.tst);
            mmOutputStream.write(line.getBytes());
            printPhoto(R.drawable.td);
            mmOutputStream.write(PrinterCommands.bb);
           // mmOutputStream.write(center);
            mmOutputStream.write(astrics.getBytes());
            mmOutputStream.write(left);
            printNewLine();
           // mmOutputStream.write(PrinterCommands.SELECT_FONT_A);
            mmOutputStream.write(id.getBytes());
            mmOutputStream.write(line.getBytes());
            mmOutputStream.write(date.getBytes());
            mmOutputStream.write(line.getBytes());
            mmOutputStream.write(time.getBytes());
            mmOutputStream.write(line.getBytes());

            mmOutputStream.write(nprice.getBytes());
            mmOutputStream.write(line.getBytes());
//            for (int i = nhor.length()-1 ; i >=0 ;i--){
//                nHour = nHour+ nhor.charAt(i);
//            }
            mmOutputStream.write(nhor.getBytes());
            mmOutputStream.write(line.getBytes());
            mmOutputStream.write(dwonst.getBytes());
            mmOutputStream.write(line.getBytes());

            mmOutputStream.write(bb3);
            mmOutputStream.write(center);
            mmOutputStream.write(astrics.getBytes());
            mmOutputStream.write(line.getBytes());
            mmOutputStream.write(numCard.getBytes());
            mmOutputStream.write(line.getBytes());
            mmOutputStream.write(astrics.getBytes());
            mmOutputStream.write(line.getBytes());
            //PrintQrCode(NumberCard);
            printPhoto(imagePrint);
          //  mmOutputStream.write(PrinterCommands.FEED_LINE);
            mmOutputStream.write(bb3);
            mmOutputStream.write(center);
          //  mmOutputStream.write(line.getBytes());
            mmOutputStream.write(astrics.getBytes());
            mmOutputStream.write(line.getBytes());
            printPhoto(R.drawable.st);
            mmOutputStream.write(line.getBytes());
            // mmOutputStream.write(PrinterCommands.cc);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
      //  mmOutputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
    }

   public void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //print photo
    public void printPhoto(int img) {
        try {
            Resources res = activity.getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res,
                    img);
          //  Bitmap bm = BitmapFactory.decodeResource(,img);
            if(bmp!=null){
                byte[] command = Utils.decodeBitmap(bmp);
//                byte[] bb3 = new byte[]{0x1B,0x21,0x10};
//                //  byte[] left = new byte[]{0x1B, 'a', 0x00};
//                byte[] center = new byte[]{0x1b, 'a', 0x01};
//                mmOutputStream.write(bb3);
           //     mmOutputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                mmOutputStream.write(PrinterCommands.bb2);
                printText(command);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }
    public void PrintQrCode(String cardnum) throws WriterException {
        String login = "http://10.0.0.1/login?username="+cardnum+"&password=";
        int width = 300;
        int height = 320;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        qrgEncoder = new QRGEncoder(
                login, null,
                QRGContents.Type.TEXT,
                smallerDimension);
        Bitmap bitmap = qrgEncoder.encodeAsBitmap();
        byte[] command = Utils.decodeBitmap(bitmap);
        try {
            byte[] align =new byte[]{0x1B, 'a', 0x02};    //this is right alignment

            byte[] bb3 = new byte[]{0x1B,0x21,0x10};
           byte[] left = new byte[]{0x1B, 'a', 0x00};
            byte[] center = new byte[]{0x1b, 'a', 0x01};
            //mmOutputStream.write(PrinterCommands.bb2);
            mmOutputStream.write(align);
        } catch (IOException e) {
            e.printStackTrace();
        }
        printText(command);



    }
    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            mmOutputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void printText(byte[] msg) {
        try {
            // Print normal text
            mmOutputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print new line
    private void printNewLine() {
        try {
            mmOutputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void printUnicode(){
        try {
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(Utils.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

}