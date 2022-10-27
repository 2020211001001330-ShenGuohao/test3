package com.sgh.controller;

import com.alibaba.fastjson.JSON;
import com.sgh.pojo.Face;
import com.sgh.pojo.FaceV3DetectBean;
import com.sgh.service.FaceService;
import com.sgh.utils.FaceSpot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * Title: FaceDemo.java
 * </p>
 * 
 * @author smallFish
 * @version 1.0
 *          <p>
 *          功能描述:
 *          </p>
 */
@Controller
@RequestMapping
public class FaceDemo {
	@Autowired
	private FaceService faceService;

	@RequestMapping("addpic")
	public String add(){
		return "forward:add.jsp";
	}
	/**
	 * 颜值排行
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/query.action")
	public ModelAndView queryVoi() throws Exception {
		
		//调用查询的服务
		List<Face> list = faceService.selectFactAll();
		
		ModelAndView modelAndView = new ModelAndView();
					modelAndView.addObject("faces",list);
					modelAndView.setViewName("index.jsp");
		return modelAndView;
	}
	
	
	/**
	 * 请求人脸检测
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save.action")
	public String saveService(MultipartFile file) {
		//将用户上传图片保存至本地
		String fileName = FaceSpot.saveFile(file
		, "D:\\JSP\\apache-tomcat-8.5.75\\webapps\\test3_war\\images\\up");
		
		if (fileName!=null) {
				
			//将上传的照片转为二进制的数据流
	        byte[] byface = FaceSpot.FileToByte(file);
	        
	        //调用人脸检测的方法
			String str = FaceSpot.detectFace(byface, "1");
			
			//将字符串转换为json对象
			JSON json = JSON.parseObject(str);
			FaceV3DetectBean bean = JSON.toJavaObject(json, FaceV3DetectBean.class);
			
			//将需要的人脸检测数据封存入Face对象中
			Face face = new Face();
			String imgpath="http://localhost:8080/test3_war/images/up/"+fileName;
			if (bean!=null) {
				for (int i = 0; i < bean.getResult().getFace_list().size(); i++) {
					// 获取年龄
					int ageOne = bean.getResult().getFace_list().get(i).getAge();
					face.setAge(ageOne);
					
					// 获取美丑打分
					Double beautyOne = (Double) bean.getResult().getFace_list().get(i).getBeauty();
					face.setBeauty(beautyOne);
					
					// 获取性别 male(男)、female(女)
					String gender = bean.getResult().getFace_list().get(i).getGender().getType();
					face.setGender(gender);
					
					// 获取是否带眼睛 none:无眼镜，common:普通眼镜，sun:墨镜
					String glasses = bean.getResult().getFace_list().get(i).getGlasses().getType();
					face.setGlasses(glasses);
					// 获取是否微笑，none:不笑；smile:微笑；laugh:大笑
					String expression = bean.getResult().getFace_list().get(i).getExpression().getType();
					face.setExpression(expression);
					
					//保存用户头像的对外访问地址在数据库中
					face.setImgPath(imgpath);
				}
				faceService.insertFact(face);
			}else {
				System.out.println("要确保网络和你的百度人脸检测应用ID是正常的额~~");
			}
		}else {
			System.out.println("请上传一张帅照额~~~~");
		}
		return "redirect:/query.action";
	}

	@RequestMapping("delete.action")
	public String delete(int id){

		faceService.deleteFact(id);
		return "redirect:/query.action";
	}




	/**
	 *<p>Title: checkFace</p> 
	 *<p>功能描述: 测试。。。</p> 
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/test.action")
	public ModelAndView checkFace(@RequestParam("files") 
											MultipartFile []files) {
		//存储人脸检测数据的List和Map集合
		List<Map<String, Object>> list = null;
		Map<String, Object> map = null;

		 //判断file数组不能为空并且长度大于0  
        if(files!=null&&files.length>0){  
        	list = new ArrayList<Map<String, Object>>();
        	
            //循环获取file数组中得文件  
            for(int i = 0;i<files.length;i++){  
            	map = new HashMap<String, Object>();
            	
                MultipartFile file = files[i];  
                //将上传的照片转为二进制的数据流
                byte[] byface = FaceSpot.FileToByte(file);
                
                //调用人脸检测的方法
    			String str = FaceSpot.detectFace(byface, "1");
    			
    			//将字符串转换为json对象
    			JSON json = JSON.parseObject(str);
    			
    			
    			//将json的属性值封存在FaceV3DetectBean对象中
    			FaceV3DetectBean bean = JSON.toJavaObject(json, FaceV3DetectBean.class);
    			
    			for (int j = 0; j < bean.getResult().getFace_list().size(); j++) {
    				// 获取年龄
    				int ageOne = bean.getResult().getFace_list().get(j).getAge();
    				// 处理年龄
    				String age = String.valueOf(new BigDecimal(ageOne).setScale(0, BigDecimal.ROUND_HALF_UP));
    				map.put("age", age);

    				// 获取美丑打分
    				Double beautyOne = (Double) bean.getResult().getFace_list().get(j).getBeauty();
    				// 处理美丑打分
    				String beauty = String.valueOf(new BigDecimal(beautyOne).setScale(0, BigDecimal.ROUND_HALF_UP));
    				map.put("beauty", beauty);

    				// 获取性别 male(男)、female(女)
    				String gender = (String) bean.getResult().getFace_list().get(j).getGender().getType();
    				map.put("gender", gender);

    				// 获取是否带眼睛 0-无眼镜，1-普通眼镜，2-墨镜
    				String glasses = bean.getResult().getFace_list().get(j).getGlasses().getType();
    				map.put("glasses", String.valueOf(glasses));

    				// 获取是否微笑，0，不笑；1，微笑；2，大笑
    				String expression = bean.getResult().getFace_list().get(j).getExpression().getType();
    				map.put("expression", String.valueOf(expression));
    				
    			}
    			
    			//将需要返回至页面的数据存储在List集合中
    			list.add(map);
    			
            }  
        }  
		System.out.println(list);
		ModelAndView modelAndView = new ModelAndView("index.jsp");
		modelAndView.addObject("list",list);
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/getFaceById",produces = {"application/json;charset=UTF-8"})
	public Face getFaceById(int id){

		return faceService.selectFaceById(id);

	}


	@ResponseBody
	@PostMapping(value = "/update.action")
	public int update(Face face){
		System.out.println("POST:"+face);

		return  faceService.updateFact(face);
	}

}
