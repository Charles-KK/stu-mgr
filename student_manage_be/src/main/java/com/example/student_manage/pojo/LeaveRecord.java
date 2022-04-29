package com.example.student_manage.pojo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.Data;
import org.apache.poi.ss.usermodel.FillPatternType;

import java.util.Date;

@Data
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 9)
@HeadFontStyle(fontHeightInPoints = 10)
@ExcelIgnoreUnannotated
public class LeaveRecord {

    private Integer recordId;

    @ExcelProperty("学号")
    private Integer studentId;

    @ExcelProperty("学术姓名")
    private String studentName;

    @ExcelProperty("请假理由")
    private String reason;

    private Integer managerId;

    @ExcelProperty("管理员姓名")
    private String managerName;

    @ExcelProperty("请假时间")
    private Date leaveTime;

    @ExcelProperty("返回时间")
    private Date returnTime;

    @ExcelProperty("通过时间")
    private Date applyTime;
}
