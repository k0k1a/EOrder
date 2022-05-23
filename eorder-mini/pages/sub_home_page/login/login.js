const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    checked: false,
    alert: false,
    userProfile: null,
    token: ''
  },

  login() {
    var _this = this;
    if (!this.checkSelect()) {
      return false;
    }
    //判断是否登录 TODO
    //校验登录是否失效
    //未登录
    _this.doLogin();

  },

  //登录逻辑
  doLogin() {
    var _this = this;

    //1.获取用户授权，获取用户信息
    wx.getUserProfile({
      desc: '用于完善注册资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success(res) {
        console.log("用户授权成功")
        _this.setData({
          userProfile: res
        })

        //2.发送登录请求
        _this.loginRequest();
      }

    })

  },

  //发送登录请求
  loginRequest() {
    var _this = this;
    console.log("开始发送登录code")
    //登录
    wx.login({
      timeout: 5000,
      success(res) {
        if (res.code) {
          //1.发起网络请求
          wx.request({
            url: app.globalData.rootUrl + '/user/wx/login',
            data: {
              code: res.code
            },
            method: "POST",
            success(res) {
              //登录成功
              if (res.data.success) {
                console.log("登录成功")
                var token = res.data.data.token;
                //存储3rd_session
                wx.setStorage({
                  key: "token",
                  data: token
                })
                console.log("插入缓存中的token=" + token)
                //2.插入用户数据
                _this.insertWxUser();
              } else {
                //登录失败
                _this.doLoginFail()
              }
            }
          })
        } else {
          _this.doLoginFail()
        }
      }
    })

  },

  //登录失败逻辑 TODO
  doLoginFail() {
    wx.showToast({
      title: '登录失败',
      icon: 'error'
    })
  },

  //插入微信用户
  insertWxUser() {
    var _this = this;
    var userProfile = this.data.userProfile;
    console.log("start inster")
    wx.request({
      url: app.globalData.rootUrl + '/user/wx/insert',
      header: {
        'token': _this.getToken()
      },
      method: "POST",
      data: {
        rawData: userProfile.rawData,
        signature: userProfile.signature,
        iv: userProfile.iv,
        encryptedData: userProfile.encryptedData
      },
      success(e) {
        if(e.data.success){
          var user = _this.data.userProfile.userInfo
          wx.setStorage({
            key: "userInfo",
            data: user
          })
          app.showMsg("登录成功")
          setTimeout(function(){
            wx.switchTab({
                url: '/pages/home/home',
            })
          },500)
        }else{
          _this.doLoginFail()
        }
      }
    })
  },

  //获取token
  getToken() {
    var token = wx.getStorageSync('token');
    return token;
  },

  //判断用户是否勾选协议
  checkSelect() {
    var checked = this.data.checked;
    if (!checked) {
      this.setData({
        alert: true
      })
      return false;
    }
    return true;
  },

  /**
   * 系统登录
   */
  handleSystemLogin() {
    var _this = this;
    if (!this.checkSelect()) {
      return false;
    }
    wx.navigateTo({
      url: '/pages/sub_home_page/system_login/system_login',
    })
  },

  //单选框
  radioTap() {
    var check = !this.data.checked;
    this.setData({
      checked: check
    })
    if (check) {
      this.setData({
        alert: false
      })
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var token = app.globalData.token;
    this.setData({
      token: token
    })
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