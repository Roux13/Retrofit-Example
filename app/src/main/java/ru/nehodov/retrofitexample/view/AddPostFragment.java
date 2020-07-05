package ru.nehodov.retrofitexample.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import ru.nehodov.retrofitexample.R;
import ru.nehodov.retrofitexample.model.Post;

public class AddPostFragment extends Fragment {

    private AddPostListener listener;

    private EditText userIdEditText;
    private EditText titleEditText;
    private EditText text;

    public static AddPostFragment newInstance() {
        return new AddPostFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_editor, container, false);
        TextView idEditText = view.findViewById(R.id.idEditText);
        idEditText.setVisibility(View.INVISIBLE);
        userIdEditText = view.findViewById(R.id.userIdEditText);
        titleEditText = view.findViewById(R.id.titleEditText);
        text = view.findViewById(R.id.textEditText);
        ImageButton acceptBtn = view.findViewById(R.id.acceptButton);
        acceptBtn.setOnClickListener(this::onAcceptButtonClick);
        ImageButton cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> requireActivity().onBackPressed());
        return view;
    }

    public void onAcceptButtonClick(View view) {
        listener.addPost(new Post(
                Integer.parseInt(userIdEditText.getText().toString()),
                titleEditText.getText().toString(),
                text.getText().toString()));
    }

    public interface AddPostListener {
        void addPost(Post post);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.listener = (AddPostListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(String.format("%s must implement %s",
                    context.getClass().getSimpleName(),
                    AddPostListener.class.getSimpleName())
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}