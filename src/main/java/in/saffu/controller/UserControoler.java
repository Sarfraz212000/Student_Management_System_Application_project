 package in.saffu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.saffu.binding.LoginForm;
import in.saffu.binding.SingUpForm;
import in.saffu.binding.UnlockForm;
import in.saffu.service.UserService;

@Controller
public class UserControoler {

	@Autowired
	private UserService service;

	@GetMapping("/signup")
	public String singupPage(Model model) {
		model.addAttribute("user", new SingUpForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handelSingup(SingUpForm form, Model model) {
		Boolean status = service.signUp(form);
		if (status) {
			model.addAttribute("succMsg", "Account created,check your email");
		} else {
			model.addAttribute("errMsg", "choose unique email");
		}
		model.addAttribute("user", new SingUpForm());
		return "signup";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam("email") String email, Model model) {
		UnlockForm form = new UnlockForm();
		form.setEmail(email);
		model.addAttribute("unlock", form);
		return "unlock";
	}

	@PostMapping("/unlockAcc")
	public String unlockAcc(@ModelAttribute("unlock") UnlockForm unlock, Model model) {
		// System.out.println(unlock);

		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean status = service.unlockAccount(unlock);

			if (status) {
				model.addAttribute("sucessMsg", "you account unlocked sucessfully");
			} else {
				model.addAttribute("notScuess", "temppwd incorrect ,check your email");
			}
		} else {
			model.addAttribute("errorMsg", "please check new and confirm password is corect ya not?");
		}
		return "unlock";
	}
	
	@GetMapping("/login")
	public String loginPage(Model model)
	{
		model.addAttribute("loginForm", new LoginForm());
		return "login";
		
	}
	

	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm form, Model model) {

		String login = service.login(form);
		if (login.contains("success")) {

			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg", login);

		return "login";
	}
	@GetMapping("/forgot")
	public String forgotPage() {
		return "forgotpass";
	}

	
	@PostMapping("/forgotpwd")
	public String forgotpwd(@RequestParam("email") String email, Model model) {
		boolean status = service.forgotpwd(email);
		
		if (status) {
			model.addAttribute("succsMsg", "password sent to your mail");	
		}
		else {
			model.addAttribute("errMsg", "inavalid Email");
		}
		
		return "forgotpass";
	}

	
	
}
