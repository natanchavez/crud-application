package com.project.management.repository;

import com.project.management.model.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository - This class provides the mechanism to perform all the CRUD operation on the jobTitle object.
 */
@Repository
public interface JobtitleRepository extends JpaRepository<JobTitle, Long> {
}
