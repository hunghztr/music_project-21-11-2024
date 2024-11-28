package com.example.music_project.fragment;


import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SearchFragment extends Fragment {
    DatabaseHelper helper;
    EditText edtSearch;
    ListView lv;
    ArrayList<Song> songs;
    SongAdapter adaper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
       songs = new ArrayList<>();
        ArrayList<Song> newSong = new ArrayList<>();

        lv = view.findViewById(R.id.lv);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fetSong = database.getReference("Songs");
        fetSong.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    Song song = child.getValue(Song.class);
                    songs.add(song);
                }
                adaper = new SongAdapter(getActivity(),R.layout.song_item,songs,true);
                lv.setAdapter(adaper);
            }
        });
        edtSearch = view.findViewById(R.id.edtSearch);


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                 newSong.clear();
                String text = edtSearch.getText().toString();
                String regex = ".*"+text+".*";
                Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
                Matcher matcher;
                for(int j = 0 ; j < songs.size() ; j++){
                    matcher = pattern.matcher(songs.get(j).getSongName()+songs.get(j).getSingerName());
                    if(matcher.matches()){
                        newSong.add(songs.get(j));
                    }
                }

                if(newSong.size() > 0) {
                    adaper.clear();
                    adaper.addAll(newSong);
                    adaper.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Animation.setAnimation(view);
                Song song = newSong.get(i);
                Log.d("CHECK", "onItemClick: "+song.getSongName()+" "+i);
                Intent intent = new Intent(getActivity(), SubActivity.class);
                intent.putExtra("song",song);
                intent.putExtra("songs",songs);
                startActivity(intent);

            }
        });
        return view;
    }

}