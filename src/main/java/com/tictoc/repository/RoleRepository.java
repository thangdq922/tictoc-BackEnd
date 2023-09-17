package com.tictoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tictoc.entity.RoleEntity;


public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	public RoleEntity findByCode(String Code) ;
	

}
