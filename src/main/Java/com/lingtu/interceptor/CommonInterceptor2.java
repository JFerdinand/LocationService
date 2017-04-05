package com.lingtu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.Controller;

public class CommonInterceptor2 extends HandlerInterceptorAdapter
{
	protected static final Log log = org.apache.commons.logging.LogFactory
			.getLog(CommonInterceptor2.class);
	
	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception
	{

		log.info("==============执行顺序: 1、preHandle2================");
		
		  if(handler.getClass().isAssignableFrom(HandlerMethod.class))
		  {
	            functionId func = ((HandlerMethod) handler).getMethodAnnotation(functionId.class);
	            if(func.value().equals("2")){
	            	return true;
	            }
	            
//	            else if()
//	            log.info("functionid:"+func.value());
		  }
		  
//		  if(Controller.class.isAssignableFrom(handler.getClass()))
//		  {
//			  functionId func = ((Controller) handler).getClass().getAnnotation(functionId.class);
//			  if(func !=null)
//	            log.info("functionid:"+func.value());
//		  }
		  
//		 log.info("handler::"+handler.getClass());
		  
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());

		log.info("requestUri:" + requestUri);
		log.info("contextPath:" + contextPath);
		log.info("url:" + url);

//		 String username = (String)request.getSession().getAttribute("username");
//		 if(username == null){
//		 log.info("Interceptor：跳转到login页面！");
//		 response.sendRedirect("/webspring/login/login.do");
//		 request.getRequestDispatcher("/WEB-INF/jsp/loginlocation.jsp").forward(request,
//		 response);
//		 return false;
//		 }else
		return true;
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{	
		
		log.info("==============执行顺序: 2、postHandle2================");
		

	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception
	{
		log.info("==============执行顺序: 3、afterCompletion2================");
	}
}
