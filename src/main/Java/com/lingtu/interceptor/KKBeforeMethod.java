package com.lingtu.interceptor;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;


@Component
public class KKBeforeMethod implements MethodBeforeAdvice
{

	@Override
	public void before(Method arg0, Object[] arg1, Object arg2)
			throws Throwable
	{
		// TODO Auto-generated method stub
		System.out.println ("调用了 "+arg0.getName());
	}

}
