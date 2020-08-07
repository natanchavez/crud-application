package com.project.management.service;

import com.project.management.model.JobTitle;
import com.project.management.repository.JobtitleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service - This class contains the business logic needed to perform most operations used on this application.
 */
@Service
public class JobTitleServiceImpl implements JobTitleService {
	
	final JobtitleRepository jobtitleRepository;
	
	public JobTitleServiceImpl(JobtitleRepository jobtitleRepository) {
		this.jobtitleRepository = jobtitleRepository;
	}
	
	/**
	 * Method used to find all the entries on the jobtitle table.
	 *
	 * @return A list with all the entries on the jobtitle table.
	 */
	@Override
	public List<JobTitle> getAllTitles() {
		return jobtitleRepository.findAll();
	}
}
