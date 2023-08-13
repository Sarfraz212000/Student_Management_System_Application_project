package in.saffu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class EnqStatusEntity {
	
	@Id
	@GeneratedValue
	private Integer status_id;
	private String status_name;

}
