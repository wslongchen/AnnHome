<?php
/*
Template Name: Page without sidebar
*/
?>
	<?php get_header(); ?>
		<?php if (have_posts()) : the_post(); update_post_caches($posts); ?>
			<div>
				<div class="page-header">
					<h1><?php the_title(); ?></h1>
				</div>
				<?php the_content(); ?>
					<?php comments_template(); ?>
			</div>
			<?php else : ?>
				<div>
					没有文章
				</div>
				<?php endif; ?>
					<!--底部-->
					<?php get_footer(); ?>