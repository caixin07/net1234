package com.toutiao.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.toutiao.service.BaiduSendService;
import com.toutiao.service.Fh21Crawler; 
import com.toutiao.service.G17173Crawler;
import com.toutiao.service.MydriversCrawler;
import com.toutiao.service.NewsService;
import com.toutiao.service.OpgirlCrawler;

@Component
public class ScheduledCrawler {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledCrawler.class);

	@Autowired
	private MydriversCrawler mydriversCrawler;
	@Autowired
	private G17173Crawler g17173Crawler;
	@Autowired
	private Fh21Crawler fh21Crawler;
	@Autowired
	private OpgirlCrawler opgirlCrawler;
	@Autowired
	private BaiduSendService baiduSendService;

	@Scheduled(cron = "0 0/5 6-23 * * ?")
	public void executeMydriversCrawler() throws IOException {
		logger.info("ScheduledTest.executeMydriversCrawler 定时任务MydriversCrawler");
		try {
//			mydriversCrawler.getNews();
		} catch (Exception e) {
			logger.error("定时任务MydriversCrawler异常",e);
		}
		// 间隔2分钟,执行工单上传任务

	}

//	@Scheduled(cron = "0 0/8 6-23 * * ?")
//	public void executeG17173Crawler() throws IOException {
//		logger.info("ScheduledTest.executeMydriversCrawler 定时任务g17173Crawler");
//		try {
//			g17173Crawler.getNews();
//		} catch (Exception e) {
//			logger.error("定时任务g17173Crawler异常",e);
//		}
//	
//		// 间隔2分钟,执行工单上传任务
//	}

	@Scheduled(cron = "0 0/2 6-23 * * ?")
	public void executeBaiduSendTask() throws IOException {
		logger.info("ScheduledTest.executeMydriversCrawler 定时任务baiduSendTask");
		try {
//			baiduSendService.baiduSendTask();
		} catch (Exception e) {
			logger.error("定时任务baiduSendService异常",e);
		}
	}
//	@Scheduled(cron = "0 0/2 6-23 * * ?")
//	public void executeFh21Task() throws IOException {
//		logger.info("ScheduledTest.executeMydriversCrawler 定时任务Fh21Task");
//		try {
//			fh21Crawler.getNews();
//		} catch (Exception e) {
//			logger.error("定时任务Fh21Task异常",e);
//		}
//	}
	@Scheduled(cron = "0 0/2 6-23 * * ?")
	public void executeFh21Task() throws IOException {
		logger.info("ScheduledTest.executeMydriversCrawler 定时任务opgirlCrawler");
		try {
//			opgirlCrawler.getNews();
		} catch (Exception e) {
			logger.error("定时任务opgirlCrawler异常",e);
		}
	}
}
