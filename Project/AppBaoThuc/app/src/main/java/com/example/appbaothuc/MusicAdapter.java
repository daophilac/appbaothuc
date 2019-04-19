package com.example.appbaothuc;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private Context context;
    private Alarm alarm;
    private List<Music> listMusic;
    private boolean multipleChoice = false; //TODO

    private List<CheckBox> listCheckBoxCheck;
    private SparseArray<Music> mapCheckBoxMusic;
    private CheckBox checkBoxCurrentChecked;

    private MediaPlayer mediaPlayer;
    private ImageButton buttonPreviousPlay;
    private ImageButton buttonNextPlay;
    public MusicAdapter(Context context, Alarm alarm, List<Music> listMusic){
        this.context = context;
        this.alarm = alarm;
        this.listMusic = listMusic;

        this.listCheckBoxCheck = new ArrayList<>();
        this.mapCheckBoxMusic = new SparseArray<>();
        this.mediaPlayer = new MediaPlayer();
    }
    public MusicAdapter(Context context, Alarm alarm){
        this.context = context;
        this.alarm = alarm;

        this.listCheckBoxCheck = new ArrayList<>();
        this.mediaPlayer = new MediaPlayer();
    }

    public void setListMusic(List<Music> listMusic) {
        this.listMusic = listMusic;
    }

    public Music getCheckedMusic(){
        if(checkBoxCurrentChecked == null){
            return null;
        }
        return mapCheckBoxMusic.get(checkBoxCurrentChecked.getId());
    }

    public void stopMusic(){
        mediaPlayer.stop();
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.item_music, viewGroup, false);
        MusicViewHolder musicViewHolder = new MusicViewHolder(itemView);
        listCheckBoxCheck.add(musicViewHolder.checkBoxCheck);
        return musicViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder musicViewHolder, int i) {
        final Music music = listMusic.get(i);
        CheckBox checkBoxCheck = musicViewHolder.checkBoxCheck;
        TextView textViewMusicName = musicViewHolder.textViewMusicName;
        ImageButton buttonPlayMusic = musicViewHolder.buttonPlayMusic;

        if(alarm.getRingtoneName() != null){
            checkBoxCheck.setChecked(alarm.getRingtoneName().equals(music.getName()));
        }
        textViewMusicName.setText(music.getName());

        checkBoxCheck.setId(View.generateViewId());
        mapCheckBoxMusic.put(checkBoxCheck.getId(), music);

        checkBoxCheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CheckBox checkBoxCurrent = (CheckBox)v;
                if(checkBoxCurrent.isChecked()){
                    if(!multipleChoice){
                        for(int i = 0; i < listCheckBoxCheck.size(); i++){
                            if(listCheckBoxCheck.get(i).isChecked()){
                                listCheckBoxCheck.get(i).setChecked(false);
                            }
                        }
                        checkBoxCurrent.setChecked(true);
                        checkBoxCurrentChecked = checkBoxCurrent;
                    }
                }
            }
        });
        buttonPlayMusic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                buttonNextPlay = (ImageButton)v;
                if(!mediaPlayer.isPlaying()){
                    buttonNextPlay.setImageDrawable(context.getDrawable(R.drawable.ic_pause));
                    if(buttonPreviousPlay != null){
                        if(buttonPreviousPlay != buttonNextPlay){
                            buttonPreviousPlay.setImageDrawable(context.getDrawable(R.drawable.ic_play));
                        }
                    }
                    mediaPlayer = MediaPlayer.create(context, Uri.fromFile(new File(music.getUrl())));
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    buttonPreviousPlay = buttonNextPlay;
                }
                else{

                    buttonPreviousPlay.setImageDrawable(context.getDrawable(R.drawable.ic_play));
                    if(buttonPreviousPlay == buttonNextPlay){
                        mediaPlayer.stop();
                    }
                    else{
                        mediaPlayer.stop();
                        buttonNextPlay.setImageDrawable(context.getDrawable(R.drawable.ic_pause));
                        mediaPlayer = MediaPlayer.create(context, Uri.fromFile(new File(music.getUrl())));
                        mediaPlayer.setLooping(true);
                        mediaPlayer.start();
                    }
                    buttonPreviousPlay = buttonNextPlay;
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return listMusic.size();
    }
    class MusicViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBoxCheck;
        private TextView textViewMusicName;
        private ImageButton buttonPlayMusic;
        MusicViewHolder(View itemView){
            super(itemView);
            checkBoxCheck = itemView.findViewById(R.id.check_box_check);
            textViewMusicName = itemView.findViewById(R.id.text_view_music_name);
            buttonPlayMusic = itemView.findViewById(R.id.button_play_music);
        }
    }
}