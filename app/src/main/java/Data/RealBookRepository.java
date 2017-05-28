package Data;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import static com.example.sorawish.myapplication.BookPresenter.DOWNLOADBOOK_CODE;
import static com.example.sorawish.myapplication.BookPresenter.DOWNLOADIMAGE_CODE;

/**
 * Created by Clunctia on 5/27/2017.
 */

public class RealBookRepository extends Observable implements Repository, Observer{
    private ArrayList<Book> books;
    private ArrayList<Book> searchBooks;
    private ArrayList<Bitmap> bitmaps;
    private static RealBookRepository instance;

    public RealBookRepository(){
        books = new ArrayList<>();
        searchBooks = new ArrayList<>();
        bitmaps = new ArrayList<>();
    }

    public ArrayList<Bitmap> getBitmaps() {return bitmaps;}

    public static RealBookRepository getInstance(){
        if(instance == null){ instance = new RealBookRepository();}
        return instance;
    }

    @Override
    public Book getBookById(String id){
        for(Book b : books) {
            if(b.getId().contains(id)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Book> searchByPublishYear(String t){
        searchBooks.clear();
        for(Book b : books) {
            if( b.getPub_year().substring(0, t.length()).equalsIgnoreCase(t)) {
                searchBooks.add(b);
            }
        }

        Collections.sort(searchBooks, new Comparator<Book>() {
            @Override
            public int compare(Book one, Book other) {
                return one.getPub_year().compareTo(other.getPub_year());
            }
        });
        return searchBooks;
    }

    public ArrayList<Book> getBookList(){ return books; }

    @Override
    public void loadData(){
        BookFetcherTask task = new BookFetcherTask();
        task.execute();
    }

    @Override
    public void update(Observable o, Object arg) {
        bitmaps.add(getPosition(getBookById(arg.toString())), getBookById(arg.toString()).getBitmap());
        System.out.println(bitmaps.size() + "    " + books.size());
        if(bitmaps.size() == books.size()) {
            setChanged();
            notifyObservers(DOWNLOADIMAGE_CODE);
        }
    }

    @Override
    public ArrayList<Book> searchByTitle(String t){
        searchBooks.clear();
        for (Book b : books) {
            if( b.getTitle().contains(t)) {
                searchBooks.add(b);
            }
        }

        Collections.sort(searchBooks, new Comparator<Book>() {
            @Override
            public int compare(Book one, Book other) {
                return one.getTitle().compareTo(other.getTitle());
            }
        });

        return searchBooks;
    }

    public int getPosition(Book b){
        int pos = -1;
        for(int i=0 ; i< books.size() ; i++){
            if(books.get(i).getTitle().contains(b.getTitle())) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    public class BookFetcherTask extends AsyncTask<Void, Void, ArrayList<Book>>{
        @Override
        protected ArrayList<Book> doInBackground(Void... params){
            String bookListJsonStr = loadBookJson();
            if (bookListJsonStr == null) {
                return null;
            }
            ArrayList<Book> results = new ArrayList<>();
            try {
                JSONArray dataArray = new JSONArray(bookListJsonStr);

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject bookJson = dataArray.getJSONObject(i);
                    Book book = new Book(bookJson.getDouble("price"),
                            bookJson.getString("img_url"), bookJson.getString("id"),
                            bookJson.getString("title"), bookJson.getString("pub_year"));
                    results.add(book);
                }
            } catch (JSONException e) {
                return null;
            }
            return results;
        }

        private String loadBookJson() {
            String result = "";
            try {
                URL dataUrl = new URL("https://theory.cpe.ku.ac.th/~jittat/courses/sw-spec/ebooks/books.json");
                URLConnection conn = dataUrl.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader((
                        conn.getInputStream()
                )));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    result += inputLine;
                }
                return result;
            } catch (IOException e) {
                return result;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Book> results) {
            if( results != null ) {
                books.clear();
                for (Book t : results) {
                    t.addObserver(RealBookRepository.getInstance());
                    books.add(t);
                }
                setChanged();
                notifyObservers(DOWNLOADBOOK_CODE);
            }
        }
    }
}


