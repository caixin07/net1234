package com.toutiao.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.toutiao.domain.Image;
import com.toutiao.domain.News;
import com.toutiao.domain.Pic;
import com.toutiao.mapper.NewsMapper;
import com.toutiao.service.ImageService;
import com.toutiao.service.MydriversCrawler;
import com.toutiao.service.NewsService;
import com.toutiao.service.OpgirlCrawler;
import com.toutiao.service.PicService;
import com.toutiao.util.Constants;
import com.toutiao.util.HTMLSpirit;

@Controller
@EnableAutoConfiguration
public class Index {
	Logger logger = LoggerFactory.getLogger(Index.class);  
	@Autowired
	private NewsService newsService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private PicService picService;
	@Autowired
	private PublicController publicController;
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
	public String showNews(ModelMap map, @PathVariable Integer pageNum, HttpServletRequest request, String type)
			throws IOException {
		News select = new News();
		select.setType(type);

		pageNum = pageNum == null ? 1 : pageNum;
		PageHelper.startPage(pageNum, pageSize);
		List<News> list = newsService.getListByType(select);//获取新闻
		
		//获取4个两性内容，插入到首页
		select.setType(Constants.TYPE_LX);
		List<News> lxList = newsService.getListByType(select);//获取新闻
		
		List<News> newList = new ArrayList<News>();
		Map<String,News> tempMap = new HashMap<String,News>();
		if(lxList != null && lxList.size() >= 4){
			int i = 1;
			for (News news : list) {
				if(i == 2 || i == 4 || i == 6 || i ==8){
					if(tempMap.get(lxList.get(i/2-1).getId()) == null){
						newList.add(lxList.get(i/2-1));
					}
					tempMap.put(lxList.get(i/2-1).getId(), lxList.get(i/2-1));
				}
				if(tempMap.get(news.getId()) == null){
					newList.add(news);
				}
				tempMap.put(news.getId(), news);
				i++;
				

			}
		}else{
			newList = list;
		}
		
		for (News news : newList) {
			news.setText(HTMLSpirit.delHTMLTag(news.getText()));
			news.setHref(request.getAttribute("basePath") + "/id/" + news.getId());

			Image image = new Image();
			image.setNews_id(news.getId());
			List<Image> images = imageService.getImageByNews(image);
			news.setImages(images);
		}

		PageInfo pageInfo = new PageInfo(list);
		long total = pageInfo.getPages();
		long nextPage = pageInfo.getNextPage();

		if (type == null) {
			type = "page";
		}

		// 生成页码
		StringBuffer pageList = new StringBuffer();

		int startNum = pageNum - 3 <= 0 ? 1 : pageNum - 3;
		int endNum = pageNum + 3 >= (int) total ? (int) total : pageNum + 3;

		//上一页
		String lastPage = "1";
		if(pageNum > 1){
			lastPage = (pageNum - 1) + "";
		}

		pageList.append("<li><a href='" + request.getAttribute("basePath") + "/" + type + "/" + lastPage
				+ "' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");

		for (int i = startNum; i < pageNum; i++) {
			pageList.append("<li><a href='" + request.getAttribute("basePath") + "/" + type + "/" + i + "'>" + i + " <span class='sr-only'>(current)</span></a></li>");
		}
		pageList.append("<li class='active'><a href='" + request.getAttribute("basePath") + "/" + type + "/" + pageNum + "'>"+pageNum+" <span class='sr-only'>(current)</span></a></li>");
		for (int i = pageNum + 1; i <= endNum; i++) {
			pageList.append("<li><a href='" + request.getAttribute("basePath") + "/" + type + "/" + i + "'>" + i + " <span class='sr-only'>(current)</span></a></li>");
		}
		pageList.append("<li><a href='" + request.getAttribute("basePath") + "/" + type + "/"
				+ nextPage + "' aria-label='Next'> <span aria-hidden='true'>&raquo;</span></a></li>");

		// 总页数
		map.put("total", total);
		map.put("nextPage", nextPage);
		map.put("pageList", pageList.toString());
		map.put("newsList", newList);
		publicController.pub(map, request,type);

		return "index";
	}

	@RequestMapping("/id/{id}")
	public String getOne(ModelMap map, @PathVariable String id, HttpServletRequest request) throws IOException {
		int d = Integer.parseInt(id);
		News news = newsService.getOne(d);

		Image image = new Image();
		image.setNews_id(news.getId());
		List<Image> images = imageService.getImageByNews(image);
		news.setImages(images);

		String count = news.getCount();
		String count_ = news.getCount_();
		int c = Integer.parseInt(count);
		c = c + 1;
		count = c + "";
		news.setCount(count);

		int e = Integer.parseInt(count_);
		e = e + 1;
		count_ = e + "";
		news.setCount_(count_);
		newsService.updateCount(news);

		publicController.pub(map, request,"");

		map.put("news", news);
		if (null != news.getKeys_() && !"".equals(news.getKeys_())) {
			map.put("keys_", news.getKeys_());
		}
		if (null != news.getTitle() && !"".equals(news.getTitle())) {
			map.put("des", news.getTitle());
			map.put("title", news.getTitle() + "—1234资讯网");
		}
		if(Constants.URL_FROM_WX.equals(news.getUrl_from())){
			return "index_info_wx";
		}else {
			return "index_info";
		}
	}

	@RequestMapping("/")
	String Index(ModelMap map, HttpServletRequest request) throws IOException {
		showNews(map, 1, request, null);
		return "index";
	}

	@RequestMapping("/lx")
	String liangxing(ModelMap map, HttpServletRequest request) throws IOException {
		return showNews(map, 1, request, Constants.TYPE_LX);
	}

	@RequestMapping("/lx/{pageNum}")
	String liangxing(ModelMap map, HttpServletRequest request, @PathVariable Integer pageNum) throws IOException {
		return showNews(map, pageNum, request, Constants.TYPE_LX);
	}

	@RequestMapping("/kj")
	String keji(ModelMap map, HttpServletRequest request) throws IOException {
		return showNews(map, 1, request, Constants.TYPE_KJ);
	}

	@RequestMapping("/kj/{pageNum}")
	String keji(ModelMap map, HttpServletRequest request, @PathVariable Integer pageNum) throws IOException {
		return showNews(map, pageNum, request, Constants.TYPE_KJ);
	}
}
