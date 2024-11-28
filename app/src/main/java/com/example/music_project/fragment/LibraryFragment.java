package com.example.music_project.fragment;



import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.music_project.R;
import com.example.music_project.SubActivity;
import com.example.music_project.domain.Animation;
import com.example.music_project.domain.DatabaseHelper;
import com.example.music_project.domain.Song;
import com.example.music_project.domain.SongAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class LibraryFragment extends Fragment {
    DatabaseHelper helper;
    ArrayList<Song> songs;
    ListView lvLibrary;
    SongAdapter adaper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        lvLibrary = view.findViewById(R.id.lvLibrary);
        this.songs = new ArrayList<>();
        ArrayList<Integer> faus = helper.getAllFauSongs();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fetSong = database.getReference("Songs");
        fetSong.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    Song song = child.getValue(Song.class);
                    songs.add(song);
                }

                songs = filterSongs(faus);
                if(songs != null){
                    adaper= new SongAdapter(getActivity(),R.layout.song_item,songs,false);
                    lvLibrary.setAdapter(adaper);
                }
            }
        });


        lvLibrary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Animation.setAnimation(view);
                Song song = songs.get(i);
                Intent intent = new Intent(getActivity(), SubActivity.class);
                intent.putExtra("song",song);
                intent.putExtra("songs",songs);
                startActivity(intent);
            }
        });
        return view;
    }

    private ArrayList<Song> filterSongs(ArrayList<Integer> faus) {
        ArrayList<Song> songs1 = new ArrayList<>();
        for(Integer i : faus){
            for(Song song : songs){
                if(song.getFileId() == i){
                    songs1.add(song);
                }
            }
        }
        return songs1;
    }
}