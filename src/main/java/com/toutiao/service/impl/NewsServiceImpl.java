package com.toutiao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.toutiao.domain.News;
import com.toutiao.mapper.NewsMapper;
import com.toutiao.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {
	
	@Autowired
	private NewsMapper newsMapper;

	@Override
	public List<News> getAll() {
		List<News> list = newsMapper.getAll();
		return list;
	}

	@Override
	public void insert(News news) {
		newsMapper.insert(news);		
	}

	@Override
	public News getOne(int id) {
		// TODO Auto-generated method stub
		return newsMapper.getOne(id);
	}

	@Override
	public News getOneByUrl(News news) {
		return newsMapper.getOneByUrl(news);
	}

	@Override
	public void updateCount(News news) {
		newsMapper.updateCount(news);		
	}

	@Override
	public List<News> getAllForTuijian() {
		List<News> list = newsMapper.getAllForTuijian();
		return list;
	}

	@Override
	public List<News> getListByType(News news) {
		return newsMapper.getListByType(news);
	}

	@Override
	public List<News> getListByBaiduSend(News news) {
		return newsMapper.getListByBaiduSend(news);
	}

	@Override
	public void updateBaiduSend(News news) {
		newsMapper.updateBaiduSend(news);
	}

}
