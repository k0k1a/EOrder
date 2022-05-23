function doExcel(uri){
	var token = localStorage.getItem('token');
	var URL = getRootUrl()+uri+'?token='+token;
	// window.location.href=rootUrl+uri;
	$.fileDownload(URL,{
			httpMethod: 'GET',
			header:{
				token:token
			},
			successCallback:function(url){
			   layer.msg("导出成功")
			},
			failCallback: function (html, url) {
			   layer.msg('导出失败')
			}
		});
}