package Data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * Created by Clunctia on 5/27/2017.
 */

public class Book extends Observable{
    private double price;
    private String img_url;
    private String id;
    private String title;
    private String pub_year;
    protected Bitmap bitmap;

    public Book(double price, String img_url, String id, String title, String pub_year){
        this.price = price;
        this.img_url = img_url;
        this.id = id;
        this.title = title;
        this.pub_year = pub_year;

        if(img_url.equals("")){
            downloadImage();
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getPub_year() { return pub_year; }

    public double getPrice() {
        return price;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImg_url(String url) {
        this.img_url = url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPub_year(String pub_year) { this.pub_year = pub_year; }

    public String toString() {
        return "Title: " + title + "\n";
    }

    public void downloadImage(){
        ImageFetchTask task = new ImageFetchTask();
        task.execute();
    }

    public class ImageFetchTask extends AsyncTask<Void, Void, Bitmap>{
        private Bitmap map;

        @Override
        protected Bitmap doInBackground(Void... params) {

            return downloadImg();
        }

        private Bitmap downloadImg() {
            URL url = null;
            try {
                url = new URL(img_url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                map = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return map;
        }

        @Override
        protected void onPostExecute(Bitmap results) {
            if( results != null ) {
                bitmap = results;
            }
            setChanged();
            notifyObservers(getId());
        }
    }
}
