		<aside class="sidebar">
			<div class="widget widget_ui_posts">
				<h3>置顶推荐</h3>
 				<ul>
<#list pubNewsList as news>
					<li><a href="${news.href}"><span
							class="thumbnail"><img
								src="${news.img_}"
								class="thumb" alt="${news.imgAlt}"></span><span class="text">${news.title}</span><span
							class="muted">${news.synTime}</span><span class="muted">阅读(${news.count})</span></a></li>
</#list>					
				</ul>
			</div>
			<!--<div class="widget widget_ui_statistics">
				<h3>网站统计</h3>
				<ul>
					<li><strong>日志总数：</strong>3219</li>
					<li><strong>评论总数：</strong>77</li>
					<li><strong>标签总数：</strong>4716</li>
					<li><strong>页面总数：</strong>0</li>
					<li><strong>分类总数：</strong>9</li>
					<li><strong>链接总数：</strong>2</li>
					<li><strong>用户总数：</strong>1</li>
					<li><strong>最后更新：</strong>2016-12-27</li>
				</ul>
			</div> -->
		</aside>
	</section>

	<footer class="footer">
	<div class="container">
	<p>&copy; 2017 内如如有侵权，请联系：281141469@qq.com
</p>
	</div>
</footer> 
<script>
window.jsui={
    www: '',
    uri: '',
    ver: '',
	roll: ["1","2"],
    ajaxpager: '0',
    url_rp: ''
};
</script>

	<script type='text/javascript'
		src='http://apps.bdimg.com/libs/bootstrap/3.2.0/js/bootstrap.min.js?ver=1.5'></script>
	<script type='text/javascript'
		src='/js/loader.js'></script>
	<script type='text/javascript'
		src='/js/wp-embed.min.js'></script>
</body>
</html>