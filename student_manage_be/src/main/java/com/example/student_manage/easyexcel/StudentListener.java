package com.example.student_manage.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.student_manage.mapper.StudentMapper;
import com.example.student_manage.pojo.Student;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StudentListener extends AnalysisEventListener<Student> {

    private StudentMapper studentMapper;

    private static final int BATCH_COUNT = 100;

    private List<Student> studentList = new ArrayList<>();

    public StudentListener(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }


    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        studentList.add(student);
        if (studentList.size() == BATCH_COUNT) {
            studentMapper.batchAddStudent(studentList);
            studentList.clear();
            log.info("upload insert {} student", BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        studentMapper.batchAddStudent(studentList);
        studentList.clear();
        log.info("upload finish");
    }
}
