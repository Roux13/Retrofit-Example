package ru.nehodov.retrofitexample;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsonPlaceHolderDispatcher {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonApi;

    public JsonPlaceHolderDispatcher() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.jsonApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    public void getAllPostsAsStrings(JsonPlaceHolderAdapter adapter) {
        List<String> postsAsStrings = new ArrayList<>();
        Call<List<Post>> call = jsonApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    postsAsStrings.add(formatErrorCode(response));
                    adapter.setPosts(postsAsStrings);
                    return;
                }
                List<Post> posts = response.body();
                if (posts != null) {
                    for (Post post : posts) {
                        postsAsStrings.add(formatPostContent(post));
                    }
                }
                adapter.setPosts(postsAsStrings);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void setAllCommentsToTextView(TextView textView) {
        Call<List<Comment>> commentCall = jsonApi.getCommentsFromUrl("comments");
        commentCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textView.setText(formatErrorCode(response));
                    return;
                }
                List<Comment> comments = response.body();
                for (Comment comment : comments) {
                    textView.append(formatCommentContent(comment));
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    public void setPostToTextView(TextView textView, int postId) {
        Call<Post> call = jsonApi.getPost(postId);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView.setText(formatErrorCode(response));
                    return;
                }
                Post post = response.body();
                textView.append(formatPostContent(post));

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    public void setCommentToTextView(TextView textView, int postId) {
        Call<List<Comment>> call = jsonApi.getCommentsByPostId(postId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textView.setText(formatErrorCode(response));
                    return;
                }
                List<Comment> comments = response.body();
                for (Comment comment : comments) {
                    textView.append(formatCommentContent(comment));
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private String formatErrorCode(Response<?> response) {
        return String.format("Code: %s", response.code());
    }

    private String formatPostContent(Post post) {
        return String.format(
                "ID: %s%n user ID: %s%n Title: %s%n Text: %s%n%n",
                post.getId(), post.getUserId(), post.getTitle(), post.getText()
        );
    }

    private String formatCommentContent(Comment comment) {
        return String.format(
                "Post ID: %s%n ID: %s%n Name: %s%n Email: %s%n Text: %s%n%n",
                comment.getPostId(), comment.getId(), comment.getName(),
                comment.getEmail(), comment.getText());
    }
}
