package com.toutiao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.toutiao.domain.Image;
import com.toutiao.domain.News;
@Mapper
public interface ImageMapper {
	public void insert(Image image);
	public List<Image> getImageByNews(Image image);
}
