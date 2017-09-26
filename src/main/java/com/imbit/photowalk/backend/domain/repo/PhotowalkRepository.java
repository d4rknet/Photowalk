package com.imbit.photowalk.backend.domain.repo;

import com.imbit.photowalk.backend.domain.entity.Photowalk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotowalkRepository extends JpaRepository<Photowalk, Integer> {
}
