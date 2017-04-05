package com.lingtu.tag;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class PrivilegeTag extends BodyTagSupport {

	private Integer target;
	
	
	@Override
	public int doEndTag() throws JspException {
		
		Integer code = getTarget();
		Object p = pageContext.getSession().getAttribute("privilegeid");
		if (p == null) {
			return SKIP_BODY;
		}
		HashSet<Integer> privilege = (HashSet<Integer>) p;
		//如果session中的privilege含有该权限，则显示其中的内容
		//否则跳过
		if (privilege.contains(code)) {
			try {
				pageContext.getOut().print(bodyContent.getString());
			} catch (IOException e) {
				e.printStackTrace();
			} 
			return EVAL_PAGE;
		}
		return SKIP_BODY;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}
	
}
