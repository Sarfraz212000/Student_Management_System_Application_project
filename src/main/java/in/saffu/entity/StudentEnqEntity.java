package in.saffu.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
public class StudentEnqEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enqid;
	
	private String name;
	
	private Long phno;
	
	private String classMode;
	private String course;
	private  String status;
	
	
	@CreationTimestamp
	private LocalDate created_date;
	
	@UpdateTimestamp
	private LocalDate update_date;
	
	@ManyToOne
	@JoinColumn(name="userid")
	private UserDtlsEntity user;

}
