package com.example.student_manage.service;

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

}
