package com.toutiao.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.toutiao.domain.News;
import com.toutiao.domain.Pic;
import com.toutiao.mapper.NewsMapper;
import com.toutiao.service.MydriversCrawler;
import com.toutiao.service.NewsService;
import com.toutiao.service.OpgirlCrawler;
import com.toutiao.service.PicService;
import com.toutiao.util.Constants;
import com.toutiao.util.HTMLSpirit;

@Controller
@EnableAutoConfiguration
public class PicController {

	@Autowired
	private PicService picService;
	@Autowired
	private PublicController publicController;

	@Value("${page.pageSize}")
	private int pageSize;
	
	@RequestMapping("/pic")
	public String showPicIndex(ModelMap map,HttpServletRequest request) throws Exception {
		return showPic(map, 1, request);
	}
	
	@RequestMapping("/pic/{pageNum}")
	public String showPic(ModelMap map, @PathVariable Integer pageNum, HttpServletRequest request) throws Exception {
		pageNum = pageNum == null ? 1 : pageNum;
		PageHelper.startPage(pageNum, 60);
		List<Pic> list = picService.getPicList();

		PageInfo pageInfo = new PageInfo(list);
		long total = pageInfo.getPages();
		long nextPage = pageInfo.getNextPage();
		// 生成页码
		StringBuffer pageList = new StringBuffer();

		int startNum = pageNum - 3 <= 0 ? 1 : pageNum - 3;
		int endNum = pageNum + 3 >= (int) total ? (int) total : pageNum + 3;


		//上一页
		String lastPage = "1";
		if(pageNum > 1){
			lastPage = (pageNum - 1) + "";
		}

		pageList.append("<li><a href='" + request.getAttribute("basePath") + "/pic/" + lastPage
				+ "' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");

		for (int i = startNum; i < pageNum; i++) {
			pageList.append("<li><a href='" + request.getAttribute("basePath") + "/pic/" + i + "'>" + i + " <span class='sr-only'>(current)</span></a></li>");
		}
		pageList.append("<li class='active'><a href='" + request.getAttribute("basePath") + "/pic/" + pageNum + "'>"+pageNum+" <span class='sr-only'>(current)</span></a></li>");
		for (int i = pageNum + 1; i <= endNum; i++) {
			pageList.append("<li><a href='" + request.getAttribute("basePath") + "/pic/" + i + "'>" + i + " <span class='sr-only'>(current)</span></a></li>");
		}

		//下一页
		if(0==nextPage){
			nextPage = 1;
		}
		pageList.append("<li><a href='" + request.getAttribute("basePath") + "/pic/"
				+ nextPage + "' aria-label='Next'> <span aria-hidden='true'>&raquo;</span></a></li>");

		// 总页数

		map.put("total", total);
		map.put("nextPage", nextPage);
		map.put("pageList", pageList.toString());
		map.put("picList", list);
		publicController.pub(map, request,Constants.TYPE_MN);

		
		return "index_pic_list";
	}

	@RequestMapping("/pic/id/{id}")
	public String showPics(ModelMap map,@PathVariable String id,HttpServletRequest request) throws Exception {
		Pic pic = new Pic();
		pic.setParent_id(id);
		List<Pic> pics = picService.getChildPicList(pic);
		map.put("pics", pics);
		publicController.pub(map, request,Constants.TYPE_MN);
		return "index_pics";
	}
}
