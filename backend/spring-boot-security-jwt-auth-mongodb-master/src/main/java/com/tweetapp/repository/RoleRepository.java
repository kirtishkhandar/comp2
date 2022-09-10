package com.tweetapp.repository;

import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.tweetapp.models.ERole;
import com.tweetapp.models.Role;

@EnableScan
public interface RoleRepository extends CrudRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
