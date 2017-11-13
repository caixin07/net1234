
				<#list newsList as news>
				<!--图片数量小于3-->
				<#if (news.images?size <= 3 || news.morePic == "0" || (news.url_from??&& news.url_from=="wx"))>
					<div class="media excerpt  more-mode"
					style="border-bottom: 1px solid #e8e8e8;">
					<div class="media-left">
						<#list news.images as image> <#if image_index <= 0> <a href="${news.href}"
							class="img-wrap img-item1"><img class="thumb"
							data-original="/img/${image.path_}" alt="..."></a> </#if> </#list>

					</div>
					<div class="media-body">
						<header>
							<h4><a href="${news.href}" class="link">${news.title}</a></h4>
						</header>
						<p class="meta">
							<span style="margin-right: 20px;"><i class="fa fa-clock-o"></i>
								${news.synTime?substring(0,10)}</span> <span
								style="margin-right: 20px;" class=" hidden-xs"><i
								class="fa fa-user"></i> ${news.user}</span> <span
								style="margin-right: 20px;"><i class="fa fa-eye"></i>阅读(${news.count})</span>

						</p>
						<#if !(news.url_from??&& news.url_from=="wx")>
						
						<p class="note  hidden-xs">
						<#if (news.text?length gt
							81)>${news.text?substring(0,80)}<#else> ${news.text} </#if>...</p>
					</#if>
					</div>
				</div>
				<#else>
					<div class="media excerpt more-mode"
					style="border-bottom: 1px solid #e8e8e8">
					<div class="title-box">
						<a href="${news.href}"  class="link"><h4>${news.title}</h4></a>
					</div>
					<div class="bui-box img-list">
						<#list news.images as image> <#if image_index <=2> <a href="${news.href}"
						 class="img-wrap img-item"> <img
							data-original="/img/${image.path_}" class="img-responsive"></a> </#if> <#if image_index ==3> <a
							href="" target="_blank" class="img-wrap img-item hidden-xs">
							<img data-original="/img/${image.path_}">
						</a> </#if> </#list>

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
				</#if>
				
				</#list> 
				<nav aria-label="..." >
					<ul class="pagination">
						${pageList}
					</ul>
				</nav>