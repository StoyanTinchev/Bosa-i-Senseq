package com.example.musicapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Musics.AlbumDetailsAdapter;
import com.example.musicapp.Musics.MusicAdapter;
import com.example.musicapp.Musics.MusicFile;
import com.example.musicapp.R;

import java.util.ArrayList;

import static com.example.musicapp.Activities.MainActivity.musicFiles;

public class AlbumDetails extends AppCompatActivity
{
    RecyclerView recyclerView;
    ImageView albumPhoto;
    String albumName;
    public ArrayList<MusicFile> albumSongs = new ArrayList<>();
    public static AlbumDetailsAdapter albumDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        recyclerView = findViewById(R.id.recyclerView);
        albumPhoto = findViewById(R.id.albumPhoto);
        albumName = getIntent().getStringExtra("albumName");
        int j = 0;
        for (int i = 0; i < musicFiles.size(); ++i)
            if (albumName.equals(musicFiles.get(i).getAlbum()))
            {
                albumSongs.add(j, musicFiles.get(i));
                ++j;
            }
        byte[] image = MusicAdapter.getAlbumArt(albumSongs.get(0).getPath());
        if (image != null)
        {
            Glide.with(this)
                    .load(image)
                    .into(albumPhoto);
        }
        else
        {
            Glide.with(this)
                    .load(R.drawable.musicimage)
                    .into(albumPhoto);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (!(albumSongs.size() < 1))
        {
            albumDetailsAdapter = new AlbumDetailsAdapter(this, albumSongs);
            recyclerView.setAdapter(albumDetailsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }
    }

    private ItemTouchHelper.Callback createHelperCallback()
    {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                ItemTouchHelper.DOWN, 0)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
            {
                moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { }
        };
    }

    public void moveItem(int oldPos, int newPos)
    {
        MusicFile musicFile = musicFiles.get(oldPos);
        musicFiles.remove(oldPos);
        musicFiles.add(newPos, musicFile);
        albumDetailsAdapter.notifyItemMoved(oldPos, newPos);
    }
}