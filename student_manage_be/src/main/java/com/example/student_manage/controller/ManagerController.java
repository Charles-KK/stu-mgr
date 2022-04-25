package com.example.student_manage.controller;

import com.example.student_manage.pojo.Manager;
import com.example.student_manage.service.ManagerService;
import com.example.student_manage.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @PostMapping("/add")
    public Response addManager(@RequestBody Manager manager) {
        if (managerService.addManager(manager)) {
            return new Response();
        }
        return new Response().code(Response.FAILED);
    }

    @DeleteMapping("/{managerId}")
    public Response deleteManager(@PathVariable Integer managerId){
        if(managerService.deleteManager(managerId)) {
            return new Response();
        }
        return new Response().code(Response.FAILED);
    }
    @PutMapping("/edit")
    public  Response editManager(@RequestBody Manager manager) {
        if(managerService.editManager(manager)) {
            return new Response();
        }
        return new Response().code(Response.FAILED);
    }

    @GetMapping("/search/{name}")
    public Response fuzzyQueryByName(Integer currentPage, @PathVariable String name){
        return new Response().data(managerService.fuzzyQueryByName(currentPage, name));
    }
    @GetMapping
    public Response getManagerList(Integer currentPage) {
        return new Response().data(managerService.getManagerList(currentPage));
    }

}
