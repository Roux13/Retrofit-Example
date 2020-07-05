package ru.nehodov.retrofitexample;

import java.io.Serializable;

import ru.nehodov.retrofitexample.model.Post;
import ru.nehodov.retrofitexample.view.ViewInterface;

public class PostCreator implements PostEditorInterface, Serializable {

    private final ViewInterface view;

    public PostCreator(ViewInterface view) {
        this.view = view;
    }

    @Override
    public void accept(int id, Integer userId, String title, String text) {
        view.addPost(new Post(userId, title, text));
    }
}
