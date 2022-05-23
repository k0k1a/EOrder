// pages/pay_success/pay_success.js

var ownType= {
  onlyTable:'onlyTable',//仅仅订桌
}

var app = getApp();

Page({

  /**
   * 组件的初始数据
   */
  data: {
    ownType:ownType,
    /**
     * 当前页面是哪里过来的。。(接地气。。)
     */
    type:'',
    price:''
  },

  onLoad(options){
    
  },
  checkOrder(){
    wx.switchTab({
      url: '../indent/indent'
    })
  },


})
