package com.sgh.service;

import com.sgh.dao.FaceMapper;
import com.sgh.pojo.Face;

import java.util.List;

/** 
 * <p>Title: FaceServiceImpl.java</p> 
 * @author smallFish 
 * @version 1.0 
 * <p>功能描述: </p>  
 */

public class FaceServiceImpl implements FaceService{
	

	private FaceMapper faceMapper;

	public void setFaceMapper(FaceMapper faceMapper) {
		this.faceMapper = faceMapper;
	}

	@Override
	public int insertFact(Face face) {
		// TODO Auto-generated method stub
		return faceMapper.insertFace(face);
	}

	@Override
	public int deleteFact(int id) {
		// TODO Auto-generated method stub
		return faceMapper.deleteFace(id);
	}

	@Override
	public int updateFact(Face face) {
		// TODO Auto-generated method stub
		return faceMapper.updateFace(face);
	}

	@Override
	public Face selectFaceById(int id) {
		return faceMapper.selectFaceById(id);
	}


	@Override
	public List<Face> selectFactAll() {
		// TODO Auto-generated method stub
		return faceMapper.selectFaceAll();
	}


}

 