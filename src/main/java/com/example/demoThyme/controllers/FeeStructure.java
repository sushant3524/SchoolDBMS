package com.example.demoThyme.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demoThyme.models.FeesStructure;
import com.example.demoThyme.models.Student;
import com.example.demoThyme.repos.StaffRepo;
import com.example.demoThyme.repos.feeStructRepo;

@Controller
public class FeeStructure {

	@Autowired
	feeStructRepo feeRepo;
	
	@Autowired
	StaffRepo staffRepo;
	
	@GetMapping("/admin/feestructure")
	public ModelAndView feeStruct(Principal pr)
	{
		ModelAndView mv=new ModelAndView("updFeeStructure");
		List<FeesStructure> y= feeRepo.allFeesStructure();
		mv.addObject("lst", y);
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	@GetMapping("/feestructure")
	public ModelAndView feeStruc(Principal pr)
	{
		ModelAndView mv=new ModelAndView("FeeStructure");
		List<FeesStructure> y= feeRepo.allFeesStructure();
		mv.addObject("lst", y);
		//mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	@GetMapping("/admin/addclass")
	public ModelAndView AddClass(Principal pr)
	{
		ModelAndView mv=new ModelAndView("addClass");
		mv.addObject("clas", new FeesStructure());
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	 @PostMapping("/admin/addclass") 
	 public ModelAndView saveClass(@ModelAttribute FeesStructure cl, Principal pr) 
	 {
		 ModelAndView mv=new ModelAndView("updFeeStructure");
		 feeRepo.save(cl); 
		 List<FeesStructure> y= feeRepo.allFeesStructure();
		 mv.addObject("lst", y);
		 mv.addObject(new FeeStructure());
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv; 
	 }
	 
	 @PostMapping("/admin/deleteclass") 
	 public ModelAndView deleteClass(@RequestParam("clss") String clss, Principal pr) 
	 {
		 System.out.println(clss+"sus");
		 feeRepo.deleteByClss(clss);
		 ModelAndView mv=new ModelAndView("updFeeStructure");
		 
		 List<FeesStructure> y= feeRepo.allFeesStructure();
		 mv.addObject("lst", y);
		 System.out.println(clss+"sus");
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv; 
	 }
	 
}
