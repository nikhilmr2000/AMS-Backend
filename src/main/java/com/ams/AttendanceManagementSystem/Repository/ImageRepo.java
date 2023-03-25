package com.ams.AttendanceManagementSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ams.AttendanceManagementSystem.Entity.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long>{
	
}
