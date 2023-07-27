package com.tictoc.user.role;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	public RoleEntity findByCode(String Code) ;
	

}
