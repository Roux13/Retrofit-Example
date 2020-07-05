package ru.nehodov.retrofitexample.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import ru.nehodov.retrofitexample.JsonPlaceHolderDispatcher;
import ru.nehodov.retrofitexample.R;
import ru.nehodov.retrofitexample.model.Post;
import ru.nehodov.retrofitexample.presenter.PresenterInterface;
import ru.nehodov.retrofitexample.presenter.RetrofitExamplePresenter;

public class MainActivity extends AppCompatActivity
        implements ViewInterface,
        ListFragment.ListFragmentListener,
        AddPostFragment.AddPostListener,
        UpdatePostFragment.UpdatePostListener {

    private PresenterInterface presenter;

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new RetrofitExamplePresenter(this, new JsonPlaceHolderDispatcher());

        fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.host);
        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.host, ListFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void addPost(Post post) {
        presenter.addPost(post);
        presenter.updatePosts();
        callList();
    }

    @Override
    public void deletePost(Post post) {
        presenter.deletePost(post);
    }

    @Override
    public void updatePost(Post post) {
        presenter.updatePost(post);
        callList();
    }

    @Override
    public void callAddPost() {
        fm.beginTransaction()
                .replace(R.id.host, AddPostFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void callUpdatePost(Post post) {
        fm.beginTransaction()
                .replace(R.id.host, UpdatePostFragment.newInstance(post))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void transferAdapter(RetrofitExampleAdapter adapter) {
        presenter.setAdapter(adapter);
    }

    public void callList() {
        fm.beginTransaction()
                .replace(R.id.host, ListFragment.newInstance())
                .commit();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}