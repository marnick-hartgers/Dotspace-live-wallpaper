package com.codeenvolved.wallpapers.dotspace.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import com.codeenvolved.wallpapers.dotspace.R;
import com.codeenvolved.wallpapers.dotspace.engine.RenderSettings;

public class DotParallaxSettingsFragment extends Fragment {

    private RenderSettings settings;

    public DotParallaxSettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DotParallaxSettingsFragment newInstance() {
        DotParallaxSettingsFragment fragment = new DotParallaxSettingsFragment();
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
        View v = inflater.inflate(R.layout.fragment_dot_parallax_settings, container, false);
        parallaxEffectAmount = v.findViewById(R.id.seekBarParallaxAmount);
        parallaxEffectAmount.setProgress((int)(settings.getParallaxAmount() * 100f));
        parallaxEffectAmount.setOnSeekBarChangeListener(parallaxEffectAmountListener);
        parallaxInvertDepthSwitch = v.findViewById(R.id.switchParallaxInvertDepth);
        parallaxInvertDepthSwitch.setChecked(settings.getParallaxInvertDepth());
        parallaxInvertDepthSwitch.setOnCheckedChangeListener(parallaxInvertDepthSwitchListner);
        motionBlurEffectAmount = v.findViewById(R.id.seekBarMotionBlur);
        motionBlurEffectAmount.setProgress((int)(settings.getParallaxAmount() * 100f));
        motionBlurEffectAmount.setOnSeekBarChangeListener(motionBlurEffectAmountListener);
        return v;
    }

    //Parallax amount
    private SeekBar parallaxEffectAmount;
    private SeekBar.OnSeekBarChangeListener parallaxEffectAmountListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            settings.setParallaxAmount((float)progress / 100f);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private Switch parallaxInvertDepthSwitch;
    private Switch.OnCheckedChangeListener parallaxInvertDepthSwitchListner = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            settings.setParallaxInvertDepth(isChecked);
        }
    };

    //Motionblur amount
    private SeekBar motionBlurEffectAmount;
    private SeekBar.OnSeekBarChangeListener motionBlurEffectAmountListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            settings.setMotionBlur((float)progress / 100f);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
