package ru.nehodov.retrofitexample.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import ru.nehodov.retrofitexample.R;
import ru.nehodov.retrofitexample.model.Post;

public class UpdatePostFragment extends Fragment {

    private static final String ARGS_POST = "args_post";

    private UpdatePostListener listener;

    private EditText userIdEditText;
    private EditText titleEditText;
    private EditText text;

    private Post post;

    public static UpdatePostFragment newInstance(Post post) {
        UpdatePostFragment fragment = new UpdatePostFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_POST, post);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            post = (Post) getArguments().getSerializable(ARGS_POST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_editor, container, false);
        EditText idEditText = view.findViewById(R.id.idEditText);
        idEditText.setText(String.valueOf(post.getId()));
        userIdEditText = view.findViewById(R.id.userIdEditText);
        userIdEditText.setText(String.valueOf(post.getUserId()));
        titleEditText = view.findViewById(R.id.titleEditText);
        titleEditText.setText(post.getTitle());
        text = view.findViewById(R.id.textEditText);
        text.setText(post.getText());
        ImageButton acceptBtn = view.findViewById(R.id.acceptButton);
        acceptBtn.setOnClickListener(this::onAcceptButtonClick);
        ImageButton cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> requireActivity().onBackPressed());
        return view;
    }

    public void onAcceptButtonClick(View view) {
        post.setUserId(Integer.parseInt(userIdEditText.getText().toString()));
        post.setTitle(titleEditText.getText().toString());
        post.setText(text.getText().toString());
        listener.updatePost(post);
    }

    public interface UpdatePostListener {
        void updatePost(Post post);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.listener = (UpdatePostListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(String.format("%s must implement %s",
                    context.getClass().getSimpleName(),
                    UpdatePostListener.class.getSimpleName())
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}