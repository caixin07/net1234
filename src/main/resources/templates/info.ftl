<#include "/public/index.ftl">
<div class="content-wrap">
	<div class="content">
		<header class="article-header">
			<h1 class="article-title">
				<a href="">${news.title}</a>
			</h1>
			<div class="article-meta">
				<span class="item">${news.synTime}</span> <span
					class="item post-views">阅读(${news.count})</span>
				<!-- <span class="item">评论(0)</span> -->
				<span class="item"></span>
			</div>
		</header>
		<article class="article-content">${news.text}
	</div>
</div>
<#include "/public/index2.ftl">
