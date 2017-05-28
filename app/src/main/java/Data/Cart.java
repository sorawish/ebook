package Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Clunctia on 5/27/2017.
 */

public class Cart implements Serializable{
    ArrayList<Book> selectedBooks;
    ArrayList<Book> history;

    public Cart(){
        selectedBooks = new ArrayList<Book>();
        history = new ArrayList<Book>();
    }

    public void addToCart(Book b){ selectedBooks.add(b); }

    public void removeFromCart(Book b){ selectedBooks.remove(b); }

    public ArrayList<Book> getSelectedBooks() {return selectedBooks;}

    public ArrayList<Book> getHistory() { return history; }

    public void clearCart(){
        history.addAll(selectedBooks);
        selectedBooks.clear();
    }

    public double getTotal(){
        double sum = 0;
        for(Book b : selectedBooks){
            sum += b.getPrice();
        }
        return sum;
    }


}
