package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.models.Borrower;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    // Additional query methods if needed
}
