package com.toutiao.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DownImage {

	@Value("${toutiao.image}")
	private String image;
	@Value("${toutiao.imageUrl}")
	private String imageUrl;
	

	/**
	 * 
	 * 根据图片的外网地址下载图片到本地硬盘的filePath
	 * 
	 * @param filePath
	 *            本地保存图片的文件路径
	 * @param imgUrl
	 *            图片的外网地址
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * 
	 */
	public String downImage(String imgUrl) throws Exception {

		String datePath = DateUtil.getDate();
		String filePath = image + datePath;
		String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
		// 构造URL
		URL url = new URL(imgUrl);
		InputStream is = null;
		OutputStream os = null;
		try {
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
			con.setReadTimeout(5 * 1000);
			// 输入流
			is = con.getInputStream();

			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			File sf = new File(filePath);
			if (!sf.exists()) {
				sf.mkdirs();
			}
			os = new FileOutputStream(sf.getPath() + "/" + fileName);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			if(os != null){
				os.close();
			}
			if(is != null){
				is.close();
			}
		}
		
		return datePath + "/" + fileName;
	}
	public String getUrl(String url){
		return "../"+imageUrl + "/" + url;
	}
}
