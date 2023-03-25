package com.ams.AttendanceManagementSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ams.AttendanceManagementSystem.Entity.Teacher;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher,Long>{

}
