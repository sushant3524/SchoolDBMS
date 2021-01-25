package com.example.demoThyme.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demoThyme.models.Fees;
import com.example.demoThyme.models.Salary;
import com.example.demoThyme.models.Staff;
import com.example.demoThyme.models.Student;
import com.example.demoThyme.repos.SalaryStructRepo;
import com.example.demoThyme.repos.StaffRepo;

@Controller
public class Register {

	@Autowired
	StaffRepo staffRepo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	SalaryStructRepo salaryStructRepo;
	
	@GetMapping("/admin/register")
	public ModelAndView register(Principal pr)
	{
		ModelAndView mv=new ModelAndView("register");
		mv.addObject("staff", new Staff());
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	@PostMapping("/admin/register")
	public String saveStaff(@Valid @ModelAttribute Staff staff, BindingResult bindingResult, Model model, Principal pr)
	{
		//ModelAndView mv=new ModelAndView("staff_saved");
		if(bindingResult.hasErrors()) {

			model.addAttribute(staff);
			model.addAttribute("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
			return "register";

		}
		int y=salaryStructRepo.getSalaryById(staff.getDesignation());
		staff.addAttendance();
		staff.setSalarydue(y);
		staff.setPassword(passwordEncoder.encode(staff.getPassword()));
		staffRepo.save(staff);
//		mv.addObject("staff",staff);
//		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
//		return mv;
		return "redirect:/admin/indstaff?id="+Integer.toString(staff.getStaffId()); 
	}
	
	@GetMapping("/admin/updatestaff")
	public ModelAndView updatestu(@RequestParam(value="id")int id, Principal pr) {
		ModelAndView mv = new ModelAndView("updatestaff");
		//mv.addObject("lst", fsr);
		mv.addObject(staffRepo.getOne(id));
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}

	
	 @PostMapping("/admin/updatestaff") 
	 public String updstu(@RequestParam("id")int id, @Valid @ModelAttribute Staff stu, BindingResult bindingResult, Model model, Principal pr) 
	 {
		 //ModelAndView mv=new ModelAndView("stu_added");
		 //stu.setFeedue(fsr.getFeesById(stu.getClss()));
		 if(bindingResult.hasErrors()) {

			 	stu.setStaffId(id);
				model.addAttribute(stu);
				model.addAttribute("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
				return "updatestaff";

			}
		 int paid=0;
		 Staff st=staffRepo.getStaffById(id);
		 stu.setAttendance(st.getAttendance());
		 stu.setSalary(st.getSalary());
		 for(Salary y:st.getSalary())
		 {
			 paid+=y.getAmount();
		 }
		 //int z=fsr.getFeesById(stu.getClss());
		 int z=salaryStructRepo.getSalaryById(stu.getDesignation());
		 z=z-paid;
		 stu.setSalarydue(z);
		 stu.setStaffId(id);
		 stu.setPassword(passwordEncoder.encode(stu.getPassword()));
		 staffRepo.save(stu); 
		 //mv.addObject(stu);
		 return "redirect:/admin/indstaff?id="+Integer.toString(stu.getStaffId()); 
	 }
	 
	 @GetMapping("/admin/checkstaff")
	 public String check(@RequestParam("id")int id)
	 {
		 Staff stu=staffRepo.getOne(id);
		 if(stu.getSalarydue()==0)
			 return "redirect:/admin/leavestaff?id="+Integer.toString(stu.getStaffId()); 
		 else
			 return "redirect:/admin/getsalary?id="+Integer.toString(stu.getStaffId()); 
	 }
 @GetMapping("/admin/leavestaff")
 public ModelAndView leaveStu(@RequestParam("id")int id, Principal pr) 
 {
	 ModelAndView mv=new ModelAndView("leavestudent");
	 Staff stu=staffRepo.getOne(id);
	 if(stu.getSalarydue()>0)
	 {
		 ModelAndView mr=new ModelAndView("indstudent");
		 mr.addObject("st",stu);
		 mr.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mr;
	 }
	 stu.setEnabled(false);
	 mv.addObject(stu);
	 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
	 return mv;
 }
 
 @PostMapping("/admin/leavestaff")
 public String leaveStuP(@ModelAttribute Staff stu, @RequestParam("id")int id) 
 {
	 Staff st=staffRepo.getOne(id);
	 st.setFeedback(stu.getFeedback());
	 st.setEnabled(false);
	 st.setRole("ROLE_STAFF");
	 staffRepo.save(st);
	 return "redirect:/admin/allstaff";
 }
 
	
	 @GetMapping("/admin/attendance")
	 public ModelAndView attendance(Principal pr)
	 {
		 ModelAndView mv=new ModelAndView("attendancestaff");
		 List<Staff> y1= staffRepo.findAll();
		 System.out.println("y1 size:"+y1.size());
		 List<Staff> y=new ArrayList<>();
		 for(int i=0;i<y1.size();i++)
		 {
			 if(y1.get(i).isEnabled() &&(y1.get(i).getAttendance().size()==0||!(LocalDate.now().equals(y1.get(i).getAttendance().get(y1.get(i).getAttendance().size()-1).getDate()))))
			 {
				 System.out.println(LocalDate.now());
				 //System.out.println(y1.get(i).getAttendance().get(y1.get(i).getAttendance().size()-1).getDate());
				 y.add(y1.get(i));
			 }
		 }
		 System.out.println("y size:"+y.size());
		 mv.addObject("lst", y);
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv;
	 }
	 
	 @PostMapping("/admin/attendanceTaken")
	 public ModelAndView attendanceTaken(@RequestParam(value="idChecked", required=false) List<String> presentList, Principal pr)
	 {
		 ModelAndView mv= new ModelAndView("home");
		 if(presentList!=null) {
		 for(String sid:presentList)
		 {
			 int id=Integer.parseInt(sid);
			 Staff st=staffRepo.getOne(id);
			 st.addAttendance();
			 int x=st.getSalarydue();
			 x+=salaryStructRepo.getSalaryById(st.getDesignation());
			 st.setSalarydue(x);
			 System.out.println(st.getAttendance().get(st.getAttendance().size()-1).getDate());
			 staffRepo.save(st);
		 }
		 }
			/*
			 * else if(check!=null) { int id=Integer.parseInt(check); Student
			 * st=stuRepo.getOne(id); st.addAttendance();
			 * System.out.println(st.getAttendance().get(st.getAttendance().size()-1).
			 * getDate()); stuRepo.save(st); }
			 */
		 else
		 {
			 System.out.println("NULL");
		 }
		 mv.addObject("attendance", 1);
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv;
	 }
	
	@GetMapping("/admin/allstaff")
	public ModelAndView allStaff(Principal pr)
	{
		ModelAndView mv=new ModelAndView("allstaff");
		List<Staff>st1=staffRepo.findAll();
		List<Staff>st=new ArrayList<>();
		for(Staff x:st1)
		{
			if(x.isEnabled())
				st.add(x);
		}
		mv.addObject("lst",st);
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	@GetMapping("/admin/archivestaff")
	public ModelAndView archives(Principal pr)
	{
		ModelAndView mv=new ModelAndView("allstaff");
		List<Staff>st1=staffRepo.findAll();
		List<Staff>st=new ArrayList<>();
		for(Staff x:st1)
		{
			if(!x.isEnabled())
				st.add(x);
		}
		mv.addObject("lst",st);
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
//	@PostMapping("/admin/deletestaff")
//	public String deleteStaff(@RequestParam("id") int id)
//	{
//		staffRepo.deleteById(id);
//		return "redirect:/admin/allstaff";
//	}
	
	@GetMapping("/admin/deletestaff")
	public String deletStaff(@RequestParam("id") int id)
	{
		staffRepo.deleteById(id);
		return "redirect:/admin/allstaff";
	}
	
	@GetMapping("/admin/indstaff")
	public ModelAndView indStaff(@RequestParam("id") int id, Principal pr)
	{
		ModelAndView mv=new ModelAndView("indstaff");
		Staff staff=staffRepo.getStaffById(id);
		if(staff==null)
	    {
	    	ModelAndView mr= new ModelAndView("allstaff");
	    	System.out.println("hi");
	    	List<Staff>st1=staffRepo.findAll();
			 List<Staff>st=new ArrayList<>();
			 for(Staff x:st1)
			 {
				 if(x.isEnabled())
					 st.add(x);
			 }
			 mr.addObject("lst", st);
			 mr.addObject("nf",1);
	    	return mr;
	    }
		mv.addObject("st",staff);
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	@GetMapping("/admin/personal")
	public ModelAndView personalAdmin(Principal pri)
	{
		String email=pri.getName();
		Staff staff=staffRepo.getStaffByEmail(email);
		ModelAndView mv=new ModelAndView("indstaff");
		//Staff staff=staffRepo.getOne(id);
		mv.addObject("st",staff);
		mv.addObject("nme",staffRepo.getStaffByEmail(pri.getName()).getName());
		return mv;
	}
	
	@GetMapping("/staff/personal")
	public ModelAndView personalStaff(Principal pri)
	{
		String email=pri.getName();
		Staff staff=staffRepo.getStaffByEmail(email);
		ModelAndView mv=new ModelAndView("perstaff");
		//Staff staff=staffRepo.getOne(id);
		mv.addObject("st",staff);
		mv.addObject("nme",staffRepo.getStaffByEmail(pri.getName()).getName());
		return mv;
	}
	
}
