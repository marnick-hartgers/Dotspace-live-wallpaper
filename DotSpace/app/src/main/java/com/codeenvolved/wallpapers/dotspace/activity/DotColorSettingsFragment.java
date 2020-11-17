package com.codeenvolved.wallpapers.dotspace.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeenvolved.wallpapers.dotspace.R;
import com.codeenvolved.wallpapers.dotspace.engine.RenderSettings;

import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DotColorSettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DotColorSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DotColorSettingsFragment extends Fragment {

    private RenderSettings settings;

    public DotColorSettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DotColorSettingsFragment newInstance() {
        DotColorSettingsFragment fragment = new DotColorSettingsFragment();
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
        View v =  inflater.inflate(R.layout.fragment_dot_color_settings, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backgroundColorPicker = view.findViewById(R.id.colorPickerBackround);
        backgroundColorPicker.setColorPick(settings.getBackgroundColor());
        backgroundColorPicker.registerOnColorPickChangeListner(backgroundColorPickerListner);

        dotsColorPicker = view.findViewById(R.id.colorPickerDots);
        dotsColorPicker.setColorPick(settings.getDotColor());
        dotsColorPicker.registerOnColorPickChangeListner(dotsColorPickerListner);

        dotsEffectColorPicker = view.findViewById(R.id.colorPickerDotsEffect);
        dotsEffectColorPicker.setColorPick(settings.getDotEffectColor());
        dotsEffectColorPicker.registerOnColorPickChangeListner(dotsEffectColorPickerListner);

        if(backgroundColorPicker == dotsColorPicker){
            Log.d("ColorFragment", "Color picker are the same");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private ColorPickerView backgroundColorPicker;
    private ColorPickerView.OnColorPickChangedListner backgroundColorPickerListner = new ColorPickerView.OnColorPickChangedListner() {
        @Override
        public void onColorPickChanged() {
            settings.setBackgroundColor(backgroundColorPicker.getColorPick());
        }
    };

    private ColorPickerView dotsColorPicker;
    private ColorPickerView.OnColorPickChangedListner dotsColorPickerListner = new ColorPickerView.OnColorPickChangedListner() {
        @Override
        public void onColorPickChanged() {
            settings.setDotColor(dotsColorPicker.getColorPick());
        }
    };

    private ColorPickerView dotsEffectColorPicker;
    private ColorPickerView.OnColorPickChangedListner dotsEffectColorPickerListner = new ColorPickerView.OnColorPickChangedListner() {
        @Override
        public void onColorPickChanged() {
            settings.setDotEffectColor(dotsEffectColorPicker.getColorPick());
        }
    };
}
