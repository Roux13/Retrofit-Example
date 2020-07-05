package ru.nehodov.retrofitexample.presenter;

import java.util.List;

import ru.nehodov.retrofitexample.JsonPlaceHolderDispatcher;
import ru.nehodov.retrofitexample.model.Post;
import ru.nehodov.retrofitexample.view.RetrofitExampleAdapter;
import ru.nehodov.retrofitexample.view.ViewInterface;

public class RetrofitExamplePresenter implements PresenterInterface {

    private final ViewInterface view;

    private final JsonPlaceHolderDispatcher dispatcher;

    private RetrofitExampleAdapter adapter;

    private List<Post> posts;

    public RetrofitExamplePresenter(ViewInterface view, JsonPlaceHolderDispatcher dispatcher) {
        this.view = view;
        this.dispatcher = dispatcher;
        updatePosts();
    }

    @Override
    public void setPosts(List<Post> posts) {
        this.posts = posts;
        adapter.setPosts(this.posts);
    }

    @Override
    public void updatePosts() {
        dispatcher.getAllPosts(this);
    }

    @Override
    public void addPost(Post post) {
        dispatcher.createPost(this, post);
        updatePosts();
    }

    @Override
    public void updatePost(Post post) {
        dispatcher.updatePost(this, post);
    }

    @Override
    public void deletePost(Post post) {
        dispatcher.deletePost(this, post);
    }

    @Override
    public void showMessage(String message) {
        view.showMessage(message);
    }

    @Override
    public void setAdapter(RetrofitExampleAdapter adapter) {
        this.adapter = adapter;
        adapter.setPosts(this.posts);
    }
}
