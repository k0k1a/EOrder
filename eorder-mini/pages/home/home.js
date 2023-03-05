// pages/home/home.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    hasUserInfo: false,
    isLogin: false
  },

  // 跳转到登录页面
  go2LoginPage() {
    wx.navigateTo({
      url: '/pages/sub_home_page/login/login',
    })
  },

  //退出登录
  logout() {
    var _this = this;
    wx.showModal({
      title: "警告",
      content: "确定退出登录吗？",
      success(e) {
        if (e.confirm) {
          _this.doLogout();
        }
      }
    })

  },

  //退出登录
  doLogout() {
    var _this = this;
    wx.request({
      url: app.globalData.rootUrl + '/user/logout',
      header: {
        'token': this.getToken()
      },
      success(e) {
        wx.removeStorage({
          key: 'token',
          success(e) {
            console.log("token移除成功")
          }
        })
        wx.removeStorage({
          key: 'userInfo',
          success(e) {
            console.log("userInfo移除成功")
          }
        })

        _this.setData({
          isLogin: false,
          userInfo: {}
        })
        app.showMsg("成功")
      }
    })
  },

  //获取token
  getToken() {
    var token = wx.getStorageSync('token');
    return token;
  },

  //获取userInfo缓存
  getUserInfo() {
    var userInfo = wx.getStorageSync('userInfo');
    return userInfo;
  },

  //跳转到优惠券页面
  handleCouponTap() {
    wx.navigateTo({
      url: '/pages/sub_home_page/coupon/coupon',
    })
  },

  handle2MyOrder() {
    wx.switchTab({
      url: '/pages/order/order',
    })
  },

  handle2Mytalk(){
    wx.navigateTo({
      url: '/pages/sub_home_page/mytalk/mytalk',
    })
  },

  handle2modify(){
    wx.navigateTo({
      url: '/pages/sub_home_page/modify-userInfo/modify-user',
    })
  },
  
  handle2AboutUs(){
    wx.navigateTo({
      url: '/pages/sub_home_page/about-us/about-us',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.initData()
  },

  initData(){
    var token = this.getToken();
    if (token) {
      this.setData({
        isLogin: true
      })
      this.getLoginUser(token)
    }
  },

  getLoginUser(token){
    var _this=this
    wx.request({
      url: app.globalData.rootUrl+'/user/get',
      method:"POST",
      header:{
        token:token
      },
      success(e){
        var user=e.data.data
        var avatarUrl=user.avatarUrl;
        if(avatarUrl!=null && !app.testUrl(avatarUrl)){
          avatarUrl=app.globalData.serverPrefix+avatarUrl;
          user.avatarUrl=avatarUrl;
        }
        
        _this.setData({
          userInfo: user
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.initData()
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