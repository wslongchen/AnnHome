<?php get_header(); ?>
		<!--内容-->
		<div class="container">
			<?php if (!function_exists('dynamic_sidebar') || !dynamic_sidebar('顶部栏') ) :
	?>
			<div class="alert alert-danger alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				＝.＝请发现bug的亲们联系我～</div>
				<?php else: ?>
		<?php endif; ?>
				<div class="row">
					<div class="col-xs-12 col-sm-6 col-md-8">
						<?php if (have_posts()) : the_post(); update_post_caches($posts); ?>
						<!--文章内容-->
						<!--<div class="panel panel-default">
							<div class="panel-body">-->
								<div class="page-header">
									<div class="panel-title">
										<h2><?php the_title(); ?><small>作者：<?php the_author(); ?></small></h2>
										<?php
										if (is_single()) {
											the_tags('<h4>标签：', ' , ', '</h4>');
										}
			?>
										<small><?php the_time('Y年n月j日') ?>   <?php comments_popup_link('0 条评论', '1 条评论', '% 条评论', '', '评论已关闭'); ?></small>
									</div>
								</div>
								<p class="text-justify">
									<?php the_content(); ?>
								</p>
							<!--</div>
						</div>-->
						<?php else : ?>
 						   <div class="errorbox">
 						       没有文章！
 						   </div>
    						<?php endif; ?>
						<!--翻页-->
						<nav>
							<ul class="pager">
								<?php
									$next_post = get_next_post();
									if (!empty( $next_post )): ?>
  									<li class="previous"><a href="<?php echo get_permalink( $next_post->ID ); ?>"><span aria-hidden="true">&larr;</span>上一篇，<?php echo $next_post->post_title; ?> </a></li>
  								<?php else :?>
  									 <li class="previous disabled"><a><span aria-hidden="true"></span> -.-木有了，已经是最新文章</a></li>
								<?php endif; ?>
								<?php
									$prev_post = get_previous_post();
									if (!empty( $prev_post )): ?>
  									<li class="next"><a href="<?php echo get_permalink( $prev_post->ID ); ?>"><?php echo $prev_post->post_title; ?>，下一篇<span aria-hidden="true">&rarr;</span></a></li>
								<?php else :?>
									<li class="next disabled"><a>-.-木有了，已经是最后一篇<span aria-hidden="true"></span></a></li>
								<?php endif; ?>
							</ul>
						</nav>
						<!--评论-->
						<?php comments_template(); ?>
					</div>
						<!--侧边栏-->
						<?php get_sidebar(); ?>
				</div>
		</div>
		<!--底部-->
		<?php get_footer(); ?>