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
import com.toutiao.util.ScheduledCrawler;

@Service
public class Fh21Crawler {

	@Autowired
	private NewsService newsService;
	@Autowired ImageService imageService;


	@Autowired
	private DownImage downImage;
	
	private String Fh21_VIEW = "https://wap.fh21.com.cn/view/";

	private static final Logger logger = LoggerFactory.getLogger(Fh21Crawler.class);

	public void getNews() throws Exception {
		Document doc = null;
		try {
			doc = Jsoup.connect("https://wap.fh21.com.cn/").get();
		} catch (Exception e) {
			logger.error("访问http://news.17173.com/异常", e);
			return;
		}
		if (doc != null) {
			Elements list = doc.select("ul.com-dise-list").select("li");
			if (list != null) {
				for (Element element : list) {
					Set<Image> imageMap = new HashSet<Image>(); 
					String title = element.select("span.mix-title-link").text();
					
					String href = element.select("a").attr("href");
					if(href == null || "".equals(href)) continue;
					System.out.println("~~~~~~~~~~herf:"+href);
					//判断链接是否已经存在
					News news = new News();
					news.setUrl_(href);
					if (newsService.getOneByUrl(news) != null) {
						continue;
					}					
					
					StringBuffer text = new StringBuffer();
					if(href.startsWith("//")){
						href = href.replaceFirst("//", "https://");
					}
					getNextPage(href, text,imageMap);


					if(imageMap == null){//图片下载失败
						continue;
					}					
					news.setSynTime(DateUtil.getTime());
					news.setText(text.toString());
					news.setTime("");
					news.setTitle(title);
					news.setUser("飞华");
					news.setCount("0");
					news.setIsShow("1");
					news.setType(Constants.TYPE_LX);
					news.setKeys_(IkAnalyzer.getGjc(text.toString(),title));
					news.setBaiduSend("0");
					newsService.insert(news);
					
					for (Image image : imageMap) {
						image.setNews_id(news.getId());
						imageService.insert(image);
					}
					
				}
			}

		}
		// System.out.println(doc.select("ul.com-dise-list").select("a").attr("href"));
		/*
		 * if (doc != null) { Elements masthead =
		 * doc.select("ul.ptlist").select("h2.tit").select("a");
		 * 
		 * continue1: for (Element element : masthead) { News news = new News();
		 * news.setUrl_(element.attr("href")); if (newsService.getOneByUrl(news)
		 * != null) { continue; }
		 * 
		 * Document info = null; try { info =
		 * Jsoup.connect(element.attr("href")).get(); } catch (Exception e) {
		 * logger.error("访问" + element.attr("href") + "异常", e); continue; }
		 * 
		 * try { String title = element.text(); StringBuffer text = new
		 * StringBuffer(); if (info.select("div#mod_article").size() <= 0) {
		 * continue; } Elements p = info.select("div#mod_article").select("p");
		 * 
		 * for (Element element2 : p) { // 下载图片 Elements urlNode =
		 * element2.select("img"); if (urlNode != null && urlNode.size() > 0) {
		 * for (Element element3 : urlNode) { String url = element3.attr("src");
		 * System.out.println(url); String imagePath = downImage.downImage(url);
		 * if(imagePath == null){ continue continue1; } element3.attr("src",
		 * imagePath); System.out.println("imagePath:" + imagePath); //
		 * 替换a标签href element3.parent().attr("href", imagePath); } } text =
		 * text.append(element2.toString()); } String imgHtml = null; imgHtml =
		 * p.select("img").get(0).toString(); if (imgHtml != null &&
		 * !"".equals(imgHtml)) {
		 * news.setImgAlt(p.select("img").get(0).attr("alt"));
		 * news.setImg_(p.select("img").get(0).attr("src")); }
		 * 
		 * news.setSynTime(DateUtil.getTime()); news.setText(text.toString());
		 * news.setTime(""); news.setTitle(title); news.setUser("17173");
		 * news.setCount("0"); news.setIsShow("1");
		 * news.setType(Constants.TYPE_YX);
		 * news.setKeys_(IkAnalyzer.getGjc(text.toString(),title));
		 * news.setBaiduSend("0"); newsService.insert(news);
		 * 
		 * } catch (Exception e) { logger.error("获取内容：" + element.attr("href") +
		 * "异常", e); continue; } } }
		 */
	}

	public void getNextPage(String url,StringBuffer text,Set<Image> imageMap) throws Exception{
		if(url != null && !"".equals(url)){				
				Document doc1 = Jsoup.connect(url).get();
				if(doc1 != null){
					Elements textElement = doc1.select("div.wrap-details-con");
					if(textElement != null && textElement.size() > 0){
						for (Element element2 : textElement) {
							// 下载图片
							Elements urlNode = element2.select("img");
							if (urlNode != null && urlNode.size() > 0) {
								for (Element element3 : urlNode) {
									String src_url = element3.attr("src");

									String imagePath = downImage.downImage(src_url);
									if(imagePath == null){
										//图片下载失败
										imageMap = null;
									}
									Image image = new Image();
									image.setSrc_(src_url);
									image.setPath_(imagePath);
									imageMap.add(image);
									imagePath = downImage.getUrl(imagePath);
									element3.attr("src", imagePath);
									// 替换a标签href
									element3.parent().attr("href", imagePath);
								}
							}
							text = text.append(element2.children().toString());
						}
					}
					
					textElement.select("a").remove();
					Elements nextPage = doc1.select("section.paging").select("a:contains(下一页)");
					if(nextPage != null && nextPage.size() > 0){
						String nextUrl = nextPage.get(0).attr("href");
						getNextPage(Fh21_VIEW+nextUrl, text,imageMap);
					}
				
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		new Fh21Crawler().getNews();
	}
}
