package com.example.sorawish.myapplication;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.CartCustomAdapter;
import Data.Book;
import Data.RealBookRepository;

/**
 * Created by Clunctia on 4/27/2017.
 */

public class CartActivity extends AppCompatActivity implements BookView {
    private BookPresenter presenter;
    private RealBookRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        repository = RealBookRepository.getInstance();
        presenter = new BookPresenter(repository, this);
        initializeListview();
        initializeShowMoney();
    }

    public void initializeShowMoney() {
        TextView textView = (TextView) findViewById(R.id.show_amount_user_textview);
        String text = String.format("%.2f", presenter.getUser().getMoney());
        textView.setText(text);
    }

    public void initializeListview() {
        ListView listView = (ListView) findViewById(R.id.cart_listview);
        CartCustomAdapter adapter = new CartCustomAdapter(presenter.getUser().getCart().getSelectedBooks(), presenter, this);
        listView.setAdapter(adapter);
        showTotal();
    }

    public void proceedCheckout(View view) {

        if(presenter.getUser().getMoney() == 0 ) {
            presenter.createDialog(3);
        } else if(presenter.getUser().getMoney() < presenter.getTotal() ) {
            presenter.createDialog(2);
        } else {
            presenter.createDialog(1);
        }

    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public void displayList(ArrayList<Book> books) {
        initializeListview();
    }

    @Override
    public void createDialog(int b) {
        if(b == 1) {
            createAbleToProceedDialog();
        } else if(b == 2) {
            createUnableToProceedDialog();
        } else if(b == 3){
            createFundIsZeroDialog();
        }
    }

    public void createFundIsZeroDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(CartActivity.this).create();
        alertDialog.setTitle("Checkout Error");
        alertDialog.setMessage("Sorry, your money is zero.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "DISMISS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void createUnableToProceedDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(CartActivity.this).create();
        alertDialog.setTitle("Checkout Error");
        alertDialog.setMessage("Sorry, you don't have enough money to checkout.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "DISMISS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void createAbleToProceedDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(CartActivity.this).create();
        alertDialog.setTitle("Checkout Completed");
        alertDialog.setMessage("Thank you for purchased books with us.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "DISMISS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        presenter.getUser().pay(presenter.getTotal());
                        presenter.getUser().emptyCart();
                        initializeShowMoney();
                        presenter.displayList(presenter.getUser().getCart().getSelectedBooks());
                    }
                });
        alertDialog.show();
    }

    public void showTotal() {
        TextView textView = (TextView) findViewById(R.id.show_total_amount);
        textView.setText(presenter.getTotal() + "");
    }

}
