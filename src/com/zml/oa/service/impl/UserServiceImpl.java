/**
 * Project Name:SpringOA
 * File Name:UserServiceImpl.java
 * Package Name:com.zml.oa.service.impl
 * Date:2014-11-9上午12:53:20
 *
 */
package com.zml.oa.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zml.oa.entity.User;
import com.zml.oa.service.IUserService;
import com.zml.oa.util.BeanUtils;

/**
 * @ClassName: UserServiceImpl
 * @Description:user实现类，开启事务
 * @author: zml
 * @date: 2014-11-9 上午12:53:20
 *
 */

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    @Autowired
    private PasswordHelper passwordHelper;
    
	@Override
	public User getUserByName(String user_name) throws Exception{
		User user = getUnique("User", new String[]{"name"}, new String[] {user_name});
		if(BeanUtils.isBlank(user)){
			return null;
		}else{
			return user;
		}
	}

	//将查询到的数据缓存到oaCache中,并使用userList作为缓存的key  
	//通常更新操作只需刷新缓存中的某个值,所以为了准确的清除特定的缓存,故定义了这个唯一的key,从而不会影响其它缓存值  
	@Cacheable(value="oaCache", key="'userList'")
	@Override
	public List<User> getUserList_page() throws Exception{
		List<User> list = findByPage("User", new String[]{}, new String[]{});
		return list;
	}

	@Override
	public User getUserById(Integer id) throws Exception {
		return getUnique("User", new String[]{"id"}, new String[]{id.toString()});
	}

	//beforeInvocation = true 在方法执行前清空缓存，如果不加这个属性。很有可能在执行doAdd的时候出现异常，导致
	//缓存不能清空，所以在方法执行前清空缓存，就不会出现不能清空缓存这种情况了。
	@Override
	@CacheEvict(value = "oaCache", allEntries = true, beforeInvocation = true)
	public Serializable doAdd(User user) throws Exception {
        //加密密码
        passwordHelper.encryptPassword(user);
		return add(user);
	}

	@Override
	@CacheEvict(value="oaCache", key="'userList'")  
	public void doUpdate(User user) throws Exception {
		//pwd 为修改后的
		passwordHelper.encryptPassword(user);
		update(user);
	}

}
