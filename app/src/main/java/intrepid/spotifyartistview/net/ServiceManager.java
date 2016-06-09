package intrepid.spotifyartistview.net;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import intrepid.spotifyartistview.Models.ArtistResponse;
import intrepid.spotifyartistview.Models.Artists;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import timber.log.Timber;

/**
 * Created by kentonguan on 6/7/16.
 */
public class ServiceManager {
    public static void init() {
        Spotify.initService();
    }

    public static class Spotify{
        public static final String BASE_URL = "https://api.spotify.com/";

        public static SpotifyService spotifyService;

        private static void initService(){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(getConverterFactory())
                    .client(getClient())
                    .build();

            spotifyService = retrofit.create(SpotifyService.class);

        }

        private static Converter.Factory getConverterFactory(){
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setPrettyPrinting()
                    .create();

            return GsonConverterFactory.create(gson);
        }

        private static OkHttpClient getClient() {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Timber.tag("OkHttp").d(message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            return new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
        }

        public interface SpotifyService {
            @GET("v1/search")
            Call<ArtistResponse> getArtists(@Query("type") String type, @Query("query") String artistName);
        }
    }
}
