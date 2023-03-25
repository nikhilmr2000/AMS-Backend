package com.ams.AttendanceManagementSystem.Repository;
	
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ams.AttendanceManagementSystem.Entity.ProfileStatus;

@Repository
public interface ProfileStatusRepo extends JpaRepository<ProfileStatus,Long>{
	
}

