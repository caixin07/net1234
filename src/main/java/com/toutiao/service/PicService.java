package com.toutiao.service;

import java.util.List;

import org.apache.ibatis.annotations.Lang;

import com.toutiao.domain.Image;
import com.toutiao.domain.News;
import com.toutiao.domain.Pic;

public interface PicService {
	public void insert(Pic pic);
	public Pic getOneByUrl(Pic pic);
	public List<Pic> getPicList();
	public List<Pic> getChildPicList(Pic pic);
}
