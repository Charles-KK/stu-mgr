package com.example.student_manage.dto;

import com.example.student_manage.pojo.Student;
import lombok.Data;

@Data
public class StudentQuery {

    private Student condition;

    private Integer currentPage;
}
