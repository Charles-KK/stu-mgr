package com.example.student_manage.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class LeaveRecord {

    private Integer recordId;

    private Integer studentId;

    private String studentName;

    private String reason;

    private Integer managerId;

    private String managerName;

    private Date leaveTime;

    private Date returnTime;

    private Date applyTime;
}
