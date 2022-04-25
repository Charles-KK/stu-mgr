package com.example.student_manage.service;

import com.example.student_manage.mapper.ManagerMapper;
import com.example.student_manage.pojo.Manager;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    public boolean addManager(Manager manager) {
        manager.setManagerPassword("123456");
        return managerMapper.addManager(manager);
    }

    public PageInfo getManagerList(Integer currentPage) {
        PageHelper.startPage(currentPage, 20);
        return new PageInfo<>(managerMapper.getManager());
    }

    public PageInfo fuzzyQueryByName(Integer currentPage, String name) {
        PageHelper.startPage(currentPage, 20);
        return new PageInfo<>(managerMapper.fuzzyQueryByName(name));
    }

    public Manager getManagerByName(String name) {
        return managerMapper.getManagerByName(name);
    }

    public boolean deleteManager(Integer managerId) {
        return managerMapper.deleteManager(managerId);
    }
    public boolean editManager(Manager manager) {
        return managerMapper.editManager(manager);
    }
}
