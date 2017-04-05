package com.lingtu.service;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;

import com.lingtu.dao.BaseDaoImpl;

public abstract class BaseService
{
	protected static final Log log = org.apache.commons.logging.LogFactory.getLog(BaseService.class);
	
	protected BaseDaoImpl dao;

	@Resource(name="baseDao")
	public void setDao(BaseDaoImpl dao) 
	{

		this.dao = dao;
	}
	
}
