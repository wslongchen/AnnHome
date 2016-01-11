<!--底部-->
<footer class="footer navbar-default">
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<address>
					<h4>
						友情链接
					</h4>
					<a href="http://www.mrpann.com">
						<span class="label label-default">小安安</span>
					</a>
					<a href="http://www.github.com">
						<span class="label label-primary">GitHub</span>
					</a>
					<a href="http://www.mrpann.com">
						<span class="label label-warning">MrPan</span>
					</a>
					<br>
					...
					<br>
				</address>
			</div>
			<div class="col-md-4">
				<address>
					<h4>
						联系小安安
					</h4>
					<a target="_blank" href="http://sighttp.qq.com/authd?IDKEY=c903dc2731f8b31f576c962cceab052fa4727edbe09fd807">
						<img border="0" src="http://wpa.qq.com/imgd?IDKEY=c903dc2731f8b31f576c962cceab052fa4727edbe09fd807&pic=51" alt="联系小安安" title="联系小安安">
					</a>
					<br/>
					<span class="glyphicon glyphicon-envelope" aria-hidden="true">
					</span>
					<a href="mailto:#">
						longchen@mrpann.com
					</a>
				</address>
			</div>
			<div class="col-md-4">
				<address>
					<h4>
						其他
					</h4>
					Copyright &copy; 2016
					<a href="http://www.mrpann.com">
						<strong>小安安的自留地</strong>
					</a>
					<br>
					<strong>湘IDCP备15015976号</strong>
					<br>
					Theme by:
					<strong>
						<a href="http://www.mrpann.com">
							拯救世界的小安安
					</strong></a>
				</address>
			</div>
		</div>
	</div>
</footer>
<div >
	<a href="#" style="background: url(<?php bloginfo('template_url'); ?>/img/back-logo.png);" id="back-to-top">
	</a>
</div>
<?php wp_footer(); ?>
<?php
$flag = TRUE;
//获取USER AGENT
$agent = strtolower($_SERVER['HTTP_USER_AGENT']);
//分析数据
$is_pc = (strpos($agent, 'windows nt')) ? true : false;
$is_iphone = (strpos($agent, 'iphone')) ? true : false;
$is_ipad = (strpos($agent, 'ipad')) ? true : false;
$is_android = (strpos($agent, 'android')) ? true : false;
$is_mac = (strpos($agent, 'macintosh')) ? true : false;
//输出数据
if ($is_pc) {
	$w_count = $wpdb -> get_var($wpdb -> prepare("SELECT count FROM wp_access where device='windows'"));
	$w_count++;
	$wpdb -> query($wpdb -> prepare("update wp_access set count=%s where device='windows'", $w_count));
	$flag = FALSE;
}
if ($is_ipad || $is_iphone) {
	$p_count = $wpdb -> get_var($wpdb -> prepare("SELECT count FROM wp_access where device='ios'"));
	$p_count++;
	$wpdb -> query($wpdb -> prepare("update wp_access set count=%s where device='ios'", $p_count));
	$flag = FALSE;
}
if ($is_android) {
	$a_count = $wpdb -> get_var($wpdb -> prepare("SELECT count FROM wp_access where device='android'"));
	$a_count++;
	$wpdb -> query($wpdb -> prepare("update wp_access set count=%s where device='android'", $a_count));
	$flag = FALSE;
}
if ($is_mac) {
	$m_count = $wpdb -> get_var($wpdb -> prepare("SELECT count FROM wp_access where device='mac'"));
	$m_count++;
	$wpdb -> query($wpdb -> prepare("update wp_access set count=%s where device='mac'", $m_count));
	$flag = FALSE;
}
if ($flag) {
	$o_count = $wpdb -> get_var($wpdb -> prepare("SELECT count FROM wp_access where device='others'"));
	$o_count++;
	$wpdb -> query($wpdb -> prepare("update wp_access set count=%s where device='others'", $o_count));
}
//if(wp_is_mobile()){
//	echo '<style>
//	article img{
//	max-width: 590px;
//	width: e­xpression(this.width>590?"590px": this.width);
//	max-height: 590px;
//	height: e­xpression(this.height>590?"590px": this.height);
//}</style>';
//}
?>
</body>
</html>