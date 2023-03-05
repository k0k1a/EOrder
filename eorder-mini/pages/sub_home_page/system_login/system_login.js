// pages/sub_home_page/system_login/system_login.js
const app = getApp()

Page({

    /**
     * 页面的初始数据
     */
    data: {
        canSend: false,
        isPassword: false,
        verificationText: "获取验证码",
        phone: "",
        password: ""
    },

    /**
     * 用户输入手机号
     * @param {*} e 
     */
    handlePhoneInput(e) {
        var phone = e.detail.value;
        var isPhone = this.isPhoneNumber(phone)
        this.setData({
            canSend: isPhone
        })
        if (isPhone) {
            this.setData({
                phone: phone
            })
        }
    },

    /**
     * 用户输入密码
     * @param {*} e 
     */
    handlePasswordInput(e) {
        var password = e.detail.value;
        if (this.verifyPasswordLength(password)) {
            this.setData({
                password: password
            })
        }

    },

    /**
     * 校验密码长度
     * @param {*} password 
     */
    verifyPasswordLength(password) {
        var isPassword = password.length >= 6;
        this.setData({
            isPassword: isPassword
        })
        return isPassword;
    },

    /**判断是否是手机号*/
    isPhoneNumber(tel) {
        var reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
        return reg.test(tel);
    },

    /**
     * 点击获取验证码
     * @param {*} e 
     */
    handleGetVerifyTap(e) {
        // console.log(e)
        this.countDown();

        //发送获取验证码请求
    },

    /**
     * 获取验证码禁用倒计时
     */
    countDown() {
        var _this = this;
        var currentTime = 60;
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
     * 点击登录
     */
    loginTap(e) {
        var _this = this;
        var phone = _this.data.phone;
        var password = _this.data.password;

        wx.request({
            url: app.globalData.rootUrl + '/user/login',
            data: {
                username: phone,
                password: password
            },
            method: "POST",
            success(e) {
                // console.log(e)
                var message = e.data.message;
                //登录成功
                if (e.data.success) {
                    _this.doLoginSuccess(e, message);
                } else {
                    _this.doLoginFail(message);
                }
            }

        })
    },

    //登录成功
    doLoginSuccess(e, message) {
        var token = e.data.data.token;
        //存储token
        wx.setStorage({
            key: "token",
            data: token
        })
        wx.showToast({
            title: message,
            icon: 'success'
        })
        var user=e.data.data.userInfo;
        wx.setStorage({
            key:"userInfo",
            data:user
        })
        setTimeout(function(){
            wx.switchTab({
                url: '/pages/home/home',
            })
        },500)
        
    },

    //登录失败逻辑
    doLoginFail(message) {
        wx.showToast({
            title: message,
            icon: 'error'
        })
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

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