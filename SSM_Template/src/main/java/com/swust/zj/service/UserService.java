package com.swust.zj.service;

import java.util.List;

import com.swust.zj.pojo.TbUser;
import com.swust.zj.util.PageBean;

public interface UserService {
	public void add(TbUser user);

	public void delete(int id);

	public void update(TbUser user);

	public TbUser get(int id);

	public PageBean<TbUser> getPage(int page, int rows);

	public List<TbUser> getAll();

	public void updateByService();
	
	public String getName(int id);
}
