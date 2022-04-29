package com.example.student_manage.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;

import java.util.*;

public class testExcel {
    public static void main(String[] args) {
        List<Map<Integer, String>> list = new LinkedList<>();
        EasyExcel.read("/Users/charles/code_learn/mySrc/stu-mgr/student_manage_be/src/main/java/com/example/student_manage/excel/studentData.xlsx").sheet()
            .registerReadListener(new AnalysisEventListener<Map<Integer, String>> () {

                    @Override
                    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
                      //先读到list里，读完再统一进行业务处理
                      list.add(integerStringMap);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        System.out.println("read done!");
                    }
                })
                .doRead();
            for(Map<Integer,String> integerStringMap : list) {
              Set<Integer> keySet = integerStringMap.keySet();
              Iterator<Integer> iterator = keySet.iterator();
              while(iterator.hasNext()) {
                Integer key = iterator.next();
                System.out.print(key + ":" + integerStringMap.get(key) + ",");
              }
              System.out.println("");
            }


    }
}
