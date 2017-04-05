package com.lingtu.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.lingtu.interceptor.functionId;
import com.lingtu.util.Fileupload;
@Controller
public class DownloadController extends MultiActionController {
	@functionId("1")
	@RequestMapping("/download.do")
	public ModelAndView showmain(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException, IOException {
		String storeName = "/lingtu.apk";
		String realName = "lingtu.apk";
		String contentType = "application/octet-stream";
        try {
			Fileupload.download(request, response, storeName, contentType,  
			        realName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
}
