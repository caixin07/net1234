<!DOCTYPE html>
<html lang="zh-CN">
<head>
<script async src="/js/googleAd.js"></script>
<script>
  (adsbygoogle = window.adsbygoogle || []).push({
    google_ad_client: "ca-pub-4289496536938075",
    enable_page_level_ads: true
  });
</script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap 101 Template</title>
<link rel="stylesheet" href="/css/boot.css">
<link rel="stylesheet" href="/css/main.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
</head>
<body>
	<!-- Static navbar -->
	<nav class="navbar  navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header" style="text-align: center">
				<a class="navbar-brand" href="#"></a>
			</div>

			<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right hidden-xs">
			<li><div class="bdsharebuttonbox"><a href="#" class="bds_more" data-cmd="more"></a><a href="#" class="bds_qzone" data-cmd="qzone"></a><a href="#" class="bds_tsina" data-cmd="tsina"></a><a href="#" class="bds_tqq" data-cmd="tqq"></a><a href="#" class="bds_renren" data-cmd="renren"></a><a href="#" class="bds_weixin" data-cmd="weixin"></a></div>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdPic":"","bdStyle":"0","bdSize":"16"},"share":{},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"分享到：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
				</li>
				</ul>
				<ul class="nav navbar-nav navbar-right visible-xs-block">
					<li class="active"><a href="#">推荐</a></li>
					<li><a href="#">两性</a></li>
					<li><a href="#">美女</a></li>
					<li><a href="#">娱乐</a></li>
					<li><a href="#">影片</a></li>
					<li><a href="#">科技</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	</nav>


	<div class="container" >

		<div class="row">
			<div class="col-sm-2 hidden-xs">
				<div class="bui-left index-channel" style="float:right">
					<div>
						<div ga_event="left-channel-click" class="channel">
							<ul>
								<li><a href="/" target="_self"
									ga_event="channel_recommand_click" class="channel-item active"><span>推荐</span></a></li>
								<li><a href="/ch/news_hot/" target="_self"
									ga_event="channel_hot_click" class="channel-item"><span>两性</span></a></li>
								<li><a href="/ch/video/" target="_self"
									ga_event="channel_video_click" class="channel-item"><span>美女</span></a></li>
								<li><a href="/ch/news_image/" target="_blank"
									ga_event="channel_image_click" class="channel-item"><span>娱乐</span></a></li>
								<li><a href="/ch/essay_joke/" target="_self"
									ga_event="channel_joke_click" class="channel-item"><span>影片</span></a></li>
								<li><a href="/ch/news_society/" target="_self"
									ga_event="channel_social_click" class="channel-item"><span>科技</span></a></li>
								<li><a href="/ch/news_entertainment/" target="_self"
									ga_event="channel_entertainment_click" class="channel-item"><span>娱乐</span></a></li>
								<li><a href="/ch/news_tech/" target="_self"
									ga_event="channel_technology_click" class="channel-item"><span>科技</span></a></li>
								
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-7">
				<div class="page-header hidden-xs">
					<h2 style="margin-top: 5px; margin-bottom: 10px;">
						<small>热门推荐</small>
					</h2>
				</div>
<#list newsList as news>
				<div class="media excerpt  more-mode"
					style="border-bottom: 1px solid #e8e8e8;">
					<div class="media-left">
											<#list news.images as image>
							<#if image_index <= 0>  
						<a href="#" class="img-wrap img-item1"><img class="thumb"
							src="/img/${image.path_}" alt="..."></a>
							</#if>
						</#list>

					</div>
					<div class="media-body">
						<header>
							<h4>${news.title}</h4>
						</header>
						<p class="meta">
							<span style="margin-right: 20px;"><i class="fa fa-clock-o"></i>
								${news.synTime?substring(0,10)}</span> <span style="margin-right: 20px;" class = " hidden-xs"><i
								class="fa fa-user" ></i> ${news.user}</span> <span style="margin-right: 20px;"><i
								class="fa fa-eye"></i>阅读(${news.count})</span>

						</p>
						<p class="note  hidden-xs"><#if news.text?length gt 81>${news.text?substring(0,80)}<#else> ${news.text} </#if>...</p>
					</div>
				</div>
</#list>
<#list newsList as news>
				<div class="media excerpt more-mode"
					style="border-bottom: 1px solid #e8e8e8">
					<div class="title-box">
						<a href="" target="_blank"><h4>${news.title}</h4></a>
					</div>
					<div class="bui-box img-list">
					<#list news.images as image>
						<#if image_index <=2>  
							<a href="" target="_blank" class="img-wrap img-item"> <img
							src="/img/${image.path_}"></a> 
						</#if>
						<#if image_index ==3>  
							<a href="" target="_blank" class="img-wrap img-item hidden-xs">
							<img src="/img/${image.path_}" >
						</a>
						</#if>

					</#list>

					</div>
					<div class="bui-box footer-bar">
						<div class="excerpt">
							<p class="mate">
								<span style="margin-right: 20px;"><i
									class="fa fa-clock-o"></i> ${news.synTime?substring(0,10)}</span> <span
									style="margin-right: 20px;"><i class="fa fa-user"></i>
									 ${news.user}</span> <span style="margin-right: 20px;"><i
									class="fa fa-eye"></i>阅读(${news.count})</span>
							</p>
						</div>
					</div>
				</div>
				</#list>
<nav aria-label="..." style="text-align:center;">
  <ul class="pagination">
    <li><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
    <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
    <li ><a href="#">2 <span class="sr-only">(current)</span></a></li>
    <li ><a href="#">3 <span class="sr-only">(current)</span></a></li>
    <li ><a href="#">4 <span class="sr-only">(current)</span></a></li>
    <li ><a href="#">5 <span class="sr-only">(current)</span></a></li>
       <li>
      <a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>
			</div>
			<div class="col-sm-3">
				<div class="row">
					<div class="col-xs-12" style="background-color: #f4f5f6;">
						<div class="page-header hidden-xs">
							<h4
								style="color: #222; padding: 0; margin-bottom: 8px; font-size: 18px; font-weight: 700;">
								热门排行</h4>
						</div>
						<div class="media excerpt">
							<div class="media-left">
								<a href="#"><img width="102px" height="73px"
									src="/img/s_6875465bbe164c3d94711a976f94de06.png" alt="..."></a>
							</div>
							<div class="media-body" style="vertical-align: middle">
								<p>就是动画电影！这款2D游戏大作要火：上瘾</p>
							</div>
						</div>
						<div class="media excerpt">
							<div class="media-left">
								<a href="#"><img width="102px" height="73px"
									src="/img/s_6875465bbe164c3d94711a976f94de06.png" alt="..."></a>
							</div>
							<div class="media-body" style="vertical-align: middle">
								<p>就是动画电影！这款2D游戏大作要火：上瘾</p>
							</div>
						</div>
						<div class="media excerpt">
							<div class="media-left">
								<a href="#"><img width="102px" height="73px"
									src="/img/s_6875465bbe164c3d94711a976f94de06.png" alt="..."></a>
							</div>
							<div class="media-body" style="vertical-align: middle">
								<p>就是动画电影！这款2D游戏大作要火：上瘾</p>
							</div>
						</div>
					</div>

					<div class="col-xs-12"
						style="background-color: #f4f5f6; margin-top: 20px;">
						<div class="row" style="margin-top: 10px; margin-bottom: 10px;">
							<div class="col-xs-4">
								<img src="/img/296_b76ed109ce9c990b298314f3e54fcde5_75.jpg"
									alt="..." class="img-responsive img-rounded"
									alt="Responsive image">
							</div>
							<div class="col-xs-4">
								<img src="/img/294_433eb5240e6b6a1db1a071cf91893f5a_75.jpg"
									alt="..." class="img-responsive img-rounded"
									alt="Responsive image">
							</div>
							<div class="col-xs-4">
								<img src="/img/296_b76ed109ce9c990b298314f3e54fcde5_75.jpg"
									alt="..." class="img-responsive img-rounded"
									alt="Responsive image">
							</div>
						</div>
					</div>
					<div class="col-xs-12"
						style="background-color: #f4f5f6; margin-top: 20px;">
						<div class="page-header hidden-xs">
							<h4
								style="color: #222; padding: 0; margin-bottom: 8px; font-size: 18px; font-weight: 700;">
								友情链接</h4>
						</div>
						<ul class="friend-links-content"
							style="list-style: none; margin-bottom: 5px;">
							<li class="item"><a target="_blank"
								href="http://www.gmw.cn/">光明网</a></li>
							<li class="item"><a target="_blank"
								href="http://www.cnr.cn/">央广网</a></li>
							<li class="item"><a target="_blank" href="http://www.cri.cn">国际在线</a></li>
							<li class="item"><a target="_blank"
								href="http://www.tibet.cn/">中国西藏网</a></li>
							<li class="item"><a target="_blank"
								href="http://www.cankaoxiaoxi.com/">参考消息</a></li>
						</ul>
					</div>

				</div>

			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">.col-sm-6</div>
		</div>

	</div>
	<script src="/js/jquery.min.js"></script>
	<script
		src="/js/bootstrap.min.js"></script>
</body>
</html>