package com.mimo.jagin.infomovie.fragments.fragments;



import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mimo.jagin.infomovie.R;
import com.mimo.jagin.infomovie.db.PreferencesManager;
import com.mimo.jagin.infomovie.servicios.MediaService;



public class InfoFragments extends Fragment {


    public static InfoFragments newInstance(){
        return new InfoFragments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_fragments, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferencesManager preferencesManager = new PreferencesManager(getActivity());
        if (preferencesManager.isMusicEnabled()) {
            getActivity().startService(new Intent(getActivity(), MediaService.class));
        }
    }
}
