package com.toutiao.service;

import java.util.List;

import org.apache.ibatis.annotations.Lang;

import com.toutiao.domain.News;

public interface NewsService {

	public List<News> getAll();
	public List<News> getAllForTuijian();
	public List<News> getListByType(News news);
	public List<News> getListByBaiduSend(News news);
	public void updateBaiduSend(News news);
	public void insert(News news);
	public News getOne(int id);
	public News getOneByUrl(News news);
	public void updateCount(News news);
}
