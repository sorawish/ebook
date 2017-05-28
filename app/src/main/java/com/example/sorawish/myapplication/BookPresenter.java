package com.example.sorawish.myapplication;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Data.Book;
import Data.RealBookRepository;
import Data.User;

/**
 * Created by Clunctia on 5/28/2017.
 */

public class BookPresenter implements Observer{
    private ArrayList<Book> books;
    private RealBookRepository repository;
    private BookView view;
    private int checkRaioButton;
    private User user;

    public static final int SEARCH_BY_TITLE = 1;
    public static final int SEARCH_BY_PUBYEAR = 2;
    public static final String DOWNLOADBOOK_CODE = "download_book_details";
    public static final String DOWNLOADIMAGE_CODE = "download_book_image";

    public BookPresenter(RealBookRepository repository, BookView view){
        this.repository = repository;
        this.view = view;
        checkRaioButton = SEARCH_BY_TITLE;
        user = User.getInstance();
    }

    public void displayList(ArrayList<Book> b){view.displayList(b);}

    public void searchByTitle(String t){
        if(t.length() == 0) view.displayList(repository.getBookList());
        else view.displayList(repository.searchByTitle(t));
    }

    public void searchByPublishYear(String t){
        if(t.length() == 0) view.displayList(repository.getBookList());
        else view.displayList(repository.searchByPublishYear(t));
    }

    public void searchBy(String t){
        if(checkRaioButton == SEARCH_BY_TITLE) searchByTitle(t);
        else if (checkRaioButton == SEARCH_BY_PUBYEAR) searchByPublishYear(t);
    }

    public void onChceckRadioButton(int checked) { checkRaioButton = checked;}

    @Override
    public void update(Observable o, Object arg) {
        if(arg.toString().contains(DOWNLOADBOOK_CODE)){
            displayList(repository.getBookList());
            System.out.println("updating data...");
        } else if(arg.toString().contains(DOWNLOADIMAGE_CODE)) {
            displayList(repository.getBookList());
            System.out.println("updating image...");
        }
    }

    public User getUser() {
        return user;
    }

    public double getTotal() {
        return user.getCart().getTotal();
    }

    public void addMoneyToUser(double amount) {
        user.addMoney(amount);
    }

    public void createDialog(int b) {
        view.createDialog(b);
    }
}
