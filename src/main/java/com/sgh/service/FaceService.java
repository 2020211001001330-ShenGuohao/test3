package com.sgh.service;

import com.sgh.pojo.Face;

import java.util.List;

/** 
 * <p>Title: FaceService.java</p> 
 * @author smallFish 
 * @version 1.0 
 * <p>功能描述: </p>  
 */
public interface FaceService {
	public int insertFact(Face face);
	public int deleteFact(int id);
	public int updateFact(Face face);
	public Face selectFaceById(int id);
	public List<Face> selectFactAll( );
}

 