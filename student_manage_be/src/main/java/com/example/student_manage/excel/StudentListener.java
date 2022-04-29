package com.example.student_manage.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.student_manage.pojo.Student;
import com.example.student_manage.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class StudentListener extends AnalysisEventListener<Student> {

    private StudentService studentService;

    private List<Student> studentList = new ArrayList<>();

    private static final int BATCH_COUNT = 50;

    public StudentListener(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
//    每解析到一条数据，回调
    public void invoke(Student student, AnalysisContext analysisContext) {
        studentList.add(student);
        if (studentList.size() >= BATCH_COUNT) {
            studentService.batchAddStudent(studentList);
            studentList.clear();
        }
    }

    @Override
//    文件读取完毕
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        studentService.batchAddStudent(studentList);
        studentList.clear();
    }
}
