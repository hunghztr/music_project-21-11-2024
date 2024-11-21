package com.example.music_project.fragment;


import android.content.Intent;
import android.os.Bundle;



import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.music_project.R;
import com.example.music_project.SubActivity;
import com.example.music_project.domain.Album;
import com.example.music_project.domain.Animation;
import com.example.music_project.domain.DatabaseHelper;
import com.example.music_project.domain.Song;
import com.example.music_project.domain.SongAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;



public class HomeFragment extends Fragment {
    DatabaseHelper helper;
    ArrayList<Song> songs;
    ListView lvSongs;
    SongAdapter adaper;
    Button btnHot1, btnHot2 , btnHot3;
    ArrayList<Album> albums;
    TextView lbAlbum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        helper = new DatabaseHelper(getContext());
        albums = helper.getAllAlbums();
        lvSongs = view.findViewById(R.id.lvSongs);
        songs = new ArrayList<>();
        songs = helper.getAllSongs();
        adaper = new SongAdapter(getActivity(),R.layout.song_item,songs,true);
        lvSongs.setAdapter(adaper);
        lbAlbum = view.findViewById(R.id.lbAlbum);
        btnHot1 = view.findViewById(R.id.btnHot1);
        btnHot2 = view.findViewById(R.id.btnHot2);
        btnHot3 = view.findViewById(R.id.btnHot3);
        btnHot1.setBackgroundResource(albums.get(0).getId());
        btnHot1.setTag(albums.get(0).getId());
        btnHot2.setBackgroundResource(albums.get(1).getId());
        btnHot2.setTag(albums.get(1).getId());
        btnHot3.setBackgroundResource(albums.get(2).getId());
        btnHot3.setTag(albums.get(2).getId());
        lbAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AlbumFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.root, fragment)
                        .addToBackStack(null)
                        .commit();
                BottomNavigationView navi = getActivity().findViewById(R.id.navi);
                navi.setSelectedItemId(R.id.album);
            }
        });
        btnHot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation.setAnimation(view);
                ArrayList<Song> songs1 = helper.getAllSongsByAlbum((int)view.getTag());
                Song song = songs1.get(0);
                Intent intent = new Intent(getActivity(), SubActivity.class);
                intent.putExtra("song",song);
                intent.putExtra("songs",songs1);
                startActivity(intent);
            }
        });
        btnHot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation.setAnimation(view);
                ArrayList<Song> songs1 = helper.getAllSongsByAlbum((int)view.getTag());
                Song song = songs1.get(0);
                Intent intent = new Intent(getActivity(),SubActivity.class);
                intent.putExtra("song",song);
                intent.putExtra("songs",songs1);
                startActivity(intent);
            }
        });
        btnHot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation.setAnimation(view);
                ArrayList<Song> songs1 = helper.getAllSongsByAlbum((int)view.getTag());
                Song song = songs1.get(0);
                Intent intent = new Intent(getActivity(),SubActivity.class);
                intent.putExtra("song",song);
                intent.putExtra("songs",songs1);
                startActivity(intent);
            }
        });

        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Animation.setAnimation(view);
                Song song = songs.get(i);
                Intent intent = new Intent(getActivity(),SubActivity.class);
                intent.putExtra("song",song);
                intent.putExtra("songs",songs);
                startActivity(intent);
            }
        });
        return view;
    }

}