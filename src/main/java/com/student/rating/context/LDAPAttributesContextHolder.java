package com.student.rating.context;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.Attributes;


public class LDAPAttributesContextHolder {

	private List<Attributes> threadLocalScope = new ArrayList<>();

	public List<Attributes> getAttributes() {
		return this.threadLocalScope;
	}

	public void setAttributes(List<Attributes> attributes) {
		this.threadLocalScope = attributes;
	}

	public void destroy() {
		this.threadLocalScope.clear();
	}
}
