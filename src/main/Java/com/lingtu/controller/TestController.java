package com.lingtu.controller;

import com.lingtu.util.BCrypt;
import com.lingtu.util.jsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by jiangmingjun on 17-3-16.
 */
@Controller
public class TestController {
    @RequestMapping("/test.do")
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response)
            throws ServletRequestBindingException,IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset-utf-8");
        request.setCharacterEncoding("utf-8");
        return new ModelAndView("WEB-INF/jsp/test.jsp");
    }

    @RequestMapping("pass.do")
    public ModelAndView pass(HttpServletRequest request,HttpServletResponse response)
        throws ServletRequestBindingException,IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset-utf-8");
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String pass = request.getParameter("sha512_Pass");
        String hashed = BCrypt.hashpw(pass,BCrypt.gensalt());
        PrintWriter out = response.getWriter();
        HashMap map = new HashMap();
        map.put("new_pass",hashed);
        session.setAttribute("hashed",hashed);
        String str = jsonUtil.toJson(map);
        out.print(str);
        return null ;

    }
    @RequestMapping("get_pass.do")
    public ModelAndView get_pass(HttpServletRequest request,HttpServletResponse response)
            throws ServletRequestBindingException,IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset-utf-8");
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String hashed = (String) session.getAttribute("hashed");
        Boolean isTrue = BCrypt.checkpw(request.getParameter("pass"),hashed);
        System.out.println(isTrue);
        return null;
    }
}
