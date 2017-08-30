<#include "/public/index.ftl">
		<div class="content-wrap">
			<div class="content">
				<div class="title">
					<h3>最新发布</h3>
				</div>
<#assign i = 1>
<#list newsList as news>

				<article class="excerpt excerpt-${i}">
					<a class="focus" href="${news.href}"><img
						src="${news.img_}"
						class="thumb" alt="${news.imgAlt}"></a>
					<header>
						<!--<a class="cat" href="#">${news.type}<i></i></a>-->
						<h2>
							<a href="${news.href}"
								title="${news.title}">${news.title}</a>
						</h2>
					</header>
					<p class="meta">
						<time>
							<i class="fa fa-clock-o"></i>${news.synTime}
						</time>
						<span class="author"><i class="fa fa-user"></i>${news.user}</span><span
							class="pv"><i class="fa fa-eye"></i>阅读(${news.count})</span><!-- <a class="pc"
							href="#"><i
							class="fa fa-comments-o"></i>评论(0)</a> -->
					</p>
					<p class="note"><#if news.text?length gt 171>${news.text?substring(0,170)}<#else> ${news.text} </#if>...</p>
				</article>
				<#assign i = i+1>
</#list>
				<div class="pagination">
					<ul>
						<li class="prev-page"></li>
						${pageList}

					</ul>
				</div>
			</div>
		</div>
<#include "/public/index2.ftl">