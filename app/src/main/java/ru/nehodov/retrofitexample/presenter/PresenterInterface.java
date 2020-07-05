package ru.nehodov.retrofitexample.presenter;

import java.util.List;

import ru.nehodov.retrofitexample.model.Post;
import ru.nehodov.retrofitexample.view.RetrofitExampleAdapter;

public interface PresenterInterface {
    void setPosts(List<Post> posts);

    void setAdapter(RetrofitExampleAdapter adapter);

    void updatePosts();

    void addPost(Post post);

    void updatePost(Post post);

    void deletePost(Post post);

    void showMessage(String message);
}
