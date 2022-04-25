package com.example.student_manage.controller;

import com.example.student_manage.pojo.LeaveRecord;
import com.example.student_manage.pojo.Manager;
import com.example.student_manage.pojo.Student;
import com.example.student_manage.service.LeaveRecordService;
import com.example.student_manage.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/leave")
public class LeaveRecordController {

    @Autowired
    private LeaveRecordService leaveRecordService;

    @PostMapping("/add")
    public Response addRecord(@RequestBody LeaveRecord record, HttpSession session) {
        Student student;
        String role = (String)session.getAttribute("role");
        if (!"student".equals(role)) return new Response().code(Response.PERMISSION_DENY);
        student = (Student)session.getAttribute("info");
        record.setStudentId(student.getStudentId());
        record.setStudentName(student.getStudentName());
        if (leaveRecordService.addRecord(record)) {
            return new Response();
        }
        return new Response().code(Response.FAILED);
    }

    @PostMapping("/approve")
    public Response approveLeave(Integer recordId, HttpSession session) {
        Manager manager;
        String role = (String)session.getAttribute("role");
        if (!"manager".equals(role)) return new Response().code(Response.PERMISSION_DENY);
        manager = (Manager)session.getAttribute("info");
        if (leaveRecordService.approveRecord(recordId, manager.getManagerId(), manager.getManagerName())) {
            return new Response();
        }
        return new Response().code(Response.FAILED);
    }

    @GetMapping
    public Response getLeaveRecord(Integer currentPage,  HttpSession session) {
        String role = (String)session.getAttribute("role");
        Integer studentId = null;
        if ("student".equals(role)) {
            Student student = (Student)session.getAttribute("info");
            studentId = student.getStudentId();
        }
        return new Response().data(leaveRecordService.getRecords(currentPage, studentId));
    }

}
