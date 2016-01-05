<?php $popular = new WP_Query('orderby=comment_count&posts_per_page=10'); ?>   
<?php while ($popular->have_posts()) : $popular->the_post(); ?>   
<li><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></li>   
<?php endwhile; ?>  