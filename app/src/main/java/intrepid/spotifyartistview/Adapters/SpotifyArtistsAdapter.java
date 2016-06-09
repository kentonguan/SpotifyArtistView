package intrepid.spotifyartistview.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import intrepid.spotifyartistview.AlbumViewActivity;
import intrepid.spotifyartistview.Models.Album;
import intrepid.spotifyartistview.Models.Artists;
import intrepid.spotifyartistview.R;
import intrepid.spotifyartistview.WebViewActivity;

/**
 * Created by kentonguan on 6/7/16.
 */
public class SpotifyArtistsAdapter extends RecyclerView.Adapter<SpotifyArtistsAdapter.ViewHolder> {

    Artists artists = new Artists();
    Album album = new Album();
    Context context;
    ImageButton menuButton;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_search_result_item, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(artists.getItems().get(position).getName());
        holder.textView.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        if (artists.getItems().get(position).getImages().size() > 0) {
            Picasso.with(context)
                    .load(Uri.parse(artists.getItems().get(position).getImages().get(0).getUrl()))
                    .resize(100, 100)
                    .centerCrop()
                    .into(holder.imageView);
        } else {
            Picasso.with(context)
                    .load(R.drawable.placeholder)
                    .resize(100, 100)
                    .centerCrop()
                    .into(holder.imageView);
        }
        holder.url = artists.getItems().get(position).getExternalUrls().getSpotify();
        holder.artistID = artists.getItems().get(position).getId();
        /*holder.albumTextView.setText(album.getName());
        if (album.getAlbumImages().size() > 0) {
            Picasso.with(context)
                    .load(Uri.parse(album.getAlbumImages().get(position).getUrl()))
                    .resize(100, 100)
                    .centerCrop()
                    .into(holder.albumImageView);
        } else {
            Picasso.with(context)
                    .load(R.drawable.placeholder)
                    .resize(100, 100)
                    .centerCrop()
                    .into(holder.albumImageView);
        }*/

    }

    @Override
    public int getItemCount() {
        return artists.getItems().size();
    }

    public void updateArtists(Artists artists) {
        this.artists = artists;
        notifyDataSetChanged();
    }

    public void updateAlbums(Album album) {
        this.album = album;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView imageView;
        public String url;
        public String artistID;
        public ImageButton menuButton;
        PopupMenu artistPopupMenu;
        public ImageView albumImageView;
        public TextView albumTextView;


        public ViewHolder(final View v) {
            super(v);
            v.setOnClickListener(this);
            textView = (TextView) v.findViewById(R.id.textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            menuButton = (ImageButton) v.findViewById(R.id.menu_button);
            albumImageView = (ImageView) v.findViewById(R.id.album_imageView);
            albumTextView = (TextView) v.findViewById(R.id.album_name_textView);

            artistPopupMenu = new PopupMenu(v.getContext(), v.findViewById(R.id.menu_button));
            artistPopupMenu.getMenuInflater().inflate(R.menu.artist_popup_menu, artistPopupMenu.getMenu());
            artistPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    startNewActivity(item, v);
                    return true;

                }
            });
        }

        public void startNewActivity(MenuItem item, View view){
            if (item.getItemId() == R.id.go_to_artist_web_page){
                Intent intent = new Intent(view.getContext(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
            else{
                Intent intent = new Intent(view.getContext(), AlbumViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("artistID", artistID);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        }

        @Override
        public void onClick(final View view) {
            artistPopupMenu.show();

        }


    }

    public SpotifyArtistsAdapter(Artists artists) {
        this.artists = artists;
    }

    public SpotifyArtistsAdapter(Album album){
        this.album = album;
    }


}
