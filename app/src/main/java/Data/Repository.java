package Data;

import java.util.ArrayList;

/**
 * Created by Clunctia on 5/27/2017.
 */

public interface Repository {
    void loadData();
    ArrayList<Book> searchByTitle(String t);
    ArrayList<Book> searchByPublishYear(String t);
    Book getBookById(String id);
}
