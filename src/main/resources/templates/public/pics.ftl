<div class="container-fluid">
<div class="row">
<#list pics as pic>
	<div class="col-xs-12" style="padding:5px;">
		<div >
			<a href="/pic/id/${pic.id_}"><img class="img-responsive img-rounded" data-original="/img/${pic.path_}"
				alt="${pic.alt_}"></a>
		</div>
	</div>
</#list>
</div>
</div>