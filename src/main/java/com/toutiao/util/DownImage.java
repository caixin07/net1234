package com.toutiao.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

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
	public synchronized String downImage(String imgUrl) throws Exception {

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
			con.setConnectTimeout(10 * 1000);
			con.setReadTimeout(10*1000);
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
			
			//图片压缩
			try {
				ZipImgUtil imgCom = new ZipImgUtil(sf.getPath() + "/" + fileName);
				imgCom.resizeFix();
			} catch (Exception e) {
				// TODO: handle exception
			}

			
		} catch (Exception e) {
			return null;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (Exception e2) {
				}
				
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception e2) {
				}
				
			}
		}

		return datePath + "/" + fileName;
	}
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
	public synchronized String downWxImage(String imgUrl) throws Exception {

		String datePath = DateUtil.getDate();
		String filePath = image + datePath;
		String fileName = UUID.randomUUID().toString();
		// 构造URL
		URL url = new URL(imgUrl);
		InputStream is = null;
		OutputStream os = null;
		try {
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(10 * 1000);
			con.setReadTimeout(10*1000);
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
			return null;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (Exception e2) {
				}
				
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception e2) {
				}
				
			}
		}

		return datePath + "/" + fileName;
	}
	public String getUrl(String url) {
		return "../" + imageUrl + "/" + url;
	}

	public String downImageTest(String imgUrl) throws Exception {

		String datePath = DateUtil.getDate();
		String filePath = "d:/test/" + datePath;
		String fileName = UUID.randomUUID().toString();
		// 构造URL
		URL url = new URL(imgUrl);
		InputStream is = null;
		OutputStream os = null;
		try {
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
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
			System.out.println(is.available());
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (os != null) {
				os.close();
			}
			if (is != null) {
				is.close();
			}
		}

		return datePath + "/" + fileName;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(new DownImage()
				.downImageTest("https://mmbiz.qpic.cn/mmbiz_png/2py1QTs1A9CicCmPtJZl9ibWZoIzo6iaRvrcfiaC9LGl7xRvibxXU0n9QdgPwfQ1C0QzstpzW0V0mia54JpD1B3G4n4g/640?wx_fmt=png"));
	
		//download("http://opgirl-tmp.adbxb.cn/images2/2943/302_b0c6acbc4aa7a3953e7e3368f437a28a_75.jpg", "51bi.gif","d:\\test\\");  
	}

	public static String Inputstr2Str_Reader(InputStream in, String encode) {

		String str = "";
		try {
			if (encode == null || encode.equals("")) {
				// 默认以utf-8形式
				encode = "utf-8";
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, encode));
			StringBuffer sb = new StringBuffer();

			while ((str = reader.readLine()) != null) {
				sb.append(str).append("\n");
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}

	public static void download(String urlString, String filename, String savePath) throws Exception {
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为5s
		con.setConnectTimeout(5 * 1000);
		// 输入流
		InputStream is = con.getInputStream();

		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}
}
