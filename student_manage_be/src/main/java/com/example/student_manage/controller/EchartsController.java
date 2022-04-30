package com.example.student_manage.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analyze")
public class EchartsController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/gender")
    public Response getGenderDistribute(){
        return new Response().data(studentService.getGenderDistrubute());
    }
}
