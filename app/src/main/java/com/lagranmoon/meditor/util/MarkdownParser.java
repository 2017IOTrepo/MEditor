package com.lagranmoon.meditor.util;

import java.util.ArrayList;

/**
 * Markdown解析器
 * 这里对每行进行解析
 *
 * 2019/03 写不完了以后写吧
 * */
public class MarkdownParser {
    // 按行储存md文件
    private ArrayList<String> mdList = new ArrayList<>();
    // 储存md文件每行对应的类型
    private ArrayList<String> mdListType = new ArrayList<>();

    /**
     * 判断每一行md语法对应的html类型
     * */
    private void defineAreaType(){
        ArrayList<String> tempList = new ArrayList<>();
        ArrayList<String> tempType = new ArrayList<>();

        // 这里是标记语句，用以标记每行的类型
        tempType.add("OTHER");
        tempList.add(" ");

        // 检测代码区块
        boolean codeBegin = false, codeEnd = false;

        for (int i = 1; i < mdList.size(); i++) {
            String line = mdList.get(i); // 获取到每一行的字符串
            if (line.length() > 2 && line.substring(0, 2).equals("```")){
                // 代码区块
                // 判断是否代码起始结束都没有，没有就标定起始点
                if (!codeBegin && !codeEnd){
                    tempType.add("CODE_BEGIN");
                    tempList.add(" ");
                    codeBegin = true;
                }else if (codeBegin && !codeEnd){
                    // 标定结束点
                    tempType.add("CODE_END");
                    tempList.add(" ");
                    codeBegin = false;
                    codeEnd = false;
                }else {

                }

            }
        }
    }

}
