package com.cespin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cespin.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
	

}
