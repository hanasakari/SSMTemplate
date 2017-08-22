package com.swust.zj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.swust.zj.pojo.TbUser;
import com.swust.zj.service.UserService;
import com.swust.zj.util.IDUtils;
import com.swust.zj.util.PageBean;
import com.swust.zj.util.Result;

@Controller
public class UserController {
	@Autowired
	private UserService service;
	@RequestMapping("/test")
	@ResponseBody
	public Object test(){
		return Result.success();
	}
	@RequestMapping(value="/user",method=RequestMethod.POST)
	@ResponseBody
	public Object add(TbUser user){
		service.add(user);
		return Result.success("添加成功！");
	}
	@RequestMapping(value="/user",method=RequestMethod.DELETE)
	@ResponseBody
	public Object delete(@PathVariable(name="id") int id){
		service.delete(id);
		return Result.success("删除成功！");
	}
	@RequestMapping(value="/user",method=RequestMethod.PUT)
	@ResponseBody
	public Object update(TbUser user){
		service.update(user);
		return Result.success("修改成功！");
	}
	@RequestMapping(value="/user/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Object get(@PathVariable int id){
		TbUser result = service.get(id);
		return Result.success(result);
	}
	@RequestMapping(value="/users/page",method=RequestMethod.GET)
	@ResponseBody
	public Object getPage(int page,int rows){
		PageBean<TbUser> result = service.getPage(page,rows);
		result.setUrl("/users/page");
		return Result.success(result);
	}
	@RequestMapping(value="/users",method=RequestMethod.GET)
	@ResponseBody
	public Object getAll(){
		List<TbUser> result = service.getAll();
		return Result.success(result);
	}
	@RequestMapping("user/transaction")
	@ResponseBody
	public Object transaction(){
		service.updateByService();
		return Result.success("事务成功！");
	}
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public Object upload(MultipartFile file,HttpServletRequest request){
		try {
			String dirPath = request.getSession().getServletContext().getRealPath("files");
			String datePath = new DateTime().toString("/yyyy/MM/dd");
			String path = dirPath + datePath;
			String fileName = file.getOriginalFilename();
			String randomName = IDUtils.genString();
			String name = randomName + "_" + fileName;
			File dir = new File(path, fileName);
			if(!dir.exists()){
				dir.mkdirs();
			}
			file.transferTo(dir);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Result.failure("上传失败！");
		}
		return Result.success("上传成功！");
	}
	@RequestMapping("/download")
	@ResponseBody
	public Object download(HttpServletRequest request, HttpServletResponse response){
		try {
			String dirPath = request.getSession().getServletContext().getRealPath("files");
			String datePath = "/2017/07/29";
			String path = dirPath + datePath;
			String fileName = "me.png";
			File file = new File(path, fileName);
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setContentType("multipart/form-data");
			try(InputStream inputStream = new FileInputStream(file);
					OutputStream outputStream = response.getOutputStream();){
				byte[] bytes = new byte[1024];
				int length = -1;
				while((length = inputStream.read(bytes)) != -1){
					outputStream.write(bytes, 0, length);
				}
				outputStream.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Result.success("下载失败！");
		}
		return Result.success("下载成功！");
	}
	@RequestMapping(value="/user/{id}/name",method=RequestMethod.GET)
	@ResponseBody
	public Object redis(@PathVariable(name="id") int id){
		String data = service.getName(id);
		return Result.success(data);
	}
}
