package com.example.student_manage.controller;


import com.example.student_manage.dto.LoginEntity;
import com.example.student_manage.pojo.Manager;
import com.example.student_manage.pojo.Student;
import com.example.student_manage.service.ManagerService;
import com.example.student_manage.service.StudentService;
import com.example.student_manage.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


//return Jason格式数据
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private StudentService studentService;

    @Autowired
    ManagerService managerService;


    @PostMapping("/studentLogin")
    public Response studentLogin(@RequestBody LoginEntity entity, HttpSession session) {
        Student student = studentService.doLogin(entity.getName(), entity.getPassword());
        if (student != null) {
            session.setAttribute("info", student);
            session.setAttribute("role", "student");
            return new Response();
        } else {
            return new Response().code(Response.FAILED);
        }
    }

    @PostMapping("/managerLogin")
    public Response managerLogin(@RequestBody LoginEntity entity, HttpSession session) {
        Manager manager = managerService.getManagerByName(entity.getName());
        if (manager == null) return new Response().code(Response.NOT_FOUND);
        if (StringUtils.hasLength(entity.getPassword()) && entity.getPassword().equals(manager.getManagerPassword())) {
            session.setAttribute("info", manager);
            session.setAttribute("role", "manager");
            return new Response();
        } else {
            return new Response().code(Response.FAILED);
        }
    }


    @GetMapping("/checkLogin")
    public Response checkLogin(HttpSession session) {
        String role = (String)session.getAttribute("role");
        if (role != null) {
            log.info(role);
            return new Response().data(role);
        } else {
            return new Response().code(Response.FAILED);
        }
    }

    @GetMapping("/logout")
    public Response logout(HttpSession session) {
        session.removeAttribute("role");
        session.removeAttribute("info");

        return new Response();
    }
}
