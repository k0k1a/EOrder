$(document).ready(function() {
	setDailyVisit();
	setDailySale();
	setDailyIncome();
	setDailyComment();
})

/**
 * 设置日访问量
 */
function setDailyVisit() {
	var res = getData('/index/visit', null)
	var num = res.data.number == null ? '0' : res.data.number
	var rate = res.data.rate;

	$('#visitNum').text(num)
	setRate('#visitRate', rate)
}

/**
 * 设置日销量
 */
function setDailySale() {
	var res = getData('/index/sale', null)
	var num = res.data.number == null ? '0' : res.data.number
	var rate = res.data.rate;
	
	$('#saleNum').text(num)
	setRate('#saleRate', rate)
}

/**
 * 设置日收入
 */
function setDailyIncome() {
	var res = getData('/index/income', null)
	var num = res.data.number == null ? '0' : res.data.number
	var rate = res.data.rate;
	$('#income').text('￥' + num)
	setRate('#incomeRate', rate)
}

/**
 * 设置日收入
 */
function setDailyComment() {
	var res = getData('/index/comment', null)
	var num = res.data.number == null ? '0' : res.data.number
	var rate = res.data.rate;
	$('#comment').text(num)
	setRate('#commentRate', rate)
}

/**
 * 根据id设置比率
 * 
 * @param {Object} id id
 * @param {Object} rate 比率
 */
function setRate(id, rate) {

	if (rate.charAt(0) == '-') {
		$(id).text('▼' + rate)
		$(id).css('color', '#bd3004')
	} else {
		$(id).text('▲' + rate)
		$(id).css('color', '#1aa094')
	}
}
