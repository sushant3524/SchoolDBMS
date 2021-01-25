package com.example.demoThyme.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Staff {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int staffId;
 
	@Column(unique=true)
	@NotBlank
	@Email(message="Please enter a valid e-mail address")
    private String email;
	
	@NotBlank(message="Password should be between 6 to 15 characters!")
	@Size(min=6)
    private String password;
	@NotBlank(message="Name field cannot be blank!")
    private String name;
    private String gender;
    private String designation;
    private int salarydue;
    private boolean enabled;
    private String imgurl;
    private String role;
    private LocalDate dateOfJoining;
    
    //@Size(min=10, max=10)
    @NotBlank
    @Pattern(regexp = "^[0-9]{10,10}", message = "Enter a valid mobile number!")
	private  String mobile;
    
    @NotBlank
	private String address;
	//private boolean transport;
    @NotBlank
    //@DateTimeFormat(pattern = "dd-mm-yyyy")
    private String dob;
    
    @Column(unique=true)
    @NotBlank
    @Pattern(regexp = "^[0-9]{12,12}", message = "Enter a valid aadhaar number!")
	private String aadhaar;
	private String feedback;
    
    @OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="attend_id", referencedColumnName="staffId")
	List<AttendanceStaff> attendance= new ArrayList<>();
    
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="salry_id", referencedColumnName="staffId")
	List<Salary> salary= new ArrayList<>();
    
    
    
    public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAadhaar() {
		return aadhaar;
	}

	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Staff() {
		super();
		enabled=true;
		dateOfJoining=LocalDate.now();
		// TODO Auto-generated constructor stub
	}

	public void addAttendance()
	{
		AttendanceStaff tmp= new AttendanceStaff();
		attendance.add(tmp);
	}
	
	public void addFees(int amt)
	{
		Salary tmp=new Salary(amt);
		salary.add(tmp);
	}
    
    public List<Salary> getSalary() {
		return salary;
	}

	public void setSalary(List<Salary> salary) {
		this.salary = salary;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}	
    
    public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public List<AttendanceStaff> getAttendance() {
		return attendance;
	}

	public void setAttendance(List<AttendanceStaff> attendance) {
		this.attendance = attendance;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getSalarydue() {
		return salarydue;
	}

	public void setSalarydue(int salarydue) {
		this.salarydue = salarydue;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
