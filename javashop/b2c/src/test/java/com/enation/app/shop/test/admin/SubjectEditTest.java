package com.enation.app.shop.test.admin;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.enation.framework.test.SpringTestSupport;

/** 
 * 专题管理--专题编辑    单元测试类
 * @author  LYH
 * @version 1.0
 * @since 6.2
 * @date 2016年12月11日 下午10:59:56  
 */

public class SubjectEditTest extends SpringTestSupport{

	/**
	 * 登陆--添加专题--编辑专题
	 * @throws Exception 
	 */
  @Test
  public void subjectEditTest() throws Exception{
	
	//管理员登陆
	this.login();
	
	//模拟图片上传
    MockMultipartFile file = new MockMultipartFile("file", "face.jpg","multipart/form-data","some pic".getBytes());
	
    //添加专题
    mockMvc.perform(
		 MockMvcRequestBuilders.fileUpload("/core/admin/subject/save-add.do?ajax=yes").file(file)
		 .param("title", "添加专题管理")
		 .param("sort", "1")
		 .param("is_display", "0")
		 .session(session)
		 )
	 	.andDo(MockMvcResultHandlers.print())  
	 	.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1) ); 
    
    //编辑专题
    mockMvc.perform(
   		 MockMvcRequestBuilders.fileUpload("/core/admin/subject/save-edit.do?ajax=yes").file(file)
   		 .param("id", "1")
   		 .param("title", "编辑专题管理")
   		 .param("sort", "2")
   		 .param("is_display", "1")
   		 .session(session)
   		 )
   	 	.andDo(MockMvcResultHandlers.print())  
   	 	.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1) ); 
	}
	
	/**
	 * 共用的登陆方法
	 * @throws Exception
	 */
	private void login() throws Exception{

	//执行验证码请求		
	mockMvc.perform(MockMvcRequestBuilders.get("/api/validcode/create.do?vtype=admin"));
			
	//管理员登陆api测试
	mockMvc.perform(
		 MockMvcRequestBuilders.post("/core/admin/admin-user/login.do")
		 .param("username", "admin")
		 .param("password", "admin")
		 .param("valid_code", "1111")
		 .contentType(MediaType.APPLICATION_JSON)
		 .accept(MediaType.APPLICATION_JSON)
		 )
	 	.andDo(MockMvcResultHandlers.print())  
	 	.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1) ); 
	}
}
