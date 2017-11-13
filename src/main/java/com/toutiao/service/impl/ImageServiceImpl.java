package com.toutiao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toutiao.domain.Image;
import com.toutiao.domain.News;
import com.toutiao.mapper.ImageMapper;
import com.toutiao.mapper.NewsMapper;
import com.toutiao.service.ImageService;
@Service
public class ImageServiceImpl implements ImageService {
	@Autowired
	private ImageMapper imageMapper;
	@Override
	public void insert(Image image) {		
		imageMapper.insert(image);
	}
	@Override
	public List<Image> getImageByNews(Image image) {
		// TODO Auto-generated method stub
		return imageMapper.getImageByNews(image);
	}

}
