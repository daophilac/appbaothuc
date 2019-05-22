package com.example.appbaothuc.challenge;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.appbaothuc.R;

public class GiveUpDialogFragment extends DialogFragment {
    private ChallengeActivity challengeActivity;
    private ImageButton imageButtonBack;
    private TextView textViewCount;
    private Button buttonGiveUp;

    private int countDownFrom;
    public static GiveUpDialogFragment newInstance(){
        GiveUpDialogFragment giveUpDialogFragment = new GiveUpDialogFragment();
        giveUpDialogFragment.countDownFrom = 500;
        return giveUpDialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        challengeActivity = (ChallengeActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_give_up, container, false);
        this.imageButtonBack = view.findViewById(R.id.image_button_back);
        this.textViewCount = view.findViewById(R.id.text_view_count);
        this.buttonGiveUp = view.findViewById(R.id.button_give_up);

        this.textViewCount.setText("Press button for " + countDownFrom + " times.");

        this.imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        this.buttonGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownFrom--;
                if(countDownFrom == 0){
                    challengeActivity.challengeFinished();
                    getDialog().dismiss();
                }
                textViewCount.setText("Press button for " + countDownFrom + " times.");
            }
        });
        return view;
    }
}
