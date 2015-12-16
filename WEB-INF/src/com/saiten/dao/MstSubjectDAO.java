package com.saiten.dao;

import java.util.List;

/**
 * @author sachin
 * @version 1.0
 * @created 20-Feb-2013 4:59:11 PM
 */
public interface MstSubjectDAO {

	@SuppressWarnings("rawtypes")
	public List findSubjectNameList();
	
	@SuppressWarnings("rawtypes")
	public List findSubjectList();
}
