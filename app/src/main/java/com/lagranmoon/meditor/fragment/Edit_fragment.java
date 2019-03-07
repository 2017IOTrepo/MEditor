package com.lagranmoon.meditor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lagranmoon.meditor.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Edit_fragment extends Fragment {

    public static final String FILE_PATH_KEY = "FILE_PATH_KEY";
    public static final String FILE_NAME_KEY = "FILE_NAME_KEY";
    private EditText Title;
    private EditText Content;
    private String fileName;
    private String filePath;
    // 打开或新建的文件
    private File file;
    // 文件内容
    private String fileContent = "";

    public static Edit_fragment getInstance(String filePath, String fileName){

        Bundle bundle = new Bundle();
        Edit_fragment edit_fragment
                = new Edit_fragment();
        bundle.putString(FILE_PATH_KEY, filePath);
        bundle.putString(FILE_NAME_KEY, fileName);
        edit_fragment.setArguments(bundle);
        return edit_fragment;
    }

    public int getLayoutId(){
        return R.layout.edit_fragment;
    }

    /**
     * 返回内容
     * */
    public String getContent(){
        return Content.getText().toString();
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

        Title = view.findViewById(R.id.edit_title_text);
        Content = view.findViewById(R.id.edit_content_text);

        Bundle bundle = getArguments();
        fileName = bundle.getString(FILE_NAME_KEY);
        filePath = bundle.getString(FILE_PATH_KEY);

        file = new File(filePath, fileName);
        loadFile();

        Title.setText(fileName.substring(0 , fileName.lastIndexOf(".")));
        Content.setText(fileContent);

        return view;
    }
}
