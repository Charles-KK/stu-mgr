package com.example.student_manage.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName(value = "t_manager")
@Data
public class Manager {

    private Integer managerId;

    private String managerName;

    private String managerPassword;

    private String organization;

    private String position;

    private String tel;

}
