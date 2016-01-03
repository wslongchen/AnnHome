<div class="col-xs-6 col-md-4">
	<div class="panel panel-default">
	<div class="panel-body">
		<span class="label label-default">小安安</span>
		<span class="label label-success">WordPress</span>
		<span class="label label-info">潘安</span>
		<span class="label label-warning">个人博客</span>
		<span class="label label-danger">mrpan</span>
	</div>
	</div>
<div class="panel panel-default">
	<div class="panel-body">
		<h3>
			潘安<small>偶尔卖唱的小码农</small></h2>
			<ul>
				<li>
					生于一个叫做凤凰的小镇
				</li>
				<li>
					喜欢唱歌，不喜撸码
				</li>
				<li>
					中度github用户
				</li>
			</ul>
	</div>
</div>
<div class="panel panel-default">
	<?php if ( !function_exists('dynamic_sidebar') || !dynamic_sidebar('First_sidebar') ) :
	?>
	<div class="panel-heading">
		分类
	</div>
	<!-- List group -->
	<ul class="list-group">
		<?php
		$args = array('orderby' => 'name', 'order' => 'ASC', 'parent' => 0);
		$categories = get_categories($args);
		foreach ($categories as $category) {
			echo ' <li class="list-group-item list-group-item-info" role="presentation">';
			echo '<span class="badge">'.$category->count .'</span>';
			echo ' <a href="' . get_category_link($category -> term_id) . '">' . $category -> name . ' </a>';
			echo ' </li>';
		}
		?>
	</ul>
	<?php endif; ?>
</div>
</div>