package com.toutiao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.toutiao.domain.News;
@Mapper
public interface NewsMapper {
	
	public List<News> getAll();
	public List<News> getAllForTuijian();
	public void insert(News news);
	public News getOne(int id);
	public News getOneByUrl(News news);
	public List<News> getListByType(News news);
	public void updateCount(News news);
	public List<News> getListByBaiduSend(News news);
	public void updateBaiduSend(News news);
}
