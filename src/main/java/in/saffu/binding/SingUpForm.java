package in.saffu.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SingUpForm {
	
	@NotBlank(message="Name is mandatory")
	private  String name;
	@Email
	@NotBlank(message="email is mandatory")
	private String email;
	@NotNull(message="MobNo is mandatory")
	private Long phno;

}
