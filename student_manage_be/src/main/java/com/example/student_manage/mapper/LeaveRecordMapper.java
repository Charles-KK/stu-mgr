package com.example.student_manage.mapper;

import com.example.student_manage.pojo.LeaveRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRecordMapper {

    boolean addRecord(LeaveRecord record);

    boolean approveRecord(
            @Param("recordId") Integer recordId,
            @Param("managerId") Integer managerId,
            @Param("managerName") String managerName
    );

    List<LeaveRecord> getAllRecords(@Param("studentId") Integer studentId);
}
