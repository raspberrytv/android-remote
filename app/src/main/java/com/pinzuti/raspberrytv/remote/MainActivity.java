package com.pinzuti.raspberrytv.remote;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


public class MainActivity extends ActionBarActivity {
    private Socket socket;
    {
        try {
            socket = IO.socket("http://192.168.1.21:3000");
        } catch (URISyntaxException e) {}
    }

    private Emitter.Listener onStarted = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Intent intent = new Intent(MainActivity.this, YoutubeActivity.class);
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        socket.connect();

        socket.on("started", onStarted);

        ListView listView = (ListView) findViewById(R.id.homeList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                socket.emit("start", "youtube");
            }
        });

    }
}
