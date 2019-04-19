package com.example.appbaothuc.alarmsetting;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appbaothuc.Alarm;
import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.Music;
import com.example.appbaothuc.MusicAdapter;
import com.example.appbaothuc.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicPickerFragment extends Fragment {
    private Context context;
    private SettingAlarmFragment settingAlarmFragment;
    private DatabaseHandler databaseHandler;
    private Alarm alarm;
    private Button buttonRingtone;
    private Button buttonMusic;
    private RecyclerView recyclerViewListMusic;
    private Button buttonCancel;
    private Button buttonOk;
    private MusicAdapter musicAdapter;

    private List<Music> listRingtone;
    private List<Music> listMusic;

    private String rootExternalDirectory;
    private ChooseMusicType chooseMusicType;

    public static MusicPickerFragment newInstance(SettingAlarmFragment settingAlarmFragment, Alarm alarm){
        MusicPickerFragment musicPickerFragment = new MusicPickerFragment();
        musicPickerFragment.settingAlarmFragment = settingAlarmFragment;
        musicPickerFragment.alarm = alarm;
        return musicPickerFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onPause() {
        super.onPause();
        musicAdapter.stopMusic();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_picker, container, false);
        databaseHandler = new DatabaseHandler(context);
        buttonRingtone = view.findViewById(R.id.button_ringtone);
        buttonMusic = view.findViewById(R.id.button_music);
        recyclerViewListMusic = view.findViewById(R.id.recycler_view_list_music);
        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonOk = view.findViewById(R.id.button_ok);

        listRingtone = new ArrayList<>();
        listMusic = new ArrayList<>();
        rootExternalDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        musicAdapter = new MusicAdapter(context, alarm, listMusic);
        recyclerViewListMusic.setAdapter(musicAdapter);
        recyclerViewListMusic.setLayoutManager(new LinearLayoutManager(getContext()));

//        buttonRingtone.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                if(chooseMusicType != ChooseMusicType.RINGTONE){
//                    chooseMusicType = ChooseMusicType.RINGTONE;
//                    makeListRingtone();
//                }
//            }
//        });
        buttonMusic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(chooseMusicType != ChooseMusicType.MUSIC){
                    chooseMusicType = ChooseMusicType.MUSIC;
                    makeListMusic();
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(MusicPickerFragment.this).commit();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Music checkedMusic = musicAdapter.getCheckedMusic();
                if(checkedMusic != null){
                    settingAlarmFragment.getMusic(checkedMusic);
                }
                getFragmentManager().beginTransaction().remove(MusicPickerFragment.this).commit();
            }
        });
        makeListMusic();
        return view;
    }
    private void makeListRingtone(){

    }
    private void makeListMusic(){
        List<String> listMusicPath = getAllMusicFile(rootExternalDirectory);
        for(int i = 0; i < listMusicPath.size(); i++){
            String path = listMusicPath.get(i);
            listMusic.add(new Music(path, path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."))));
        }
        Collections.sort(listMusic, new Music.NameComparator());
        musicAdapter.setListMusic(listMusic);
        musicAdapter.notifyDataSetChanged();
    }
    private List<String> getAllMusicFile(String fileOrDirectory){
        List<String> filePath = new ArrayList<>();
        File file = new File(fileOrDirectory);
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                filePath.addAll(getAllMusicFile(f.getAbsolutePath()));
            }
            else if(checkIfIsMusicFile(f.getName())){
                filePath.add(f.getAbsolutePath());
            }
        }
        return filePath;
    }
    private boolean checkIfIsMusicFile(String fileName){
        fileName = fileName.toLowerCase();
        if(fileName.endsWith(".mp3") ||
            fileName.endsWith(".flac") ||
            fileName.endsWith(".wav") ||
            fileName.endsWith(".m4a") ||
            fileName.endsWith(".ogg") ||
            fileName.endsWith(".wma")){
            return true;
        }
        return false;
    }
    public enum ChooseMusicType {
        RINGTONE, MUSIC
    }
}
