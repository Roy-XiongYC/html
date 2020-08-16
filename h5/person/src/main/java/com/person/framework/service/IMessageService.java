package com.person.framework.service;

public interface IMessageService {

	public String message(String key, Object... params);

	public void loadAll();

	public void loadProperties(String fileName);

}
