package com.toutiao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toutiao.domain.News;
import com.toutiao.util.BaiduSend;

@Service
public class BaiduSendService {

	@Autowired
	private NewsService newsService;
	
	public void baiduSendTask(){
		
		News news = new News();
		news.setBaiduSend("0");//未发送至百度
		List<News> list = newsService.getListByBaiduSend(news);
		if(list != null && list.size() > 0){
			for (News news2 : list) {
				// 拼接url
				String[] param = { news2.getId() };
				String json = BaiduSend.Post(param);// 执行推送方法
				System.out.println("结果是" + json); // 打印推送结果
				if(json.indexOf("success") > 0){//推送成功
					news2.setBaiduSend("1");//已发送至百度
					newsService.updateBaiduSend(news2);
				}
			}
		}

	}
	
}
