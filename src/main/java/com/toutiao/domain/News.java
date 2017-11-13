package com.toutiao.domain;

import java.util.List;

public class News {

	
	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", user=" + user + ", time=" + time + ", text=" + text
				+ ", synTime=" + synTime + ", url_=" + url_ + ", img_=" + img_ + ", imgAlt=" + imgAlt + ", href=" + href
				+ ", isShow=" + isShow + ", count=" + count + ", type=" + type + ", keys_=" + keys_ + ", des_=" + des_
				+ ", baidusend=" + baidusend + ", images=" + images + "]";
	}
	String id;
	String title;
	String user;
	String time;
	String text;
	String synTime;
	String url_;
	String img_;
	String imgAlt;
	String href;
	String isShow;
	String count;
	String count_;
	String type;
	String keys_;
	String des_;
	String baidusend;
	String morePic;
	String url_from;
	private List<Image> images;
	
	public String getBaidusend() {
		return baidusend;
	}
	public void setBaidusend(String baidusend) {
		this.baidusend = baidusend;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public String getCount_() {
		return count_;
	}
	public void setCount_(String count_) {
		this.count_ = count_;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getImgAlt() {
		return imgAlt;
	}
	public void setImgAlt(String imgAlt) {
		this.imgAlt = imgAlt;
	}

	public String getUrl_() {
		return url_;
	}
	public void setUrl_(String url_) {
		this.url_ = url_;
	}
	public String getImg_() {
		return img_;
	}
	public void setImg_(String img_) {
		this.img_ = img_;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSynTime() {
		return synTime;
	}
	public void setSynTime(String synTime) {
		this.synTime = synTime;
	}
	public String getKeys_() {
		return keys_;
	}
	public void setKeys_(String keys_) {
		this.keys_ = keys_;
	}
	public String getDes_() {
		return des_;
	}
	public void setDes_(String des_) {
		this.des_ = des_;
	}
	public String getBaiduSend() {
		return baidusend;
	}
	public void setBaiduSend(String baiduSend) {
		this.baidusend = baiduSend;
	}
	public String getMorePic() {
		return morePic;
	}
	public void setMorePic(String morePic) {
		this.morePic = morePic;
	}
	public String getUrl_from() {
		return url_from;
	}
	public void setUrl_from(String url_from) {
		this.url_from = url_from;
	}

	
}
