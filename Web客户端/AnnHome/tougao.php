<?php /**
 * Template Name: 投稿界面
 *
 * 更新记录
 *  2016年01月10日 ：
 *  首个版本发布
 *
 */
get_header();
if (isset($_POST['tougao_form']) && $_POST['tougao_form'] == 'send') {
	global $wpdb;
	$current_url = 'http://www.mrpann.com/?page_id=136';
	// 注意修改此处的链接地址

	$last_post = $wpdb -> get_var("SELECT `post_date` FROM `$wpdb->posts` ORDER BY `post_date` DESC LIMIT 1");

	// 博客当前最新文章发布时间与要投稿的文章至少间隔120秒。
	// 可自行修改时间间隔，修改下面代码中的120即可
	// 相比Cookie来验证两次投稿的时间差，读数据库的方式更加安全
	if ((date_i18n('U') - strtotime($last_post)) < 120) {
		wp_die('您投稿也太勤快了吧，先歇会儿！<a href="' . $current_url . '">点此返回</a>');
	}

	// 表单变量初始化
	$name = isset($_POST['tougao_authorname']) ? trim(htmlspecialchars($_POST['tougao_authorname'], ENT_QUOTES)) : '';
	$email = isset($_POST['tougao_authoremail']) ? trim(htmlspecialchars($_POST['tougao_authoremail'], ENT_QUOTES)) : '';
	$blog = isset($_POST['tougao_authorblog']) ? trim(htmlspecialchars($_POST['tougao_authorblog'], ENT_QUOTES)) : '';
	$title = isset($_POST['tougao_title']) ? trim(htmlspecialchars($_POST['tougao_title'], ENT_QUOTES)) : '';
	$category = isset($_POST['cat']) ? (int)$_POST['cat'] : 0;
	$content = isset($_POST['tougao_content']) ? trim(htmlspecialchars($_POST['tougao_content'], ENT_QUOTES)) : '';
	$flag=TRUE;
	// 表单项数据验证
	if (empty($name) || mb_strlen($name) > 20) {
		//wp_die('昵称必须填写，且长度不得超过20字。<a href="' . $current_url . '">点此返回</a>');
		echo '<script>alert("昵称必须填写，且长度不得超过20字。")</script>';
		$flag=FALSE;
	}

	if (empty($email) || strlen($email) > 60 || !preg_match("/^([a-z0-9\+_\-]+)(\.[a-z0-9\+_\-]+)*@([a-z0-9\-]+\.)+[a-z]{2,6}$/ix", $email)) {
		//wp_die('Email必须填写，且长度不得超过60字，必须符合Email格式。<a href="' . $current_url . '">点此返回</a>');
	echo '<script>alert("Email必须填写，且长度不得超过60字，必须符合Email格式。")</script>';
	$flag=FALSE;
	}

	if (empty($title) || mb_strlen($title) > 100) {
		//wp_die('标题必须填写，且长度不得超过100字。<a href="' . $current_url . '">点此返回</a>');
		echo '<script>alert("标题必须填写，且长度不得超过100字。")</script>';
		$flag=FALSE;
	}

	if (empty($content) || mb_strlen($content) > 3000 || mb_strlen($content) < 10) {
		//wp_die('内容必须填写，且长度不得超过3000字，不得少于10字。<a href="' . $current_url . '">点此返回</a>');
		echo '<script>alert("内容必须填写，且长度不得超过3000字，不得少于10字。")</script>';
		$flag=FALSE;
	}

	$post_content = '昵称: ' . $name . '<br />Email: ' . $email . '<br />blog: ' . $blog . '<br />内容:<br />' . $content;

	$tougao = array('post_title' => $title, 'post_content' => $post_content, 'post_category' => array($category));

	// 将文章插入数据库
	if($flag){
		$status = wp_insert_post($tougao);
	
	if ($status != 0) {
		// 投稿成功给博主发送邮件
		wp_mail('longchen@mrpann.com","新投稿","又有人投稿了，内容:' . $content);

		wp_die('投稿成功！感谢投稿！<a href="' . $current_url . '">点此返回</a>', '投稿成功');
	} else {
		wp_die('投稿失败！<a href="' . $current_url . '">点此返回</a>');
	}
	}
}
?>
<!--内容-->
<div class="container">
	<?php if (!function_exists('dynamic_sidebar') || !dynamic_sidebar('顶部栏') ) :
	?>
	<div class="alert alert-info alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		加入我们，一起交流，一起成长。
	</div>
	<?php else: ?>
	<?php endif; ?>
		<ol class="breadcrumb">
 		<li><a href="<?php echo get_option('home'); ?>/">首页</a></li>
		<li class="active"><?php the_title(); ?></li>	 
		</ol>
	<div class="row">
		<div class="col-xs-12 col-sm-6 col-md-8">
			<?php if (have_posts()) : the_post(); update_post_caches($posts);
			?>
			<!--文章内容-->
			<div class="panel panel-default">
			<div class="panel-body">
			<div class="page-header">
			<div class="panel-title">
			<h2><?php the_title(); ?></h2>
			</div>
			</div>
			<?php the_content(); ?>
			<br/>
			<br/>

				<div class="tabbable">
					<div class="tab-content">
				    <div id="demo" class="tab-pane active">
				    	<div class="alert alert-success">
				    		<h4>小贴士</h4>
				    		<ul>
				    			<li>把你的好文章，好句子分享给小安安.</li>
						    	<li>赶快留下你的美丽句子吧=.=</li>
						    </ul>
				    	</div>
						<form class="form-horizontal" method="post" id="contact-form" action="<?php echo $_SERVER["REQUEST_URI"];?>">
						<?php $current_user = wp_get_current_user(); ?>
  					<div class="form-group">
  						<label for="tougao_authorname" class="col-sm-2 control-label">你的昵称</label>
    						<div class="col-sm-10">
      					<input type="text" class="form-control" id="tougao_authorname"  name="tougao_authorname" placeholder="小安安" value="<?php
							if (0 != $current_user -> ID)
								echo $current_user -> user_login;?>"/>
   						 </div>
  					</div>
 					<div class="form-group">
  						<label for="tougao_authoremail" class="col-sm-2 control-label" for="tougao_authoremail">邮箱地址</label>
    						<div class="col-sm-10">
      					<input type="email" class="form-control" id="tougao_authoremail" name="tougao_authoremail" placeholder="example@mrpann.com" value="<?php
						if (0 != $current_user -> ID)
				echo $current_user -> user_email;?>">
   						 </div>
  					</div>
  					<div class="form-group">
  						<label class="col-sm-2 control-label" for="tougao_authorblog">你的网址</label>
    						<div class="col-sm-10">
      					<input type="url" class="form-control input-xlarge" id="tougao_authorblog" name="tougao_authorblog" placeholder="www.mrpann.com" value="<?php
			if (0 != $current_user -> ID)
				echo $current_user -> user_url;
			?>">
   						 </div>
  					</div>
  					<div class="form-group">
  						<label class="col-sm-2 control-label" for="tougao_title">文章标题</label>
    						<div class="col-sm-10">
      					<input type="text" class="form-control"  id="tougao_title" name="tougao_title" placeholder="随笔">
   						 </div>
  					</div>
  					<div class="form-group">
  						<label class="col-sm-2 control-label" for="tougao_content">文章内容</label>
    						<div class="col-sm-10">
      					<textarea class="form-control" rows="5" placeholder="😄说点什么吧..." id="tougao_content" name="tougao_content"></textarea>
   						 </div>
  					</div>
 				 <div class="form-group">
    				<div class="col-sm-offset-2 col-sm-10">
     			 
     			 <input type="hidden" value="send" name="tougao_form" />   
     			 <button type="submit" class="btn btn-primary">提交</button>
     			 <button type="reset" class="btn btn-default">重填</button>
  					<!--<input type="submit" value="提交" class="btn btn-primary btn-lg active"/>   
  			<input type="reset" value="重填" class="btn btn-primary btn-lg active"/> -->
    				</div>
  				</div>
				</form>
					</div><!-- .tab-pane -->
				</div><!-- .tab-content -->
				</div><!-- .tabbable -->
			</div>
			</div>
			<?php else : ?>
			<div class="errorbox">
				没有文章！
			</div>
			<?php endif; ?>
		</div>
		<!--侧边栏-->
		<?php get_sidebar(); ?>
	</div>
</div>
<!--底部-->
<?php get_footer(); ?>
