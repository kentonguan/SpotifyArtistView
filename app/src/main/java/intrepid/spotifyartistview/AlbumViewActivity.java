package intrepid.spotifyartistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import intrepid.spotifyartistview.Adapters.SpotifyArtistsAdapter;
import intrepid.spotifyartistview.Models.Album;
import intrepid.spotifyartistview.Models.AlbumResponse;
import intrepid.spotifyartistview.Models.ArtistResponse;
import intrepid.spotifyartistview.net.ServiceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumViewActivity extends AppCompatActivity {

    @BindView(R.id.album_recyclerview)
    RecyclerView albumsRV;
    SpotifyArtistsAdapter artistsAdapter;
    Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);

        ButterKnife.bind(this);
        albumsRV.setHasFixedSize(true);
        setRecyclerViewLayoutManager();
        setRecyclerViewAdapter();
        ServiceManager.init();
        ServiceManager.Spotify.spotifyService.getAlbumsByArtistID(super.getIntent().getExtras().getString("artistID")).enqueue(new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                if (response.body() != null) {
                    AlbumResponse albumResponse = response.body();
                    updateAlbums(albumResponse);
                    Log.d("ALBUMRESPONSE", albumResponse.toString());
                }
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {
                Log.d("RESPONSE_FAILURE: ", t.toString());
            }
        });
    }

    private void setRecyclerViewAdapter() {
        artistsAdapter = new SpotifyArtistsAdapter(album);
        albumsRV.setAdapter(artistsAdapter);
    }

    private void setRecyclerViewLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        albumsRV.setLayoutManager(linearLayoutManager);
    }

    private void updateAlbums(AlbumResponse albumResponse) {
        artistsAdapter.updateAlbums(albumResponse.album);

    }
}
