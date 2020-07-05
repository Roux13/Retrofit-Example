package ru.nehodov.retrofitexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;
import ru.nehodov.retrofitexample.model.Comment;
import ru.nehodov.retrofitexample.model.Post;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET
    Call<List<Comment>> getCommentsFromUrl(@Url String url);

    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") int postId);

    @GET("posts/{Id}/comments")
    Call<List<Comment>> getCommentsByPostId(@Path("Id") int postId);

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") Integer userId,
            @Field("title") String title,
            @Field("text") String text
    );

    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);

    @PATCH("posts/{id}")
    Call<Post> updatePost(@Path("id") int id, @Body Post updatedPost);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
