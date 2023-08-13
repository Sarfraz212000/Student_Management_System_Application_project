package in.saffu.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.saffu.binding.LoginForm;
import in.saffu.binding.SingUpForm;
import in.saffu.binding.UnlockForm;
import in.saffu.entity.UserDtlsEntity;
import in.saffu.repository.UserDtlsRepo;
import in.saffu.util.EmailUtil;
import in.saffu.util.PasswordUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepo repo;

	@Autowired
	EmailUtil util;
	
	@Autowired
	private  HttpSession session;

	@Override
	public Boolean signUp(SingUpForm form) {

		UserDtlsEntity user = repo.findByEmail(form.getEmail());
		if (user != null) {

			return false;
		}

		// copy data obj from binding to entity

		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);

		// genarate random password and set the object

		String tempPwd = PasswordUtil.genrateRandomPassword();
		entity.setPwd(tempPwd);

		// set account statsu object

		entity.setAcc_status("locked");

		// insert the record

		repo.save(entity);

		// send the email

		String to = form.getEmail();
		String subject = "unlock your accounts";

		StringBuffer body = new StringBuffer("");
		body.append("<h1>Use below tempory password to unlock your accounts</h1>");
		body.append("temprory pwd:" + tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:9090/unlock?email=" + to + "\">Click here to unlock your Account</a>");

		util.sendEmail(to, subject, body.toString());

		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {

		String email = form.getEmail();
		UserDtlsEntity entity = repo.findByEmail(form.getEmail());

		if (entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAcc_status("unlocked");
			repo.save(entity);
			return true;

		} else {
			return false;
		}

	}

	@Override
	public String login(LoginForm form) {

		UserDtlsEntity entity = repo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		if (entity == null) {
			return "invalid cridentials";

		}
		if (entity.getAcc_status().equals("locked")) {
			return "your account locked";
		}
		//create session and store data in session
		
		session.setAttribute("userId", entity.getUserid());

		return "success";

	}

	@Override
	public boolean forgotpwd(String email) {

		UserDtlsEntity entity = repo.findByEmail(email);

		if (entity == null) {
			return false;
		}

		String subject = "recover password";
		String body = "your pass::" + entity.getPwd();

		util.sendEmail(email, subject, body);

		return true;
	}

}
