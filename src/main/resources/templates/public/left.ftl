
<div class="row" style="margin-left: 0px;margin-right: 5px;">
	<div class="col-xs-12 hidden-xs" style="background-color: #f4f5f6;">
		<div class="page-header hidden-xs">
			<h4
				style="color: #222; padding: 0; margin-bottom: 8px; font-size: 18px; font-weight: 700;">
				热门排行</h4>
		</div>
		<#list pubNewsList as news>
		<div class="media excerpt">
			<div class="media-left">
				<#list news.images as image> <#if image_index <= 0> <a
					href="${news.href}"><img width="102px" height="73px"
					src="/img/${image.path_}" alt="..."></a></#if> </#list>

			</div>
			<div class="media-body" style="vertical-align: middle">
				<p>${news.title}</p>
			</div>
		</div>
		</#list>
	</div>
 	<div class="col-xs-12 visible-xs-inline-block" style="background-color: #f4f5f6;">
		<#list pubLxList as news>
		<div class="media excerpt">
			<div class="media-left">
				<#list news.images as image> <#if image_index <= 0> <a
					href="${news.href}"><img width="102px" height="73px"
					src="/img/${image.path_}" alt="..."></a></#if> </#list>

			</div>
			<div class="media-body" style="vertical-align: middle">
				<p>${news.title}</p>
			</div>
		</div>
		</#list>
	</div> 
 	<div class="col-xs-12"
		style="background-color: #f4f5f6; margin-top: 20px;">
		<div class="row" style="margin-top: 10px; margin-bottom: 10px;">
			<#list pubPicList as pic>
			<div class="col-xs-4" style="padding:5px;">
				<div class="thumbnail">
					<a href="/pic/id/${pic.id_}" style="display: inline-block;max-height: 110px;overflow: hidden;"><img
						class="img-responsive" src="/img/${pic.path_}"
						alt="${pic.alt_}" ></a>
				</div>
			</div>
			</#list>
		</div>
	</div>
	<div class="col-xs-12  hidden-xs"
		style="background-color: #f4f5f6; margin-top: 20px;">
		<div class="page-header hidden-xs">
			<h4
				style="color: #222; padding: 0; margin-bottom: 8px; font-size: 18px; font-weight: 700;">
				友情链接</h4>
		</div>
		<ul class="friend-links-content"
			style="list-style: none; margin-bottom: 5px;">
			<li class="item"><a target="_blank" href="http://www.gmw.cn/">光明网</a></li>
			<li class="item"><a target="_blank" href="http://www.cnr.cn/">央广网</a></li>
			<li class="item"><a target="_blank" href="http://www.cri.cn">国际在线</a></li>
			<li class="item"><a target="_blank" href="http://www.tibet.cn/">中国西藏网</a></li>
			<li class="item"><a target="_blank"
				href="http://www.cankaoxiaoxi.com/">参考消息</a></li>
			<li class="item"><a target="_blank"
				href="http://www.xian52.com/">西安发布网</a></li>
		</ul>
	</div>

</div>