$(document).ready(function() {
	initSelectData()
})

function initSelectData() {
	//加载机构类型
	var res=getData('/goods/type');
	$.each(res.data, function(index, item) {
		$('#goods-type-select').append(new Option(item.type, item.id)); // 下拉菜单里添加元素
	});
	layui.form.render("select");
}

/**
 * 按照id删除商品
 * @param {Integer} goodsId 商品id
 */
function deleteGoodsById(goodsId){
	$.ajax({
		url:getRootUrl()+'/goods/delete',
		method:'GET',
		headers:{
			token:getToken()
		},
		dataType:'json',
		data:{
			goodsId:goodsId
		},
		success(e){
			if (e.success) {
				layer.msg("删除成功")
			}
		}
	})
}