package com.example.student_manage.pojo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;

import lombok.Data;
import org.apache.poi.ss.usermodel.FillPatternType;

@Data
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 9)
@HeadFontStyle(fontHeightInPoints = 10)
@ExcelIgnoreUnannotated
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

    @ExcelProperty("生源地")
    private String sourceCity;

    @ExcelProperty("家庭地址")
    private String address;

}
