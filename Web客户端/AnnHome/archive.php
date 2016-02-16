<?php get_header(); ?>

		<!--内容-->
		<div class="container">
			<ol class="breadcrumb">
 						 <li><a href="<?php echo get_option('home'); ?>/">首页</a></li>
					<?php  
					global $cat; $category = get_category($cat); 
							$category2=$category;  
						while($category->category_parent){
							$category=get_category($category->category_parent);
							echo '<li><a href="'.get_category_link($category->term_id).'">'.$category->name.'</a></li>';
						}

						echo '<li class="active">'.$category2->name.'（ 一共'.$category2->count.'篇文章 ）</li>';
						
					?>
  						 
 						 
					</ol>
				<div class="row">
					
					<div class="col-xs-12 col-sm-6 col-md-8">
						 <?php 
						 if (have_posts()) : while (have_posts()) : the_post(); ?>
						<!--文章内容-->
						<div class="post">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">
									<h2><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a><small>作者：<?php the_author(); ?></small></h2>
									<?php 
										the_tags('<h4 class="text-left">标签：', ' , ', '</h4>');
										?>
									<small><?php the_time('Y年n月j日') ?>   <?php comments_popup_link('0 条评论', '1 条评论', '% 条评论', '', '评论已关闭'); ?></small>
								</h3>
							</div>
							<div class="panel-body">
								<p class="text-justify">
									摘要：<?php the_excerpt(); ?>
								</p>
							</div>
						</div>
						</div>
								<!--<div class="list-group">
  									<div href="#" class="list-group-item">
  										<h3 class="list-group-item-heading">
											<h2><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a><small><?php the_author(); ?></small></h2>
											<small><?php the_time('Y年n月j日') ?>   <?php comments_popup_link('0 条评论', '1 条评论', '% 条评论', '', '评论已关闭'); ?></small>
										</h3>
    										<p class="list-group-item-text"><?php the_excerpt(); ?></p>
  									</div>
								</div>-->
						
						<?php endwhile; ?>
						<?php else : ?>
        					<h3 class="title"><a href="#" rel="bookmark">未找到</a></h3>
       					<p>没有找到任何文章！</p>
        				<?php endif; ?>
						<!--翻页-->
						<!--<p class="clearfix"><?php previous_posts_link('&lt;&lt; 查看新文章', 0); ?> <span class="float right"><?php next_posts_link('查看旧文章 &gt;&gt;', 0); ?></span></p>
<!--				<nav>
							<ul class="pagination">
								<li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
								<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">5</a></li>
								<li><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
							</ul>
						</nav>-->
						<?php if( function_exists( 'Bing_get_pagenavi' ) ) Bing_get_pagenavi(); ?>
							<div id="page"><?php mrpan_paginate();?></div>
					</div>
						<!--侧边栏-->
						<?php get_sidebar(); ?>
				</div>
		</div>
		
		<?php get_footer(); ?>