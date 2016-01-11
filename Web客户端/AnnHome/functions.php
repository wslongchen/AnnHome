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
	register_sidebar(array(
		'name' => '顶部栏',
		'description' => '某些页面的顶部栏，只能',
		'before_widget' => '',
		'after_widget' => '',
		'before_title' => '',
		'after_title' => ''
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

if(function_exists('register_nav_menus')){
register_nav_menus( array(
'header-menu' => __( '导航自定义菜单' ),
'footer-menu' => __( '页角自定义菜单' ),
'sider-menu' => __('侧边栏菜单')
) );
}

$defaults = array(
    'default-color'          => '',
    'default-image'          => '',
    'wp-head-callback'       => '_custom_background_cb',
    'admin-head-callback'    => '',
    'admin-preview-callback' => ''
);

global $wp_version;
if ( version_compare( $wp_version, '3.4', '>=' ) )
    add_theme_support( 'custom-background', $defaults );
else
    add_custom_background( $args );

?>

