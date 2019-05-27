package com.example.appbaothuc.challenge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.appbaothuc.R;
import com.peanut.androidlib.sensormanager.MovingDetector;

public class MovingChallengeDebugDialogFragment extends DialogFragment {
    private MovingDetector.LocationDetector locationDetector;
    private EditText editTextRadius;
    private Button buttonCancel;
    private Button buttonOk;

    public static MovingChallengeDebugDialogFragment newInstace(MovingDetector.LocationDetector locationDetector){
        MovingChallengeDebugDialogFragment movingChallengeDebugDialogFragment = new MovingChallengeDebugDialogFragment();
        movingChallengeDebugDialogFragment.locationDetector = locationDetector;
        return movingChallengeDebugDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_debug_moving_challenge, container, false);
        editTextRadius = view.findViewById(R.id.edit_text_radius);
        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonOk = view.findViewById(R.id.button_ok);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextRadius.getText() != null){
                    locationDetector.setMaxAccuracyRadius(Float.parseFloat(editTextRadius.getText().toString()));
                    dismiss();
                }
            }
        });
        return view;
    }
}
