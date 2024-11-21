package com.example.music_project;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.music_project.data.DataForm;
import com.example.music_project.domain.Album;
import com.example.music_project.domain.DatabaseHelper;
import com.example.music_project.domain.Song;
import com.example.music_project.fragment.AlbumFragment;
import com.example.music_project.fragment.HomeFragment;
import com.example.music_project.fragment.LibraryFragment;
import com.example.music_project.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addFragment(new HomeFragment());
        DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
        if(helper.isTableEmpty()){
            ArrayList<Song> songs = DataForm.setSongData();
            ArrayList<Album> albums = DataForm.setAlbumData();
            for(Song song : songs){
                helper.addSong(song);
            }
            for(Album album : albums){
                helper.addAlbum(album);
            }
            DataForm.setSongInAlbum(songs,albums,helper);
        }

        navi = findViewById(R.id.navi);
        navi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    replaceFragment(new HomeFragment());
                } else if (id == R.id.search) {
                    replaceFragment(new SearchFragment());
                } else if (id == R.id.library) {
                    replaceFragment(new LibraryFragment());
                }else{
                    replaceFragment(new AlbumFragment());
                }
                return true;
            }
        });
    }

    public void addFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.root, fragment).addToBackStack(null).commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.root, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}