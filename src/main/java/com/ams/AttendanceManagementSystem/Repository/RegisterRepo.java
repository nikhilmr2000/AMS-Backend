package com.ams.AttendanceManagementSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ams.AttendanceManagementSystem.Entity.Register;

@Repository
public interface RegisterRepo extends JpaRepository<Register,Long>{
	
	
}
