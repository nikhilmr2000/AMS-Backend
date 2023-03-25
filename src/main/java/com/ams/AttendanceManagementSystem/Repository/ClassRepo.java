package com.ams.AttendanceManagementSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ams.AttendanceManagementSystem.Entity.Classes;

@Repository
public interface ClassRepo extends JpaRepository<Classes,Long>{

}
