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
import com.lagranmoon.meditor.view.SymbolView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class EditFragment extends Fragment {

    // 传参定义思密达
    public static final String FILE_PATH_KEY = "FILE_PATH_KEY";
    public static final String FILE_NAME_KEY = "FILE_NAME_KEY";
    public static final String IF_NEW = "IF_NEW";

    private EditText textTitle;
    private EditText textContent;
    private String fileTitle;
    private String fileName;
    private String filePath;
    // 打开或新建的文件
    private File file;
    // 文件内容
    private String fileContent = "";
    // 用于记录是否是新打开的文件
    private boolean isNew = false;

    public boolean isTextChange = false;
    public boolean isTitleChanged = false;
    public LinkedList<String> beforeString = new LinkedList<>(); // 所有的撤销栈

    private SymbolView symbolView; // 一个快捷输入

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
     * 获取标题
     * */
    public String getFileTitle(){
        return textTitle.getText().toString();
    }

    public String getFileName(){
        return fileName;
    }

    /**
     * 返回内容
     * */
    public String getTextContent(){
        return textContent.getText().toString();
    }

    /**
     * 设定内容
     * */
    public void setTextContent(String content){
        System.out.println(content);
        textContent.setText(content);
        return;
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

            fileReader.close();
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
            file = new File(filePath);
            loadFile();
            fileTitle = fileName.substring(0 , fileName.lastIndexOf("."));
            textTitle.setText(fileTitle);
            textContent.setText(fileContent);
        }

        /**
         * 文本更改监听器
         * */
        textContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            /**
             * 压入撤回队列
             * */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(s);
                isTextChange = true; // 已经改变
                beforeString.add(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /**
         * 检测标题是否更变
         * */
        textTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isTitleChanged = true;
                isTextChange = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
}
