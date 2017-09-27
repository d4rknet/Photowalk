package com.imbit.photowalk.backend.domain.repo;

import com.imbit.photowalk.backend.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}
