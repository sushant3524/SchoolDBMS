package com.example.demoThyme.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demoThyme.models.Fees;
import com.example.demoThyme.models.FeesStructure;
import com.example.demoThyme.models.Student;
import com.example.demoThyme.repos.StaffRepo;
import com.example.demoThyme.repos.feeStructRepo;
import com.example.demoThyme.repos.studentRepo;

@Controller
public class AddStudent {

	@Autowired
	studentRepo stuRepo;
	
	@Autowired
	StaffRepo staffRepo;
	
	@Autowired
	feeStructRepo fsr;
	
	@GetMapping("/admin/addStudent")
	public ModelAndView addstu(Principal pr) {
		ModelAndView mv = new ModelAndView("addStudent");
		mv.addObject("lst", fsr);
		mv.addObject(new Student());
		mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		return mv;
	}

	
	 @PostMapping("/admin/addStudent") 
	 public String savestu(@Valid @ModelAttribute Student stu, BindingResult bindingResult, Model model, Principal pr) 
	 {
		 if(bindingResult.hasErrors()) {

				model.addAttribute(stu);
				model.addAttribute("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
				return "addStudent";

			}
		 //ModelAndView mv=new ModelAndView("stu_added");
		 stu.setFeedue(fsr.getFeesById(stu.getClss()));
		 stuRepo.save(stu); 
		 //mv.addObject(stu);
		 return "redirect:/admin/indstudent?id="+Integer.toString(stu.getStuId()); 
	 }
	 
	 @GetMapping("/admin/updateStudent")
		public ModelAndView updatestu(@RequestParam(value="id")int id, Principal pr) {
			ModelAndView mv = new ModelAndView("updatestudent");
			mv.addObject("lst", fsr);
			mv.addObject(stuRepo.getStudentById(id));
			//mv.addObject("id",id);
			mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
			return mv;
		}

		
		 @PostMapping("/admin/updateStudent") 
		 public String updstu(@Valid @ModelAttribute Student stu, BindingResult bindingResult, Model model, Principal pr, @RequestParam("id")int id) 
		 {
			 //ModelAndView mv=new ModelAndView("stu_added");
			 //stu.setFeedue(fsr.getFeesById(stu.getClss()));
			 if(bindingResult.hasErrors()) {

				 	stu.setStuId(id);
					model.addAttribute(stu);
					model.addAttribute("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
					return "updatestudent";

				}
			 
			 int paid=0;
			 Student st=stuRepo.getStudentById(id);
			 for(Fees y:st.getFees())
			 {
				 paid+=y.getAmount();
			 }
			 stu.setFees(st.getFees());
			 stu.setAttendance(st.getAttendance());
			 int z=fsr.getFeesById(stu.getClss());
			 z=z-paid;
			 stu.setFeedue(z);
			 stu.setStuId(id);
			 stuRepo.save(stu); 
			 //mv.addObject(stu);
			 return "redirect:/admin/indstudent?id="+Integer.toString(stu.getStuId()); 
		 }
		 
		 @GetMapping("/admin/checkStudent")
		 public String check(@RequestParam("id")int id)
		 {
			 Student stu=stuRepo.getStudentById(id);
			 if(stu.getFeedue()==0)
				 return "redirect:/admin/leaveStudent?id="+Integer.toString(stu.getStuId()); 
			 else
				 return "redirect:/admin/getfees?id="+Integer.toString(stu.getStuId()); 
		 }
	 @GetMapping("/admin/leaveStudent")
	 public ModelAndView leaveStu(@RequestParam("id")int id, Principal pr) 
	 {
		 ModelAndView mv=new ModelAndView("leavestudent");
		 Student stu=stuRepo.getStudentById(id);
		 if(stu.getFeedue()>0)
		 {
			 ModelAndView mr=new ModelAndView("indstudent");
			 mr.addObject("st",stu);
			 mr.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
			 return mr;
		 }
		 //stu.setAttending(false);
		 mv.addObject(stu);
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv;
	 }
	 
	 @PostMapping("/admin/leaveStudent")
	 public String leaveStuP(@ModelAttribute Student stu, @RequestParam("id")int id) 
	 {
		 Student st=stuRepo.getStudentById(id);
		 st.setFeedback(stu.getFeedback());
		 st.setAttending(false);
		 stuRepo.save(st);
		 return "redirect:/admin/allstudent";
	 }
	 
	 @GetMapping("/staff/attendance")
	 public ModelAndView clssAttendance(Principal pr)
	 {
		 ModelAndView mv= new ModelAndView("clssAttendanceStaff");
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv;
	 }
	 
	 @PostMapping("/staff/attendance")
	 public ModelAndView attendance(@RequestParam("clss") String clss, Principal pr)
	 {
		 ModelAndView mv=new ModelAndView("attendanceSt");
		 List<Student> y1= stuRepo.allStudent();
		 System.out.println("y1 size:"+y1.size());
		 List<Student> y=new ArrayList<>();
		 for(int i=0;i<y1.size();i++)
		 {
			 if(y1.get(i).isAttending()&& y1.get(i).getClss().equals(clss) && (y1.get(i).getAttendance().size()==0||!(LocalDate.now().equals(y1.get(i).getAttendance().get(y1.get(i).getAttendance().size()-1).getDate()))))
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
	 
	 @PostMapping("/staff/attendanceTaken")
	 public ModelAndView attendanceTaken(@RequestParam(value="idChecked", required=false) List<String> presentList, Principal pr)
	 {
		 ModelAndView mv= new ModelAndView("homestaff");
		 if(presentList!=null) {
		 for(String sid:presentList)
		 {
			 int id=Integer.parseInt(sid);
			 Student st=stuRepo.getOne(id);
			 st.addAttendance();
			 System.out.println(st.getAttendance().get(st.getAttendance().size()-1).getDate());
			 stuRepo.save(st);
		 }
		 }
			/*
			 * else if(check!=null) { int id=Integer.parseInt(check); Student
			 * st=stuRepo.getOne(id); st.addAttendance();
			 * System.out.println(st.getAttendance().get(st.getAttendance().size()-1).
			 * getDate()); stuRepo.save(st); }
			 */
		 else
			 System.out.println("NULL");
		 mv.addObject("attendance",1);
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv;
	 }
	 
	 @GetMapping("/admin/attendancestu")
	 public ModelAndView clssAttendancead(Principal pr)
	 {
		 ModelAndView mv= new ModelAndView("clssAttendance");
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv;
	 }
	 
	 @PostMapping("/admin/attendance")
	 public ModelAndView attendancead(@RequestParam("clss") String clss, Principal pr)
	 {
		 ModelAndView mv=new ModelAndView("attendance");
		 List<Student> y1= stuRepo.allStudent();
		 System.out.println("y1 size:"+y1.size());
		 List<Student> y=new ArrayList<>();
		 for(int i=0;i<y1.size();i++)
		 {
			 if(y1.get(i).isAttending()&& y1.get(i).getClss().equals(clss) && (y1.get(i).getAttendance().size()==0||!(LocalDate.now().equals(y1.get(i).getAttendance().get(y1.get(i).getAttendance().size()-1).getDate()))))
			 {
				 System.out.println(LocalDate.now());
				 //System.out.println(y1.get(i).getAttendance().get(y1.get(i).getAttendance().size()-1).getDate());
				 y.add(y1.get(i));
			 }
		 }
		 System.out.println("y size:"+y.size());
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 mv.addObject("lst", y);
		 return mv;
	 }
	 
	 @PostMapping("/admin/attendanceTakenstu")
	 public ModelAndView attendanceTakenad(@RequestParam(value="idChecked", required=false) List<String> presentList, Principal pr)
	 {
		 ModelAndView mv= new ModelAndView("home");
		 if(presentList!=null) {
		 for(String sid:presentList)
		 {
			 int id=Integer.parseInt(sid);
			 Student st=stuRepo.getOne(id);
			 st.addAttendance();
			 System.out.println(st.getAttendance().get(st.getAttendance().size()-1).getDate());
			 stuRepo.save(st);
		 }
		 }
			/*
			 * else if(check!=null) { int id=Integer.parseInt(check); Student
			 * st=stuRepo.getOne(id); st.addAttendance();
			 * System.out.println(st.getAttendance().get(st.getAttendance().size()-1).
			 * getDate()); stuRepo.save(st); }
			 */
		 else
			 System.out.println("NULL");
		 mv.addObject("attendance",1);
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv;
	 }
	 
	 
	 @GetMapping("/admin/archivestudent")
	 public ModelAndView Archive(Principal pr)
	 {
		 ModelAndView mv= new ModelAndView("allstudent");
		 List<Student>st1=stuRepo.allStudent();
		 List<Student>st=new ArrayList<>();
		 for(Student x:st1)
		 {
			 if(!x.isAttending())
				 st.add(x);
		 }
		 mv.addObject("lst", st);
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv;
	 }
	 
	 @GetMapping("/admin/allstudent")
	 public ModelAndView AllStudent(Principal pr)
	 {
		 ModelAndView mv= new ModelAndView("allstudent");
		 List<Student>st1=stuRepo.allStudent();
		 List<Student>st=new ArrayList<>();
		 for(Student x:st1)
		 {
			 if(x.isAttending())
				 st.add(x);
		 }
		 mv.addObject("lst", st);
		 mv.addObject("nf",0);
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv;
	 }
	 
//	 @GetMapping("/staff/indstudent")
//	 public ModelAndView indStudent(@RequestParam("id") int id)
//	 {
//		 
//		 try {
//			 ModelAndView mv= new ModelAndView("indstudent");
//			 Student st=stuRepo.getStudentById(id);
//			 mv.addObject("st",st);
//			 return mv;
//		 }
//		 
//		 
//		 catch(EntityNotFoundException e)
//		 {
//			 ModelAndView mr=new ModelAndView("allstudent");
//			 mr.addObject("nf",1);
//			 mr.addObject("lst", stuRepo.allStudent());
//			 return mr;
//		 }
//		 
//	 }	
//	 
	 @GetMapping("/admin/indstudent")
	 public ModelAndView indStdnt(@RequestParam("id") int id, Principal pr)
	 {
		 ModelAndView mv= new ModelAndView("indstudent");
		 Student st=stuRepo.getStudentById(id);
		 if(st==null)
		 {
			 ModelAndView mr=new ModelAndView("allstudent");
			 mr.addObject("nf",1);
			 mr.addObject("lst", stuRepo.allStudent());
			 return mr;
		 }
		 mv.addObject("st",st);
		 mv.addObject("nme",staffRepo.getStaffByEmail(pr.getName()).getName());
		 return mv;
	 }	
	 
	 @GetMapping("/admin/deletestudent")
	 public String deleteStudent(@RequestParam("id") int id)
	 {
		 stuRepo.deleteById(id);
		 return "redirect:/admin/allstudent";
	 }
	 
	 @GetMapping("/admin/upgradeStudent")
	 public String upgstu(@RequestParam(value="id")int id)
	 {
		 Student stu=stuRepo.getStudentById(id);
		 int rem=stu.getFeedue();
		 
		 if(stu.getClss().equals("Nursery"))
		 {
			 stu.setClss("LKG");
		 }
		 else if(stu.getClss().equals("LKG"))
		 {
			 stu.setClss("UKG");
		 }
		 else if(stu.getClss().equals("UKG"))
		 {
			 stu.setClss("1st");
		 }
		 else if(stu.getClss().equals("1st"))
		 {
			 stu.setClss("2nd");
		 }
		 else if(stu.getClss().equals("2nd"))
		 {
			 stu.setClss("3rd");
		 }
		 else if(stu.getClss().equals("3rd"))
		 {
			 stu.setClss("4th");
		 }
		 else if(stu.getClss().equals("4th"))
		 {
			 stu.setClss("5th");
		 }
		 else if(stu.getClss().equals("5th"))
		 {
			 stu.setClss("6th");
		 }
		 else if(stu.getClss().equals("6th"))
		 {
			 stu.setClss("7th");
		 }
		 else if(stu.getClss().equals("7th"))
		 {
			 stu.setClss("8th");
		 }
		 else if(stu.getClss().equals("8th"))
		 {
			 stu.setClss("9th");
		 }
		 else if(stu.getClss().equals("9th"))
		 {
			 stu.setClss("10th");
		 }
		 else if(stu.getClss().equals("10th"))
		 {
			 stu.setClss("11th");
		 }
		 else if(stu.getClss().equals("11th"))
		 {
			 stu.setClss("12th");
		 }
		 else if(stu.getClss().equals("12th"))
		 {
			 //stu.setClss("12th");
			 return "redirect:/admin/indstudent?id="+Integer.toString(stu.getStuId()); 
		 }
		 int nxt=fsr.getFeesById(stu.getClss());
		 nxt+=rem;
		 stu.setFeedue(nxt);
		 stuRepo.save(stu);
		 return "redirect:/admin/indstudent?id="+Integer.toString(stu.getStuId()); 
	 }
 
	 @GetMapping("/admin/repeatStudent")
	 public String rptstu(@RequestParam(value="id")int id)
	 {
		 Student stu=stuRepo.getStudentById(id);
		 int rem=stu.getFeedue(); 
		 int nxt=fsr.getFeesById(stu.getClss());
		 nxt+=rem;
		 stu.setFeedue(nxt);
		 stuRepo.save(stu);
		 return "redirect:/admin/indstudent?id="+Integer.toString(stu.getStuId()); 
	 }
	 
//	 @PostMapping("/staff/indstudent")
//	 public ModelAndView indStudnt(@RequestParam("id") int id)
//	 {
//		 ModelAndView mv= new ModelAndView("indstudent");
//		 mv.addObject("st",stuRepo.getOne(id));
//		 return mv;
//	 }
}
