package intrepid.spotifyartistview.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kentonguan on 6/9/16.
 */
public class Album {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("images")
    @Expose
    private List<Image> albumImages;

    public String getName() {
        return name;
    }

    public List<Image> getAlbumImages() {
        return albumImages;
    }
}
