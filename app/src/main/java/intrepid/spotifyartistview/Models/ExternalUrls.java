package intrepid.spotifyartistview.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kentonguan on 6/7/16.
 */
public class ExternalUrls {

    @SerializedName("spotify")
    @Expose
    private String spotify;

    /**
     *
     * @return
     * The spotify
     */
    public String getSpotify() {
        return spotify;
    }

    /**
     *
     * @param spotify
     * The spotify
     */
    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

}