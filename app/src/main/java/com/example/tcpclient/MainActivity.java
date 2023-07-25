package com.example.tcpclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TcpClient mTcpClient;
    TextView textView;
    Button connectBtn,sendbtn,stopBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectBtn=findViewById(R.id.connectBtn);
        sendbtn=findViewById(R.id.sendBtn);
        stopBtn=findViewById(R.id.stopBtn);
        textView=findViewById(R.id.textView);

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConnectTask().execute("");
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTcpClient != null) {
                    mTcpClient.sendMessage("testing");
                }
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTcpClient != null) {
                    mTcpClient.stopClient();
                }
            }
        });
        //textView.setText(mTcpClient.mServerMessage);

    }

    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {

            //we create a TCPClient object
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                    //for both sending and receiving
                    Log.e("received message:",message);
                    textView.setText(message);
                }
            });
            mTcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test:", "response: " + values[0]);
            //process server response here....

        }
    }
}