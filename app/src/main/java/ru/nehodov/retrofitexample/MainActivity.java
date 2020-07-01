package ru.nehodov.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private JsonPlaceHolderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JsonPlaceHolderAdapter();
        recyclerView.setAdapter(adapter);
        JsonPlaceHolderDispatcher jsonDispatcher = new JsonPlaceHolderDispatcher();
        jsonDispatcher.getAllPostsAsStrings(adapter);
//        jsonDispatcher.getAllPostsAsStrings(result);
//        jsonDispatcher.setAllCommentsToTextView(result);
//        jsonDispatcher.setPostToTextView(result, 1);
//        jsonDispatcher.setCommentToTextView(result, 1);
    }
}