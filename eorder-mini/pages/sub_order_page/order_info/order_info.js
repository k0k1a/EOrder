// pages/sub_index_page/order_info/order_info.js
// 获取应用实例
const app = getApp()

Page({

    /**
     * 页面的初始数据
     */
    data: {
        cartList: [],
        total: 0,
        subTotal: 0,
        time: null,
        userInfo: {},
        animationData: {},
        couponState: false,
        remark: '',

        tabIndex: 0,
        coupon: [],
        token: '',
        isLogin: false,
        myCoupon: null,

        //选择的优惠券
        selectCoupon: null

    },

    /**
     * 得到购物车缓存数据
     */
    getCartListStorage() {
        var _this = this;
        wx.getStorage({
            key: "cartList",
            success(e) {
                var cartList = e.data;
                _this.calculateTotalPrice(cartList)
                _this.setData({
                    cartList: cartList
                })
            }
        })
    },

    /**
     * 计算总价
     * @param {Array} cartList 
     */
    calculateTotalPrice(cartList) {
        var total = 0;
        cartList.forEach(cart => {
            total += cart.selectedOptions.extra * cart.num + cart.goods.price * cart.num;
        })
        this.setData({
            total: total,
            subTotal: total
        })
    },

    handleTimeChange(e) {
        this.setData({
            time: e.detail.value
        })
    },

    getPhoneNumber(e) {
        console.log(e)
    },

    //获取userInfo缓存
    getUserInfo() {
        var userInfo = wx.getStorageSync('userInfo');
        return userInfo;
    },

    //初始化userInfo
    initUserInfo() {
        var user = this.getUserInfo();
        this.setData({
            userInfo: user
        })
    },

    //点击选择优惠券
    handleSelectCouponTap() {
        this.getLoginCoupons(0, 10);
        this.showModel();
    },

    //显示对话框
    showModel: function () {
        // 显示遮罩层
        var animation = wx.createAnimation({
            duration: 200,
            timingFunction: "linear",
            delay: 0
        })
        this.animation = animation
        animation.translateY(300).step()

        // var state=!this.data.GoodsCartInfoShowState;
        this.setData({
            animationData: animation.export(),
            couponState: true
        })

        setTimeout(function () {
            animation.translateY(0).step()
            this.setData({
                animationData: animation.export()
            })
        }.bind(this), 200)
    },

    //隐藏对话框
    hideModel: function () {
        // 隐藏遮罩层
        var animation = wx.createAnimation({
            duration: 200,
            timingFunction: "linear",
            delay: 0
        })
        this.animation = animation
        animation.translateY(300).step()
        this.setData({
            animationData: animation.export(),
        })
        setTimeout(function () {
            animation.translateY(0).step()
            this.setData({
                animationData: animation.export(),
                couponState: false
            })
        }.bind(this), 200)
    },

    //点击购买
    handlePayTap() {

        this.prePay();
    },

    //预支付
    prePay() {
        var _this = this
        var orderList = this.changeCartList2OrderList();
        var couponId = _this.data.selectCoupon == null ? null : _this.data.selectCoupon.id;
        wx.request({
            url: app.globalData.rootUrl + '/user/order/pre/pay',
            method: "POST",
            header: {
                token: _this.data.token
            },
            data: {
                userCouponId: couponId,
                remark: _this.data.remark,
                cartGoodsList: orderList
            },
            success(e) {
                if (e.data.success) {
                    _this.pay(e.data.data.order);
                } else {
                    _this.showFailMsg(e.data.message)
                }
            }

        })
    },

    /**
     * 显示错误信息
     * @param {*} msg 信息
     */
    showFailMsg(msg) {
        wx.showToast({
            title: msg,
            icon: "error"
        })
    },

    /**
     * 支付
     * @param {*} order 预支付返回的订单json
     */
    pay(order) {
        var _this = this
        wx.showModal({
            title: '支付提示',
            content: '确认支付' + order.payAmount + "元？",
            success(res) {
                wx.request({
                    url: app.globalData.rootUrl + '/user/order/pay',
                    method: "POST",
                    header: {
                        token: _this.data.token
                    },
                    data: {
                        paid: res.confirm,
                        orderNum: order.orderNum
                    },
                    success(e) {
                        if (e.data.success) {
                            _this.clearCartList();
                            //跳转到支付结果页面
                            wx.redirectTo({
                                url: '/pages/sub_order_page/order_detail/detail?orderNum=' + order.orderNum,
                            })
                        } else {
                            _this.clearCartList();
                            //跳转到我的订单页面
                            wx.switchTab({
                                url: '/pages/order/order',
                            })
                        }
                    }
                })
            }
        })
    },

    /**
     * 清除购物车
     */
    clearCartList() {
        wx.removeStorageSync('cartList')
    },
    changeCartList2OrderList() {
        var cartList = this.data.cartList;
        var res = []
        cartList.forEach(cart => {
            res.push({
                goodsId: cart.goodsId,
                optionIds: cart.selectedOptionsIds,
                quantity: cart.num
            })
        })
        return res;
    },

    handInputRemark(e) {
        var remark = e.detail.value;
        this.setData({
            remark: e.detail.value
        })

    },

    //点击标题
    handleTitleTap(e) {
        var index = e.currentTarget.dataset.index
        if (index == 1) {
            //查询可以使用优惠券
            this.getAvailableCoupons(0, 10);
        } else if (index == 2) {
            this.getExpireCoupons(0, 10);
        }

        this.setData({
            tabIndex: index
        })
    },

    /**
     * 获取可以使用优惠券
     */
    getAvailableCoupons(pageNo, pageSize) {
        var _this = this;
        wx.request({
            url: app.globalData.rootUrl + '/user/coupon/valid',
            method: "POST",
            header: {
                token: _this.data.token
            },
            data: {
                pageNo: pageNo,
                pageSize: pageSize
            },
            success(e) {
                if (e.data.success) {
                    _this.setData({
                        myCoupon: e.data.data
                    })
                } else {
                    _this.showFail()
                }
            }
        })
    },

    //查询已过期的优惠券
    getExpireCoupons(pageNo, pageSize) {
        var _this = this;
        wx.request({
            url: app.globalData.rootUrl + '/user/coupon/expire',
            method: "POST",
            header: {
                token: _this.data.token
            },
            data: {
                pageNo: pageNo,
                pageSize: pageSize
            },
            success(e) {
                if (e.data.success) {
                    _this.setData({
                        myCoupon: e.data.data
                    })
                } else {
                    _this.showFail()
                }
            }
        })
    },

    showFail() {
        wx.showToast({
            title: '获取失败',
            icon: 'error'
        })
    },

    //获取优惠券，已登录用户
    getLoginCoupons(pageNo, pageSize) {
        var _this = this
        var token = _this.data.token;
        wx.request({
            url: app.globalData.rootUrl + '/coupon/get',
            method: "POST",
            header: {
                token: token
            },
            data: {
                pageNo: pageNo,
                pageSize: pageSize
            },
            success(e) {
                if (e.data.success) {
                    _this.setData({
                        coupon: e.data.data
                    })
                } else {
                    _this.showFail()
                }
            }
        })
    },

    //点击优惠券，获取优惠券
    handleCouponTap(e) {
        var id = e.currentTarget.dataset.id;
        this.getCoupons(id);
    },

    //用户点击可用优惠券时，使用优惠券
    handleCouponUseTap(e) {
        var coupon = e.currentTarget.dataset.coupon;
        var total = this.data.total;

        //选择优惠券
        if (coupon.reach > total) {
            wx.showToast({
                title: '未达到使用金额',
                icon: 'error'
            })
        } else {
            var res = total - coupon.reduce;
            this.setData({
                subTotal: res,
                selectCoupon: coupon
            })
            this.hideModel()
        }

    },

    //点击失效的优惠券
    handleExpireCouponTap() {
        wx.showToast({
            title: '已经失效了哦',
            icon: 'error'
        })
    },

    /**
     * 已登录用户，领取优惠券
     * @param {*} couponId 优惠券id
     */
    getCoupons(couponId) {
        wx.request({
            url: app.globalData.rootUrl + '/user/coupon/get',
            method: "GET",
            header: {
                token: this.data.token
            },
            data: {
                couponId: couponId
            },
            success(e) {
                wx.showToast({
                    title: e.data.message,
                    icon: 'none'
                })
            }
        })
    },

    /**
     * 同步获取token
     */
    getTokenSync() {
        var token = wx.getStorageSync('token')
        if (token) {
            this.setData({
                token: token
            })
        }

    },

    handleReachBottom1() {
        var coupon = this.data.coupon;
        //已到最后一页
        if (coupon.pageNum == coupon.pages) {
            return;
        }
        this.getNextPageCoupon(coupon.pageNum + 1, coupon.pageSize, '/coupon/get');
    },

    handleReachBottom2() {
        var tabIndex = this.data.tabIndex;
        var myCoupon = this.data.myCoupon;
        var urls = ["/user/coupon/valid", "/user/coupon/expire"]
        if (myCoupon.pageNum == myCoupon.pages) {
            return;
        }
        this.getNextMyCoupon(myCoupon.pageNum + 1, myCoupon.pageSize, urls[tabIndex - 1]);
    },

    //获取更多
    getNextPageCoupon(pageNo, pageSize, url) {
        app.showLoading("加载中")
        var _this = this
        var token = _this.data.token;
        wx.request({
            url: app.globalData.rootUrl + url,
            method: "POST",
            header: {
                token: token
            },
            data: {
                pageNo: pageNo,
                pageSize: pageSize
            },
            success(e) {
                if (e.data.success) {
                    //加入已有的数据集合中
                    _this.pushMoreCoupons(e.data.data)
                } else {
                    _this.showFail()
                }
                wx.hideLoading()
            },
            fail(e) {
                wx.hideLoading()
            }
        })
    },

    /**
     * 放入已有coupon页面对象中
     * @param {JSON} pageData 
     */
    pushMoreCoupons(pageData) {
        var couponPage = this.data.coupon;
        couponPage.pageNum = pageData.pageNum;
        couponPage.pageNo = pageData.pageNo;
        couponPage.pages = pageData.pages;
        couponPage.total = pageData.total;

        pageData.data.forEach(page => {
            couponPage.data.push(page);
        })
        this.setData({
            coupon: couponPage
        })
    },

    getNextMyCoupon(pageNo, pageSize, url) {
        app.showLoading("加载中")
        var _this = this;
        wx.request({
            url: app.globalData.rootUrl + url,
            method: "POST",
            data: {
                pageNo: pageNo,
                pageSize: pageSize
            },
            header: {
                token: _this.data.token
            },
            success(e) {
                if (e.data.success) {
                    _this.pushMoreMyCoupons(e.data.data);
                } else {
                    _this.showFail()
                }
                wx.hideLoading()
            },
            fail() {
                wx.hideLoading()
            }
        })

    },

    pushMoreMyCoupons(pageData) {
        var couponPage = this.data.myCoupon;
        couponPage.pageNum = pageData.pageNum;
        couponPage.pageNo = pageData.pageNo;
        couponPage.pages = pageData.pages;
        couponPage.total = pageData.total;

        pageData.data.forEach(page => {
            couponPage.data.push(page);
        })
        this.setData({
            myCoupon: couponPage
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.getCartListStorage();
        this.initUserInfo();
        this.getTokenSync();

    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})