<?php 

class My_Widget extends WP_Widget {

function __construct() {
    parent::__construct( 
        // 小工具ID
        'tutsplus_list_pages_widget',
 
        // 小工具名称
        __('提示框', 'tips' ),
 
        // 小工具选项
        array (
            'description' => __( '顶部提示框', 'tips' )
        )
 
    );
 
}
	function form($instance) { // 给小工具(widget) 添加表单内容
		$title = esc_attr($instance['title']);
		$text = esc_attr($instance['text']);
	?>
	<div>
		<label 
		for="<?php echo $this->get_field_id('title'); ?>">
			<?php esc_attr_e('标题:'); ?> 
		<input class="widefat" 
			id="<?php echo $this->get_field_id('title'); ?>" 
			name="<?php echo $this->get_field_name('title'); ?>" 
				type="text" 
				value="<?php echo $title; ?>" />
		</label>
		</div>
		<div>
		<label 
		for="<?php echo $this->get_field_id('text'); ?>">
			<?php esc_attr_e('文本:'); ?> 
			<br>
			<textarea class="widefat" id="<?php echo $this->get_field_id('text'); ?>" 
			name="<?php echo $this->get_field_name('text'); ?>" 
			style="width:290px;height:150px;"><?php echo $text; ?></textarea>
		</label>
		<br>
	</div>

	<?php
    }
	function update($new_instance, $old_instance) { // 更新保存
		return $new_instance;
	}
	function widget($args, $instance) { // 输出显示在页面上
	extract( $args );
        $title = apply_filters('widget_title', empty($instance['title']) ? __('暂无') : $instance['title']);
        $text = empty($instance['text']) ? __('暂无') : $instance['text'];
        ?>
              <?php echo $before_widget; ?>
                  <?php 
                        echo $before_title. $after_title; 
                        echo '<div class="alert alert-danger alert-dismissible" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  						<span class="sr-only">Error:</span>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'.$text.'</div>';
 ?>
                       <!-- <?php echo $text ?>-->
              <?php echo $after_widget; ?>
        <?php
	}
}
?>