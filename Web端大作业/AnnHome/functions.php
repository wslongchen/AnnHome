<?php
/** widgets */
if( function_exists('register_sidebar') ) {
	register_sidebar(array(
		'name' => '侧边栏1',
		'description' => '警告的侧边栏',
		'before_widget' => '<div class="panel panel-danger">',
		'after_widget' => '</div></div>',
		'before_title' => '<div class="panel-heading"><h3 class="panel-title">',
		'after_title' => '</h3></div><div class="panel-body">'
	));
	register_sidebar(array(
		'name' => '侧边栏2',
		'description' => '正常的侧边栏',
		'before_widget' => '<div class="panel panel-default">',
		'after_widget' => '</div></div>',
		'before_title' => '<div class="panel-heading"><h3 class="panel-title">',
		'after_title' => '</h3></div><div class="panel-body">'
	));
}


if( function_exists( 'register_sidebar_widget' ) ) {
	include(TEMPLATEPATH . "/widget/diytips.php");   
   register_widget('My_Widget');   
}  

function curPageURL() {
	$pageURL = 'http://';

	$this_page = $_SERVER["REQUEST_URI"]; 
	if (strpos($this_page , "?") !== false) 
		$this_page = reset(explode("?", $this_page));

	$pageURL .= $_SERVER["SERVER_NAME"]  . $this_page;

	return $pageURL;
}


function aurelius_comment($comment, $args, $depth) 
{
   $GLOBALS['comment'] = $comment; ?>
   <li class="comment" id="li-comment-<?php comment_ID(); ?>">
        <div class="gravatar"> <?php if (function_exists('get_avatar') && get_option('show_avatars')) { echo get_avatar($comment, 48); } ?>
 <?php comment_reply_link(array_merge( $args, array('reply_text' => '回复','depth' => $depth, 'max_depth' => $args['max_depth']))) ?> </div>
        <div class="comment_content" id="comment-<?php comment_ID(); ?>">   
            <div class="clearfix">
                    <?php printf(__('<cite class="author_name">%s</cite>'), get_comment_author_link()); ?>
                    <div class="comment-meta commentmetadata">发表于：<?php echo get_comment_time('Y-m-d H:i'); ?></div>
                    &nbsp;&nbsp;&nbsp;<?php edit_comment_link('修改'); ?>
            </div>

            <div class="comment_text">
                <?php if ($comment->comment_approved == '0') : ?>
                    <em>你的评论正在审核，稍后会显示出来！</em><br />
        <?php endif; ?>
        <?php comment_text(); ?>
            </div>
        </div>
<?php } 
	?>
