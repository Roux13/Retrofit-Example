package ru.nehodov.retrofitexample.view;

import java.io.Serializable;

import ru.nehodov.retrofitexample.model.Post;

public interface ViewInterface extends Serializable {

    void showMessage(String message);

    void addPost(Post post);

}
