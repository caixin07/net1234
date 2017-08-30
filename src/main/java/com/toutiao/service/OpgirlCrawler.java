package com.toutiao.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toutiao.domain.Pic;
import com.toutiao.util.DownImage;

@Service
public class OpgirlCrawler {

	@Autowired
	private PicService picService;
	@Autowired
	ImageService imageService;

	@Autowired
	private DownImage downImage;
	private String Fh21_VIEW = "https://wap.fh21.com.cn/view/";

	private static final Logger logger = LoggerFactory.getLogger(OpgirlCrawler.class);
	public static Map<String, String> headers = null;

	public void getNews() throws Exception {
		for (int i = 0; i <= 160; i++) {
			System.out.println("**************开始处理第" + i + "页**************");
			String json = null;
			try {
				json = loadJSON("http://www.opgirl.cn/gallery/list?page=" + i + "&start=" + i * 10 + "&end="
						+ (i * 10 + 9) + "&strategy=0&cid=24&did=333");
			} catch (Exception e) {
				System.out.println("**************第" + i + "页获取失败**************");
			}

			if (json == null) {
				continue;
			}
			JSONObject pic_json = JSON.parseObject(json);
			List<Map> pic_list = null;
			try {
				pic_list = (List) pic_json.get("data");
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			if (pic_list == null) {
				System.out.println("1");
				continue;
			}
			imgList: for (Map map : pic_list) {
				String id = map.get("id") + "";
				String title = map.get("title") + "";
				String url = map.get("url") + "";
				String width = map.get("width")+"";
				String height = map.get("height")+"";

				String path_ = downImage.downImage("http:" + url);
				String pictureCount = map.get("pictureCount") + "";
				System.out.println("title:"+title);
				Pic pic = new Pic();
				pic.setAlt_(title);
				pic.setPath_(path_);
				pic.setUrl_(url);
				pic.setHeight_(height);
				pic.setWidth_(width);
				if (picService.getOneByUrl(pic) != null) {
					System.out.println("2");
					continue;
				}
				picService.insert(pic);

				int page = Integer.parseInt(pictureCount) / 3 + 1;
				for (int j = 1; j <= page; j++) {
					Document doc = null;
					try {
						doc = Jsoup.connect("http://www.opgirl.cn/lite/" + id + "/details.html?cid=24&did=333&offset=" + j
								+ "&fromList=true").get();
					} catch (Exception e) {
						doc = null;
						e.printStackTrace();
					}

					if (doc == null) {
						continue imgList;
					}

					Elements script = doc.select("script");
					if (script != null && script.size() > 0) {
						JSONObject object = JSON
								.parseObject(script.get(0).html().replace("var GLOBALINFO = ", "").replace(";", ""));

						JSONObject detailData = (JSONObject) object.get("detailData");
						List<Map> imgs = (List) detailData.get("imgs");
						// List<Map<String, String>> imgsList = (List)
						// JSONArray.parseArray(imgs.toString());

						for (Map map2 : imgs) {
							String path_2 = downImage.downImage("http:" + map2.get("url").toString());

							Pic pic2 = new Pic();
							pic2.setAlt_(title);
							pic2.setPath_(path_2);
							pic2.setUrl_(map2.get("url").toString());
							pic2.setParent_id(pic.getId_());
							pic2.setHeight_(map2.get("height")+"");
							pic2.setWidth_(map2.get("width")+"");
							if (picService.getOneByUrl(pic2) != null) {
								System.out.println(3);
								continue;
							}
							picService.insert(pic2);
						}
					}
				}
				System.out.println("**************第" + i + "页处理成功**************");

			}

		}

	}

	public static String loadJSON(String url) throws IOException {
		StringBuilder json = new StringBuilder();
		BufferedReader in = null;
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			yc.setConnectTimeout(10000);
			yc.setReadTimeout(10000);
			in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "utf-8"));// 防止乱码
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return json.toString();
	}

	public static void main(String[] args) throws IOException {
		System.out.println(6 / 3 + 1);
	}
}
