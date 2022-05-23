$(document).ready(function() {
	initSelectData()
})

function initSelectData() {
	//加载商品类型
	var res=getData('/goods/type');
	$.each(res.data, function(index, item) {
		$('#goods-type-select').append(new Option(item.type, item.id)); // 下拉菜单里添加元素
	});
	layui.form.render("select");
}

/**
 * @param {JSON} data
 */
function addGoods(data) {
	$.ajax({
		url: getRootUrl() + '/goods/add',
		type: 'POST',
		headers: {
			token: getToken()
		},
		contentType: 'application/json',
		dataType: 'json',
		data: data,
		success(e) {
			lay.msg(e.message)
			var goodsId = e.data;
		},
		error(e) {
			lar.msg('添加失败')
		}

	})
}

function renderCarousel(id){
	//渲染轮播图
	layui.use('carousel', function() {
		var carousel = layui.carousel;
		//建造实例
		carousel.render({
			elem: id,
			width: '150px', //设置容器宽度
			height: '150px',
			arrow: 'hover' ,//始终显示箭头
			indicator:'none'
		});
	});
}