package me.devsaki.hentoid.retrofit;

import io.reactivex.Single;
import me.devsaki.hentoid.parsers.content.EHentaiGalleriesMetadata;
import me.devsaki.hentoid.parsers.content.EHentaiGalleryQuery;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class EHentaiServer {

    private final static String API_URL = "http://e-hentai.org/";

    public static final Api API = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api.class);

    public interface Api {

        @POST("api.php")
        Single<EHentaiGalleriesMetadata> getGalleryMetadata(@Body EHentaiGalleryQuery query);
    }
}
