// pages/sub_index_page/coupon/coupon.js
// 获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    tabIndex: 0,
    coupon: [],
    token: '',
    isLogin: false,
    myCoupon: null
  },

  //点击标题
  handleTitleTap(e) {
    var index = e.currentTarget.dataset.index;
    var isLogin = this.data.isLogin;

    if (index != 0 && !isLogin) {
      wx.showToast({
        title: '请先登录',
        icon: 'error'
      })
      return;
    }

    if (index == 1) {
      //查询可以使用优惠券
      this.getAvailableCoupons(0, 5);
    } else if (index == 2) {
      this.getExpireCoupons(0, 5);
    }

    this.setData({
      tabIndex: index
    })
  },

  /**
   * 获取可以使用优惠券
   */
  getAvailableCoupons(pageNo, pageSize) {
    app.showLoading("加载中")
    var _this = this;
    wx.request({
      url: app.globalData.rootUrl + '/user/coupon/valid',
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
          _this.setData({
            myCoupon: e.data.data
          })
        } else {
          _this.showFail()
        }
        wx.hideLoading()
      },
      fail(){
        wx.hideLoading()
      }
    })
  },

  //查询已过期的优惠券
  getExpireCoupons(pageNo, pageSize) {
    app.showLoading("加载中")
    var _this = this;
    wx.request({
      url: app.globalData.rootUrl + '/user/coupon/expire',
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
          _this.setData({
            myCoupon: e.data.data
          })
        } else {
          _this.showFail()
        }
        wx.hideLoading()
      },
      fail(){
        wx.hideLoading()
      }
    })
  },

  //游客用户
  getVisitCoupons(pageNo, pageSize) {
    var _this = this;
    app.showLoading("加载中")
    wx.request({
      url: app.globalData.rootUrl + '/coupon/visit/get',
      method: "POST",
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
        wx.hideLoading()
      },
      fail() {
        wx.hideLoading()
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
    app.showLoading("加载中")
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
        wx.hideLoading()
      },
      fail(e) {
        wx.hideLoading()
      }
    })

  },

  //点击优惠券，获取优惠券
  handleCouponTap(e) {
    var id = e.currentTarget.dataset.id;
    var isLogin = this.data.isLogin;

    //没有登录
    if (!isLogin) {
      wx.showToast({
        title: '请先登录',
        icon: 'error'
      })
    } else {
      this.getCoupons(id);
    }
  },

  /**
   * 已登录用户，领取优惠券
   * @param {*} couponId 优惠券id
   */
  getCoupons(couponId) {
    // console.log(couponId)
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
    couponPage.pageSize = pageData.pageSize;
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
      fail(){
        wx.hideLoading()
      }
    })

  },

  pushMoreMyCoupons(pageData) {
    var couponPage = this.data.myCoupon;
    couponPage.pageNum = pageData.pageNum;
    couponPage.pageSize = pageData.pageSize;
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
    var token = wx.getStorageSync('token');
    if (token) {
      //已登录用户
      this.setData({
        token: token,
        isLogin: true
      })
      this.getLoginCoupons(0, 5);
    } else {
      this.getVisitCoupons(0, 5);
    }

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var tabIndex = this.data.tabIndex;
    var isLogin = this.data.isLogin
    var coupon = this.data.coupon;
    var myCoupon = this.data.myCoupon;

    if (tabIndex == 0) {
      //已到最后一页
      if (coupon.pageNum >= coupon.pages) {
        return;
      }
      if (isLogin) {
        this.getNextPageCoupon(coupon.pageNum + 1, coupon.pageSize, '/coupon/get');
      } else {
        //TODO
        this.getNextPageCoupon(coupon.pageNum + 1, coupon.pageSize, '/coupon/visit/get');
      }
    } else if (tabIndex == 1) {
      if (myCoupon.pageNum >= myCoupon.pages) {
        return;
      }
      this.getNextMyCoupon(myCoupon.pageNum + 1, myCoupon.pageSize, '/user/coupon/valid');
    } else {
      if (myCoupon.pageNum >= myCoupon.pages) {
        return;
      }
      this.getNextMyCoupon(myCoupon.pageNum + 1, myCoupon.pageSize, '/user/coupon/expire');
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})