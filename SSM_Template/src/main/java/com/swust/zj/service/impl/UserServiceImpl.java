package com.swust.zj.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.swust.zj.cache.JedisClient;
import com.swust.zj.mapper.TbUserMapper;
import com.swust.zj.pojo.TbUser;
import com.swust.zj.pojo.TbUserExample;
import com.swust.zj.service.UserService;
import com.swust.zj.util.PageBean;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Override
	public void add(TbUser user) {
		// TODO Auto-generated method stub
		userMapper.insert(user);
	}
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		userMapper.deleteByPrimaryKey(id);
	}
	@Override
	public void update(TbUser user) {
		// TODO Auto-generated method stub
		userMapper.updateByPrimaryKey(user);
	}
	@Override
	public TbUser get(int id) {
		// TODO Auto-generated method stub
		TbUser result = userMapper.selectByPrimaryKey(id);
		return result;
	}
	@Override
	public PageBean<TbUser> getPage(int page, int rows) {
		// TODO Auto-generated method stub
		TbUserExample example = new TbUserExample();
		PageHelper.startPage(page, rows);
		List<TbUser> result = userMapper.selectByExample(example);
		PageBean<TbUser> pageBean = new PageBean<>();
		pageBean.setCurrentPage(page);
		pageBean.setPageSize(rows);
		pageBean.setTotalRecord((int) new PageInfo<TbUser>(result).getTotal());
		pageBean.setBeanList(result);
		return pageBean;
	}
	@Override
	public List<TbUser> getAll() {
		// TODO Auto-generated method stub
		TbUserExample example = new TbUserExample();
		List<TbUser> result = userMapper.selectByExample(example);
		return result;
	}
	@Override
	public void updateByService() {
		// TODO Auto-generated method stub
		TbUser user = new TbUser();
		user.setId(1);
		user.setName("yangmiao1");
		userMapper.updateByPrimaryKey(user);
		if(true){
			throw new RuntimeException();
		}
		user.setName("yangmiao2");
		userMapper.updateByPrimaryKey(user);
	}
	@Override
	public String getName(int id) {
		// TODO Auto-generated method stub
		String value = null;
		String key = "name_key:"+id;
		int seconds = 86400;
		System.out.println(1);
		try {
			value = jedisClient.get(key);
			if(!StringUtils.isBlank(value)) {
				return value;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(2);
		//模拟耗时
		try {
			Thread.sleep(5000);
		}catch (Exception e) {
			e.printStackTrace();
		}
		TbUser result = userMapper.selectByPrimaryKey(id);
		value = result.getName();
		try {
			jedisClient.set(key, value);
			jedisClient.expire(key, seconds);
			System.out.println(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}
