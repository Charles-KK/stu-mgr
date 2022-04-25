package com.example.student_manage.mapper;

import com.example.student_manage.pojo.Manager;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ManagerMapper {

    boolean addManager(Manager manager);

    boolean deleteManager(Integer managerId);

    boolean editManager(Manager manager);

    Manager getManagerByName(String name);

    List<Manager> fuzzyQueryByName(String name);

    List<Manager> getManager();
}


