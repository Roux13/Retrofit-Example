package ru.nehodov.retrofitexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET
    Call<List<Comment>> getCommentsFromUrl(@Url String url);

    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") int postId);

    @GET("posts/{Id}/comments")
    Call<List<Comment>> getCommentsByPostId(@Path("Id") int postId);
}
