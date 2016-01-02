<div class="panel panel-default">
							<div class="panel-body">
								<span class="label label-default">小安安</span>
								<span class="label label-success">WordPress</span>
								<span class="label label-info">潘安</span>
								<span class="label label-warning">个人博客</span>
								<span class="label label-danger">mrpan</span>
								<div class="row">
									<div class="col-xs-3">
										<img src="<?php bloginfo('template_url'); ?>/img/logo.png" alt="mrpan" class="img-circle" id="index_logo">

									</div>
									<h3>潘安<small>偶尔卖唱的小码农</small></h2>
								</div>
								<ul>
									<li>生于一个叫做凤凰的小镇</li>
									<li>喜欢唱歌，不喜撸码</li>
									<li>中度github用户</li>
								</ul>
							</div>
						</div>
						<div class="panel panel-default">
							<?php if ( !function_exists('dynamic_sidebar') || !dynamic_sidebar('First_sidebar') ) : ?>
							<div class="panel-heading">分类</div>
							<!-- List group -->
							<ul class="list-group">
								<?php wp_list_categories('depth=1&title_li=&orderby=id&show_count=0&hide_empty=1&child_of=0'); ?>
								<li class="list-group-item list-group-item-success"><span class="badge">14</span><a href="#">java</a></li>
								<li class="list-group-item list-group-item-info"><span class="badge">4</span>c#</li>
								<li class="list-group-item list-group-item-warning"><span class="badge">1</span>lua</li>
								<li class="list-group-item list-group-item-danger"><span class="badge">4</span>php</li>
							</ul>
							<?php endif; ?>
						</div>