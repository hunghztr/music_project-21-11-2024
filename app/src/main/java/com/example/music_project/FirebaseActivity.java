package com.example.music_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.music_project.data.DataForm;
import com.example.music_project.domain.Album;
import com.example.music_project.domain.AlbumDetail;
import com.example.music_project.domain.DatabaseHelper;
import com.example.music_project.domain.Song;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_firebase);
        DatabaseHelper helper = new DatabaseHelper(FirebaseActivity.this);
        ArrayList<Song> songs = DataForm.setSongData();
        ArrayList<Album> albums = DataForm.setAlbumData();
        helper.deleteAlLSongsInAlbum();
        helper.deleteAllSongs();
        helper.deleteAllAlbums();
        DataForm.setSongInAlbum(songs,albums,helper);
//        ArrayList<AlbumDetail> details = helper.getAllsAlbumDetail();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Albums Detail");
//        for(AlbumDetail detail : details) {
//            myRef.child(detail.getSongId()+"").setValue(detail).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    Toast.makeText(FirebaseActivity.this, "thêm thành công"+detail.getSongId(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }


        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//               for(DataSnapshot child : dataSnapshot.getChildren()){
//                   Song song = child.getValue(Song.class);
//                   songs.add(song);
//               }
//               for(Song song : songs){
//                   Log.d("TAG", "onDataChange: "+song.getSongName());
//               }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("TAG", "Failed to read value.", error.toException());
//            }
//        });
    }
}