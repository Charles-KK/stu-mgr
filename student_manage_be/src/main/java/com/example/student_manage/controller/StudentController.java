package com.example.student_manage.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.student_manage.dto.LoginEntity;
import com.example.student_manage.dto.StudentQuery;
import com.example.student_manage.excel.StudentListener;
import com.example.student_manage.mapper.StudentMapper;
import com.example.student_manage.pojo.Student;
import com.example.student_manage.service.StudentService;
import com.example.student_manage.util.Response;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
//@RestController 是一个结合了 @ResponseBody 和 @Controller 的注解,@Responsebody 注解表示该方法的返回的结果
// 直接写入 HTTP 响应正文（ResponseBody）中，一般在异步获取数据时使用，
// 通常是在使用 @RequestMapping 后，返回值通常解析为跳转路径，加上@Responsebody 后返回结果不会被解析为跳转路径，
// 而是直接写入HTTP 响应正文中。
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;


//    @Resource
//    StudentMapper studentMapper;
////GetMapping(value=“url地址”) 指定url 地址，添加一个get请求方法，这样就可以用注解的方法来实现get请求的监听了
//    @GetMapping
//    public Response findPage(@RequestParam(defaultValue = "1") Integer currentPage,
//                             @RequestParam(defaultValue = "10") Integer pageSize,
//                             @RequestParam(defaultValue = "") String search) {
//        //分页对象，两个参数
//        Wrapper<Student> wrapper = new EntityWrapper<>();
//
//        if(StrUtil.isNotBlank(search)){
//            wrapper.like("student_name", search);
//        }
//        PageHelper.startPage(currentPage, pageSize);
//        PageInfo pageInfo = new PageInfo<>(studentMapper.selectList(wrapper));
//        return new Response().data(pageInfo);
//    }
    //添加单个学生
    @PostMapping("/add")
    public Response addStudent(@RequestBody Student student) {
        if (studentService.addStudent(student)) {
            return new Response();
        } else {
            return new Response().code(Response.FAILED);
        }
    }
    //删除指定学生Id的记录
    @DeleteMapping("/{stuId}")
    public Response deleteStudent(@PathVariable Integer stuId) {
        if(studentService.deleteStudent(stuId)) {
            return new Response();
        } else {
            return new Response().code(Response.FAILED);
        }
    }
    //编辑更新学生信息
    @PutMapping("/edit")
    public Response editStudent(@RequestBody Student student) {
        if(studentService.editStudent(student)) {
            return new Response();
        } else {
            return new Response().code(Response.FAILED);
        }
    }
    //以表格形式导入学生信息
    @PostMapping("/import")
    public Response importStudents(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Student.class, new StudentListener(studentService)).sheet().doRead();
        return new Response();
    }
    //获取学生列表
    @PostMapping
    public Response getStudents(@RequestBody StudentQuery query) {
        return new Response().data(studentService.getStudents(query.getCondition(), query.getCurrentPage()));
    }
    //以姓名为关键字模糊查询学生或管理员
    @GetMapping("/search/{name}")
    public Response fuzzyQueryByName(Integer currentPage, @PathVariable String name){
        return new Response().data(studentService.fuzzyQueryByName(currentPage, name));
    }


}
