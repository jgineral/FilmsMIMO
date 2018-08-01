package com.mimo.jagin.infomovie.fragments;



import android.Manifest;
import android.app.DownloadManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mimo.jagin.infomovie.R;
import com.mimo.jagin.infomovie.db.PreferencesManager;
import com.mimo.jagin.infomovie.servicios.MediaService;

import java.util.Objects;


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

        Button sendButton = Objects.requireNonNull(getView()).findViewById(R.id.btDownload);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadArchive();
            }
        });

    }

    private void downloadArchive(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                downloadPdf();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {
            downloadPdf();
        }
    }

    private void downloadPdf()
    {
        Uri resource = Uri.parse("http://www.web.upsa.es/mimo/guias/android.pdf");
        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(resource);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Android");
        request.setDestinationInExternalFilesDir(getActivity(), Environment.DIRECTORY_DOCUMENTS, "Android.pdf");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferencesManager preferencesManager = new PreferencesManager(getActivity());
        if (preferencesManager.isMusicEnabled()) {
            getActivity().startService(new Intent(getActivity(), MediaService.class));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().stopService(new Intent(getActivity(), MediaService.class));

    }
}
