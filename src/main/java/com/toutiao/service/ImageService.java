package com.toutiao.service;

import java.util.List;

import org.apache.ibatis.annotations.Lang;

import com.toutiao.domain.Image;
import com.toutiao.domain.News;

public interface ImageService {
	public void insert(Image image);
}
