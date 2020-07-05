package ru.nehodov.retrofitexample.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.nehodov.retrofitexample.R;
import ru.nehodov.retrofitexample.model.Post;

public class ListFragment extends Fragment implements RetrofitExampleAdapter.AdapterListener {

    private ListFragmentListener listener;

    private RecyclerView recyclerView;
    private RetrofitExampleAdapter adapter;

    public static ListFragment newInstance() {
        return  new ListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new RetrofitExampleAdapter(this);
        recyclerView.setAdapter(adapter);
        listener.transferAdapter(adapter);
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(this::onFabClick);
        return view;
    }

    public void onFabClick(View view) {
        listener.callAddPost();
    }

    @Override
    public void updatePost(Post post) {
        listener.callUpdatePost(post);
    }

    @Override
    public void deletePost(Post post) {
        listener.deletePost(post);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.listener = (ListFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(String.format(
                    "%s must implement %s.",
                    context.getClass().getSimpleName(), ListFragmentListener.class.getSimpleName()
                    ));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    public interface ListFragmentListener {
        void callAddPost();

        void callUpdatePost(Post post);

        void deletePost(Post post);

        void transferAdapter(RetrofitExampleAdapter adapter);
    }

}




