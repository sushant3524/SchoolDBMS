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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int stuId;
	@NotBlank
	private String stuName;
	private String clss;
	private LocalDate dateOfAdmission;
	private String gender;
	private int feedue;
	private boolean attending;
	@NotBlank
	@Pattern(regexp = "^[0-9]{10,10}", message = "Enter a valid mobile number!")
	private  String mobile;
	@NotBlank
	private String address;
	private boolean transport;
	@NotBlank
	private String dob;
	
	@Column(unique=true)
	@NotBlank
	@Pattern(regexp = "^[0-9]{12,12}", message = "Enter a valid aadhaar number!")
	private String aadhaar;
	
	private String feedback;
	
	@NotBlank
	private String guardian;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="attend_id", referencedColumnName="stuId")
	List<AttendanceStudent> attendance= new ArrayList<>();
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="fee_id", referencedColumnName="stuId")
	List<Fees> fees= new ArrayList<>();
	
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
	public boolean isTransport() {
		return transport;
	}
	public void setTransport(boolean transport) {
		this.transport = transport;
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
	public void setDateOfAdmission(LocalDate dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}
	public boolean isAttending() {
		return attending;
	}
	public void setAttending(boolean attending) {
		this.attending = attending;
	}
	public LocalDate getDateOfAdmission() {
		return dateOfAdmission;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public List<Fees> getFees() {
		return fees;
	}
	public void setFees(List<Fees> fees) {
		this.fees = fees;
	}
	public List<AttendanceStudent> getAttendance() {
		return attendance;
	}
	public void setAttendance(List<AttendanceStudent> attendance) {
		this.attendance = attendance;
	}
	
	public void addAttendance()
	{
		AttendanceStudent tmp= new AttendanceStudent();
		attendance.add(tmp);
	}
	
	public void addFees(int amt)
	{
		Fees tmp=new Fees(amt);
		fees.add(tmp);
	}
	
	public int getStuId() {
		return stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getClss() {
		return clss;
	}
	public void setClss(String clss) {
		this.clss = clss;
	}
	public int getFeedue() {
		return feedue;
	}
	public void setFeedue(int feedue) {
		this.feedue = feedue;
	}
	public String getGuardian() {
		return guardian;
	}
	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}
	public Student(int stuId, String stuName, String clss, LocalDate doa, int feedue, String guardian) {
		super();
		this.stuId = stuId;
		this.stuName = stuName;
		this.clss = clss;
		this.dateOfAdmission = doa;
		this.feedue = feedue;
		this.guardian = guardian;
	}
	public Student() {
		super();
		attending=true;
		dateOfAdmission=LocalDate.now();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "student [stuId=" + stuId + ", stuName=" + stuName + ", clss=" + clss + ", feedue="
				+ feedue + ", guardian=" + guardian + "]";
	}
	
}
