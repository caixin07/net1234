package com.toutiao.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.github.pagehelper.PageHelper;
import com.toutiao.domain.News;
import com.toutiao.util.HTMLSpirit;

@Service
public class PublicController {
	
	
	public void pub(ModelMap map, HttpServletRequest request) throws IOException {
//		int pageNum = 1;
//		PageHelper.startPage(pageNum, pageSize);
//		List<News> list = newsService.getAllForTuijian();
//		for (News news : list) {
//			news.setText(HTMLSpirit.delHTMLTag(news.getText()));
//			news.setHref(request.getAttribute("basePath") + "/id/" + news.getId());
//		}
//		map.put("pubNewsList", list);

		map.put("keys_", "新闻,科技,娱乐,游戏,体育,段子");
		map.put("des", "获取每日最新的资讯，及时更新，及时了解，及时掌握");
		map.put("title", "1234资讯网-最新的资讯信息");
	}
}
