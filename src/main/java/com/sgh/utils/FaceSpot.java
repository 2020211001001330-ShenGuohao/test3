package com.sgh.utils;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.FaceVerifyRequest;
import com.sgh.pojo.Face;
import com.sgh.pojo.FaceV3DetectBean;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class FaceSpot {
	/*
	 * 在你创建完毕应用后，平台将会分配给您此应用的相关凭证：API Key、Secret Key。
	 * 使用秘钥将可以在下一步中获取调用接口所需的Access Token。
	 */
	private static final String AppID = "28026845";
	private static final String APIKey = "rbvlZ35UpeaYXrcH3be3ggOX";
	private static final String SecretKey = "yVwoi34LXq5uDQDT9Zv6vUMTxGzheoeA";

	static AipFace client = null;
	static {
		client = new AipFace(AppID, APIKey, SecretKey);
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
	}

	public static void main(String[] args) throws IOException {
		String filePath = "D:\\pic\\k.png";

		byte[] imgData = FileToByte(new File(filePath));

//		System.out.println(detectFace(imgData,"1"));

//		String filePath1 = "F:/3.jpg";
//		String filePath2 = "F:/7.jpg";
//		byte[] imgData1 = FileUtil.readFileByBytes(filePath1);
//		byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
//		System.out.println(faceverify(imgData1));



		//调用人脸检测的方法
		String str = FaceSpot.detectFace(imgData, "1");

		//将字符串转换为json对象
		JSON json = JSON.parseObject(str);
		FaceV3DetectBean bean = JSON.toJavaObject(json, FaceV3DetectBean.class);

		//将需要的人脸检测数据封存入Face对象中
		Face face = new Face();

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
//				face.setImgPath("http://localhost:8080/tanghulu/"+fileName);
			}

		}else {
			System.out.println("要确保网络和你的百度人脸检测应用ID是正常的额~~");
		}
		System.out.println(face);
	}

	/**
	 * 人脸检测
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String detectFace(File file, String max_face_num) {
		try {
			return detectFace(FileToByte(file), max_face_num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 人脸检测
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String detectFace(byte[] arg0, String max_face_num) {
		try {

			HashMap<String, String> options = new HashMap<String, String>();
			options.put("face_field", "age,beauty,expression,faceshape,gender,glasses,race,qualities");
			options.put("max_face_num", max_face_num);
			//LIVE表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等
			options.put("face_type", "LIVE");

			// 图片数据
			String imgStr = Base64Util.encode(arg0);
			String imageType = "BASE64";
			JSONObject res = client.detect(imgStr, imageType, options);
//			System.out.println(res.toString(2));
			return res.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
   
	/**
	 * 活体检测
	 * @param arg0
	 * @return
	 */
	public static String faceverify(byte[] arg0){
		String imgStr = Base64Util.encode(arg0);
        String imageType = "BASE64";
        FaceVerifyRequest req = new FaceVerifyRequest(imgStr, imageType);
        ArrayList<FaceVerifyRequest> list = new ArrayList<FaceVerifyRequest>();
        list.add(req);
        JSONObject res = client.faceverify(list);
        return res.toString();
	}
	
	/**
	 *<p>Title: FileToByte</p> 
	 *<p>功能描述: 测试使用</p> 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static byte[] FileToByte(File file) throws IOException {
		// 将数据转为流
		InputStream content = new FileInputStream(file);
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = content.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		// 获得二进制数组
		return swapStream.toByteArray();
	}
	/**
	 *<p>Title: FileToByte</p> 
	 *<p>功能描述: 将上传的文件转换为字节流</p> 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] FileToByte(MultipartFile file){
		 
		try {
			// 将数据转为流
			InputStream content = file.getInputStream();
		 
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = content.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			// 获得二进制数组
			return swapStream.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *<p>Title: saveFile</p> 
	 *<p>功能描述:将用户上传的文件保存至本地指定目录 </p>
	 */
	public static String saveFile(MultipartFile file,String savePath) {
		if (file!=null) {
			//获取原始图片的拓展名
	        String originalFilename = file.getOriginalFilename();
	        //新的文件名字
	        String newFileName = UUID.randomUUID()+"_"+originalFilename;
	        //封装上传文件位置的全路径
	        File targetFile = new File(savePath,newFileName);



	        String savePath2="D:\\code\\test3\\src\\main\\webapp\\images\\up";
			File tarageFile_rel=new File(savePath2,newFileName);
	        try {
	        	//把本地文件上传到封装上传文件位置的全路径
				file.transferTo(targetFile);
				file.transferTo(tarageFile_rel);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        return newFileName;
		}
		 
		return null;
	}
}
