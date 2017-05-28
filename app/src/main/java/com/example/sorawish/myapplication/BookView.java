package com.example.sorawish.myapplication;

import java.util.ArrayList;

import Data.Book;

/**
 * Created by Clunctia on 5/28/2017.
 */

public interface BookView {
    void displayList(ArrayList<Book> books);
    void createDialog(int b);
}
