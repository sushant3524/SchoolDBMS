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
import com.example.demoThyme.models.Resources;
import com.example.demoThyme.repos.ResourceRepo;
import com.example.demoThyme.repos.StaffRepo;

@Controller
public class Resrces {

	@Autowired
	StaffRepo staffRepo;
	
	@Autowired
	ResourceRepo resourceRepo;
	
	@GetMapping("/admin/resources")
	public ModelAndView feeStruct(Principal pr)
	{
		ModelAndView mv=new ModelAndView("updResources");
		List<Resources> y= resourceRepo.allResources();
		mv.addObject("lst", y);
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	@GetMapping("/admin/addresources")
	public ModelAndView AddClass(Principal pr)
	{
		ModelAndView mv=new ModelAndView("addResources");
		mv.addObject("clas", new Resources());
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}
	
	 @PostMapping("/admin/addresources") 
	 public ModelAndView saveClass(@ModelAttribute Resources cl, Principal pr) 
	 {
		 ModelAndView mv=new ModelAndView("updResources");
		 resourceRepo.save(cl);
		 List<Resources> y= resourceRepo.allResources();
		 mv.addObject("lst", y);
		 //mv.addObject(new FeeStructure());
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv; 
	 }
	 
	 @PostMapping("/admin/deleteresources") 
	 public ModelAndView deleteClass(@RequestParam("id") int id, Principal pr) 
	 {
		 //System.out.println(clss+"sus");
		 resourceRepo.deleteById(id);
		 ModelAndView mv=new ModelAndView("updResources");
		 
		 List<Resources> y= resourceRepo.allResources();
		 mv.addObject("lst", y);
		 //System.out.println(clss+"sus");
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv; 
	 }
	
}
