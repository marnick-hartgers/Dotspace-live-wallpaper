package com.codeenvolved.wallpapers.dotspace.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.codeenvolved.wallpapers.dotspace.R;
import com.codeenvolved.wallpapers.dotspace.engine.RenderSettings;


public class DotAmountSettingsFragment extends Fragment {


    private RenderSettings settings;

    public DotAmountSettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DotAmountSettingsFragment newInstance() {
        DotAmountSettingsFragment fragment = new DotAmountSettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = new RenderSettings(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dot_amount_settings, container, false);

        numDotsInput = v.findViewById(R.id.numDotsInput);
        String numDotsString = ""+ settings.getNumDots();
        numDotsInput.setText(numDotsString);
        numDotsInput.setOnKeyListener(numDotsInputListner);

        spawnModeRadioGroup = v.findViewById(R.id.spawn_mode_radiogroup);

        switch (settings.getSpawnMode()){
            case 0:
                spawnModeRadioGroup.check(R.id.rb_spawn_random);
                break;
            case 1:
                spawnModeRadioGroup.check(R.id.rb_spawn_grid);
                break;
            case 2:
                spawnModeRadioGroup.check(R.id.rb_spawn_grid_r);
                break;
            case 3:
                spawnModeRadioGroup.check(R.id.rb_spawn_rects);
                break;
            case 4:
                spawnModeRadioGroup.check(R.id.rb_spawn_tri);
                break;
        }
        spawnModeRadioGroup.setOnCheckedChangeListener(spawnModeRadioGroupListner);

        return v;
    }


    private EditText numDotsInput;
    private EditText.OnKeyListener numDotsInputListner = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(numDotsInput.getText().length() > 0){
                settings.setNumDots(Integer.parseInt(numDotsInput.getText().toString()));
            }
            return false;
        }
    };

    private RadioGroup spawnModeRadioGroup;
    private RadioGroup.OnCheckedChangeListener spawnModeRadioGroupListner = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_spawn_random:
                    settings.setSpawnMode(0);
                    break;
                case R.id.rb_spawn_grid:
                    settings.setSpawnMode(1);
                    break;
                case R.id.rb_spawn_grid_r:
                    settings.setSpawnMode(2);
                    break;
                case R.id.rb_spawn_rects:
                    settings.setSpawnMode(3);
                    break;
                case R.id.rb_spawn_tri:
                    settings.setSpawnMode(4);
                    break;
            }

        }
    };
}
