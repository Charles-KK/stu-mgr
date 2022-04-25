package com.example.student_manage.mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.student_manage.pojo.Student;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository


public interface StudentMapper extends BaseMapper<Student> {

    Student getStudentByName(String name);

    boolean addStudent(Student student);

    boolean batchAddStudent(@Param("students") List<Student> students);

    List<Student> getStudents(Student student);

    boolean deleteStudent(Integer stuId);

    boolean editStudent(Student student);

    List<Student> fuzzyQueryByName(String name);

}


//public interface StudentMapper {
//
//    Student getStudentByName(String name);
//
//    boolean addStudent(Student student);
//
//    boolean batchAddStudent(@Param("students") List<Student> students);
//
//    boolean deleteStudent(Student student);
//    List<Student> getStudents(Student student);
//
//}