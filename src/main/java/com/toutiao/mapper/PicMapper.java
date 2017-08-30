package com.toutiao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.toutiao.domain.Image;
import com.toutiao.domain.News;
import com.toutiao.domain.Pic;
@Mapper
public interface PicMapper {
	public void insert(Pic pic);
	public Pic getOneByUrl(Pic pic);
}
