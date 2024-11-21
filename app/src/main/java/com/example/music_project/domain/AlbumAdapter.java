package com.example.music_project.domain;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.music_project.R;

import java.util.ArrayList;

public class AlbumAdapter extends ArrayAdapter<Album> {
    private Activity context;
    private int layoutId;
    private ArrayList<Album> albums;
    public AlbumAdapter( Activity context, int layoutId, ArrayList<Album> albums) {
        super(context, layoutId,albums);
        this.context = context;
        this.layoutId = layoutId;
        this.albums = albums;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        Album album = albums.get(position);
        ImageView img = convertView.findViewById(R.id.ivSong);
        TextView name = convertView.findViewById(R.id.lbSongName);
        TextView singerName = convertView.findViewById(R.id.lbSingerName);
        img.setImageResource(album.getId());
        name.setText(album.getName());
        singerName.setText(album.getSingerName());

        return convertView;
    }
}
