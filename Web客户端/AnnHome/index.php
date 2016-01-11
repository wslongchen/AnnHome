<?php get_header(); ?>
		<!--内容-->
		<div class="container">
			<?php if (!function_exists('dynamic_sidebar') || !dynamic_sidebar('顶部栏') ) :
	?>
			<div class="alert alert-success" role="alert">
				与小安安同款WordPress主题即将发布～～详情点击
				<a href="#" class="alert-link">AnnHome</a>
			</div>
			<?php else: ?>
		<?php endif; ?>
				<div class="row">
					<div class="col-xs-12 col-sm-6 col-md-8">
						<?php if (have_posts()) : while (have_posts()) : the_post(); ?>
						<!--文章内容-->
						<div class="post">
						<article class="panel panel-default">
							<div class="panel-heading">
								<div class="panel-title">
									<h2><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a><small>作者：<?php the_author(); ?></small></h2>
									<?php 
										the_tags('<h4 class="text-right">', ' , ', '</h4>');
										?>
									<h4><small><?php the_time('Y年n月j日') ?>   <?php comments_popup_link('0 条评论', '1 条评论', '% 条评论', '', '评论已关闭'); ?></small></h4>
									
								</div>
							</div>
							<div class="panel-body">
									<?php the_content(); ?>
							</div>
						</article>
						</div>
						<?php endwhile; ?>
						<?php else : ?>
        					<h3 class="title"><a href="#" rel="bookmark">未找到</a></h3>
       					<p>没有找到任何文章！</p>
        					<?php endif; ?>
						<!--翻页-->
						<!--<p class="clearfix"><?php previous_posts_link('&lt;&lt; 查看新文章', 0); ?> <span class="float right"><?php next_posts_link('查看旧文章 &gt;&gt;', 0); ?></span></p>
-->						<nav hidden="hidden">
							<ul class="pagination">
								<li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
								<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">5</a></li>
								<li><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
							</ul>
						</nav>
						<div id="page"><?php par_pagenavi(5); ?></div>
					</div>			
						<!--侧边栏-->
						<?php get_sidebar(); ?>
				</div>
			</div>
		<?php get_footer(); ?>