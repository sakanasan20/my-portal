package com.niq_dev.portal.config.oauth2;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingRefreshTokenUsageTracker {

	public void recordRefresh(String principalName, String registrationId) {
		log.info("Access token refreshed for user [{}] with client [{}]", principalName, registrationId);
	}

}
