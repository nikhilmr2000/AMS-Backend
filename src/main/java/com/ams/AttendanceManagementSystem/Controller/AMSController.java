package com.ams.AttendanceManagementSystem.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ams.AttendanceManagementSystem.Entity.Classes;
import com.ams.AttendanceManagementSystem.Entity.Image;
import com.ams.AttendanceManagementSystem.Entity.ProfileStatus;
import com.ams.AttendanceManagementSystem.Entity.Register;
import com.ams.AttendanceManagementSystem.Entity.Teacher;
import com.ams.AttendanceManagementSystem.Entity.TeacherImage;
import com.ams.AttendanceManagementSystem.Repository.ImageRepo;
import com.ams.AttendanceManagementSystem.Repository.RegisterRepo;
import com.ams.AttendanceManagementSystem.Service.AMSService;

@RestController
public class AMSController {
	
	@Autowired
	public AMSService service;
	
	@Autowired
	public RegisterRepo repo;
	
	@Autowired
	public ImageRepo imageRepo;
	
	@Autowired 
	private MultipartProperties multipartProperties;
	
	
	@PostMapping("/register")
	public Register saveRegister(@RequestBody Register register) {
		Register savedRegister=service.saveRegister(register);
		return savedRegister;
	}	
	
	@GetMapping("/user")
	public Register getLoggedInUser(Authentication authentication) {
		
		List<Register> allUsers=repo.findAll();
		
		for(Register users:allUsers) {
			if(users.getUsername().equals(authentication.getName())) {
				return users;
			}
		}
		return new Register();
		
	}
	
	
	@GetMapping("/logger/{username}/{secretcode}")
	public boolean getLoginDetails(@PathVariable String username , @PathVariable String secretcode) {
		
		boolean value=service.loginValidate(username, secretcode);
		return value;
		
	}
	
	@PostMapping("/profile")
	public ProfileStatus saveprofileStatus(@RequestBody ProfileStatus profileStatus,Authentication authentication){
		ProfileStatus profile=service.saveProfileStatus(profileStatus,authentication);
		return profile;
	}
	
	@PostMapping(value={"/image"})
	public Image saveImage(@RequestPart("file") MultipartFile file,Authentication authentication) throws IOException{
		Image savedImage=service.saveImage(file, authentication);
		return savedImage;
	}
	
	@GetMapping("/maxsize")
	public long getMaxSize() {
		long maxFileSizeBytes = multipartProperties.getMaxFileSize().toBytes();
		return maxFileSizeBytes;
	}
	
	@PostMapping("/teacher")
	public Teacher saveTeacher(@RequestBody Teacher teacher,Authentication authentication) {
		Teacher saveTeacher=service.saveTeacher(teacher, authentication);
		return saveTeacher;
	}
	
	@PostMapping("/teacherimage")
	public TeacherImage saveteacherimage(@RequestPart("image") MultipartFile image,Authentication authentication) throws IOException{
		TeacherImage saveTeacherImage=service.saveTeacherImage(image, authentication);
		return saveTeacherImage;
	}
	
	@GetMapping("/getProfile/{name}")
	public List<ProfileStatus> getProfileStatus(@PathVariable String name,Authentication authentication) {
		List<ProfileStatus> allUserList=service.getProfileStatus(name,authentication);
		if(allUserList.size()==0) {
			System.out.println("No Such User with this name");
			return allUserList;
		}
		else {
			return allUserList;
		}
	}
	
	@GetMapping("/getImage/{id}")
	public Image getImage(@PathVariable long id) {
		Image getImage=service.getImageById(id);
		return getImage;
	}
	
	@GetMapping("/image/{id}")
	public ResponseEntity<byte[]> getAllImage(@PathVariable long id) {
		Image getImage=service.getImageById(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
	    return new ResponseEntity<>(getImage.getImageurl(), headers, HttpStatus.OK);		
	}
	
	@GetMapping("/listofprofilestatus")
	public List<ProfileStatus> getCurrentProfileStatusList(Authentication authentication){
		List<ProfileStatus> getCurrentProfile=service.getlasttenstudents(authentication);
		return getCurrentProfile;
	}
	
	@GetMapping("/allProfiles")
	public List<ProfileStatus> allProfiles(Authentication authentication){
		return service.getAllProfileStatus(authentication);
	}
	
	@GetMapping("/getActiveProfiles")
	public List<ProfileStatus> getActiveProfileStatus(Authentication authentication){
		return service.getActiveProfileStatus(authentication);
	}
	
	@GetMapping("/getProfileByRollNumber/{rollnumber}")
	public List<ProfileStatus> getProfileStatusByRollNumber(@PathVariable String rollnumber,Authentication authentication){
		List<ProfileStatus> allProfiles=service.getProfileByRollNumber(rollnumber, authentication);
		return allProfiles;
	}
	
	@GetMapping("/getProfileByStandard/{standard}")
	public List<ProfileStatus> getProfileStatusByStandard(@PathVariable String standard,Authentication authentication){
		List<ProfileStatus> allProfiles=service.getProfileStatusByStandard(standard, authentication);
		return allProfiles;
	}
	
	@GetMapping("/getTeacherByName/{teachername}")
	public List<Teacher> getTeacherName(@PathVariable String teachername,Authentication authentication){
		List<Teacher> getAllTeacherByName=service.getTeacherByName(teachername, authentication);
		return getAllTeacherByName;
	}
	
	@GetMapping("/teacherImage/{id}")
	public ResponseEntity<byte[]> getTeacherImage(@PathVariable long id) {
		TeacherImage getImage=service.getTeacherImage(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
	    return new ResponseEntity<>(getImage.getTeacherurl(), headers, HttpStatus.OK);
	}
	
	
	@GetMapping("/subset/{profile}/{name}")
	public String getProfileStatus(@PathVariable String profile,@PathVariable String name) {
		String profileSubSet=service.getSubSet(profile, name);
		return profileSubSet;
	}
	
	@GetMapping("/getTeacherList")
	public List<Teacher> getTeacherList(Authentication authentication){
		List<Teacher> allTeachersList=service.getListofTeacherList(authentication);
		return allTeachersList;
	}
	
	@GetMapping("/allTeacherList")
	public List<Teacher> getAllTeacherList(Authentication authentication){
		return service.allTeachers(authentication);
	}
	
	@GetMapping("/getTeacherByBranch/{branch}")
	public List<Teacher> getTeacherByBranchName(@PathVariable String branch,Authentication authentication){
		List<Teacher> getTeachers=service.getTeacherBySubject(branch, authentication);
		return getTeachers;
	}
	
	@GetMapping("/getTeacherByExperience/{experience}")
	public List<Teacher> getTeacherByExperienceList(@PathVariable String experience,Authentication authentication){
		List<Teacher> getAllTeachers=service.getTeacherByExperience(experience, authentication);
		return getAllTeachers;
	}
	
	
	@PostMapping("/getProfileFirst")
	public ProfileStatus getProfileStatusFirst(@RequestBody ProfileStatus profile,Authentication authentication) {
		return service.getProfileStatusFirst(profile, authentication);
	}
	
	@PutMapping("/updateProfileStatus")
	public ProfileStatus updateProfileStatus(@RequestBody ProfileStatus profile,Authentication authentication) {
		return service.updateProfileStatus(profile, authentication);
	}
	
	@GetMapping("/updateImage/{id}")
	public ResponseEntity<byte[]> updateImage(@PathVariable long id, Authentication authentication) {
		Image getImage=service.updateImageStatus(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
	    return new ResponseEntity<>(getImage.getImageurl(), headers, HttpStatus.OK);
	}
	
	@PutMapping("/updateprofileimage/{id}")
	public Image saveprofileimage(@RequestPart("img") MultipartFile image,@PathVariable long id,Authentication authentication) throws IOException{
		Image updateImg=service.updateImageStatus(image, id,authentication);
		return updateImg;
	}
	
	
	@PutMapping("/updateTeacher")
	public Teacher updateTeacher(@RequestBody Teacher teacher, Authentication authentication) {
		return service.updateTeacherDetails(teacher, authentication);
	}
	
	@PutMapping("/updateTeacherImage/{id}")
	public TeacherImage updateTeacherImage(@RequestPart("teacher") MultipartFile image,@PathVariable long id,Authentication authentication) throws IOException{
		TeacherImage teacherImage=service.updateTeacherImageStatus(image, id, authentication);
		return teacherImage;
	}
	
	@GetMapping("/updateTeacherImage/{id}")
	public ResponseEntity<byte[]> updateTeacherImage(@PathVariable long id, Authentication authentication) {
		TeacherImage getImage=service.updateTeacherImageStatus(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
	    return new ResponseEntity<>(getImage.getTeacherurl(), headers, HttpStatus.OK);
	}
	
	@PutMapping("/persistProfile")
	public ProfileStatus persistProfileStatus(@RequestBody ProfileStatus profile,Authentication authentication) {
		ProfileStatus profiles=service.persistOrNot(profile, authentication);
		return profiles;
	}
	
	
	@PutMapping("/persistTeacher")
	public Teacher persistTeacherStatus(@RequestBody Teacher teacher,Authentication authentication) {
		Teacher profiles=service.teacherPersistOrNot(teacher, authentication);
		return profiles;
	}
	
	@GetMapping("/updateProfileByName/{name}")
	public List<ProfileStatus> updateProfileByName(@PathVariable String name,Authentication authentication){
		return service.updateProfileByName(name, authentication);
	}
	
	@GetMapping("/updateProfileByRollNumber/{rollnumber}")
	public List<ProfileStatus> updateProfileByRollNumber(@PathVariable String rollnumber,Authentication authentication){
		return service.updateProfileStatusByRollNumber(rollnumber, authentication);
	}
	
	
	@GetMapping("/updateProfileByStandard/{standard}")
	public List<ProfileStatus> updateProfileByStandard(@PathVariable String standard,Authentication authentication){
		return service.updateProfileStatusByStandard(standard, authentication);
	}
	
	@GetMapping("/updateTeacherByName/{name}")
	public List<Teacher> updateTeacherByName(@PathVariable String name,Authentication authentication){
		return service.updateTeacherByName(name, authentication);
	}
	
	@GetMapping("/updateTeacherBySubject/{subject}")
	public List<Teacher> updateTeacherBySubject(@PathVariable String subject,Authentication authentication){
		return service.updateTeacherBySubject(subject, authentication);
	}
	
	@GetMapping("/updateTeacherByExperience/{experience}")
	public List<Teacher> updateTeacherByExperience(@PathVariable String experience,Authentication authentication){
		return service.updateTeacherByExperience(experience, authentication);
	}
	
	@PostMapping("/addclass")
	public Classes saveClasses(@RequestBody Classes classes,Authentication authentication) {
		List<Register> allUsers=repo.findAll();
		
		for(Register user :allUsers) {
			if(user.getUsername().equals(authentication.getName())) {
				return service.saveClasses(classes,authentication);
			}
		}
		
		return new Classes();
	}
	
	
	@GetMapping("/getTeacherById/{id}")
	public Teacher getTeacherById(@PathVariable long id) {
		return service.getTeacher(id);
	}
	

	@GetMapping("/getProfileById/{id}")
	public ProfileStatus getProfileById(@PathVariable long id) {
		return service.getProfile(id);
	}
	
	@GetMapping("/listofstandards")
	public List<String> getListOfStandards(Authentication authentication){
		return service.allStandards(authentication);
	}
	
	@GetMapping("/listofsections")
	public List<String> getListOfSections(Authentication authentication){
		return service.allSections(authentication);
	}
	
	@GetMapping("/getAllTeacher")
	public List<Teacher> getAllTeachers(Authentication authentication){
		return service.getAllTeacher(authentication);
	}
	
}
