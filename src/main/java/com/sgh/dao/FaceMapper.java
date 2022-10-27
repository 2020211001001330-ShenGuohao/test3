package com.sgh.dao;

import com.sgh.pojo.Face;

import java.util.List;

public interface FaceMapper {
    public int insertFace(Face face );
    public int deleteFace(int id);
    public int updateFace(Face face);

    public List<Face> selectFaceAll();
    public Face selectFaceById(int id);


}
