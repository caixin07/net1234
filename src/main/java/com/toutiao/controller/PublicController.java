package com.toutiao.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.github.pagehelper.PageHelper;
import com.toutiao.domain.Image;
import com.toutiao.domain.News;
import com.toutiao.domain.Pic;
import com.toutiao.service.ImageService;
import com.toutiao.service.NewsService;
import com.toutiao.service.PicService;
import com.toutiao.util.Constants;
import com.toutiao.util.HTMLSpirit;

@Service
public class PublicController {
	@Autowired
	private NewsService newsService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private PicService picService;
	public void pub(ModelMap map, HttpServletRequest request,String type) throws IOException {
		int pageNum = 1;
		PageHelper.startPage(pageNum, 4);
		List<News> list = newsService.getAllForTuijian();
		for (News news : list) {
			news.setText(HTMLSpirit.delHTMLTag(news.getText()));
			news.setHref(request.getAttribute("basePath") + "/id/" + news.getId());
			
			//获取图片
			Image image = new Image();
			image.setNews_id(news.getId());
			List<Image> images = imageService.getImageByNews(image);
			news.setImages(images);
		}
		PageHelper.startPage(pageNum, 6);
		List<Pic> piclist = picService.getPicListRandom();
		
		News temp = new News();
		temp.setType(Constants.TYPE_LX);
		PageHelper.startPage(pageNum, 3);
		List<News> pubLxList = newsService.getListByType(temp);
		for (News news : pubLxList) {
			news.setText(HTMLSpirit.delHTMLTag(news.getText()));
			news.setHref(request.getAttribute("basePath") + "/id/" + news.getId());
			
			//获取图片
			Image image = new Image();
			image.setNews_id(news.getId());
			List<Image> images = imageService.getImageByNews(image);
			news.setImages(images);
		}
		
		
		
		//导航
		StringBuffer m_bar = new StringBuffer();
		StringBuffer pc_bar = new StringBuffer();
		if(type.equals(Constants.TYPE_KJ)){
			m_bar.append("<li><a href='/'>推荐</a></li><li><a href='/lx/'>两性</a></li><li><a href='/pic/'>美女</a></li><li class='active'><a href='/kj/'>科技</a></li><li><a href='/lx/'>娱乐</a></li><li><a href='/kj/'>影片</a></li>");
			pc_bar.append("<li><a href='/' target='_self' ga_event='channel_recommand_click' class='channel-item'><span>推荐</span></a></li><li><a href='/lx/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>两性</span></a></li><li><a href='/pic/' target='_self' ga_event='channel_video_click' class='channel-item'><span>美女</span></a></li><li><a href='/kj/' target='_self' ga_event='channel_social_click' class='channel-item active'><span>科技</span></a></li>");
		}else if(type.equals(Constants.TYPE_MN)){
			m_bar.append("<li><a href='/'>推荐</a></li><li><a href='/lx/'>两性</a></li><li class='active'><a href='/pic/'>美女</a></li><li><a href='/kj/'>科技</a></li><li><a href='/lx/'>娱乐</a></li><li><a href='/kj/'>影片</a></li>");
			pc_bar.append("<li><a href='/' target='_self' ga_event='channel_recommand_click' class='channel-item'><span>推荐</span></a></li><li><a href='/lx/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>两性</span></a></li><li><a href='/pic/' target='_self' ga_event='channel_video_click' class='channel-item active'><span>美女</span></a></li><li><a href='/kj/' target='_self' ga_event='channel_social_click' class='channel-item'><span>科技</span></a></li>");
		}else if(type.equals(Constants.TYPE_LX)){
			m_bar.append("<li><a href='/'>推荐</a></li><li class='active'><a href='/lx/'>两性</a></li><li><a href='/pic/'>美女</a></li><li><a href='/kj/'>科技</a></li><li><a href='/lx/'>娱乐</a></li><li><a href='/kj/'>影片</a></li>");
			pc_bar.append("<li><a href='/' target='_self' ga_event='channel_recommand_click' class='channel-item'><span>推荐</span></a></li><li><a href='/lx/' target='_self' ga_event='channel_hot_click' class='channel-item active'><span>两性</span></a></li><li><a href='/pic/' target='_self' ga_event='channel_video_click' class='channel-item'><span>美女</span></a></li><li><a href='/kj/' target='_self' ga_event='channel_social_click' class='channel-item'><span>科技</span></a></li>");
		}else{
			m_bar.append("<li class='active'><a href='/'>推荐</a></li><li><a href='/lx/'>两性</a></li><li><a href='/pic/'>美女</a></li><li><a href='/kj/'>科技</a></li><li><a href='/lx/'>娱乐</a></li><li><a href='/kj/'>影片</a></li>");
			pc_bar.append("<li><a href='/' target='_self' ga_event='channel_recommand_click' class='channel-item active'><span>推荐</span></a></li><li><a href='/lx/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>两性</span></a></li><li><a href='/pic/' target='_self' ga_event='channel_video_click' class='channel-item'><span>美女</span></a></li><li><a href='/kj/' target='_self' ga_event='channel_social_click' class='channel-item'><span>科技</span></a></li>");
		}

		map.put("m_bar", m_bar);
		map.put("pc_bar", pc_bar);

		map.put("pubNewsList", list);
		map.put("pubPicList", piclist);
		map.put("pubLxList", pubLxList);
		map.put("keys_", "新闻,科技,娱乐,游戏,体育,段子");
		map.put("des", "获取每日最新的资讯，及时更新，及时了解，及时掌握");
		map.put("title", "1234资讯网-最新的资讯信息");
	}
}
