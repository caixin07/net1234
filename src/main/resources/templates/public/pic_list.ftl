<div class="container-fluid">
<div class="row">
<#list picList as pic>
	<div class="col-sm-2 col-xs-4" style="padding:5px;">
		<div class="thumbnail">
			<a href="/pic/id/${pic.id_}"><img class="img-responsive img-rounded" style="height:150px;" src="/img/${pic.path_}"
				alt="${pic.alt_}"></a>
		</div>
	</div>
</#list>
</div>
</div>
							<div class="pagination">
					<ul>
						<li class="prev-page"></li>
						${pageList}

					</ul>
				</div>