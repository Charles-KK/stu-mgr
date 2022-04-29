package com.example.student_manage.controller;

import com.example.student_manage.service.LeaveRecordService;
import com.example.student_manage.service.StudentService;
import com.example.student_manage.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private LeaveRecordService leaveRecordService;

    @PostMapping("/uploadStudent")
    public Response uploadStudent(MultipartFile file) {
        try {
            studentService.uploadStudent(file);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("upload student err: {}", e.getMessage());
        }
        return new Response();
    }


    @GetMapping("/exportStudent")
    public void exportStudent(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = null;
        try {
            fileName = URLEncoder.encode("学生信息", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            studentService.exportStudent(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("export student err: {}", e.getMessage());
        }

    }

    @GetMapping("/exportLeave/{student_id}")
    public void exportLeaveRecord(HttpServletResponse response, @PathVariable Integer studentId) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = null;
        try {
            fileName = URLEncoder.encode("请假记录", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            leaveRecordService.exportRecord(studentId, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("export student err: {}", e.getMessage());
        }
    }

}
