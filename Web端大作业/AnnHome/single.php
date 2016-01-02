<?php get_header(); ?>
		<!--内容-->
		<div class="container-fluid">
			<div class="alert alert-danger alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				＝.＝请发现bug的亲们联系我～</div>
			<div class="row-fluid">
				<div class="row">
					<div class="col-xs-12 col-sm-6 col-md-8">
						<?php if (have_posts()) : the_post(); update_post_caches($posts); ?>
						<!--文章内容-->
						<!--<div class="panel panel-default">
							<div class="panel-body">-->
								<div class="page-header">
									<h3 class="panel-title">
										<h2><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a><small><?php the_author(); ?></small></h2>
										<small><?php the_time('Y年n月j日') ?>   <?php comments_popup_link('0 条评论', '1 条评论', '% 条评论', '', '评论已关闭'); ?></small>
									</h3>
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
								<li class="previous disabled"><a href="#"><span aria-hidden="true">&larr;</span> 上一篇</a></li>
								<li class="next"><a href="#">下一篇 <span aria-hidden="true">&rarr;</span></a></li>
							</ul>
						</nav>
						<!--评论-->
						<?php comments_template(); ?>
					</div>
						<!--侧边栏-->
						<?php get_sidebar(); ?>
					<div class="clearfix visible-xs-block"></div>
				</div>
			</div>
		</div>
		<!--底部-->
		<?php get_footer(); ?>