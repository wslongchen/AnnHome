<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
		<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<script src="<?php bloginfo('template_url'); ?>/js/myjs.js" type="text/javascript"></script>
		<link href="<?php bloginfo('template_url'); ?>/style.css" rel="stylesheet" />
		<link rel="pingback" href="<?php bloginfo('pingback_url'); ?>" />
		<title>
			<?php
			if (is_home()) {
				bloginfo('name');
				echo " - ";
				bloginfo('description');
			} elseif (is_category()) {
				single_cat_title();
				echo " - ";
				bloginfo('name');
			} elseif (is_single() || is_page()) {
				single_post_title();
			} elseif (is_search()) {
				echo "搜索结果";
				echo " - ";
				bloginfo('name');
			} elseif (is_404()) {
				echo '页面未找到!';
			} else {
				wp_title('', true);
			}
 ?>
		</title>
		<link rel="alternate" type="application/rss+xml" title="RSS 2.0 - 所有文章" href="<?php echo get_bloginfo('rss2_url'); ?>" />
		<link rel="alternate" type="application/rss+xml" title="RSS 2.0 - 所有评论" href="<?php bloginfo('comments_rss2_url'); ?>" />
		<?php wp_head(); ?>
	</head>
	<?php flush(); ?>
	<body>
		<header class="navbar navbar-default navbar-fixed-top">
			<!--inverse-->
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="<?php echo get_option('home'); ?>"><?php bloginfo('name'); ?></a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<!--导航条-->
					<ul class="nav navbar-nav">
						<ul class="nav navbar-nav nav-pills">
							<li role="presentation" <?php
							if (is_home()) { echo 'class="active"';
							}
 ?>><a href="<?php echo get_option('home'); ?>/">首页</a></li>
							<!--<?php wp_list_categories('depth=1&title_li=0&sort_column=menu_order'); ?>-->
								<?php
								$args = array('orderby' => 'name', 'parent' => 0);
								$categories = get_categories($args);
								foreach ($categories as $category) {
									if (get_category_children($category->term_id) != "") {
										$args2 = array('orderby' => 'name', 'child_of' => $category->term_id);
										$categories2 = get_categories($args2);
											echo '<li class="dropdown">
												<a href="'.get_category_link($category -> term_id).'" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">'.$category->name.'<span class="badge">'.$category->count.'</span><span class="caret"></span></a>';
											echo '<ul class="dropdown-menu">';
										foreach ($categories2 as $category2) {
											echo '<li><a href="'.get_category_link($category2 -> term_id).'" id="tencent">'.$category2->name.'<span class="badge">'.$category2->count.'</span></a></li>';
										}
											echo '</ul></li>';
									} else {
										echo ' <li role="presentation">';
										echo ' <a href="' . get_category_link($category -> term_id) . '">' . $category -> name . '<span class="badge">'.$category->count.'</span> </a>';
										echo ' </li>';
									}
									
								}
							?>
							<?php wp_list_pages('depth=1&title_li=0&sort_column=menu_order'); ?>
						</ul>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">分享<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="#" id="tencent">QQ空间</a></li>
								<li role="separator" class="divider"></li>
								<li><a href="#" id="qq">QQ</a></li>
								<li role="separator" class="divider"></li>
								<li>
									<a href="javascript:void(function(){var d=document,e=encodeURIComponent,s1=window.getSelection,s2=d.getSelection,s3=d.selection,s=s1?s1():s2?s2():s3?s3.createRange().text:'',r='http://www.douban.com/recommend/?url='+e(d.location.href)+'&title='+e(d.title)+'&sel='+e(s)+'&v=1',w=450,h=330,x=function(){if(!window.open(r,'douban','toolbar=0,resizable=1,scrollbars=yes,status=1,width='+w+',height='+h+',left='+(screen.width-w)/2+',top='+(screen.height-h)/2))location.href=r+'&r=1'};if(/Firefox/.test(navigator.userAgent)){setTimeout(x,0)}else{x()}})()"><img src="http://img3.douban.com/pics/fw2douban1.png" alt="推荐到豆瓣" /></a>
								</li>
								<li role="separator" class="divider"></li>
								<li><a href="#" id="sina">新浪微博</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</header>