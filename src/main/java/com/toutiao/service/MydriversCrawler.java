package com.toutiao.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.toutiao.domain.News;
import com.toutiao.util.BaiduSend;
import com.toutiao.util.Constants;
import com.toutiao.util.DateUtil;
import com.toutiao.util.DownImage;
import com.toutiao.util.IkAnalyzer;
import com.toutiao.util.ScheduledCrawler;

@Service
public class MydriversCrawler {

	@Autowired
	private NewsService newsService;

	@Autowired
	private DownImage downImage;

	private static final Logger logger = LoggerFactory.getLogger(MydriversCrawler.class);

	public void getNews() throws IOException {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.mydrivers.com/").get();
		} catch (Exception e) {
			logger.error("访问http://www.mydrivers.com/异常", e);
			return;
		}

		if (doc != null) {
			Elements masthead = doc.select("ul.newslist").select("a");

			for (Element element : masthead) {
				News news = new News();
				news.setUrl_(element.attr("href"));
				if (newsService.getOneByUrl(news) != null) {
					continue;
				}

				Document info = null;
				try {
					info = Jsoup.connect(element.attr("href")).get();
				} catch (Exception e) {
					logger.error("访问" + element.attr("href") + "异常", e);
					continue;
				}

				try {
					String title = element.text();
					StringBuffer text = new StringBuffer();
					if (info.select("div.news_info").size() <= 0) {
						continue;
					}
					Elements p = info.select("div.news_info").select("p");

					for (Element element2 : p) {
						if (element2.select("p.jcuo1") == null || element2.select("p.jcuo1").size() > 0) {
							break;
						} else {
							// 下载图片
							Elements urlNode = element2.select("img");
							if (urlNode != null && urlNode.size() > 0) {
								for (Element element3 : urlNode) {
									String url = element3.attr("src");
									System.out.println(url);
									String imagePath = downImage.downImage(url);
									element3.attr("src", imagePath);
									System.out.println("imagePath:" + imagePath);
									if (element3.parent().attr("href").equals(url)) {
										element3.parent().attr("href", imagePath);
									}
								}
							}
							text = text.append(element2.toString());
						}
					}
					String imgHtml = null;
					imgHtml = p.select("img").get(0).toString();
					if (imgHtml != null && !"".equals(imgHtml)) {
						news.setImgAlt(p.select("img").get(0).attr("alt"));
						news.setImg_(p.select("img").get(0).attr("src"));
					}

					news.setSynTime(DateUtil.getTime());
					news.setText(text.toString());
					news.setTime("");
					news.setTitle(title);
					news.setUser("驱动之家");
					news.setCount("0");
					news.setIsShow("1");
					news.setType(Constants.TYPE_KJ);
					news.setKeys_(IkAnalyzer.getGjc(text.toString(),title));
					news.setBaiduSend("0");
					newsService.insert(news);

				} catch (Exception e) {
					logger.error("获取内容：" + element.attr("href") + "异常", e);
					continue;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Document info = Jsoup.connect("http://news.mydrivers.com/1/543/543501.htm").get();

		// System.out.println(info.select("div.news_info").select("p"));

		StringBuffer text = new StringBuffer();
		Elements p = info.select("div.news_info").select("p");
		String img = null;
		img = p.select("img").get(0).toString();

		for (Element element2 : p) {
			if (element2.select("p.jcuo1") == null || element2.select("p.jcuo1").size() > 0) {
				break;
			} else {
				String url = null;
				try {
					url = element2.select("img").get(0).attr("src");
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (url != null) {

					// DownImage.downImage(url);
				}

				// System.out.println(element2);
				text = text.append(element2.toString());
			}
		}
		System.out.println(text.toString());

	}
}
