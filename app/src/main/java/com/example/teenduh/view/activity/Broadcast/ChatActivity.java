package com.example.teenduh.view.activity.Broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.teenduh.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    public static final String ACTION_MESSSAGE = "message";

    ListView listView;
    EditText textMessage;
    Button buttonSend;

    ArrayAdapter<String> adapter;
    List<String> listMessage = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = findViewById(R.id.list_view);
        textMessage = findViewById(R.id.text_message);
        buttonSend = findViewById(R.id.button_send);

        listMessage.add("Hello");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMessage);
        listView.setAdapter(adapter);

        buttonSend.setOnClickListener(v -> {
            String message = textMessage.getText().toString();
            //send data
            Intent intent = new Intent();
            intent.setAction(ACTION_MESSSAGE);
            intent.putExtra("message", message);
            sendBroadcast(intent);
        });

        MessageReceiver receiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_MESSSAGE);
        registerReceiver(receiver, filter);

    }

    //receive data
    class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case ACTION_MESSSAGE:
                    String message = intent.getStringExtra("message");
                    listMessage.add(message);
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    }
}