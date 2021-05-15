package com.example.musicapp.Musics;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Activities.AlbumDetails;
import com.example.musicapp.Activities.PlayerActivity;
import com.example.musicapp.R;

import java.util.ArrayList;

public class AlbumDetailsAdapter extends RecyclerView.Adapter<AlbumDetailsAdapter.MyHolder>
{
    private Context mContext;
    public static ArrayList<MusicFile> albumFiles;
    View view;

    public AlbumDetailsAdapter(Context mContext, ArrayList<MusicFile> albumFiles)
    {
        this.mContext = mContext;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @Override
    public AlbumDetailsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        view = LayoutInflater.from(mContext).inflate(R.layout.music_items, parent, false);
        return new AlbumDetailsAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumDetailsAdapter.MyHolder holder, int position)
    {
        String album_name_text = albumFiles.get(position).getTitle();

        if (album_name_text.length() > 20)
            album_name_text = album_name_text.substring(0, 20) + "...";

        holder.album_name.setText(album_name_text);
        byte[] image = null;
        if (albumFiles.get(position).getPath() != null && MusicAdapter.isPathValid(albumFiles.get(position).getPath()))
            image = MusicAdapter.getAlbumArt(albumFiles.get(position).getPath());

        if (image != null)
        {
            Glide.with(mContext).asBitmap()
                    .load(image)
                    .into(holder.album_image);
        }
        else
        {
            Glide.with(mContext)
                    .load(R.drawable.musicimage)
                    .into(holder.album_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("sender", "albumDetails");
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return albumFiles.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder
    {
        ImageView album_image;
        TextView album_name;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            album_image = itemView.findViewById(R.id.music_img);
            album_name = itemView.findViewById(R.id.music_file_name);
        }
    }
}
