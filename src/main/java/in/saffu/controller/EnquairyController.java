package in.saffu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.saffu.binding.DashbordForm;
import in.saffu.binding.EnquaryForm;
import in.saffu.binding.EnquarySearchCriteria;
import in.saffu.entity.StudentEnqEntity;
import in.saffu.service.EnquairyService;

@Controller
public class EnquairyController {

	@Autowired
	private HttpSession session;

	@Autowired
	private EnquairyService service;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		Integer userId = (Integer) session.getAttribute("userId");

		DashbordForm dashbordForm = service.getDashboardData(userId);

		model.addAttribute("dashbordForm", dashbordForm);
		return "dashboard";
	}

	@GetMapping("/add")
	public String addEnquiryPage(Model model) {

		EnquaryForm formobj = new EnquaryForm();
		model.addAttribute("course", service.getcourseName());
		model.addAttribute("status", service.getEnqStatus());
		model.addAttribute("form", formobj);

		return "add-enquiry";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("form") EnquaryForm form, Model model) {
		System.out.println(form);

		boolean saveEnquiry = service.saveEnquiry(form);

		if (saveEnquiry) {
			model.addAttribute("sucessMsg", "Enquiry Added");

		} else {
			model.addAttribute("errMsg", "problem occured");

		}

		return "add-enquiry";

	}

	public void initForm(Model model) {
		model.addAttribute("course", service.getcourseName());
		model.addAttribute("status", service.getEnqStatus());
		model.addAttribute("formObj", new EnquaryForm());
	}
	@GetMapping("/view")
	public String enquiryPage(Model model)
	{
		initForm(model);
		List<StudentEnqEntity> allStudentData = service.getAllStudentData();
		model.addAttribute("allStudentData", allStudentData);		
		return "view-enquiries";
	}
	
	@GetMapping("/filter-enquiries")
	public String getFileterEnq(@RequestParam String cname, @RequestParam String mode, @RequestParam String status,
			Model model) {
		EnquarySearchCriteria criteria = new EnquarySearchCriteria();
		criteria.setCourseame(cname);
		criteria.setClassMode(mode);
		criteria.setEnqstatus(status);

		System.out.println(criteria);

		Integer userId = (Integer) session.getAttribute("userId");
		List<StudentEnqEntity> filteredEnqs = service.getFilteredEnqs(criteria, userId);

		model.addAttribute("enquiries", filteredEnqs);
		return "view-enquiries-page";
	}

}
