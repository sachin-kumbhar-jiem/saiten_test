package com.saiten.dao;

import java.util.List;

import com.saiten.model.MstDbInstance;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public interface MstDbInstanceDAO {

	List<MstDbInstance> findAll();

}
