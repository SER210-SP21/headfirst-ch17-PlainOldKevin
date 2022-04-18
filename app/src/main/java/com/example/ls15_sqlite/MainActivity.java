package com.example.ls15_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {
    private CommentsDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new CommentsDataSource(this);
        dataSource.open();
        List<Comment> allComments = dataSource.getAllComments();
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this, android.R.layout.simple_list_item_1, allComments);
        setListAdapter(adapter);
    }

    public void onClick(View view) {
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        if (view.getId() == R.id.add) {
            String[] comments = new String[] {"cool", "love it", "hate it", "ok"};
            int nextInt = new Random().nextInt(4);
            comment = dataSource.createComment(comments[nextInt]);
            adapter.add(comment);
        }
        else {
            if (getListAdapter().getCount() > 0) {
                comment = (Comment) getListAdapter().getItem(0);
                dataSource.deleteComment(comment);
                adapter.remove(comment);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }
}