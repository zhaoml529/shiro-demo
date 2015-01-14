package com.zml.oa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zml.oa.entity.Resource;
import com.zml.oa.service.IResourceService;

@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements
		IResourceService {

	@Override
	public Resource getPermissions(Integer id) throws Exception {
		return getBean(Resource.class, id);
	}

}
