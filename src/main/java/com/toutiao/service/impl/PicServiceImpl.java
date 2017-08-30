package com.toutiao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toutiao.domain.Pic;
import com.toutiao.mapper.PicMapper;
import com.toutiao.service.PicService;
@Service
public class PicServiceImpl implements PicService {
	@Autowired
	private PicMapper picMapper;
	@Override
	public void insert(Pic pic) {		
		picMapper.insert(pic);
	}
	@Override
	public Pic getOneByUrl(Pic pic) {
		return picMapper.getOneByUrl(pic);
	}

}
