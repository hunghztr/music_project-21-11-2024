package com.example.music_project.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.music_project.R;
import com.example.music_project.SubActivity;
import com.example.music_project.domain.Album;
import com.example.music_project.domain.AlbumAdapter;
import com.example.music_project.domain.AlbumDetail;
import com.example.music_project.domain.DatabaseHelper;
import com.example.music_project.domain.Song;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AlbumFragment extends Fragment {
    ListView lvAlbum;
    AlbumAdapter adaper;
    ArrayList<Song> songs;
    ArrayList<Album> albums;
    ArrayList<AlbumDetail> details;
    DatabaseHelper helper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        albums = new ArrayList<>();
        songs = new ArrayList<>();
        details = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fetAlbum = database.getReference("Albums");
        DatabaseReference fetSong = database.getReference("Songs");
        DatabaseReference fetAlbumDetail = database.getReference("Albums Detail");
        fetAlbum.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    Album album = child.getValue(Album.class);
                    albums.add(album);
                }
                adaper = new AlbumAdapter(getActivity(),R.layout.song_item,albums);
                lvAlbum.setAdapter(adaper);
            }
        });
        fetSong.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    Song song = child.getValue(Song.class);
                    songs.add(song);
                }
            }
        });
        fetAlbumDetail.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    AlbumDetail detail = child.getValue(AlbumDetail.class);
                    details.add(detail);
                }
            }
        });
        lvAlbum = view.findViewById(R.id.lvAlbum);

        lvAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Album album = albums.get(i);
                ArrayList<Song> choiceSongs = getSongsByAlbum(album.getId());
                Song song = new Song();
                if(choiceSongs.size() > 0) {
                    song = choiceSongs.get(0);
                }
                Intent intent = new Intent(getActivity(), SubActivity.class);
                intent.putExtra("songs",choiceSongs);
                intent.putExtra("song",song);
                startActivity(intent);
            }
        });
        return view;
    }

    private ArrayList<Song> getSongsByAlbum(int id) {
        ArrayList<Song> songs1 = new ArrayList<>();
        ArrayList<AlbumDetail> details1 = new ArrayList<>();
        for(AlbumDetail detail : details){
            if(detail.getAlbumId() == id){
                details1.add(detail);
            }
        }
        for(Song song : songs){
            for(AlbumDetail detail : details1) {
                if (song.getFileId() == detail.getSongId()){
                    songs1.add(song);
                }
            }
        }
        return songs1;
    }
}