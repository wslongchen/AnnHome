<?php
/*
Template Name: 无侧边栏页面
*/
?>
	<?php get_header(); ?>
		<?php if (have_posts()) : the_post(); update_post_caches($posts); ?>
			<div class="container">
				<ol class="breadcrumb">
 					<li><a href="<?php echo get_option('home'); ?>/">首页</a></li>
					<li class="active"><?php the_title(); ?></li>	 
				</ol>
				<div class="page-header">
					<h1><?php the_title(); ?></h1>
				</div>
				<?php the_content(); ?>
				<?php else : ?>
 						   <div class="errorbox">
 						       没有文章！
 						   </div>
    						<?php endif; ?>
			</div>
<!--底部-->
		<?php get_footer(); ?>