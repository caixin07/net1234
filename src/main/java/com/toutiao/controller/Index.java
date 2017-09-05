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
import com.toutiao.mapper.NewsMapper;
import com.toutiao.service.MydriversCrawler;
import com.toutiao.service.NewsService;
import com.toutiao.service.OpgirlCrawler;
import com.toutiao.util.Constants;
import com.toutiao.util.HTMLSpirit;

@Controller
@EnableAutoConfiguration
public class Index {

	@Autowired
	private NewsService newsService;

	@Value("${page.pageSize}")
	private int pageSize;
	@Autowired
	private OpgirlCrawler opgirlCrawler;
	
	@RequestMapping("/test")
	public void test() throws Exception {
		opgirlCrawler.getNews();
	}
	@RequestMapping("/showNews")
	public List<News> showNews() throws IOException {
		List<News> list = newsService.getAll();
		return newsService.getAll();
	}

	@RequestMapping("/page/{pageNum}")
	public String showNews(ModelMap map, @PathVariable Integer pageNum, HttpServletRequest request) throws IOException {
		pageNum = pageNum == null ? 1 : pageNum;
		PageHelper.startPage(pageNum, pageSize);
		List<News> list = newsService.getAll();
		for (News news : list) {
			news.setText(HTMLSpirit.delHTMLTag(news.getText()));
			news.setHref(request.getAttribute("basePath") + "/id/" + news.getId());
		}

		PageInfo pageInfo = new PageInfo(list);
		long total = pageInfo.getPages();
		long nextPage = pageInfo.getNextPage();
		// 生成页码
		StringBuffer pageList = new StringBuffer();

		int startNum = pageNum - 3 <= 0 ? 1 : pageNum - 3;
		int endNum = pageNum + 3 >= (int) total ? (int) total : pageNum + 3;
		for (int i = startNum; i < pageNum; i++) {
			pageList.append("<li><a href='" + request.getAttribute("basePath") + "/page/" + i + "'>" + i + "</a></li>");
		}
		pageList.append("<li class='active'><span>" + pageNum + "</span></li>");
		for (int i = pageNum + 1; i <= endNum; i++) {
			pageList.append("<li><a href='" + request.getAttribute("basePath") + "/page/" + i + "'>" + i + "</a></li>");
		}
		pageList.append("<li><span> ... </span></li><li class='next-page'><a href='" + request.getAttribute("basePath")
				+ "/page/" + nextPage + "'>下一页</a></li><li><span>共" + total + " 页</span></li>");

		// 总页数

		map.put("total", total);
		map.put("nextPage", nextPage);
		map.put("pageList", pageList.toString());
		map.put("newsList", list);
		pub(map, request);

		return "index";
	}

	@RequestMapping("/id/{id}")
	public String getOne(ModelMap map, @PathVariable String id, HttpServletRequest request) throws IOException {
		int d = Integer.parseInt(id);
		News news = newsService.getOne(d);

		String count = news.getCount();

		int c = Integer.parseInt(count);
		c = c + 1;
		count = c + "";
		news.setCount(count);

		newsService.updateCount(news);

		pub(map, request);
		map.put("news", news);
		if(null != news.getKeys_() && !"".equals(news.getKeys_())){
			map.put("keys_", news.getKeys_());
		}
		if(null != news.getTitle() && !"".equals(news.getTitle())){
			map.put("des", news.getTitle());	
			map.put("title", news.getTitle()+"—1234资讯网");
		}
		return "index_info";
	}

	@RequestMapping("/")
	String Index(ModelMap map, HttpServletRequest request) throws IOException {
		showNews(map, 1, request);
		return "index";
	}

	@RequestMapping("/tuijian")
	String tuijian(ModelMap map, HttpServletRequest request) throws IOException {
		return tuijian(map, request, 1);
	}

	@RequestMapping("/tuijian/{pageNum}")
	String tuijian(ModelMap map, HttpServletRequest request, @PathVariable Integer pageNum) throws IOException {
		pageNum = pageNum == null ? 1 : pageNum;
		PageHelper.startPage(pageNum, pageSize);
		List<News> list = newsService.getAllForTuijian();
		for (News news : list) {
			news.setText(HTMLSpirit.delHTMLTag(news.getText()));
			news.setHref(request.getAttribute("basePath") + "/id/" + news.getId());
		}

		PageInfo pageInfo = new PageInfo(list);
		long total = pageInfo.getPages();
		long nextPage = pageInfo.getNextPage();
		// 生成页码
		StringBuffer pageList = new StringBuffer();

		int startNum = pageNum - 3 <= 0 ? 1 : pageNum - 3;
		int endNum = pageNum + 3 >= (int) total ? (int) total : pageNum + 3;
		for (int i = startNum; i < pageNum; i++) {
			pageList.append(
					"<li><a href='" + request.getAttribute("basePath") + "/tuijian/" + i + "'>" + i + "</a></li>");
		}
		pageList.append("<li class='active'><span>" + pageNum + "</span></li>");
		for (int i = pageNum + 1; i <= endNum; i++) {
			pageList.append(
					"<li><a href='" + request.getAttribute("basePath") + "/tuijian/" + i + "'>" + i + "</a></li>");
		}
		pageList.append("<li><span> ... </span></li><li class='next-page'><a href='" + request.getAttribute("basePath")
				+ "/tuijian/" + nextPage + "'>下一页</a></li><li><span>共" + total + " 页</span></li>");

		// 总页数

		map.put("total", total);
		map.put("nextPage", nextPage);
		map.put("pageList", pageList.toString());
		map.put("newsList", list);

		pub(map, request);
		return "index";
	}

	@RequestMapping("/keji")
	String keji(ModelMap map, HttpServletRequest request) throws IOException {
		return keji(map, request, 1);
	}

	@RequestMapping("/keji/{pageNum}")
	String keji(ModelMap map, HttpServletRequest request, @PathVariable Integer pageNum) throws IOException {

		News type = new News();
		type.setType(Constants.TYPE_KJ);
		pageNum = pageNum == null ? 1 : pageNum;
		PageHelper.startPage(pageNum, pageSize);
		List<News> list = newsService.getListByType(type);
		for (News news : list) {
			news.setText(HTMLSpirit.delHTMLTag(news.getText()));
			news.setHref(request.getAttribute("basePath") + "/id/" + news.getId());
		}

		PageInfo pageInfo = new PageInfo(list);
		long total = pageInfo.getPages();
		long nextPage = pageInfo.getNextPage();
		// 生成页码
		StringBuffer pageList = new StringBuffer();

		int startNum = pageNum - 3 <= 0 ? 1 : pageNum - 3;
		int endNum = pageNum + 3 >= (int) total ? (int) total : pageNum + 3;
		for (int i = startNum; i < pageNum; i++) {
			pageList.append("<li><a href='" + request.getAttribute("basePath") + "/keji/" + i + "'>" + i + "</a></li>");
		}
		pageList.append("<li class='active'><span>" + pageNum + "</span></li>");
		for (int i = pageNum + 1; i <= endNum; i++) {
			pageList.append("<li><a href='" + request.getAttribute("basePath") + "/keji/" + i + "'>" + i + "</a></li>");
		}
		pageList.append("<li><span> ... </span></li><li class='next-page'><a href='" + request.getAttribute("basePath")
				+ "/keji/" + nextPage + "'>下一页</a></li><li><span>共" + total + " 页</span></li>");
		// 总页数

		map.put("total", total);
		map.put("nextPage", nextPage);
		map.put("pageList", pageList.toString());
		map.put("newsList", list);

		pub(map, request);
		return "index";
	}

	@RequestMapping("/youxi")
	String youxi(ModelMap map, HttpServletRequest request) throws IOException {
		return tuijian(map, request, 1);
	}

	@RequestMapping("/youxi/{pageNum}")
	String youxi(ModelMap map, HttpServletRequest request, @PathVariable Integer pageNum) throws IOException {
		News type = new News();
		type.setType(Constants.TYPE_YX);
		pageNum = pageNum == null ? 1 : pageNum;
		PageHelper.startPage(pageNum, pageSize);
		List<News> list = newsService.getListByType(type);
		for (News news : list) {
			news.setText(HTMLSpirit.delHTMLTag(news.getText()));
			news.setHref(request.getAttribute("basePath") + "/id/" + news.getId());
		}

		PageInfo pageInfo = new PageInfo(list);
		long total = pageInfo.getPages();
		long nextPage = pageInfo.getNextPage();
		// 生成页码
		StringBuffer pageList = new StringBuffer();

		int startNum = pageNum - 3 <= 0 ? 1 : pageNum - 3;
		int endNum = pageNum + 3 >= (int) total ? (int) total : pageNum + 3;
		for (int i = startNum; i < pageNum; i++) {
			pageList.append(
					"<li><a href='" + request.getAttribute("basePath") + "/youxi/" + i + "'>" + i + "</a></li>");
		}
		pageList.append("<li class='active'><span>" + pageNum + "</span></li>");
		for (int i = pageNum + 1; i <= endNum; i++) {
			pageList.append(
					"<li><a href='" + request.getAttribute("basePath") + "/youxi/" + i + "'>" + i + "</a></li>");
		}
		pageList.append("<li><span> ... </span></li><li class='next-page'><a href='" + request.getAttribute("basePath")
				+ "/youxi/" + nextPage + "'>下一页</a></li><li><span>共" + total + " 页</span></li>");

		// 总页数

		map.put("total", total);
		map.put("nextPage", nextPage);
		map.put("pageList", pageList.toString());
		map.put("newsList", list);

		pub(map, request);
		return "index";
	}

	public void pub(ModelMap map, HttpServletRequest request) throws IOException {
		int pageNum = 1;
		PageHelper.startPage(pageNum, pageSize);
		List<News> list = newsService.getAllForTuijian();
		for (News news : list) {
			news.setText(HTMLSpirit.delHTMLTag(news.getText()));
			news.setHref(request.getAttribute("basePath") + "/id/" + news.getId());
		}
		map.put("pubNewsList", list);

		map.put("keys_", "新闻,科技,娱乐,游戏,体育,段子");
		map.put("des", "获取每日最新的资讯，及时更新，及时了解，及时掌握");
		map.put("title", "1234资讯网-最新的资讯信息");
	}
}
