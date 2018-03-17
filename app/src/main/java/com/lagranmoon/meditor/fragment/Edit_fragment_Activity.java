package com.lagranmoon.meditor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lagranmoon.meditor.R;

import java.io.File;

import rx.subscriptions.CompositeSubscription;

public class Edit_fragment_Activity extends Fragment {

    public static final String FILE_PATH_KEY = "FILE_PATH_KEY";
    private EditText Title;
    private EditText Context;
    private String fileName;
    private CompositeSubscription mCompositeSubscription;


    public static Edit_fragment_Activity getInstance(String filePath, String fileName){

        Bundle bundle = new Bundle();
        Edit_fragment_Activity edit_fragment_activity
                = new Edit_fragment_Activity();
        bundle.putString(FILE_PATH_KEY, filePath);
        edit_fragment_activity.setArguments(bundle);

        
        return edit_fragment_activity;
    }

    public int getLayoutId(){
        return R.layout.edit_fragment_activity;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Title = view.findViewById(R.id.edit_title_text);
        Context = view.findViewById(R.id.edit_content_text);
        loadFile();

//        Bundle arguments = getArguments();
//        String fileTemp = arguments.getString(FILE_PATH_KEY);
//        File file = new File(fileTemp);

    }

    /*
    * 加载文件
    *
    * 来源网络 作者:qinc
    * */
    private void loadFile() {
        //mCompositeSubscription.add();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_fragment_activity, container, false);
    }

}
