package com.example.demoThyme.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demoThyme.models.FeesStructure;
import com.example.demoThyme.models.SalaryStructure;
import com.example.demoThyme.repos.SalaryStructRepo;
import com.example.demoThyme.repos.StaffRepo;

@Controller
public class SalaryStructures {

	@Autowired
	SalaryStructRepo salaryStructRepo;
	
	@Autowired
	StaffRepo staffRepo;
	
	@GetMapping("/admin/designationstructure")
	public ModelAndView salaryStruct(Principal pr)
	{
		ModelAndView mv=new ModelAndView("updSalaryStructure");
		List<SalaryStructure> y= salaryStructRepo.allSalaryStructure();
		mv.addObject("lst", y);
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	@GetMapping("/admin/adddesignation")
	public ModelAndView AddDesignation(Principal pr)
	{
		ModelAndView mv=new ModelAndView("addDesignation");
		mv.addObject("salry", new SalaryStructure());
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	 @PostMapping("/admin/adddesignation") 
	 public ModelAndView saveDesignation(@ModelAttribute SalaryStructure salry, Principal pr) 
	 {
		 ModelAndView mv=new ModelAndView("updSalaryStructure");
		 salaryStructRepo.save(salry); 
		 List<SalaryStructure> y= salaryStructRepo.allSalaryStructure();
		 mv.addObject("lst", y);
		 mv.addObject(new SalaryStructure());
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv; 
	 }
	 
	 @PostMapping("/admin/deletedesignation") 
	 public ModelAndView deleteDesignation(@RequestParam("designation") String designation, Principal pr) 
	 {
		 System.out.println(designation+"sus");
		 salaryStructRepo.deleteByDesignation(designation);
		 ModelAndView mv=new ModelAndView("updSalaryStructure");
		 
		 List<SalaryStructure> y= salaryStructRepo.allSalaryStructure();
		 mv.addObject("lst", y);
		 System.out.println(designation+"sus");
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv; 
	 }
}
