package com.example.demoThyme.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demoThyme.models.Staff;
import com.example.demoThyme.models.Student;
import com.example.demoThyme.repos.StaffRepo;
import com.example.demoThyme.repos.studentRepo;

@Controller
public class SalaryPay {

	@Autowired
	StaffRepo staffRepo;
	
	public Staff staff;
	
	@GetMapping("/admin/salary")
	public ModelAndView payFees(Principal pr)
	{
		ModelAndView mv= new ModelAndView("getsalary");
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	@PostMapping("/admin/getsalary")
	public ModelAndView getFees(@RequestParam("id") int id, Principal pr)
	{
		ModelAndView mv= new ModelAndView("salary");
	    staff=staffRepo.getStaffById(id);
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
			 mr.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
				
	    	return mr;
	    }
		mv.addObject("st", staff);
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		
		return mv;
	}
	
	@GetMapping("/admin/getsalary")
	public ModelAndView gtFees(@RequestParam("id") int id, Principal pr)
	{
		ModelAndView mv= new ModelAndView("salary");
	    staff=staffRepo.getStaffById(id);
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
			 mr.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
				
	    	return mr;
	    }
		mv.addObject("st", staff);
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		
		return mv;
	}
	
	@PostMapping("/admin/salary")
	public String fees(@RequestParam("amt") int amt)
	{
		//ModelAndView mv= new ModelAndView("salary_given");
		int remaining=staff.getSalarydue()-amt;
		staff.addFees(amt);
		staff.setSalarydue(remaining);
		staffRepo.save(staff);
		//mv.addObject("staff", staff);
		//mv.addObject("remain", remaining);
		//return mv;
		return "redirect:/admin/indstaff?id="+Integer.toString(staff.getStaffId());
	}
}
