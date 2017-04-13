package com.lingtu.util;

import java.io.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


public class Fileupload {
	public static void downLoadFile(HttpServletResponse response, File file) { 
		if (file == null || !file.exists()) 
		{ return; } 
		OutputStream out = null; 
		try { 
			response.reset(); 
			response.setContentType("application/octet-stream; charset=utf-8"); 
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName()); 
			out = response.getOutputStream(); 
			out.write(FileUtils.readFileToByteArray(file)); 
			out.flush(); 
			} catch (IOException e) 
		{ 
				e.printStackTrace(); 
				} finally { 
					if (out != null) { 
						try { 
							out.close(); 
						} catch (IOException e) { 
							e.printStackTrace(); 
							} 
						} 
					}
	}
	
    public static void download(HttpServletRequest request,  
            HttpServletResponse response, String storeName, String contentType,  
            String realName) throws Exception {  
        response.setContentType("text/html;charset=UTF-8");  
        request.setCharacterEncoding("UTF-8");
		BufferedReader bis = null;
		BufferedWriter bos = null;
		String ctxpath = request.getSession().getServletContext().getRealPath("/source");
		String downLoadPath = ctxpath+storeName;

		long fileLength = new File(downLoadPath).length();

		response.setContentType(contentType);
		response.setHeader("Content-disposition","attachment;filename="
				+ new String(realName.getBytes("utf-8"),"ISO-8859-1"));
		response.setHeader("Content-Length",String.valueOf(fileLength));

		bis = new BufferedReader(new FileReader(downLoadPath));
		bos = new BufferedWriter(response.getWriter());
		String b;
		while ((b = bis.readLine()) != null) {
			bos.write(b);
		}
		bos.flush();
		bis.close();
		bos.close();
    }  




}
