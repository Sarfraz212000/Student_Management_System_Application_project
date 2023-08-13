package in.saffu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.saffu.entity.StudentEnqEntity;

public interface StudentEnqEntityRepo extends JpaRepository<StudentEnqEntity, Integer> {

}
