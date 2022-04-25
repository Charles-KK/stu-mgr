package com.example.student_manage.service;

import com.example.student_manage.mapper.LeaveRecordMapper;
import com.example.student_manage.pojo.LeaveRecord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LeaveRecordService {

    @Autowired
    private LeaveRecordMapper leaveRecordMapper;

    public boolean addRecord(LeaveRecord record) {
        return leaveRecordMapper.addRecord(record);
    }

    public boolean approveRecord(Integer recordId, Integer managerId, String managerName) {
        log.info(recordId + "  " + managerId + " " + managerName);
        return leaveRecordMapper.approveRecord(recordId, managerId, managerName);
    }

    public PageInfo getRecords(Integer currentPage, Integer studentId) {
        PageHelper.startPage(currentPage, 20);
        return new PageInfo<>(leaveRecordMapper.getAllRecords(studentId));
    }
}
