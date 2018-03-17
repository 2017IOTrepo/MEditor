package com.lagranmoon.meditor.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lagranmoon.meditor.R;

public class Edit_fragment_Activity extends Fragment {


    private EditText Title;
    private EditText Context;


    public static Edit_fragment_Activity newInstance(){

        Bundle bundle = new Bundle();
        Edit_fragment_Activity edit_fragment_activity
                = new Edit_fragment_Activity();
        edit_fragment_activity.setArguments(bundle);

        return edit_fragment_activity;
    }

    public int getLayoutId(){
        return R.layout.edit_fragment_activity;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_fragment_activity, container, false);
    }

}
