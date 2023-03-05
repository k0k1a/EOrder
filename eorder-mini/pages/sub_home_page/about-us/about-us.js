// pages/sub_home_page/about-us/about-us.js
const app = getApp()

Page({

    /**
     * 页面的初始数据
     */
    data: {
        merchant:{}
    },

    /**
     * 请求店铺信息
     */
    requestMerchantInfo(){
        var _this=this
        wx.request({
          url: app.globalData.rootUrl + '/user/visit/merchant/get',
          method:"GET",
          success(e){
              if(e.data.success){
                  _this.setData({
                    merchant:e.data.data
                  })
              }
          }
        })
    },

    handleAddressTap(){
        var merchant=this.data.merchant
        wx.openLocation({
          latitude: 30.651446,
          longitude: 104.189026,
          name:merchant.storeName,
          address:merchant.description
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.requestMerchantInfo()
        this.setData({
            serverPrefix:app.globalData.serverPrefix
        })
    }

})