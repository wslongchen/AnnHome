<div class="col-xs-6 col-md-4">
	<?php if (!function_exists('dynamic_sidebar') || !dynamic_sidebar('侧边栏1') ) :


	?>
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
	<?php else: ?>
	<?php endif; ?>
	<?php if (!function_exists('dynamic_sidebar') || !dynamic_sidebar('侧边栏2') ) :
	?>
	<div class="panel panel-default">
		<div class="panel-heading">
			分类
		</div>
		<!-- List group -->
		<ul class="list-group">
			<?php $args = array('orderby' => 'name', 'order' => 'ASC', 'parent' => 0);
			$categories = get_categories($args);
			foreach ($categories as $category) {
				echo ' <li class="list-group-item list-group-item-info" role="presentation">';
				echo '<span class="badge">' . $category -> count . '</span>';
				echo ' <a href="' . get_category_link($category -> term_id) . '">' . $category -> name . ' </a>';
				echo ' </li>';
			}
			?>
		</ul>
	</div>
	<div class="well">
		<?php
			global $wpdb;
			$count = $wpdb -> get_var($wpdb -> prepare("SELECT sum(count) FROM wp_access"));
			?>
		<center>
			<h4>
				访问记录
			</h4>
		</center>
		<br>
			<?php 
 				$windows = $wpdb->get_var($wpdb->prepare("SELECT count FROM wp_access where device='windows'"));
 				$windows_count=$windows/$count; $windows_count=sprintf("%.2f",$windows_count*100);
				$mac = $wpdb->get_var($wpdb->prepare("SELECT count FROM wp_access where device='mac'"));
 				$mac_count=$mac/$count; $mac_count=sprintf("%.2f",$mac_count*100);
				$ios = $wpdb->get_var($wpdb->prepare("SELECT count FROM wp_access where device='ios'"));
 				$ios_count=$ios/$count; $ios_count=sprintf("%.2f",$ios_count*100);
				$android = $wpdb->get_var($wpdb->prepare("SELECT count FROM wp_access where device='android'"));
 				$android_count=$android/$count; $android_count=sprintf("%.2f",$android_count*100);
				$others = $wpdb->get_var($wpdb->prepare("SELECT count FROM wp_access where device='others'"));
 				$others_count=$others/$count; $others_count=sprintf("%.2f",$others_count*100);
 		?>
		<strong>Windows PC</strong><span class="pull-right"><?php echo $windows_count; ?>%</span>
		<div class="progress">
			<div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="<?php echo $windows_count; ?>" aria-valuemin="0" aria-valuemax="100" style="width: <?php echo $windows_count; ?>%">
				<span class="sr-only">40% Complete (success)</span>
			</div>
		</div>
		<strong>Mac</strong><span class="pull-right"><?php echo $mac_count; ?>%</span>
		<div class="progress">
			<div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="<?php echo $mac_count; ?>" aria-valuemin="0" aria-valuemax="100" style="width: <?php echo $mac_count; ?>%">
				<span class="sr-only"><?php echo $mac_count; ?>% Complete</span>
			</div>
		</div>
		<strong>iPad/iPhone</strong><span class="pull-right"><?php echo $ios_count; ?>%</span>
		<div class="progress">
			<div class="progress-bar progress-bar-warning progress-bar-striped" role="progressbar" aria-valuenow="<?php echo $ios_count; ?>" aria-valuemin="0" aria-valuemax="100" style="width: <?php echo $ios_count; ?>%">
				<span class="sr-only"><?php echo $ios_count; ?>% Complete (warning)</span>
			</div>
		</div>
		<strong>Android</strong><span class="pull-right"><?php echo $android_count; ?>%</span>
		<div class="progress">
			<div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" aria-valuenow="<?php echo $android_count; ?>" aria-valuemin="0" aria-valuemax="100" style="width: <?php echo $android_count; ?>%">
				<span class="sr-only"><?php echo $android_count; ?>% Complete (danger)</span>
			</div>
		</div>
		<strong>Others</strong><span class="pull-right"><?php echo $others_count; ?>%</span>
		<div class="progress">
			<div class="progress-bar  progress-bar-striped" role="progressbar" aria-valuenow="<?php echo $others_count; ?>" aria-valuemin="0" aria-valuemax="100" style="width: <?php echo $others_count; ?>%">
				<span class="sr-only"><?php echo $others_count; ?>% Complete (danger)</span>
			</div>
		</div>
		<p class="text-warning text-right">
			小安安一共被访问了<?php echo $count; ?>次
		</p>
	</div>
	<?php else: ?>
	<?php endif; ?>
</div>