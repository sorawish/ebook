package com.example.sorawish.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.OrdersCustomAdapter;
import Data.Book;
import Data.RealBookRepository;

/**
 * Created by Clunctia on 5/27/2017.
 */

public class OrdersActivity extends AppCompatActivity implements BookView {
    private BookPresenter presenter;
    private RealBookRepository repository;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_orders);

        repository = RealBookRepository.getInstance();
        presenter = new BookPresenter(repository, this);

        initializeShowMoney();
        initializeListView();
    }

    public void back(View view){ finish();}

    public void initializeShowMoney(){
        TextView textView = (TextView) findViewById(R.id.show_amount_user_orders_textview);
        String text = String.format("%.2f", presenter.getUser().getMoney());
        textView.setText(text);
    }

    public void initializeListView(){
        ListView listView = (ListView) findViewById(R.id.show_orders_listview);
        System.out.println(presenter.getUser().getCart().getHistory().toString());
        OrdersCustomAdapter ordersCustomAdapter = new OrdersCustomAdapter(presenter.getUser().getCart().getHistory(), presenter, this);
        listView.setAdapter(ordersCustomAdapter);
    }

    @Override
    public void displayList(ArrayList<Book> books) {
        initializeListView();
        initializeShowMoney();
    }

    @Override
    public void createDialog(int b) {

    }
}
