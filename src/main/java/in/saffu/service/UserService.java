package in.saffu.service;

import in.saffu.binding.LoginForm;
import in.saffu.binding.SingUpForm;
import in.saffu.binding.UnlockForm;

public interface UserService {

	public String login(LoginForm form);

	public Boolean signUp(SingUpForm form);

	public boolean unlockAccount(UnlockForm form);

	public boolean forgotpwd(String email);

}
