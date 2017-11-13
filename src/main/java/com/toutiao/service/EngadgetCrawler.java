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

import com.toutiao.domain.Image;
import com.toutiao.domain.News;
import com.toutiao.util.BaiduSend;
import com.toutiao.util.Constants;
import com.toutiao.util.DateUtil;
import com.toutiao.util.DownImage;
import com.toutiao.util.IkAnalyzer;
import com.toutiao.util.RandomCount;
import com.toutiao.util.ScheduledCrawler;

@Service
public class EngadgetCrawler {

	@Autowired
	private NewsService newsService;

	@Autowired
	private DownImage downImage;
	@Autowired ImageService imageService;
	private static final Logger logger = LoggerFactory.getLogger(EngadgetCrawler.class);

	public void getNews() throws IOException {
		Document doc = null;
		try {
			doc = Jsoup.connect("https://cn.engadget.com").get();
		} catch (Exception e) {
			logger.error("访问https://cn.engadget.com异常", e);
			return;
		}

		if (doc != null) {
			Elements masthead = doc.select("a.o-hit__link");
			continue2:
			for (Element element : masthead) {
				String href = "https://cn.engadget.com"+element.attr("href");
				News news = new News();
				news.setUrl_(href);
				if (newsService.getOneByUrl(news) != null) {
					continue;
				}

				Document info = null;
				try {
					info = Jsoup.connect(href).get();
				} catch (Exception e) {
					logger.error("访问" + element.attr("href") + "异常", e);
					continue;
				}

				try {
					String title = info.select("h1.mt-20").text();
					StringBuffer text = new StringBuffer();
					if (info.select("div.article-text").size() <= 0) {
						continue;
					}
					Elements p = info.select("div.o-article_block");
					Set<Image> imageMap = new HashSet<Image>(); 
					
					for (Element element2 : p) {
							// 下载图片
							Elements urlNode = element2.select("img");
							if (urlNode != null && urlNode.size() > 0) {
								for (Element element3 : urlNode) {
									String url = element3.attr("src");
									String imagePath = downImage.downImage(url);
									if(imagePath == null){
										//图片下载失败
										continue continue2;
									}
									Image image = new Image();
									image.setSrc_(url);
									image.setPath_(imagePath);
									imageMap.add(image);
									
									element3.attr("src", downImage.getUrl(imagePath));
									if (element3.parent().attr("href") != null && !"".equals(element3.parent().attr("href"))) {
										element3.parent().attr("href", downImage.getUrl(imagePath));
									}
								}
							}
							text = text.append(element2.toString());
						
					}
//					String imgHtml = null;
//					imgHtml = p.select("img").get(0).toString();
//					if (imgHtml != null && !"".equals(imgHtml)) {
//						news.setImgAlt(p.select("img").get(0).attr("alt"));
//						news.setImg_(p.select("img").get(0).attr("src"));
//					}

					news.setSynTime(DateUtil.getTime());
					news.setText(text.toString());
					news.setTime("");
					news.setTitle(title);
					news.setUser("engadget");
					news.setCount(RandomCount.getRandomCount()+"");
					news.setCount_("0");
					news.setIsShow("1");
					news.setType(Constants.TYPE_KJ);
					news.setKeys_(IkAnalyzer.getGjc(text.toString(),title));
					news.setBaiduSend("0");
					news.setMorePic(Constants.MOREPIC_NO);
					newsService.insert(news);

					for (Image image : imageMap) {
						image.setNews_id(news.getId());
						imageService.insert(image);
					}
					
				} catch (Exception e) {
					logger.error("获取内容：" + element.attr("href") + "异常", e);
					continue;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Document info = Jsoup.connect("http://cn.reuters.com/news/archive/techMediaTelcoNews").get();

		System.out.println(info);

	}
}
