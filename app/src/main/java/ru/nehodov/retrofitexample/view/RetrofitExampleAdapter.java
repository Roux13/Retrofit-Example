package ru.nehodov.retrofitexample.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nehodov.retrofitexample.R;
import ru.nehodov.retrofitexample.model.Post;

public class RetrofitExampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final AdapterListener listener;

    private List<Post> posts;

    public RetrofitExampleAdapter(AdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_holder_item, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post post = posts.get(position);
        TextView textView = holder.itemView.findViewById(R.id.itemTextView);
        ImageButton updatePostButton = holder.itemView.findViewById(R.id.updatePostButton);
        updatePostButton.setOnClickListener(v -> listener.updatePost(post));
        ImageButton deletePostButton = holder.itemView.findViewById(R.id.deletePostButton);
        deletePostButton.setOnClickListener(v -> listener.deletePost(post));
        textView.setText(String.format(
                "ID: %s%n user ID: %s%n Title: %s%n Text: %s%n%n",
                post.getId(), post.getUserId(), post.getTitle(), post.getText()
        ));
    }

    @Override
    public int getItemCount() {
        if (posts == null) {
            return 0;
        }
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyItemChanged(1);
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void updatePost(Post post);

        void deletePost(Post post);
    }
}
