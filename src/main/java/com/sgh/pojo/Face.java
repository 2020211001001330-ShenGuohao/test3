package com.sgh.pojo;
/** 
 * <p>Title: Face.java</p> 
 * @author smallFish 
 * @version 1.0 
 * <p>功能描述: </p>  
 */
public class Face {
	private Integer id;
	//年龄
	private Integer age;
	//颜值
	private Double beauty;
	//性别 male(男)、female(女)
	private String gender;
	//获取是否带眼睛  none:无眼镜，common:普通眼镜，sun:墨镜
	private String glasses;
	//获取是否微笑，none:不笑；smile:微笑；laugh:大笑
	private String expression;
	//头像保存的路径
	private String imgPath;

	public Face() {
	}

	public Face(Integer id, Integer age, Double beauty, String gender, String glasses, String expression, String imgPath) {
		this.id = id;
		this.age = age;
		this.beauty = beauty;
		this.gender = gender;
		this.glasses = glasses;
		this.expression = expression;
		this.imgPath = imgPath;
	}

	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Double getBeauty() {
		return beauty;
	}
	public void setBeauty(Double beauty) {
		this.beauty = beauty;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getGlasses() {
		return glasses;
	}
	public void setGlasses(String glasses) {
		this.glasses = glasses;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	@Override
	public String toString() {
		return "Face [id=" + id + ", age=" + age + ", beauty=" + beauty + ", gender=" + gender + ", glasses=" + glasses
				+ ", expression=" + expression + ", imgPath=" + imgPath + "]";
	}
	 
	
	
	
}

 