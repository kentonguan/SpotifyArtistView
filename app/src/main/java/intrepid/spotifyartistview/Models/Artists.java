package intrepid.spotifyartistview.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kentonguan on 6/7/16.
 */
public class Artists {
    @SerializedName("items")
    @Expose
    private List<Item> items = new ArrayList<Item>();

    @Override
    public String toString() {
        return "Artists{" +
                "items=" + items +
                '}';
    }

    public List<Item> getItems() {
        return items;
    }


}