// var Utils = require("../../utils/util.js");
const app = getApp()

var datas = {

    default_header: "/img/icon/default_header.png",
    header: "", // 上传图片的图片
    phone: "", // 手机号码
    uploadImg: null, // 提交的img
    isUpload:false,
    nickname: "", // 昵称

};

Page({

    data: datas,

    onLoad: function (options) {
        // var logValue = wx.getStorageSync("userInfo");
        // var default_header = this.data.default_header;
        // var header='';
        // if(logValue.avatarUrl==null){
        //     header=default_header;
        // }else{
        //     header=app.testUrl(logValue.avatarUrl)?logValue.avatarUrl:app.globalData.serverPrefix+logValue.avatarUrl;
        // }
        
        // this.setData({
        //     phone: logValue.phone,
        //     nickname: logValue.nickName,
        //     header: header,
        //     serverPrefix:app.globalData.serverPrefix
        // })

        var token = app.getToken();
        if (token) {
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
            var user=e.data.data;
            var default_header = _this.data.default_header;
            var header='';
            if(user.avatarUrl==null){
                header=default_header;
            }else{
                header=app.testUrl(user.avatarUrl)?user.avatarUrl:app.globalData.serverPrefix+user.avatarUrl;
            }
            _this.setData({
                phone: user.phone,
                nickname: user.nickName,
                header: header,
                serverPrefix:app.globalData.serverPrefix
            })
          }
        })
      },

    formSubmit: function (e) { //提交数据

        if (this.data.nickname.length==0) {
            app.showErrMsg("昵称不能为空")
            return;
        }
        var phone = this.data.phone;
        if (!this.isPhoneNumber(phone)) {
            app.showErrMsg("手机号格式有误")
            return;
        }
        var isUpload=this.data.isUpload;
        var header=isUpload ? this.data.uploadImg : null;

        var param = {
            nickname:this.data.nickname,
            phone:this.data.phone,
            avatarUrl:header
        }

        wx.request({
          url: app.globalData.rootUrl+'/user/update',
          method:"POST",
          header:{
            token:app.getToken()
          },
          data:param,
          success(e){
            var data=e.data
            app.showMsg(data.message)
            if(data.success){
                setTimeout(function(){
                    wx.switchTab({
                        url: '/pages/home/home',
                    })
                },500)
            }
          }
        })
    },

    handleName(e) {
        this.setData({
            nickname: e.detail.value
        })
    },

    handlePhone(e) {
        var phone = e.detail.value;
        this.setData({
            phone: phone
        })
    },

    /**判断是否是手机号*/
    isPhoneNumber(tel) {
        var reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
        return reg.test(tel);
    },

    uploadFn: function () { // 上传图片
        var _this = this;
        wx.chooseImage({
            count: 1,
            sizeType: ['original', 'compressed'],
            sourceType: ['album', 'camera'],
            success: function (res) {
                var tempFilePaths = res.tempFilePaths;
                wx.uploadFile({
                    url: app.globalData.fileUrl + "/upload",
                    filePath: tempFilePaths[0],
                    name: 'file',
                    header: {
                        token: app.getToken()
                    },
                    
                    success: function (res) {
                        var data = JSON.parse(res.data)
                        if (!data.success) {
                            app.showErrMsg("上传失败")
                        } else {
                            _this.setData({
                                isUpload:true,
                                uploadImg: data.data.fileDownloadUri
                            })
                        }
                    }
                })
            }
        })
    },


})