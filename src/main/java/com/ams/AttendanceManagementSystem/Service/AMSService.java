package com.ams.AttendanceManagementSystem.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ams.AttendanceManagementSystem.Entity.Classes;
import com.ams.AttendanceManagementSystem.Entity.Image;
import com.ams.AttendanceManagementSystem.Entity.ProfileStatus;
import com.ams.AttendanceManagementSystem.Entity.Register;
import com.ams.AttendanceManagementSystem.Entity.Teacher;
import com.ams.AttendanceManagementSystem.Entity.TeacherImage;
import com.ams.AttendanceManagementSystem.Repository.ClassRepo;
import com.ams.AttendanceManagementSystem.Repository.ImageRepo;
import com.ams.AttendanceManagementSystem.Repository.ProfileStatusRepo;
import com.ams.AttendanceManagementSystem.Repository.RegisterRepo;
import com.ams.AttendanceManagementSystem.Repository.TeacherImageRepo;
import com.ams.AttendanceManagementSystem.Repository.TeacherRepo;

@Service
public class AMSService {
	
	@Autowired
	public RegisterRepo registerRepo;
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Autowired
	public ProfileStatusRepo profileRepo;
	
	@Autowired
	public ImageRepo imageRepo;
	
	@Autowired
	public TeacherRepo teacherRepo;
	
	@Autowired
	public TeacherImageRepo teacherimgrepo;
	
	@Autowired
	public ClassRepo classesRepo;
	
	
	
	public Register saveRegister(Register register) {
		
		boolean userExist=false;
		
		List<Register> allUsers=registerRepo.findAll();
		
		for(Register user : allUsers) {
			if(user.getUsername().equals(register.getUsername())) {
				userExist=true;
			}
		}
		
		if(userExist==false) {
			
			String username="AMS@123"+register.getUsername();
			System.out.println(username);
			
			if(register.getAccesscode().equals(username)) {
				
				String encodedPassword=passwordEncoder.encode(register.getSecretcode());
				register.setSecretcode(encodedPassword);
				
				Register saveRegister=registerRepo.save(register);
				return saveRegister;
			}
			else {
				return new Register("","","","accesserror");
			}
		}
		else {
			return new Register();
		}
	
	}
	
	public boolean loginValidate(String username,String secretcode) {
		
		List<Register> allUsers=registerRepo.findAll();
		
		for(Register user : allUsers) {
			if(user.getUsername().equals(username) && passwordEncoder.matches(secretcode,user.getSecretcode())) {
				return true;
			}
		}
		return false;
 	}
	
	
	public ProfileStatus saveProfileStatus(ProfileStatus profileStatus,Authentication authentication) {
		
		List<ProfileStatus> allProfile=profileRepo.findAll();
		boolean profileExist=false;
		
		for(ProfileStatus profile : allProfile) {
			if(profile.getRoll_no().equals(profileStatus.getRoll_no()) && profile.getStandard().equals(profileStatus.getStandard())
					&& profile.getSection().equals(profileStatus.getSection()) && profile.getRegister().getUsername().equals(authentication.getName())) {
				profileExist=true;
			}
		}
		
		if(profileExist==false) {
			
			List<Register> allUsers=registerRepo.findAll();
			
			for(Register user : allUsers) {
				if(user.getUsername().equals(authentication.getName())) {
					profileStatus.setRegister(user);
					ProfileStatus saveProfile=profileRepo.save(profileStatus);
					return saveProfile;
				}
			}
			return new ProfileStatus();
			
		}
		else {
			return new ProfileStatus();
		}
		
	}
	
	public Image saveImage(MultipartFile file,Authentication authentication) throws IOException{
		
		List<Register> allUsers=registerRepo.findAll();
		List<ProfileStatus> allProfile=profileRepo.findAll();
		
		long val=allProfile.get(allProfile.size()-1).getId();
		
		for(Register user : allUsers) {
			if(user.getUsername().equals(authentication.getName())) {
				Image image=new Image();
				image.setName(file.getName());
				image.setImageurl(file.getBytes());
				image.setProfileid(val); 
				Image img=imageRepo.save(image);
				return img;
			}
		}
		return new Image();
	}
	
	
	public Teacher saveTeacher(Teacher teacher,Authentication authentication) {
		
		List<Register> allUsers=registerRepo.findAll();
		
		for(Register user: allUsers) {
			if(user.getUsername().equals(authentication.getName())) {
				teacher.setPersist(true);
				teacher.setUser(user);
				Teacher saveTeacher=teacherRepo.save(teacher);
				return saveTeacher;
			}
		}
		
		return new Teacher();
		
	}
	
	public TeacherImage saveTeacherImage(MultipartFile file,Authentication authentication) throws IOException{
		
		List<Register> allUsers=registerRepo.findAll();
		List<Teacher> allteachers=teacherRepo.findAll();
		
		long val=allteachers.get(allteachers.size()-1).getId();
		
		for(Register user : allUsers) {
			if(user.getUsername().equals(authentication.getName())) {
				TeacherImage images=new TeacherImage();
				images.setName(file.getName());
				images.setTeacherurl(file.getBytes());
				images.setTeacherid(val);
				TeacherImage img=teacherimgrepo.save(images);
				return img;
				
			}
		}
		return new TeacherImage();
		
	}
	
	
	//NameList
	
	public List<ProfileStatus> getProfileStatus(String namelist,Authentication authentication) {
		
		List<ProfileStatus> allUsers= profileRepo.findAll();
		List<ProfileStatus> currentOrg=new ArrayList<>();
		List<ProfileStatus> returnUsers=new ArrayList<>();
		
		namelist=namelist.toLowerCase();
		char[] names=namelist.toCharArray();
		
		System.out.println(names);
		
		
			
		for(ProfileStatus profile:allUsers) {
			if(profile.getRegister().getUsername().equals(authentication.getName())) {
				System.out.println(authentication.getName());
				currentOrg.add(profile);
			}
		}
		
//		System.out.println(currentOrg);
		
		
		for(ProfileStatus profile : currentOrg ) {
			
			String name=profile.getName();
			name=name.toLowerCase();
			char[] namearray=name.toCharArray();
//			System.out.println(namearray);
			
			
			if(namearray.length>=names.length) {
				String returnData=getSubSet(name,namelist);
				if(returnData.length()!=0) {
					returnUsers.add(profile);
				}
			}
			
		}
		System.out.println(returnUsers);
		return returnUsers;
		
	}
	
	// Image GetList
	
	public Image getImageById(long id) {
		List<Image> allImages=imageRepo.findAll();
		for(Image image: allImages) {
			if(image.getProfileid()==id) {
				return image;
			}
		}
		return new Image();
	}
		 
	
	//List of 10 registered Students of Current User
	
	public List<ProfileStatus> getlasttenstudents(Authentication authentication){
		List<ProfileStatus> getProfileStatus=profileRepo.findAll();
		
		List<ProfileStatus> currentOrganization=new ArrayList<>();
		
		for(ProfileStatus profile:getProfileStatus) {
			if(profile.getRegister().getUsername().equals(authentication.getName())) {
				currentOrganization.add(profile);
			}
		}
		
		List<ProfileStatus> recentlyAdded=new ArrayList<>();
		
		int checker=0;
		for(int i=currentOrganization.size()-1;i>=0;i--) {
			recentlyAdded.add(currentOrganization.get(i));
			checker++;
			if(checker==10) {
				break;
			}
		}
		return recentlyAdded;
	}
	
	public List<ProfileStatus> getAllProfileStatus(Authentication authentication){
		List<ProfileStatus> getAllProfiles=profileRepo.findAll();
		List<ProfileStatus> currentUser=new ArrayList<>();
		
		for(ProfileStatus profiles:getAllProfiles) {
			if(profiles.getRegister().getUsername().equals(authentication.getName())) {
				currentUser.add(profiles);
			}
		}
		return currentUser;
	}
	
	
	public List<ProfileStatus> getActiveProfileStatus(Authentication authentication){
		List<ProfileStatus> getAllProfiles=profileRepo.findAll();
		List<ProfileStatus> currentUser=new ArrayList<>();
		
		for(ProfileStatus profiles:getAllProfiles) {
			if(profiles.getRegister().getUsername().equals(authentication.getName())) {
				if(profiles.isPersist()==true) {
					currentUser.add(profiles);
				}
			}
		}
		return currentUser;
	}
	
	//Roll Number
	
	public List<ProfileStatus> getProfileByRollNumber(String rollnumber,Authentication authentication){
		
		List<ProfileStatus> allProfiles=profileRepo.findAll();
		List<ProfileStatus> returnProfiles=new ArrayList<>();
		List<ProfileStatus> currentOrganisation=new ArrayList<>();
		
		for(ProfileStatus profile : allProfiles) {
			if(profile.getRegister().getUsername().equals(authentication.getName())) {
				currentOrganisation.add(profile);
			}
		}
		
		rollnumber=rollnumber.toLowerCase();
		char[] rollArray=rollnumber.toCharArray();
		
		
		for(ProfileStatus profile : currentOrganisation) {
			
			String names=profile.getRoll_no().toLowerCase();
			char[] profilerollnumber=names.toCharArray();
			
			if(rollArray.length<=profilerollnumber.length) {
				String returnData=getSubSet(names,rollnumber);
				if(returnData.length()!=0) {
					returnProfiles.add(profile);
				}
				
			}
			
		}
		
		return returnProfiles;
	}
	
	
	//Standard
	
	public List<ProfileStatus> getProfileStatusByStandard(String standard,Authentication authentication){
		
		List<ProfileStatus> getProfile=profileRepo.findAll();
		
		List<ProfileStatus> currentOrganisation=new ArrayList<>();
		
		List<ProfileStatus> returnProfiles=new ArrayList<>();
		
		standard=standard.toLowerCase();
		char[] standardArray=standard.toCharArray();
 		
		for(ProfileStatus profile: getProfile) {
			if(profile.getRegister().getUsername().equals(authentication.getName())) {
				currentOrganisation.add(profile);
;			}
		}
		
		for(ProfileStatus profile : currentOrganisation) {
			
			String names=profile.getStandard().toLowerCase();
			char[] profilestandard=names.toCharArray();
			
			if(standardArray.length<=profilestandard.length) {
				String returnData=getSubSet(names,standard);
				if(returnData.length()!=0) {
					returnProfiles.add(profile);
				}
			}
		}
		
		return returnProfiles;
	}
	
	
	//Add Teacher By Name
	
	public List<Teacher> getTeacherByName(String name,Authentication authentication){
		
		List<Teacher> getTeacherProfile=teacherRepo.findAll();
		
		List<Teacher> allTeachers=new ArrayList<>();
		
		List<Teacher> allTeachersProfile=new ArrayList<>();
		
		
		for(Teacher teacher : getTeacherProfile) {
			if(teacher.getUser().getUsername().equals(authentication.getName())) {
				allTeachers.add(teacher);
			}
		}
		
		name=name.toLowerCase();
		char[] nameArray=name.toCharArray();
		
		for(Teacher teacher: allTeachers) {
			
			String names=teacher.getName().toLowerCase();
			char[] teacherArray=names.toCharArray();
			
			if(nameArray.length<=teacherArray.length) {
				String returnData=getSubSet(names,name);
				if(returnData.length()!=0) {
					allTeachersProfile.add(teacher);
				}
			}
			
		}
		
		return allTeachersProfile;
	}
	
	public TeacherImage getTeacherImage(long id) {
		List<TeacherImage> allImages=teacherimgrepo.findAll();
		for(TeacherImage image : allImages) {
			if(image.getTeacherid()==id) {
				return image;
			}
		}
		return new TeacherImage();
	}
	
	
	public List<ProfileStatus> getProfileStatusSubsetByName(String name,Authentication authentication){
		
		List<ProfileStatus> allProfiles=profileRepo.findAll();
		
		List<ProfileStatus> currentOrganisation=new ArrayList<>();
		List<ProfileStatus> returnProfiles=new ArrayList<>();
		
		for(ProfileStatus profile : allProfiles) {
			if(profile.getRegister().getUsername().equals(authentication.getName())) {
				currentOrganisation.add(profile);
			}
		}
		
		name=name.toLowerCase();
		char[] nameArray=name.toCharArray();
		
		for(ProfileStatus profile :currentOrganisation) {
			//Algorithm for for creating subset
			String nameLower=profile.getName().toLowerCase();
			char[] profileArray=nameLower.toCharArray();
			
			if(nameArray.length<=profileArray.length) {
				//Nothing for now
				/*String returnSubset=getSubSet(nameLower,name);
				if(returnSubset.length()!=0) {
					returnProfiles.add(profile);
				}*/
			}
			
		}
		
		return returnProfiles;
		
	}
	
	
	// Name 
	public String getSubSet(String profile,String name){
		
		//Make Lowercase
		name=name.toLowerCase();
		String profileLower=profile.toLowerCase();
		
		//Char Array
		char[] profileArray=profileLower.toCharArray(); // [b ,h,d,b,h,n,i,k,k,h,i,]
		char[] nameArray=name.toCharArray();  // [ b,h,n,i,k,k] 		
		
		char prev=nameArray[0];int presentindex=0;int first=0;int k=0;
		for(int i=0;i<profileArray.length;i++) {
			
			for(int j=first;j<nameArray.length;j++) {
				if(profileArray[i]==nameArray[j] && prev==nameArray[j]) {
					first++;
					if(j+1<nameArray.length) {
						prev=nameArray[j+1];
					}
					presentindex=i;
					k++;
				
					if(k==nameArray.length) {
						return profile;
					}
					break;
				}
				else {
					if(presentindex+1==i) {
						prev=nameArray[0];
						first=0;
						k=0;
						break;
					}
				}
				
			}
		}
		
		
		return ""; 
	}
	
	
	public List<Teacher> getListofTeacherList(Authentication authentication){
		List<Teacher> allTeachersList=teacherRepo.findAll();
		List<Teacher> allTeachers=new ArrayList<>();
		List<Teacher> returnTenStudents=new ArrayList<>();
		
		for(Teacher teacher : allTeachersList) {
			if(teacher.getUser().getUsername().equals(authentication.getName())) {
				allTeachers.add(teacher);
			}
		}
		
		int count=0;
		for(Teacher teacher : allTeachers) {
			if(count<=10) {
				returnTenStudents.add(teacher);
				count++;
			}
		}
		
		return returnTenStudents;
	}
	
	public List<Teacher> allTeachers(Authentication authentication){
		List<Teacher> allTeachersList=teacherRepo.findAll();
		List<Teacher> allTeachers=new ArrayList<>();
		
		for(Teacher teacher : allTeachersList) {
			if(teacher.getUser().getUsername().equals(authentication.getName())) {
				allTeachers.add(teacher);
			}
		}
		
		return allTeachers;
	}
	
	public List<Teacher> getTeacherBySubject(String branch,Authentication authentication){
		
		List<Teacher> teacherSubject=teacherRepo.findAll();
		List<Teacher> allTeachers=new ArrayList<>();
		List<Teacher> returnBranch=new ArrayList<>();
		
		for(Teacher teacher : teacherSubject) {
			if(teacher.getUser().getUsername().equals(authentication.getName())) {
				allTeachers.add(teacher);
			}
		}
		
		for(Teacher teacher : allTeachers) {
			if(branch.length()<=teacher.getBranch().length()) {
				String returnValue=getSubSet(teacher.getBranch(),branch);
				if(returnValue.length()!=0) {
					returnBranch.add(teacher);
				}
			}
		}
		
		return returnBranch;
		
	}
	
	public List<Teacher> getTeacherByExperience(String experience,Authentication authentication){
		List<Teacher> teacherSubject=teacherRepo.findAll();
		List<Teacher> allTeachers=new ArrayList<>();
		List<Teacher> returnExperience=new ArrayList<>();
		
		for(Teacher teacher : teacherSubject) {
			if(teacher.getUser().getUsername().equals(authentication.getName())) {
				allTeachers.add(teacher);
			}
		}
		
		for(Teacher teacher : allTeachers) {
			String exp=Float.toString(teacher.getExperience());
			String returndata=getSubSet(exp,experience);
			if(returndata.length()!=0) {
				returnExperience.add(teacher);
			}
		}
		
		return returnExperience;
	}
	
	
	//Before this search the profiles by the help of the search engine algorithm
	
	public ProfileStatus getProfileStatusFirst(ProfileStatus profile,Authentication authentication) {
		if(profile.getRegister().getUsername().equals(authentication.getName())) {
			return 	profile;
		}
		return new ProfileStatus();
	}
	
	public ProfileStatus updateProfileStatus(ProfileStatus profile,Authentication authentication) {
		List<Register> allRegisteredUsers=registerRepo.findAll();
		
		List<ProfileStatus> allProfiles=profileRepo.findAll();
		
		
		for(Register users : allRegisteredUsers) {
			if(users.getUsername().equals(authentication.getName())) {
				profile.setRegister(users);
			}
		}
		
		for(ProfileStatus profiles :allProfiles) {
			if(profiles.getRegister().getUsername().equals(authentication.getName())) {
				
				ProfileStatus updateProfile=profileRepo.save(profile);
				return updateProfile;
			}
		}
		return new ProfileStatus();
	}
	
	
	public Image updateImageStatus(MultipartFile file,long id,Authentication authentication) throws IOException {
		List<Image> allImages=imageRepo.findAll();
		List<Register> allUsers=registerRepo.findAll();
		
		
		boolean val=false;
		
			
		for(Register user : allUsers) {
			if(user.getUsername().equals(authentication.getName())) {
				val=true;
			}
		}
		
		
		if(val==true) {
			for(Image image : allImages) {
				if(image.getProfileid()==id) {
					
					image.setName(file.getName());
					image.setImageurl(file.getBytes());
					
					Image imgRepo=imageRepo.save(image);
					return imgRepo;
				}
			}
		}
		
		return new Image();
	}
	
	
	public Image updateImageStatus(long id) {
		
		List<Image> getAllImages=imageRepo.findAll();
		
		for(Image image : getAllImages) {
			if(image.getProfileid()==id) {
				return image;
			}
		}
		
		return new Image();
	}
	
	
	public Teacher updateTeacherDetails(Teacher teacher,Authentication authentication){
		
		List<Teacher> allTeacherProfile=teacherRepo.findAll();
		List<Register> allRegisters=registerRepo.findAll();
		List<Teacher> currentTeachers=new ArrayList<>();
		
		for(Register users : allRegisters) {
			if(users.getUsername().equals(authentication.getName())) {
				teacher.setUser(users);
			}
		}
		
		for(Teacher teach: allTeacherProfile) {
			if(teach.getUser().getUsername().equals(authentication.getName())) {
				Teacher teacherProfile=teacherRepo.save(teacher);
				return teacherProfile;
			}
		}
		
		return new Teacher();
		
	}
	
	public TeacherImage updateTeacherImageStatus(MultipartFile file,long id,Authentication authentication) throws IOException {
		List<TeacherImage> allImages=teacherimgrepo.findAll();
		List<Register> allUsers=registerRepo.findAll();
		
		
		boolean val=false;
		
		
		for(Register user : allUsers) {
			if(user.getUsername().equals(authentication.getName())) {
				val=true;
			}
		}
		
		
		if(val==true) {
			for(TeacherImage image : allImages) {
				if(image.getTeacherid()==id) {
					
					image.setName(file.getName());
					image.setTeacherurl(file.getBytes());
					
					TeacherImage imgRepo=teacherimgrepo.save(image);
					return imgRepo;
				}
			}
		}
		
		return new TeacherImage();
	}
	
	

	public TeacherImage updateTeacherImageStatus(long id) {
		
		List<TeacherImage> getAllImages=teacherimgrepo.findAll();
		
		for(TeacherImage image : getAllImages) {
			if(image.getTeacherid()==id) {
				return image;
			}
		}
		
		return new TeacherImage();
	}
	
	
	
	public ProfileStatus persistOrNot(ProfileStatus profile, Authentication authentication) {
		
		List<ProfileStatus> allProfiles=profileRepo.findAll();		
		List<ProfileStatus> currentOrganisation=new ArrayList<>();
		
		
		for(ProfileStatus profilestatus : allProfiles) {
			if(profilestatus.getRegister().getUsername().equals(authentication.getName())) {
				currentOrganisation.add(profilestatus);
			}
		}
		
		for(ProfileStatus profiles : currentOrganisation) {
			if(profiles.getId()==profile.getId()) {
				profiles.setPersist(false);
				ProfileStatus returnedProfile=profileRepo.save(profiles);
				return returnedProfile;
			}
		}
		
		return new ProfileStatus();
	}
	
	public Teacher teacherPersistOrNot(Teacher teacher , Authentication authentication) {
		
		List<Teacher> allTeachers=teacherRepo.findAll();
		List<Teacher> currentOrganisation=new ArrayList<>();
		
		for(Teacher teachers : allTeachers) {
			if(teachers.getUser().getUsername().equals(authentication.getName())) {
				currentOrganisation.add(teachers);
			}
		}
		
		for(Teacher teach : currentOrganisation) {
			if(teach.getId()==teacher.getId()) {
				teach.setPersist(false);
				Teacher updatedTeacher=teacherRepo.save(teach);
				return updatedTeacher;
			}
		}
		
		return new Teacher();
		
	}
	
	public List<ProfileStatus> updateProfileByName(String namelist,Authentication authentication) {
		
		List<ProfileStatus> allUsers= profileRepo.findAll();
		List<ProfileStatus> currentOrg=new ArrayList<>();
		List<ProfileStatus> returnUsers=new ArrayList<>();
		
		namelist=namelist.toLowerCase();
		char[] names=namelist.toCharArray();
		
		System.out.println(names);
		
		
			
		for(ProfileStatus profile:allUsers) {
			if(profile.getRegister().getUsername().equals(authentication.getName())) {
				System.out.println(authentication.getName());
				currentOrg.add(profile);
			}
		}
		
//		System.out.println(currentOrg);
		
		
		for(ProfileStatus profile : currentOrg ) {
			
			String name=profile.getName();
			name=name.toLowerCase();
			char[] namearray=name.toCharArray();
//			System.out.println(namearray);
			
			
			if(namearray.length>=names.length) {
				String returnData=getSubSet(name,namelist);
				if(returnData.length()!=0) {
					if(profile.isPersist()==true) {
						returnUsers.add(profile);
					}
					
				}
			}
			
		}
		System.out.println(returnUsers);
		return returnUsers;
		
	}
	
	public List<ProfileStatus> updateProfileStatusByRollNumber(String rollnumber,Authentication authentication){
		
		List<ProfileStatus> allProfiles=profileRepo.findAll();
		List<ProfileStatus> returnProfiles=new ArrayList<>();
		List<ProfileStatus> currentOrganisation=new ArrayList<>();
		
		for(ProfileStatus profile : allProfiles) {
			if(profile.getRegister().getUsername().equals(authentication.getName())) {
				currentOrganisation.add(profile);
			}
		}
		
		rollnumber=rollnumber.toLowerCase();
		char[] rollArray=rollnumber.toCharArray();
		
		
		for(ProfileStatus profile : currentOrganisation) {
			
			String names=profile.getRoll_no().toLowerCase();
			char[] profilerollnumber=names.toCharArray();
			
			if(rollArray.length<=profilerollnumber.length) {
				String returnData=getSubSet(names,rollnumber);
				if(returnData.length()!=0) {
					if(profile.isPersist()==true) {
						returnProfiles.add(profile);
					}
				}
				
			}
			
		}
		
		return returnProfiles;
	}
	
	public List<ProfileStatus> updateProfileStatusByStandard(String standard,Authentication authentication){
		
		List<ProfileStatus> getProfile=profileRepo.findAll();
		
		List<ProfileStatus> currentOrganisation=new ArrayList<>();
		
		List<ProfileStatus> returnProfiles=new ArrayList<>();
		
		standard=standard.toLowerCase();
		char[] standardArray=standard.toCharArray();
 		
		for(ProfileStatus profile: getProfile) {
			if(profile.getRegister().getUsername().equals(authentication.getName())) {
				currentOrganisation.add(profile);
;			}
		}
		
		for(ProfileStatus profile : currentOrganisation) {
			
			String names=profile.getStandard().toLowerCase();
			char[] profilestandard=names.toCharArray();
			
			if(standardArray.length<=profilestandard.length) {
				String returnData=getSubSet(names,standard);
				if(returnData.length()!=0) {
					if(profile.isPersist()==true) {
						returnProfiles.add(profile);
					}
				}
			}
		}
		
		return returnProfiles;
	}
	
	
public List<Teacher> updateTeacherByName(String name,Authentication authentication){
		
		List<Teacher> getTeacherProfile=teacherRepo.findAll();
		
		List<Teacher> allTeachers=new ArrayList<>();
		
		List<Teacher> allTeachersProfile=new ArrayList<>();
		
		
		for(Teacher teacher : getTeacherProfile) {
			if(teacher.getUser().getUsername().equals(authentication.getName())) {
				allTeachers.add(teacher);
			}
		}
		
		name=name.toLowerCase();
		char[] nameArray=name.toCharArray();
		
		for(Teacher teacher: allTeachers) {
			
			String names=teacher.getName().toLowerCase();
			char[] teacherArray=names.toCharArray();
			
			if(nameArray.length<=teacherArray.length) {
				String returnData=getSubSet(names,name);
				if(returnData.length()!=0) {
					if(teacher.isPersist()==true) {
						allTeachersProfile.add(teacher);
					}
				}
			}
			
		}
		
		return allTeachersProfile;
	}

public List<Teacher> updateTeacherBySubject(String branch,Authentication authentication){
	
	List<Teacher> teacherSubject=teacherRepo.findAll();
	List<Teacher> allTeachers=new ArrayList<>();
	List<Teacher> returnBranch=new ArrayList<>();
	
	for(Teacher teacher : teacherSubject) {
		if(teacher.getUser().getUsername().equals(authentication.getName())) {
			allTeachers.add(teacher);
		}
	}
	
	for(Teacher teacher : allTeachers) {
		if(branch.length()<=teacher.getBranch().length()) {
			String returnValue=getSubSet(teacher.getBranch(),branch);
			if(returnValue.length()!=0) {
				if(teacher.isPersist()==true) {
					returnBranch.add(teacher);
				}
			}
		}
	}
	
	return returnBranch;
	
}

public List<Teacher> updateTeacherByExperience(String experience,Authentication authentication){
	List<Teacher> teacherSubject=teacherRepo.findAll();
	List<Teacher> allTeachers=new ArrayList<>();
	List<Teacher> returnExperience=new ArrayList<>();
	
	for(Teacher teacher : teacherSubject) {
		if(teacher.getUser().getUsername().equals(authentication.getName())) {
			allTeachers.add(teacher);
		}
	}
	
	for(Teacher teacher : allTeachers) {
		String exp=Float.toString(teacher.getExperience());
		String returndata=getSubSet(exp,experience);
		if(returndata.length()!=0) {
			if(teacher.isPersist()==true) {
				returnExperience.add(teacher);
			}
		}
	}
	
	return returnExperience;
}


	public Classes saveClasses(Classes classes,Authentication authentication) {
		
		List<Classes> allClasses=classesRepo.findAll();
		int yes=0;
		List<ProfileStatus> totalProfiles=profileRepo.findAll();
		List<ProfileStatus> allProfiles=new ArrayList<>();
		
		
		
		List<Register> allRegisters=registerRepo.findAll();
		
		for(Register users : allRegisters) {
			if(users.getUsername().equals(authentication.getName())) {
				classes.setUniversity(users);
			}
		}
		
		for(ProfileStatus profile : totalProfiles) {
			if(profile.getRegister().getUsername().equals(authentication.getName())) {
				allProfiles.add(profile);
			}
		}
		
		boolean exist=false;
		
		for(Classes room : allClasses) {
			if(room.getClassname().equals(classes.getClassname()) && 	
					room.getSection().equals(classes.getSection())) {
				exist=true;
			}			
		}
		
		
		if(exist==false) {
			
			String teachercode="";
			
			if(authentication.getName().length()>5) {
				teachercode=authentication.getName().substring(0,5).concat(classes.getTeacher().getName());
			}
			else {
				teachercode=authentication.getName().concat(classes.getTeacher().getName());
			}
			
			classes.setTeachercode(teachercode);
			
			for(ProfileStatus profile : classes.getProfile()) {
				for(ProfileStatus student : allProfiles) {
					if(profile.getId()==student.getId()) {
						if(student.getClasses()==null) {
//							System.out.println(profile);
							yes++;
							break;
						}
					}
				}
			}
			
			
			if(yes==classes.getProfile().size()) {
				Classes newClass=classesRepo.save(classes);
				for(ProfileStatus profile : newClass.getProfile()) {
					for(ProfileStatus student : allProfiles) {
						if(profile.getId()==student.getId()) {
							ProfileStatus newprofile=student;
							newprofile.setClasses(classes);
							profileRepo.save(newprofile);
							break;
						}
					}
				}
				return newClass;
			}
			
			
		}
		return new Classes();
		
	}
	
	public Teacher getTeacher(long id) {
		List<Teacher> allTeachers=teacherRepo.findAll();
		
		for(Teacher teacher : allTeachers) {
			if(teacher.getId()==id) {
				return teacher;
			}
		}
		
		return new Teacher();
	}
	
	public ProfileStatus getProfile(long id) {
		List<ProfileStatus> allTeachers=profileRepo.findAll();
		
		for(ProfileStatus teacher : allTeachers) {
			if(teacher.getId()==id) {
				return teacher;
			}
		}
		
		return new ProfileStatus();
	}
	
	
	public List<String> allStandards(Authentication authentication){
		
		List<Register> allUsers=registerRepo.findAll();
		boolean organisation=false;
		String[] standards= {"Pre-KG","LKG","UKG","I","II","III","IV","V","VI","VII","VIII"
				,"IX","X","XI","XII"};
		List<String> standardList=new ArrayList<>();
		
		standardList=Arrays.asList(standards);
 		
		
		for(Register user : allUsers) {
			if(user.getUsername().equals(authentication.getName())) {
				organisation=true;
			}
		}
		
		if(organisation==true) {
			return standardList;
		}
		
		return null;
	}
	
	public List<String> allSections(Authentication authentication){
		List<Register> allUsers=registerRepo.findAll();
		
		String[] sections= {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R"
				,"S","T","U","V","W","X","Y","Z"};
		
		List<String> sectionList=new ArrayList<>();
		
		sectionList=Arrays.asList(sections);
		
		boolean organisation=false;
		for(Register user : allUsers) {
			if(user.getUsername().equals(authentication.getName())) {
				organisation=true;
			}
		}
		
		if(organisation==true) {
			return sectionList;
		}
		
		return null;
	}
	
	public List<Teacher> getAllTeacher(Authentication authentication){
		
		List<Teacher> allTeacher=teacherRepo.findAll();
		List<Teacher> orgTeachers=new ArrayList<>();
		
		List<Teacher> allUsers=new ArrayList<>();
		
		for(Teacher teacher : allTeacher) {
			if(teacher.getUser().getUsername().equals(authentication.getName())) {
				orgTeachers.add(teacher);
			}
		}
		
		for(Teacher teach : orgTeachers) {
			if(teach.getClasses()==null) {
				allUsers.add(teach);
			}
		}
		
		return allUsers;
	}

}












