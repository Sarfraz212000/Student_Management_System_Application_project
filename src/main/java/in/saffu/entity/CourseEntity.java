package in.saffu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CourseEntity {
	
	@Id
	@GeneratedValue
	private Integer courseid;
	private String coursename;

}
