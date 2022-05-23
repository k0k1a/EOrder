// pages/order/order.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    tabIdx: 0,
    token: '',
    orderPage: {},
    staticUrl: '',
    reachedBottom: false,
    pageNo:0,
    pageSize:20,
    url:['/user/order/list','/user/order/noComment/list']
  },

  //点击标题
  handleTitleTap(e) {
    var idx = e.currentTarget.dataset.index
    this.setData({
      tabIdx: idx
    })
    if(idx==2){
      //退款
      return;
    }
    var url=this.data.url
    var pageNo=this.data.pageNo;
    var pageSize=this.data.pageSize;
    this.requestOrderList(pageNo,pageSize,url[idx])
    
  },

  //删除订单
  handleDeleteOrder() {
    wx.showModal({
      title: '提示',
      content: '确定要删除该订单吗？',
      success(res) {
        if (res.confirm) {
          console.log('用户点击确定')
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    });
  },

  /**
   * 请求我的订单列表
   * @param {*} pageNo 页数
   * @param {*} pageSize 页面大小
   * @param {string} url 请求路径
   * return 分页数据
   */
  requestOrderList(pageNo, pageSize,url) {
    var _this = this
    var token = this.data.token
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
          _this.setData({
            orderPage: e.data.data
          })
        } else {
          if(e.data.code=='4001'){
            app.showErrMsg('请先登录')
            return;
          }
          app.showErrMsg(e.data.message)
        }
      }
    })
  },

  initData() {
    var token = wx.getStorageSync('token')
    var staticUrl = app.globalData.staticUrl;
    this.setData({
      token: token,
      staticUrl: staticUrl,
      serverPrefix:app.globalData.serverPrefix
    })
  },

  //触底事件
  handleReachBottom() {
    var _this = this
    var orderPage = this.data.orderPage;
    if (orderPage.pageNum >= orderPage.pages) {
      this.setData({
        reachedBottom: true
      })
      return;
    }
    var pageNo = orderPage.pageNum + 1;
    var pageSize = orderPage.pageSize;
    var token = this.data.token
    var url=this.data.url;
    var idx=this.data.tabIdx
    app.showLoading("加载中")
    wx.request({
      url: app.globalData.rootUrl + url[idx],
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
          _this.moreOrder(e.data.data)
        } else {
          app.showErrMsg(e.data.message)
        }
      },
      complete(){
        wx.hideLoading()
      }
    })

  },

  //加载更多订单
  moreOrder(order) {
    var orderPage = this.data.orderPage;
    orderPage.pageNum = order.pageNum;
    orderPage.pageSize = order.pageSize;
    orderPage.pages = order.pages;
    orderPage.total = order.total;
    order.data.forEach(o => {
      orderPage.data.push(o)
    })
    this.setData({
      orderPage: orderPage
    })
  },

  //跳转到订单详情页面
  handle2Detail(e) {
    var orderNum = e.currentTarget.dataset.orderNum
    wx.navigateTo({
      url: '/pages/sub_order_page/order_detail/detail?orderNum=' + orderNum,
    })
  },

  //关闭订单点击
  handleCloseTap(e) {
    var _this = this;
    var index = e.currentTarget.dataset.index;
    wx.showModal({
      title: '提示',
      content: '确认要关闭订单吗？',
      success(res) {
        if (res.confirm) {
          _this.requestCancelOrder(index)
        }
      }
    })

  },

  /**
   * 请求取消订单
   * @param {Integer} index orderPage.data的索引
   */
  requestCancelOrder(index) {
    var _this = this;
    var orderPage = this.data.orderPage;
    var orderNum = orderPage.data[index].orderNum;
    wx.request({
      url: app.globalData.rootUrl + '/user/order/cancel',
      method: "GET",
      header: {
        token: this.data.token
      },
      data: {
        orderNum: orderNum
      },
      success(e) {
        if (e.data.success) {
          orderPage.data[index].tradeStatus = 3;
          _this.setData({
            orderPage: orderPage
          })
        }
      }
    })
  },

  //点击立即支付
  handlePayTap(e) {
    var _this = this
    var index = e.currentTarget.dataset.index;
    var orderPage = this.data.orderPage;
    var order = orderPage.data[index];
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
              orderPage.data[index].tradeStatus = 1;
              app.showMsg(e.data.message)
            }else{
              // orderPage.data[index].tradeStatus = 3;
              app.showErrMsg(e.data.message)
            }
            _this.setData({
              orderPage: orderPage
            })
          }
        })
      }
    })
  },

  //点击再来一单
  handleReOrderTap(e) {
    //把订单商品全部加入购物车
    var index = e.currentTarget.dataset.index;
    var order = this.data.orderPage.data[index];

    order.goodsList.forEach(goods => {
      var res = {
        id: goods.goodsId,
        price: goods.goodsPrice,
        pictures: [{
          picUrl: goods.goodsPic
        }],
        goodsName: goods.goodsName
      }
      this.addCartList(res, goods.goodsQuantity)
    });
    wx.showToast({
      title: '加入购物车成功',
    })
  },

  /**
   * 添加购物车
   * @param {*} goods 
   * @param {*} num 
   */
  addCartList(goods, num) {
    var cartList = wx.getStorageSync('cartList');
    var contained = false;
    if(cartList==''){
      cartList=[]
    }
    //判断购物车中是否含有，含有就只修改数量
    cartList.forEach((cart, i) => {
      if (cart.goodsId == goods.id) {
        var oldV = cartList[i].num;
        cartList[i].num = oldV + num;
        contained = true;
      }
    })
    if (!contained) {
      //购物车中没有
      cartList.push({
        goodsId: goods.id,
        goods: goods,
        num: num,
        selectedOptions: {
          text: "",
          extra: 0
        }
      })
    }
    wx.setStorage({
      key: "cartList",
      data: cartList
    })
  },

  /**
   * 跳转到评论页面
   */
  handle2Comment(e){
    var index = e.currentTarget.dataset.index;
    var order = this.data.orderPage.data[index];
    var orderNum=order.orderNum;
    wx.navigateTo({
      url: '/pages/sub_order_page/review/review?orderNum='+orderNum,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.initData();
    var tabIdx = this.data.tabIdx;
    if(tabIdx==2){
      //退款暂时没写
      return;
    }
    var url=this.data.url
    var pageNo=this.data.pageNo;
    var pageSize=this.data.pageSize;
    this.requestOrderList(pageNo,pageSize,url[tabIdx])
  },
})