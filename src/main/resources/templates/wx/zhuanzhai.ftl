<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/bootstrap.min.css">
<style>
.container {
	height: 100%;
	vertical-align: middle;
}

.row {
	display: table-cell;
	vertical-align: middle;
}
</style>
</head>
<body>


	<div class="container">
		<div class="row">
			<form class="form-inline" action="/wx/tj">
				<div class="form-group">
					<label for="exampleInputName2">URL</label> <input type="text"
						class="form-control" id="url" name="url" placeholder="URL">
				</div>
				<div class="form-group">
					<select class="form-control" name="type" >
  <option value="kj">科技</option>
  <option value="mv">美女</option>
  <option>3</option>
  <option>4</option>
  <option>5</option>
</select>
				</div>
				<button type="submit" class="btn btn-default">Send
					invitation</button>
			</form>
		</div>

	</div>
</body>
</html>