$(document).ready(function() {
	initData();
	checkToken();

})

/**
 * 初始化数据
 */
function initData() {
	var rootUrl = 'rootUrl'
	if (!localStorage.getItem(rootUrl))
		localStorage.setItem(rootUrl, 'http://localhost:8080/eorder/admin')
}

/**
 * 得到请求跟路径
 */
function getRootUrl() {
	var rootUrl = localStorage.getItem('rootUrl');
	return rootUrl;
}

/**
 * 服务器ip:port
 */
function getServerIPPrefix(){
	var serverPrefix='http://localhost:8080';
	return serverPrefix;
}
/**
 * 获取上传文件接口
 */
function getUploadUrl(){
	return 'http://localhost:8080/eorder/file/upload'
}

/**
 * 获取token
 */
function getToken() {
	var token = localStorage.getItem('token');
	return token;
}

var loginPath = '/eorder-admin/page/login.html';
/**
 * 校验token合法性
 */
function checkToken() {
	var token = localStorage.getItem('token');
	var rootUrl = getRootUrl();
	$.ajax({
		url: rootUrl + '/token/check',
		type: 'GET',
		headers: {
			token: token
		},
		dataType: 'json',
		async: false,
		success(e) {
			if (!e.success) {
				if (top.location.pathname != loginPath) {
					window.location = loginPath
				}
				localStorage.removeItem('token')
			}
		},
		error(e) {
			if (top.location.pathname != loginPath) {
				window.location = loginPath
			}
			localStorage.removeItem('token')
		}

	})
}

/**
 * POST请求服务器
 * @param {String} uri 请求路径
 * @param {JSON} data JSON数据
 * @return {Object} 接口返回数据
 */
function postData(uri, data) {
	var token = localStorage.getItem('token');
	var rootUrl = getRootUrl();
	var temp;
	$.ajax({
		url: rootUrl + uri,
		type: 'POST',
		headers: {
			token: token
		},
		data: data,
		contentType: 'application/json',
		dataType: 'json',
		async: false,
		success(e) {
			if (e.success) {
				temp = e.data
			}else{
				layer.msg(e.message)
			}
		},
		error(e) {
			layer.msg('连接超时')
		}
	});
	return temp;
}

/**
 * 向服务器发送GET请求
 * @param {String} uri url
 * @param {JSON} data 数据
 * @return {Object} 接口返回数据
 */
function getData(uri, data) {
	var token = localStorage.getItem('token');
	var rootUrl = getRootUrl();
	var temp;
	$.ajax({
		url: rootUrl + uri,
		type: 'GET',
		headers: {
			token: token
		},
		data: data,
		dataType: 'json',
		async: false,
		success(e) {
			if (e.success) {
				temp = e
			}else{
				layer.msg(e.message)
			}
		},
		error(e) {
			layer.msg('连接超时')
		}
	});
	return temp;
}
