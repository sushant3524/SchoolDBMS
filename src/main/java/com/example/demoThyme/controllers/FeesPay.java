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

import com.example.demoThyme.models.Student;
import com.example.demoThyme.repos.StaffRepo;
import com.example.demoThyme.repos.studentRepo;

@Controller
public class FeesPay {
	
	@Autowired
	studentRepo stuRepo;
	
	@Autowired
	StaffRepo staffRepo;
	
	public Student stu;
	
	@GetMapping("/admin/fees")
	public ModelAndView payFees(Principal pr)
	{
		ModelAndView mv= new ModelAndView("getfees");
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	@PostMapping("/admin/getfees")
	public ModelAndView getFees(@RequestParam("id") int id, Principal pr)
	{
		ModelAndView mv= new ModelAndView("fees");
	    stu=stuRepo.getStudentById(id);
	    System.out.println("hh");
	    if(stu==null)
	    {
	    	ModelAndView mr= new ModelAndView("allstudent");
	    	System.out.println("hi");
	    	List<Student>st1=stuRepo.allStudent();
			 List<Student>st=new ArrayList<>();
			 for(Student x:st1)
			 {
				 if(x.isAttending())
					 st.add(x);
			 }
			 mr.addObject("lst", st);
			 mr.addObject("nf",1);
			 mr.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
	    	return mr;
	    }
		mv.addObject("st", stu);
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	@GetMapping("/admin/getfees")
	public ModelAndView gtFees(@RequestParam("id") int id, Principal pr)
	{
		ModelAndView mv= new ModelAndView("fees");
	    stu=stuRepo.getStudentById(id);
	    if(stu==null)
	    {
	    	ModelAndView mr= new ModelAndView("allstudent");
	    	System.out.println("hi");
	    	List<Student>st1=stuRepo.allStudent();
			 List<Student>st=new ArrayList<>();
			 for(Student x:st1)
			 {
				 if(x.isAttending())
					 st.add(x);
			 }
			 mr.addObject("lst", st);
			 mr.addObject("nf",1);
			 mr.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
	    	return mr;
	    }
		mv.addObject("st", stu);
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	@PostMapping("/admin/fees")
	public String fees(@RequestParam("amt") int amt)
	{
		//ModelAndView mv= new ModelAndView("indstudent");
		int remaining=stu.getFeedue()-amt;
		stu.addFees(amt);
		stu.setFeedue(remaining);
		stuRepo.save(stu);
		//mv.addObject("attendance",2);
		//mv.addObject("st",stu);
		return "redirect:/admin/indstudent?id="+Integer.toString(stu.getStuId());
	}
}
