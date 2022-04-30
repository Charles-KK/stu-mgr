package com.example.student_manage.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.student_manage.easyexcel.StudentListener;
import com.example.student_manage.mapper.StudentMapper;
import com.example.student_manage.pojo.Student;
import com.example.student_manage.util.Response;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public Student doLogin(String name, String password) {
        Student result = studentMapper.getStudentByName(name);
        if (result == null) return null;
        if (StringUtils.hasLength(password) && password.equals(result.getStudentPassword())) {
            return result;
        } else {
            return null;
        }
    }
    public boolean addStudent(Student student) {
        String no = student.getStudentNo();
        //新增学生同时为其账号设置默认密码：学号后六位
        student.setStudentPassword(no.length() > 6 ? no.substring(no.length() - 6) : no);
        return studentMapper.addStudent(student);
    }
    //表格形式导入学生信息
    public boolean batchAddStudent(List<Student> students) {
        students.forEach(student -> {
            String no = student.getStudentNo();
            student.setStudentPassword(no.length() > 6 ? no.substring(no.length() - 6) : no);
        });
        return studentMapper.batchAddStudent(students);
    }
    //获取学生列表，分页展示模块
    public PageInfo getStudents(Student condition, Integer currentPage) {
        PageHelper.startPage(currentPage, 20);
        return new PageInfo<>(studentMapper.getStudents(condition));
    }
    //编辑学生信息
    public boolean editStudent(Student student) {
        return studentMapper.editStudent(student);
    }
    //删除指定Id的学生
    public boolean deleteStudent(Integer stuId) {
        return  studentMapper.deleteStudent(stuId);
    }


    //按姓名模糊查询，分页查询
    public PageInfo fuzzyQueryByName(Integer currentPage, String name) {
        PageHelper.startPage(currentPage, 20);
        return new PageInfo<>(studentMapper.fuzzyQueryByName(name));
    }

//    public void uploadStudent(MultipartFile file) throws IOException {
//      EasyExcel.read(file.getInputStream(), Student.class, new StudentListener(studentMapper)).sheet().doRead();
//    }

    public void exportStudent(OutputStream outputStream) {
        List<Student> list = studentMapper.getStudents(new Student());
        System.out.println(list);
        ExcelWriter excelWriter = EasyExcel.write(outputStream, Student.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("学生信息").build();
        excelWriter.write(list, writeSheet);
    }

    public List<Map<String, Integer>> getGenderDistrubute() {
        return studentMapper.getGenderDistribute();
    }

}
