window.onload = function() {
	var p = {
		url: location.href,
		showcount: '0',
		/*是否显示分享总数,显示：'1'，不显示：'0' */
		desc: '',
		/*默认分享理由(可选)*/
		summary: '我们都是平凡的，但是却一直都在成就着自己的不平凡',
		/*分享摘要(可选)*/
		title: '小安安の自留地',
		/*分享标题(可选)*/
		site: '小安安',
		/*分享来源 如：腾讯网(可选)*/
		style: '101',
		width: 142,
		height: 30
	};
	var s = [];
	for (var i in p) {
		s.push(i + '=' + encodeURIComponent(p[i] || ''));
	}
	document.getElementById("tencent").href = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?" + s.join("&");
}
window.onload = function() {
	var p = {
		url: location.href,
		/*获取URL，可加上来自分享到QQ标识，方便统计*/
		desc: '',
		/*分享理由(风格应模拟用户对话),支持多分享语随机展现（使用|分隔）*/
		title: '小安安の自留地',
		/*分享标题(可选)*/
		summary: '我们都是平凡的，但是却一直都在成就着自己的不平凡',
		/*分享摘要(可选)*/
		pics: '',
		/*分享图片(可选)*/
		flash: '',
		/*视频地址(可选)*/
		site: '小安安',
		/*分享来源(可选) 如：QQ分享*/
		style: '201',
		width: 32,
		height: 32
	};
	var s = [];
	for (var i in p) {
		s.push(i + '=' + encodeURIComponent(p[i] || ''));
	}
	document.getElementById("qq").href ="http://connect.qq.com/widget/shareqq/index.html?"+ s.join("&");
}
window.onload=function(){
	var p={
		title:'小安安の自留地',
		url:'http://www.mrpann.com',
		appkey: '2683370438',
		content:''
	};
	var s = [];
	for (var i in p) {
		s.push(i + '=' + encodeURIComponent(p[i] || ''));
	}
	document.getElementById("sina").href ="http://v.t.sina.com.cn/share/share.php?"+s.join("&");
}
