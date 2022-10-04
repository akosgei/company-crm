package com.company.crm.review.repository;

import com.company.crm.review.entity.DuplicateThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuplicateThreadRepository extends JpaRepository<DuplicateThread,Long> {
}

