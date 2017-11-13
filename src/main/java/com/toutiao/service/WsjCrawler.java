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
public class WsjCrawler {

	@Autowired
	private NewsService newsService;

	@Autowired
	private DownImage downImage;
	@Autowired ImageService imageService;
	private static final Logger logger = LoggerFactory.getLogger(WsjCrawler.class);

	public void getNews()  throws IOException {
		Set<String> urlList = new HashSet<String>();
		urlList.add("http://cn.wsj.com/gb/bch.php");
		urlList.add("http://cn.wsj.com/gb/markets.php");
		urlList.add("http://cn.wsj.com/gb/economy.php");
		urlList.add("http://cn.wsj.com/gb/industry.php");
		urlList.add("http://cn.wsj.com/gb/tech.php");
		
		for (String string : urlList) {
			synchronized (string) {
				getNews(string);
			}			
		}

	}
	public synchronized void getNews(String string) throws IOException {
		//http://cn.wsj.com/gb/bch.php
		//http://cn.wsj.com/gb/markets.php
		//http://cn.wsj.com/gb/economy.php
		//http://cn.wsj.com/gb/industry.php
		
		Document doc = null;
		try {
			doc = Jsoup.connect(string).get();
		} catch (Exception e) {
			logger.error("访问"+string+"异常", e);
			return;
		}

		if (doc != null) {
			Elements masthead = doc.select("article.hed-summ").select("a");
			continue2:
			for (Element element : masthead) {
				String href = element.attr("href");
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
					String title = info.select("h1.wsj-article-headline").text();
					StringBuffer text = new StringBuffer();
					if (info.select("div.article-wrap").size() <= 0) {
						continue;
					}
					Set<Image> imageMap = new HashSet<Image>();	
					//下载图片
					Elements imgNode = info.select("div.media-object-image").select("img");
					for (Element element2 : imgNode) {
						String url = element2.attr("src");
						String imagePath = downImage.downImage(url);
						if(imagePath == null){
							//图片下载失败
							continue continue2;
						}
						Image image = new Image();
						image.setSrc_(url);
						image.setPath_(imagePath);
						imageMap.add(image);
						
						element2.attr("src", downImage.getUrl(imagePath));
						text = text.append(element2.toString());
					}
					
					Elements p = info.select("div.article-wrap").select("p");
					
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
					news.setUser("wsj");
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
		Document info = Jsoup.connect("http://cn.wsj.com/gb/tech.php").get();

		System.out.println(info.select("article.hed-summ"));

	}
}
