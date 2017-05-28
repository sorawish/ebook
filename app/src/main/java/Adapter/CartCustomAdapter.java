package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.sorawish.myapplication.BookPresenter;
import com.example.sorawish.myapplication.R;

import java.util.ArrayList;

import Data.Book;
import Data.RealBookRepository;

/**
 * Created by Clunctia on 5/28/2017.
 */

public class CartCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Book> list = new ArrayList<>();
    private Context context;
    private BookPresenter presenter;

    public CartCustomAdapter(ArrayList<Book> list, BookPresenter presenter, Context context) {
        this.list = list;
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(list.get(position).getId());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cart_listview_adapter, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string_cart);
        listItemText.setText(list.get(position).toString());

        if(!list.get(position).getImg_url().equals("")) {
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview_cart);
            imageView.setImageBitmap(RealBookRepository.getInstance().getBookById(list.get(position).getId()).getBitmap());
        }


        //Handle buttons and add onClickListeners
        Button addBtn = (Button)view.findViewById(R.id.delete_btn);

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.getUser().removeFromCart(list.get(position));
                presenter.displayList(list);
            }
        });

        return view;
    }
}
