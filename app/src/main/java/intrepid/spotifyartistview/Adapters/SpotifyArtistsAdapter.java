package intrepid.spotifyartistview.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import intrepid.spotifyartistview.Models.Artists;
import intrepid.spotifyartistview.R;
import intrepid.spotifyartistview.WebViewActivity;

/**
 * Created by kentonguan on 6/7/16.
 */
public class SpotifyArtistsAdapter extends RecyclerView.Adapter<SpotifyArtistsAdapter.ViewHolder> {

    Artists artists = new Artists();
    Context context;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_search_result_item, parent, false);
        context=parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(artists.getItems().get(position).getName());
        holder.textView.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        if (artists.getItems().get(position).getImages().size()>0) {
            Picasso.with(context)
                    .load(Uri.parse(artists.getItems().get(position).getImages().get(0).getUrl()))
                    .resize(100, 100)
                    .centerCrop()
                    .into(holder.imageView);
        }
        else{
            Picasso.with(context)
                    .load(R.drawable.placeholder)
                    .resize(100,100)
                    .centerCrop()
                    .into(holder.imageView);
        }
        holder.url = artists.getItems().get(position).getExternalUrls().getSpotify();

    }

    @Override
    public int getItemCount() {
        return artists.getItems().size();
    }

    public void updateItems(Artists artists){
        this.artists = artists;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public ImageView imageView;
        public String url;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            textView = (TextView) v.findViewById(R.id.textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View view){
            Intent intent = new Intent(view.getContext(), WebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);

        }
    }
    public SpotifyArtistsAdapter(Artists artists) {
        this.artists = artists;
    }


}
