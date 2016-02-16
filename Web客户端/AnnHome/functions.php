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
		'description' => '首页及某些页面的顶部栏，提供提示',
		'before_widget' => '<div class="panel panel-default">',
		'after_widget' => '</div></div>',
		'before_title' => '',
		'after_title' => '<div class="panel-body">'
	));
	register_sidebar(array(
		'name' => '文章广告栏',
		'description' => '文章放置广告的地方',
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
'header_right_menu' => __( '页角自定义菜单' ),
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

function mrpan_paginate( $wp_query='' ){
  
  if ( empty($wp_query) )
    global $wp_query;
    
  $pages = $wp_query->max_num_pages;
  if ( $pages < 2 )
    return;
  
  $big = 999999999;
  $paginate = paginate_links( array(
    'base' => str_replace( $big, '%#%', esc_url( get_pagenum_link( $big ) ) ),
    'format' => '?paged=%#%',
    'current' => max( 1, get_query_var('paged') ),
    'total' => $pages,
    'type' => 'array'
  ) );
  echo '<div id="pagination"><ul class="pagination" role="navigation" itemscope itemtype="http://schema.org/SiteNavigationElement">';
  foreach ($paginate as $value) {
    echo '<li itemprop="name">'.$value.'</li>';
  }
  echo '</ul></div>';
}

function mrpan_post_page_nav( $echo=true ) {

  return wp_link_pages( array(
    'before'      => '<nav class="pager" role="navigation" itemscope itemtype="http://schema.org/SiteNavigationElement"><span>'.__('分页','dmeng').'</span>',
    'after'       => '</nav>',
    'link_before' => '<span itemprop="name">',
    'link_after'  => '</span>',
    'pagelink' => __('%','mrpan'),
    'echo' => $echo
  ) );

}

global $wp_version;
if ( version_compare( $wp_version, '3.4', '>=' ) )
    add_theme_support( 'custom-background', $defaults );
else
    add_custom_background( $args );

?>

