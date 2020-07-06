package ru.nehodov.retrofitexample;

import android.util.Log;

import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor;

import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nehodov.retrofitexample.model.Post;
import ru.nehodov.retrofitexample.presenter.PresenterInterface;

public class JsonPlaceHolderDispatcher {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private final HttpLoggingInterceptor interceptor;

    private final OkHttpClient.Builder httpClient;

    private final JsonPlaceHolderApi jsonApi;

    private final PresenterInterface presenter;

    private final OkHttpClient errorInterceptor;

    public JsonPlaceHolderDispatcher(PresenterInterface presenter) {
        this.presenter = presenter;
        this.interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        httpClient = new OkHttpClient.Builder().addInterceptor(interceptor);
        httpClient.addInterceptor(new OkHttpProfilerInterceptor());

        errorInterceptor = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request();
            okhttp3.Response response = chain.proceed(request);
            int responseCode = response.code();
            if (!response.isSuccessful()) {
                String errorMessage = response.body().toString();
                errorMessage = String.format(
                        Locale.US, "Code: %d. %s", responseCode, errorMessage);
                JsonPlaceHolderDispatcher.this.presenter.showMessage(errorMessage);
            }
            return response;
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .client(errorInterceptor)
                .build();
        this.jsonApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    public void getAllPosts() {
        Call<List<Post>> call = jsonApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    presenter.showMessage(formatErrorCode(response));
                    return;
                }
                presenter.setPosts(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                presenter.showMessage(t.getMessage());
            }
        });
    }

    public void createPost(Post post) {
        Call<Post> call = jsonApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    Log.d(this.getClass().getSimpleName(), formatErrorCode(response));
                    presenter.showMessage(formatErrorCode(response));
                    return;
                }
                presenter.showMessage(formatPostContent(response.body()));
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                presenter.showMessage(t.getMessage());
            }
        });
    }

    public void updatePost(Post post) {
        Call<Post> call = jsonApi.updatePost(post.getId(), post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    presenter.showMessage(formatErrorCode(response));
                    return;
                }
                presenter.showMessage(formatPostContent(response.body()));
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                presenter.showMessage(t.getMessage());
            }
        });
    }

    public void deletePost(Post post) {
        Call<Void> call = jsonApi.deletePost(post.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("TAG", String.valueOf(response.code()));
                presenter.showMessage(formatErrorCode(response));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private String formatErrorCode(Response<?> response) {
        return String.format("Code: %s, %s", response.code(), response.body());
    }

    private String formatPostContent(Post post) {
        return String.format(
                "ID: %s%n user ID: %s%n Title: %s%n Text: %s%n%n",
                post.getId(), post.getUserId(), post.getTitle(), post.getText()
        );
    }
}
