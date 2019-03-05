package com.lagranmoon.meditor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lagranmoon.meditor.R;

import us.feras.mdv.MarkdownView;

public class Display_fragment extends Fragment {
    private MarkdownView markdownView;

    public static Display_fragment getInstance(){

        Bundle bundle = new Bundle();
        Display_fragment display_fragment_
                = new Display_fragment();
        display_fragment_.setArguments(bundle);

        return display_fragment_;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.display_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        markdownView = view.findViewById(R.id.markdown_content);
        //markdownView.loadMarkdown();
    }
}
