package in.saffu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.saffu.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
