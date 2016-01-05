<?php /*
 Template Name: 联系我们页面
 */
?>
<?php get_header(); ?>
		<?php if (have_posts()) : the_post(); update_post_caches($posts); ?>
<div class="container">
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="panel-title">
				<h2><?php the_title(); ?></h2>
			</div>
		</div>
		<div class="panel-body">
			<?php the_content(); ?>
				<?php else : ?>
 						   <div class="errorbox">
 						       没有文章！
 						   </div>
    				<?php endif; ?>
    			<?php comments_template(); ?>		
		</div>
	</div>		
</div>
<!--底部-->
		<?php get_footer(); ?>