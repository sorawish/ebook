package com.example.sorawish.myapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import Data.MockUpBook;

public class MainActivity extends Activity {
    ListView listView ;
    MockUpBook mockUpBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mockUpBook = new MockUpBook();

        listView = (ListView) findViewById(R.id.list);
        String [] values = mockUpBook.getMockUp();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int itemPosition     = position;

                String  itemValue    = (String) listView.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });
    }

    public void onSaveInstanceState(){

    }

    private String loadBookJson(){
        String result = "";
        try{
            URL jittatUrl = new URL(" https://theory.cpe.ku.ac.th/~jittat/courses/sw-spec/ebooks/books.json");
            URLConnection connection = jittatUrl.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = "";
            while((inputLine = in.readLine() )!= null){
                System.out.println(inputLine);
                in.close();
                return null;
            }
            return result;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public void onStart(){
        super.onStart();

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onRestart(){
        super.onRestart();
    }






}
