// app.js
App({
  onLaunch() {
    var _this=this;
    //小程序初始化时获取token缓存
    var token=wx.getStorageSync('token');
    _this.globalData.token=token;
    this.checkToken(token);
  },
  globalData: {
    userInfo: null,
    rootUrl:"http://localhost:8080/eorder/app",
    fileUrl:"http://localhost:8080/eorder/file",
    staticUrl:"http://localhost:8080/eorder/static",
    serverPrefix:"http://localhost:8080",
    token:''
  },

  checkToken(token){
    if(!token)
      return;
    var _this=this;
    wx.request({
      url: this.globalData.rootUrl+'/user/token/check',
      method:"GET",
      header:{
        token:token
      },
      success(e){
        if(!e.data.success){
          _this.showErrMsg("登录状态失效")
          wx.removeStorageSync('token')
          wx.removeStorageSync('userInfo')
        }
      }
    })
  },

  /**
   * 显示成功消息
   * @param {*} msg 
   */
  showMsg(msg){
    wx.showToast({
      title: msg,
      icon:'success'
    })
  },

  /**
   * 显示错误消息
   * @param {*} msg 
   */
  showErrMsg(msg){
    wx.showToast({
      title: msg,
      icon:'error'
    })
  },

  /**
   * 
   * @param {显示加载} msg 
   */
  showLoading(msg){
    wx.showLoading({
      title: msg,
    })
  },
  
  //获取token
  getToken() {
    var token = wx.getStorageSync('token');
    return token;
  },
  
  /**
   * 判断是否是url
   */
  testUrl(url){
    var reg='(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]'
    var re=new RegExp(reg)
    return re.test(url);
  }
})
