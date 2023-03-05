// pages/order/detail/detail.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    model: 0, //1是预约模式  0是到店模式
    appointTime: "",
    token: '',
  },


  //获取订单详情
  getMyOrderDetail: function (orderNum) {
    var _this = this;
    wx.request({
      url: app.globalData.rootUrl + '/user/order/detail',
      method: "GET",
      header: {
        token: this.data.token
      },
      data: {
        orderNum: orderNum
      },
      success(e) {
        if (e.data.success) {
          _this.setData({
            orderDetail: e.data.data
          })
        } else {
          app.showErrMsg(e.data.message);
        }
      }
    })
  },

  initToken() {
    var token = wx.getStorageSync('token');
    this.setData({
      token: token
    })
  },

  countDown() {
    var interval = null;
    interval = setInterval(function () {
      _this.setData({
        verificationText: currentTime + '秒',
        canSend: false
      })
      if (--currentTime <= 0) {
        clearInterval(interval);
        _this.setData({
          verificationText: '重新获取',
          canSend: true
        })
      }
    }, 1000);
  },
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.setNavigationBarTitle({
      title: '订单详情'
    })
    this.initToken();
    this.getMyOrderDetail(options.orderNum)
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