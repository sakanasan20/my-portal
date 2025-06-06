package com.niq_dev.portal.service.common;

public enum ApiPaths {
	SYSTEMS("/api/v1/systems"), 
	USERS("/api/v1/users"), 
	ROLES("/api/v1/roles"), 
	AUTHORITIES("/api/v1/authorities"), 
	LICENSES("/api/v1/licenses");

	private final String path;

	ApiPaths(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
