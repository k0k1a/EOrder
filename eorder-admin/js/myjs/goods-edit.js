$(document).ready(function() {
	initFormData2();
	bindPictureClick();
})

/**
 * 渲染&回显商品类型
 * @param {Object} goodsType 类型
 */
function initSelectData(goodsType) {
	//加载商品类型
	var res = getData('/goods/type');
	$.each(res.data, function(index, item) {
		$('#goodsType').append(new Option(item.type, item.id)); // 下拉菜单里添加元素
	});

	$("#goodsType").each(function() {
		// this代表的是<option></option>，对option再进行遍历
		$(this).children("option").each(function() {
			// 判断需要对那个选项进行回显，这里用值比较
			if ($(this).text() == goodsType) {
				// 进行回显
				$(this).attr("selected", "selected");
			}
		});
	})
	layui.form.render("select");
}

/**
 * 表单数据回显，可以用Ajax请求，成功后回调回显
 */
function initFormData2() {
	layui.use(['form'], function() {
		var form = layui.form;
		var goods = layui.sessionData('goods-update')
		goods = goods.goods;
		form.val("goods-form", {
			"goodsName": goods.goodsName,
			"description": goods.description,
			"cost": goods.cost,
			"id": goods.id,
			"ingredients": goods.ingredients,
			"isMeat": goods.isMeat,
			"price": goods.price,
			"stock": goods.stock,
			"taste": goods.taste,
			"weight": goods.weight
		})
		initSelectData(goods.goodsType);
		initPictures(goods.id);
	});
}

/**
 * 回显商品图片
 * @param {Object} goodsId 商品id
 */
function initPictures(goodsId) {
	var e = getData('/goods/picture/get', {
		goodsId: goodsId
	});
	var pictures = e.data;
	pictures.forEach(picture => {
		var img = new Image();
		img.src = getServerIPPrefix()+picture.picUrl;
		// $(img).addClass('prePicture')
		// img.id=picture.id
		$("#goodsPictures").append(img)
	})

	//渲染轮播图
	// layui.use('carousel', function() {
	// 	var carousel = layui.carousel;
	// 	//建造实例
	// 	carousel.render({
	// 		elem: '#picCarousel',
	// 		width: '150px', //设置容器宽度
	// 		height: '150px',
	// 		arrow: 'hover' ,//始终显示箭头
	// 		indicator:'none'
	// 	});
	// });
	renderCarousel('#picCarousel');
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

function bindPictureClick(){
	$("#goodsPictures").find("img").click(function(){
		console.log($(this).attr('id'))
	})
}

function initFormData() {
	var goods = layui.sessionData('goods-update')
	goods = goods.goods;
	$("#goodsName").val(goods.goodsName)
	$("#goodsType").val(goods.goodsType)
	$("#description").val(goods.description)
	$("#cost").val(goods.cost)

	$("#id").val(goods.id)
	$("#ingredients").val(goods.ingredients)
	$("#isMeat").val(goods.isMeat)
	$("#price").val(goods.price)
	$("#stock").val(goods.stock)
	$("#taste").val(goods.taste)
	$("#weight").val(goods.weight)

	// $("#goodsType option").each(function(){
	// 	var value=$(this).attr("value")
	// 	// console.log(goods.goodsType==value)
	// 	if(goods.goodsType==value){
	// 		$(this).attr("selected")
	// 	}
	// })

}
