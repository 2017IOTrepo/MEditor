package com.lagranmoon.meditor.fragment;

import android.annotation.SuppressLint;
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
import com.lagranmoon.meditor.util.FileUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class Edit_fragment_Activity extends Fragment {

    public static final String FILE_PATH_KEY = "FILE_PATH_KEY";
    public static final String FILE_NAME_KEY = "FILE_NAME_KEY";
    private EditText Title;
    private EditText Context;
    private String fileName;
    private String filePath;
    private File file;
    private String fileContent;
    private CompositeSubscription mCompositeSubscription;


    public static Edit_fragment_Activity getInstance(String filePath, String fileName){

        Bundle bundle = new Bundle();
        Edit_fragment_Activity edit_fragment_activity
                = new Edit_fragment_Activity();
        bundle.putString(FILE_PATH_KEY, filePath);
        bundle.putString(FILE_NAME_KEY, fileName);
        edit_fragment_activity.setArguments(bundle);
        return edit_fragment_activity;
    }

    public int getLayoutId(){
        return R.layout.edit_fragment_activity;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Bundle arguments = getArguments();
//        String fileTemp = arguments.getString(FILE_PATH_KEY);
//        File file = new File(fileTemp);

    }

    /*
    * 读取文件
    *
    * 部分来源网络 作者:qinc
    * */
    private String loadFile() {
        Long fileLenth = file.length();
        //如果一次性读取不完（超出最大值）
        //便按行读取
        if (fileLenth > Integer.MAX_VALUE){
            return readFilesByLines();
        }
        byte[] fileContent = new byte[fileLenth.intValue()];
        FileInputStream in = null;
        try{
            in = new FileInputStream(file);
            in.read(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseableClose(in);
        }

        return new String(fileContent);
    }

    private String readFilesByLines() {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
         reader = new BufferedReader(new FileReader(file));
         //防止出现除第一行外 行首字符丢失现象
         String tempString;
         while ((tempString = reader.readLine()) != null){
             builder.append(tempString);
         }
        } catch (FileNotFoundException e) {
            //读取文件的受检异常
            e.printStackTrace();
        } catch (IOException e) {
            //readline的受检异常
            e.printStackTrace();
        } finally {
            CloseableClose(reader);
        }

        return builder.toString();
    }

    public void CloseableClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    /*
    * 写入文件
    *
    * */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_fragment_activity, container, false);

        Title = view.findViewById(R.id.edit_title_text);
        Context = view.findViewById(R.id.edit_content_text);

        Bundle bundle = getArguments();
        fileName = bundle.getString(FILE_NAME_KEY);
        filePath = bundle.getString(FILE_PATH_KEY);

        file = new File(filePath, fileName);
        fileContent =  loadFile();

        Title.setText(fileName.substring(0 , fileName.lastIndexOf(".")));
        Context.setText(fileContent);

        return view;
    }

}
