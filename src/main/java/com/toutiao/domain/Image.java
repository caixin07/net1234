package com.toutiao.domain;

public class Image {

	@Override
	public String toString() {
		return "Image [id_=" + id_ + ", news_id=" + news_id + ", path_=" + path_ + ", alt_=" + alt_ + ", type_=" + type_
				+ ", size_=" + size_ + ", src_=" + src_ + "]";
	}
	String id_;
	String news_id;
	public String getNews_id() {
		return news_id;
	}
	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}
	String path_;
	String alt_;
	String type_;
	String size_;
	String src_;
	public String getId_() {
		return id_;
	}
	public void setId_(String id_) {
		this.id_ = id_;
	}
	public String getPath_() {
		return path_;
	}
	public void setPath_(String path_) {
		this.path_ = path_;
	}
	public String getAlt_() {
		return alt_;
	}
	public void setAlt_(String alt_) {
		this.alt_ = alt_;
	}
	public String getType_() {
		return type_;
	}
	public void setType_(String type_) {
		this.type_ = type_;
	}
	public String getSize_() {
		return size_;
	}
	public void setSize_(String size_) {
		this.size_ = size_;
	}
	public String getSrc_() {
		return src_;
	}
	public void setSrc_(String src_) {
		this.src_ = src_;
	}	
	
}
