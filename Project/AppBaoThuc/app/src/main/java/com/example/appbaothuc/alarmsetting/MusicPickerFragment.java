package com.example.appbaothuc.alarmsetting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.Music;
import com.example.appbaothuc.MusicAdapter;
import com.example.appbaothuc.R;
import com.example.appbaothuc.appsetting.AppSettingFragment;
import com.example.appbaothuc.models.Alarm;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicPickerFragment extends Fragment {
    private OnFinishPickMusicListener onFinishPickMusicListener;
    private Alarm alarm;
    private Button buttonRingtone;
    private Button buttonMusic;
    private Button buttonSortByName;
    private Button buttonSortByUrl;
    private ImageView imageViewSortByName;
    private ImageView imageViewSortByUrl;
    private RecyclerView recyclerViewListMusic;
    private Button buttonCancel;
    private Button buttonOk;
    private MusicAdapter musicAdapter;

    private List<Music> listRingtone;
    private List<Music> listMusic;

    private ChooseMusicType chooseMusicType;
    private SortBy isSortingBy;
    private Order isWithOrder;

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listRingtone = new ArrayList<>();
        listMusic = new ArrayList<>();
        musicAdapter = new MusicAdapter(context, alarm.getRingtone(), listMusic);
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
        buttonRingtone = view.findViewById(R.id.button_ringtone);
        buttonMusic = view.findViewById(R.id.button_music);
        buttonSortByName = view.findViewById(R.id.button_sort_by_name);
        buttonSortByUrl = view.findViewById(R.id.button_sort_by_url);
        imageViewSortByName = view.findViewById(R.id.image_sort_by_name);
        imageViewSortByUrl = view.findViewById(R.id.image_sort_by_url);
        recyclerViewListMusic = view.findViewById(R.id.recycler_view_list_music);
        buttonCancel = view.findViewById(R.id.button_give_up);
        buttonOk = view.findViewById(R.id.button_ok);

        recyclerViewListMusic.setAdapter(musicAdapter);
        recyclerViewListMusic.setLayoutManager(new LinearLayoutManager(getContext()));
        isSortingBy = SortBy.NAME;
        isWithOrder = Order.ASC;
        buttonRingtone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(chooseMusicType != ChooseMusicType.RINGTONE){
                    chooseMusicType = ChooseMusicType.RINGTONE;
                    makeListRingtone();
                }
            }
        });
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
            public void onClick(View v) {getFragmentManager().popBackStack(); }
        });
        buttonOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onFinishPickMusicListener.onFinishPickMusic(musicAdapter.getCurrentMusic());
                getFragmentManager().popBackStack();
            }
        });
        buttonSortByName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isSortingBy != SortBy.NAME){
                    isSortingBy = SortBy.NAME;
                    isWithOrder = Order.ASC;
                    Collections.sort(listMusic, new Music.NameComparator());
                    imageViewSortByName.setImageDrawable(getContext().getDrawable(R.drawable.ic_arrow_drop_up));
                }
                else if(isWithOrder == Order.ASC){
                    isWithOrder = Order.DESC;
                    Collections.sort(listMusic, new Music.NameComparator());
                    Collections.reverse(listMusic);
                    imageViewSortByName.setImageDrawable(getContext().getDrawable(R.drawable.ic_arrow_drop_down));
                }
                else if(isWithOrder == Order.DESC){
                    isWithOrder = Order.ASC;
                    Collections.sort(listMusic, new Music.NameComparator());
                    imageViewSortByName.setImageDrawable(getContext().getDrawable(R.drawable.ic_arrow_drop_up));
                }
                musicAdapter.notifyDataSetChanged();
            }
        });
        buttonSortByUrl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isSortingBy != SortBy.URL){
                    isSortingBy = SortBy.URL;
                    isWithOrder = Order.ASC;
                    Collections.sort(listMusic, new Music.UrlComparator());
                    imageViewSortByUrl.setImageDrawable(getContext().getDrawable(R.drawable.ic_arrow_drop_up));
                }
                else if(isWithOrder == Order.ASC){
                    isWithOrder = Order.DESC;
                    Collections.sort(listMusic, new Music.UrlComparator());
                    Collections.reverse(listMusic);
                    imageViewSortByUrl.setImageDrawable(getContext().getDrawable(R.drawable.ic_arrow_drop_down));
                }
                else if(isWithOrder == Order.DESC){
                    isWithOrder = Order.ASC;
                    Collections.sort(listMusic, new Music.UrlComparator());
                    imageViewSortByUrl.setImageDrawable(getContext().getDrawable(R.drawable.ic_arrow_drop_up));
                }
                musicAdapter.notifyDataSetChanged();
            }
        });
        makeListMusic();
        return view;
    }
    private void makeListRingtone(){

    }
    private void makeListMusic(){
        List<String> listMusicPath = getAllMusicFileFromListFilePath(AppSettingFragment.listRingtoneDirectory);
        for(int i = 0; i < listMusicPath.size(); i++){
            String path = listMusicPath.get(i);
            listMusic.add(new Music(path, path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."))));
        }
        Collections.sort(listMusic, new Music.NameComparator());
        musicAdapter.notifyDataSetChanged();
    }
    private List<String> getAllMusicFileRecursively(String fileOrDirectory){
        List<String> filePath = new ArrayList<>();
        File file = new File(fileOrDirectory);
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                filePath.addAll(getAllMusicFileRecursively(f.getAbsolutePath()));
            }
            else if(checkIfIsMusicFile(f.getName())){
                filePath.add(f.getAbsolutePath());
            }
        }
        return filePath;
    }
    private List<String> getAllMusicFileFromListFilePath(List<String> listRingtoneDirectory){
        List<String> filePath = new ArrayList<>();
        for(String path : listRingtoneDirectory){
            filePath.addAll(getAllMusicFileRecursively(path));
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

    public void setOnFinishPickMusicListener(OnFinishPickMusicListener onFinishPickMusicListener) {
        this.onFinishPickMusicListener = onFinishPickMusicListener;
    }
    public interface OnFinishPickMusicListener{
        void onFinishPickMusic(Music music);
    }
    public enum ChooseMusicType {
        RINGTONE, MUSIC
    }
    enum SortBy{
        NAME, URL
    }
    enum Order{
        ASC, DESC
    }
}
