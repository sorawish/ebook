package com.example.sorawish.myapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.list);
        String[] values = new String[] {
                "Functional Web Development with Elixir, OTP, and Phoenix",
                "A Common-Sense Guide to Data Structures and Algorithms",
                "Rails, Angular, Postgres, and Bootstrap, Second Edition",
                "Effective Testing with RSpec 3",
                "Design It!",
                "Scalable Cloud Ops with Fugue",
                "Swift Style",
                "iOS 10 SDK Development",
                "The Cucumber Book, Second Edition",
                "Take My Money",
                "tmux 2",
                "Programming Elixir 1.3",
                "Test-Driving JavaScript Applications",
                "Agile Web Development with Rails 5",
                "The Way of the Web Tester"
        };


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

}
