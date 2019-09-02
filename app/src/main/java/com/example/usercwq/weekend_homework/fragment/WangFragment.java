package com.example.usercwq.weekend_homework.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.usercwq.weekend_homework.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WangFragment extends Fragment {


    public WangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wang, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WebView webview = getView().findViewById(R.id.webview);
        webview.loadUrl("http://www.baidu.com");
        webview.setWebViewClient(new WebViewClient());

    }
}
