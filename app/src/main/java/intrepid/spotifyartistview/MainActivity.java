package intrepid.spotifyartistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import intrepid.spotifyartistview.Adapters.SpotifyArtistsAdapter;
import intrepid.spotifyartistview.Models.ArtistResponse;
import intrepid.spotifyartistview.Models.Artists;
import intrepid.spotifyartistview.net.ServiceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.activityMain_recyclerview)
    RecyclerView artistsRV;
    @BindView(R.id.artistNameText)
    EditText artistText;



    private Artists aList;
    private SpotifyArtistsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ButterKnife.bind(this);
        artistText.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doSearchQuery();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        aList = new Artists();

        
        artistsRV.setHasFixedSize(true);
        setRecyclerViewLayoutManager();
        setRecyclerViewAdapter();


    }

    private void setRecyclerViewAdapter() {
        adapter = new SpotifyArtistsAdapter(aList);
        artistsRV.setAdapter(adapter);
    }

    private void setRecyclerViewLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        artistsRV.setLayoutManager(linearLayoutManager);
    }

    @OnClick(R.id.search_button)
    public void doSearchQuery() {
        String artistName =artistText.getText().toString();
        ServiceManager.init();
        ServiceManager.Spotify.spotifyService.getArtists("artist", artistName).enqueue(new Callback<ArtistResponse>() {
            @Override
            public void onResponse(Call<ArtistResponse> call, Response<ArtistResponse> response) {
                if (response.body() != null) {
                    ArtistResponse artistResponse = response.body();
                    updateArtists(artistResponse);
                }
            }

            @Override
            public void onFailure(Call<ArtistResponse> call, Throwable t) {
                Log.d("RESPONSE_FAILURE: ", t.toString());
            }
        });


    }



    private void updateArtists(ArtistResponse artists) {
        adapter.updateArtists(artists.artists);

    }
}
