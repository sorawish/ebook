package com.example.sorawish.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import java.util.ArrayList;

import Adapter.MainCustomAdapter;
import Data.Book;
import Data.RealBookRepository;

import static com.example.sorawish.myapplication.BookPresenter.SEARCH_BY_PUBYEAR;
import static com.example.sorawish.myapplication.BookPresenter.SEARCH_BY_TITLE;

public class MainActivity  extends AppCompatActivity implements BookView {

    private BookPresenter presenter;
    private RealBookRepository repository;

    private final String CHECK_FUND = "Check Fund";
    private final String ADD_FUND = "Add Fund";
    private final String CART = "Cart";
    private final String ORDER = "Orders";
    public static final String AMOUNT_ADD_FUND = "adding_fund_amount_key";
    public static final int ADDING_FUND_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = RealBookRepository.getInstance();
        presenter = new BookPresenter(repository, this);
        repository.addObserver(presenter);
        repository.loadData();
        initializeSpinner();
        initializeRadioButton();
        initializeEditText();
    }

    @Override
    public void displayList(ArrayList<Book> books) {
        MainCustomAdapter mainCustomAdapter = new MainCustomAdapter(books, presenter, this);
        ListView listView = (ListView) findViewById(R.id.show_list_listview);
        listView.setAdapter(mainCustomAdapter);
    }

    public void createCheckFundDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Check Fund");
        String value = String.format("%.2f", presenter.getUser().getMoney());
        alertDialog.setMessage(value);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "DISMISS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void createInformationDialog(int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Book Information");
        Book b = repository.getBookList().get(position);
        String value = String.format("ID: %s \nTitle: %s \nPrice: %.2f \nPublished Year: %s", b.getId(), b.getTitle(), b.getPrice(), b.getPub_year());
        alertDialog.setMessage(value);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "DISMISS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void createDialog(int b) {
        if(b == -1) {
            createCheckFundDialog();
        } else {
            createInformationDialog(b);
        }
    }

    public void initializeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.account_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.contains(CHECK_FUND)) {
                    presenter.createDialog(-1);
                } else if(selectedItem.contains(ADD_FUND)) {
                    startAddingFund();
                } else if(selectedItem.contains(CART)) {
                    startCartActivity();
                } else if(selectedItem.contains(ORDER)) {
                    startOrdersActivity();
                }
                parent.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initializeRadioButton() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.search_by_radiogroup);
        radioGroup.check(R.id.search_by_title_radiobutton);
    }

    public void initializeEditText() {
        EditText editText = (EditText) findViewById(R.id.search_edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.searchBy(s.toString());
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        RadioButton radioButton = (RadioButton) findViewById(view.getId());
        switch (radioButton.getId()) {
            case R.id.search_by_title_radiobutton:
                presenter.onChceckRadioButton(SEARCH_BY_TITLE);
                break;

            case R.id.search_by_pubyear_radiobutton:
                presenter.onChceckRadioButton(SEARCH_BY_PUBYEAR);
                break;

            default:
                System.out.println("error");
        }
    }

    public void startAddingFund() {
        Intent addFundIntent = new Intent(this, AddMoneyActivity.class );
        startActivityForResult(addFundIntent, ADDING_FUND_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADDING_FUND_REQUEST) {
            if (resultCode == RESULT_OK) {
                double amount = (double) data.getSerializableExtra(AMOUNT_ADD_FUND);
                presenter.addMoneyToUser(amount);
            }
        }
    }

    public void startCartActivity() {
        Intent cartIntent = new Intent(this, CartActivity.class);
        startActivity(cartIntent);
    }

    public void startOrdersActivity() {
        Intent ordersIntent = new Intent(this, OrdersActivity.class);
        startActivity(ordersIntent);
    }





}
