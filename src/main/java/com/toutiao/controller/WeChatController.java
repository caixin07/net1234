package com.toutiao.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.domain.Image;
import com.toutiao.domain.News;
import com.toutiao.service.ImageService;
import com.toutiao.service.NewsService;
import com.toutiao.util.Constants;
import com.toutiao.util.DateUtil;
import com.toutiao.util.DownImage;
import com.toutiao.util.IkAnalyzer;
import com.toutiao.util.RandomCount;

@Controller
@EnableAutoConfiguration
public class WeChatController {

	@Autowired
	private NewsService newsService;
	@Autowired
	private DownImage downImage;
	@Autowired ImageService imageService;
	@RequestMapping("/wx/zz")
	public String zhuanzhai() throws Exception {
		return "wx/zhuanzhai";
	}

	@RequestMapping("/wx/tj")
	public String tj(ModelMap map, HttpServletRequest request) throws Exception {
		String url = request.getParameter("url");
		String type = request.getParameter("type");
		if (null == url || "".endsWith(url)) {
			return "wx/zhuanzhai";
		}

		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			return "wx/zhuanzhai";
		}
		String user = doc.select("#post-user").text();
		String data = doc.select("#post-date").text();
		String title = doc.select("#activity-name").text();
		String style = doc.select("style").toString();
		Set<Image> imageMap = new HashSet<Image>();
		Elements page_content = doc.select("#page-content");

		// 获取阅读原文url
		Map<String, Object> scriptmap = getScript(doc);
		String msg_source_url = scriptmap.get("msg_source_url").toString();
		if(msg_source_url != null && !"".equals(msg_source_url)){
			msg_source_url = msg_source_url.substring(1, msg_source_url.length()-2);
		}
		doc.select("#js_view_source").attr("href", msg_source_url);
		
		Elements urlNode = page_content.select("img");
		if (urlNode != null && urlNode.size() > 0) {
			for (Element element3 : urlNode) {
				String src_url = element3.attr("data-src");
				if (src_url == null || "".equals(src_url)) {
					continue;
				}
				String imagePath = downImage.downWxImage(src_url);
				if (imagePath == null) {
					continue;
				}
				Image image = new Image();
				image.setSrc_(src_url);
				image.setPath_(imagePath);
				imageMap.add(image);
				imagePath = downImage.getUrl(imagePath);
				element3.attr("src", imagePath);
			}
		}

		News news = new News();
		news.setUrl_(url);
		news.setSynTime(DateUtil.getTime());
		news.setText(style + page_content.toString());
		news.setTime("");
		news.setTitle(title);
		news.setUser(user);
		news.setCount(RandomCount.getRandomCount() + "");
		news.setCount_("0");
		news.setIsShow("1");
		news.setType(type);
		news.setKeys_(IkAnalyzer.getGjc(page_content.toString(), title));
		news.setBaiduSend("0");
		news.setMorePic(Constants.MOREPIC_YES);
		news.setUrl_from(Constants.URL_FROM_WX);
		newsService.insert(news);
		
		for (Image image : imageMap) {
			image.setNews_id(news.getId());
			imageService.insert(image);
		}	
		return "wx/zhuanzhai";
	}

	public Map<String, Object> getScript(Document doc) {
		Map<String, Object> map = new HashMap<String, Object>();
		Elements script = doc.select("script");
		for (Element element : script) {

			/* 取得JS变量数组 */
			String[] data1 = element.data().toString().split("var");

			/* 取得单个JS变量 */
			for (String variable : data1) {

				/* 过滤variable为空的数据 */
				if (variable.contains("=")) {

					// System.out.println(variable);

					String[] kvp = variable.split("=");

					/* 取得JS变量存入map */
					if (!map.containsKey(kvp[0].trim()))
						map.put(kvp[0].trim(), kvp[1].trim());

				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		String url = "https://mp.weixin.qq.com/s/rY9i7_rkLmcKkhThUhdYVQ";
		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {

		}

		String user = doc.select("#post-user").text();
		String data = doc.select("#post-date").text();
		String text = doc.select("#js_content").toString();
		String style = doc.select("style").toString();

		Elements script = doc.select("script");
		Map<String, Object> map = new HashMap<String, Object>();

		for (Element element : script) {

			/* 取得JS变量数组 */
			String[] data1 = element.data().toString().split("var");

			/* 取得单个JS变量 */
			for (String variable : data1) {

				/* 过滤variable为空的数据 */
				if (variable.contains("=")) {

					// System.out.println(variable);

					String[] kvp = variable.split("=");

					/* 取得JS变量存入map */
					if (!map.containsKey(kvp[0].trim()))
						map.put(kvp[0].trim(), kvp[1].trim());

				}
			}
		}

		String msg_source_url = map.get("msg_source_url").toString();
		msg_source_url = msg_source_url.substring(1, msg_source_url.length()-2);

		System.out.println(msg_source_url);

	}
}
