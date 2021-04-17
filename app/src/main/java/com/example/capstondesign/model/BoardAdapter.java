package com.example.capstondesign.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.capstondesign.R;

import java.util.Vector;

public class BoardAdapter extends ArrayAdapter<Board> {
    Vector<Board> board;
    Context context;

    public BoardAdapter(@NonNull Context context, int resource, Vector<Board> board) {
        super(context, resource, board);
        this.board = board;
        this.context = context;
    }

    class ViewHolder {
        //ImageView imageView;
        TextView title;
        TextView text;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemLayout = convertView;
        final ViewHolder viewHolder;

        if (itemLayout == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemLayout = inflater.inflate(R.layout.board_layout, null, true);

            viewHolder = new ViewHolder();
            //viewHolder.imageView = (ImageView) itemLayout.findViewById(R.id.imageView);
            viewHolder.text = (TextView) itemLayout.findViewById(R.id.title);
            viewHolder.title = (TextView) itemLayout.findViewById(R.id.text);

            itemLayout.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) itemLayout.getTag();
        }

        //viewHolder.imageView.setImageURI(board.get(position).image);
        viewHolder.title.setText(board.get(position).title);
        viewHolder.text.setText(board.get(position).text);

        return itemLayout;
    }
}
