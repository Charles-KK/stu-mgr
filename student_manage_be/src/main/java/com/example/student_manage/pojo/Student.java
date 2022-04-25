package com.example.student_manage.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value="t_student")
@Data 
public class Student {
//    @TableId(value = "student_id",type = IdType.AUTO)
    private Integer studentId;

    @ExcelProperty("学号")
    private String studentNo;

    @ExcelProperty("姓名")
    private String studentName;

    private String studentPassword;

    @ExcelProperty("性别")
    private String gender;

    @ExcelProperty("学院")
    private String department;

    @ExcelProperty("专业")
    private String majority;

    @ExcelProperty("年级")
    private Integer grade;

    @ExcelProperty("班级")
    private Integer classNo;

    @ExcelProperty("毕业年份")
    private Integer graduateYear;

    @ExcelProperty("城市")
    private String sourceCity;

    @ExcelProperty("地址")
    private String address;

}
