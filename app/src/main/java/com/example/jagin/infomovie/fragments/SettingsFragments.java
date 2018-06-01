package com.example.jagin.infomovie.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.jagin.infomovie.R;
import com.example.jagin.infomovie.db.PreferencesManager;
import com.example.jagin.infomovie.servicios.MediaService;

import java.util.Objects;

public class SettingsFragments extends Fragment {

    private PreferencesManager preferenceManager;

    public SettingsFragments() {
    }

    public static SettingsFragments newInstance() {
        return new SettingsFragments();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_fragments, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        preferenceManager = new PreferencesManager(getActivity());

        Switch switch2 = Objects.requireNonNull(getView()).findViewById(R.id.switch2);

        if (preferenceManager.isMusicEnabled()) {
            switch2.setChecked(true);
        }
        else
        {  switch2.setChecked(false);}

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    setValueTrue();
                }else{
                    setValueFalse();
                }

            }
        });


    }

    private void setValueFalse() {
        preferenceManager.setMusicEnabled(false);
        getActivity().stopService(new Intent(getActivity(), MediaService.class));
    }

    private void setValueTrue() {
        preferenceManager.setMusicEnabled(true);
        getActivity().startService(new Intent(getActivity(), MediaService.class));
    }

}
