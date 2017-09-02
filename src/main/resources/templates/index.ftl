<!DOCTYPE html>
<html lang="zh-CN">
<head>
<script async src="/js/googleAd.js"></script>
<script>
	(adsbygoogle = window.adsbygoogle || []).push({
		google_ad_client : "ca-pub-4289496536938075",
		enable_page_level_ads : true
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
	<!-- 手机导航 -->
<#include "/public/m_nav.ftl">


	<div class="container">

		<div class="row">
			<div class="col-sm-2 hidden-xs">
				<#include "/public/pc_nav.ftl">
			</div>
			<div class="col-sm-7">
				<#include "/public/hot.ftl">
			</div>
			<div class="col-sm-3">
				<#include "/public/left.ftl">
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">.col-sm-6</div>
		</div>

	</div>
	<script src="/js/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
</body>
</html>