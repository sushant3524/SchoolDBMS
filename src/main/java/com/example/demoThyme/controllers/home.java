package com.example.demoThyme.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demoThyme.models.Staff;
import com.example.demoThyme.repos.StaffRepo;

@Controller
public class home {
	
	@Autowired
	StaffRepo staffRepo;
	
	@GetMapping("/")
	public ModelAndView homefn()
	{
		ModelAndView mod= new ModelAndView("basenorml");
		mod.addObject("attendance",0);
		return mod;
	}
	
	@GetMapping("/signin")
	public ModelAndView signin()
	{
		ModelAndView mv= new ModelAndView("login");
		return mv;
	}
	
	@GetMapping("/staff/check")
	public String check(Principal pri)
	{
		String email=pri.getName();
		Staff st=staffRepo.getStaffByEmail(email);
		if(st.isEnabled()) {
		if(st.getRole().equals("ROLE_STAFF"))
		{
			return "redirect:/staff/home";
		}
		else
		{
			return "redirect:/admin/home";
		}
		}
		else 
		{
			return "redirect:/exman";
		}
	}
	
	@GetMapping("/staff/home")
	public ModelAndView homestaff(Principal pri)
	{
		String email=pri.getName();
		Staff st=staffRepo.getStaffByEmail(email);
		ModelAndView mv=new ModelAndView("homestaff");
		mv.addObject("id",st.getStaffId());
		mv.addObject("nme",st.getName());
		return mv;
	}
	
	@GetMapping("/admin/home")
	public ModelAndView homeadmin(Principal pri)
	{
		String email=pri.getName();
		Staff st=staffRepo.getStaffByEmail(email);
		ModelAndView mv=new ModelAndView("home");
		mv.addObject("id",st.getStaffId());
		mv.addObject("nme",st.getName());
		return mv;
	}
	
}
