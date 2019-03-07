package com.lagranmoon.meditor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lagranmoon.meditor.R;
import com.zzhoujay.richtext.RichText;

public class Display_fragment extends Fragment {
    private TextView markdownTitle;
    private TextView markdownContent;
    private Bundle getsBundle;

    public static Display_fragment getInstance(){

        Bundle bundle = new Bundle();
        Display_fragment display_fragment
                = new Display_fragment();
        display_fragment.setArguments(bundle);

        return display_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.display_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

//        getsBundle.getString();
    }

    /**
     * 初始化视图
     * */
    private void initView(View view) {
        markdownTitle = view.findViewById(R.id.markdown_title);
        markdownContent = view.findViewById(R.id.markdown_content);
        getsBundle = new Bundle();
    }

    /**
     * 设置markdown文本
     * */
    public void setMarkdownContent(String fromText) {
        RichText
                .from(fromText)
                .into(markdownContent);
    }
}
