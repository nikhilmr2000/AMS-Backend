package com.ams.AttendanceManagementSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ams.AttendanceManagementSystem.Entity.TeacherImage;

@Repository
public interface TeacherImageRepo extends JpaRepository<TeacherImage,Long>{

}
