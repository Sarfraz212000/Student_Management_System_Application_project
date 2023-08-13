package in.saffu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.saffu.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer>{

}
