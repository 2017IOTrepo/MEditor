package com.lagranmoon.meditor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lagranmoon.meditor.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EditFragment extends Fragment {

    public static final String FILE_PATH_KEY = "FILE_PATH_KEY";
    public static final String FILE_NAME_KEY = "FILE_NAME_KEY";
    public static final String IF_NEW = "IF_NEW";
    private EditText textTitle;
    private EditText textContent;
    private String fileName;
    private String filePath;
    // 打开或新建的文件
    private File file;
    // 文件内容
    private String fileContent = "";
    // 用于记录是否是新打开的文件
    private boolean isNew = false;

    public boolean isTextChange = false;

    // 记录起始点于更改数值
    int startWatcher = -1, countWatcher = -1, beforeWatcher = -1;

    public static EditFragment getInstance(String filePath, String fileName, boolean ifNew){

        Bundle bundle = new Bundle();
        EditFragment edit_fragment
                = new EditFragment();
        bundle.putString(FILE_PATH_KEY, filePath);
        bundle.putString(FILE_NAME_KEY, fileName);
        bundle.putBoolean(IF_NEW, ifNew);
        edit_fragment.setArguments(bundle);
        return edit_fragment;
    }

    public int getLayoutId(){
        return R.layout.edit_fragment;
    }

    /**
     * 返回内容
     * */
    public String getTextContent(){
        return textContent.getText().toString();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /*
    * 读取文件
    * 流读取
    * */
    private void loadFile() {
        try {
            FileReader fileReader = new FileReader(file);

            char[] cbuf = new char[32];
            int hasRead = 0;
            while ((hasRead = fileReader.read(cbuf)) > 0){
                fileContent += (new String(cbuf, 0, hasRead));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_fragment, container, false);

        textTitle = view.findViewById(R.id.edit_title_text);
        textContent = view.findViewById(R.id.edit_content_text);

        Bundle bundle = getArguments();
        fileName = bundle.getString(FILE_NAME_KEY);
        filePath = bundle.getString(FILE_PATH_KEY);
        isNew = bundle.getBoolean(IF_NEW);

        // 如果是新建文件就不用读取内容了
        if (!isNew){
            file = new File(filePath, fileName);
            loadFile();
        }

        textTitle.setText(fileName.substring(0 , fileName.lastIndexOf(".")));
        textContent.setText(fileContent);

        /**
         * 文本更改监听器
         * */
        textContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            /**
             * TODO 进行撤回与恢复的操作
             * */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isTextChange = true;
            }
        });

        return view;
    }
}
