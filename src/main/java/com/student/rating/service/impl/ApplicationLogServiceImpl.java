package com.student.rating.service.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.student.rating.entity.log.ApplicationLog;
import com.student.rating.entity.log.LogType;
import com.student.rating.repository.ApplicationLogRepository;
import com.student.rating.service.ApplicationLogService;

@Service
public class ApplicationLogServiceImpl implements ApplicationLogService {
	private static final Logger LOG = LoggerFactory.getLogger(ApplicationLogServiceImpl.class);

	private final ApplicationLogRepository applicationLogRepository;


	@Autowired
	public ApplicationLogServiceImpl(ApplicationLogRepository applicationLogRepository) {
		this.applicationLogRepository = applicationLogRepository;
	}

	@Override
	public Page<ApplicationLog> getApplicationLogs(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
		return null;
	}

	@Override
	public void saveSystemLog(ApplicationLog applicationLog) {
		LOG.debug("Saving app log [{}]", applicationLog);
		applicationLogRepository.save(applicationLog);
	}

	@Override
	public void deleteLogById(String logId) {

	}

	@Override
	public void addSystemLogSuccess(String userId, LogType logType, String description, boolean addIpAddress) {

	}

	@Override
	public void addSystemLogFailure(String userId, LogType logType, String description, boolean addIpAddress) {

	}

	@Override
	public ApplicationLog getLastUserSuccessfulLogin(String username) {
		return null;
	}

	@Override
	public List<ApplicationLog> getLastUserLoginFailuresFromData(String login, LocalDateTime fromDate) {
		return null;
	}
}
