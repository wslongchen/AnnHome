<?php
    if (isset($_SERVER['SCRIPT_FILENAME']) && 'comments.php' == basename($_SERVER['SCRIPT_FILENAME']))
        die ('Please do not load this page directly. Thanks!');
?>
	<!--评论-->
	<div class="page-header">
		<h2>评论区</h2>
	</div>
	<ol class="commentlist">
		<?php 
    if (!empty($post->post_password) && $_COOKIE['wp-postpass_' . COOKIEHASH] != $post->post_password) { 
        // if there's a password
        // and it doesn't match the cookie
    ?>
			<li class="decmt-box">
				<p><a href="#addcomment">请输入密码再查看评论内容.</a></p>
			</li>
			<?php 
        } else if ( !comments_open() ) {
    ?>
				<li class="decmt-box">
					<p><a href="#addcomment">评论功能已经关闭!</a></p>
				</li>
				<?php 
        } else if ( !have_comments() ) { 
    ?>
					<li class="decmt-box">
						<p><a href="#addcomment">还没有任何评论，你来说两句吧</a></p>
					</li>
					<?php 
        } else {
            wp_list_comments('type=comment&callback=aurelius_comment');
        }
    ?>
	</ol>
	<div class="page-header">
		<h2>留下点什么</h2>
	</div>
	<!--评论表单-->
	<?php 
if ( !comments_open() ) :
// If registration required and not logged in.
elseif ( get_option('comment_registration') && !is_user_logged_in() ) : 
?>
<p>你必须 <a href="<?php echo wp_login_url( get_permalink() ); ?>">登录</a> 才能发表评论.</p>
<?php else  : ?>
	
	<form class="form-horizontal"action="<?php echo get_option('siteurl'); ?>/wp-comments-post.php" method="post" id="commentform">
		<?php if ( !is_user_logged_in() ) : ?>
		<div class="form-group">
			<label for="myInputName1" class="col-sm-2 control-label">你的名字</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="myInputName1" name="author" placeholder="小安安" value="<?php echo $comment_author; ?>" tabindex="1" />
			</div>
		</div>
		<div class="form-group">
			<label for="myInputEmail1" class="col-sm-2 control-label">邮箱地址</label>
			<div class="col-sm-10">
				<input type="email" class="form-control" id="myInputEmail1" name="email" placeholder="example@mrpann.com" value="<?php echo $comment_author_email; ?>" tabindex="2" />
			</div>
		</div>
		<div class="form-group">
			<label for="myInputWebsite1" class="col-sm-2 control-label">你的网址</label>
			<div class="col-sm-10">
				<input type="url" class="form-control" id="myInputWebsite1" name="url" placeholder="www.mrpann.com" value="<?php echo $comment_author_url; ?>" tabindex="3" />
				<?php else : ?>
        			<label class="clearfix">您已登录:<a href="<?php echo get_option('siteurl'); ?>/wp-admin/profile.php"><?php echo $user_identity; ?></a>. <a href="<?php echo wp_logout_url(get_permalink()); ?>" title="退出登录">退出 &raquo;</a></label>
        			<?php endif; ?>
			</div>
		</div>
		<div class="form-group">
			<label for="myInputComments1" class="col-sm-2 control-label">想说的话</label>
			<div class="col-sm-10">
				<textarea class="form-control" name="comment" id="myInputComments1" rows="5" placeholder="😄说点什么吧..." tabindex="4"></textarea>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10"> 
				<button type="submit" class="btn btn-primary btn-lg active" onClick="Javascript:document.forms['commentform'].submit()">提交</button>
			</div>
		</div>
	<?php comment_id_fields(); ?>
    <?php do_action('comment_form', $post->ID); ?>
	</form>