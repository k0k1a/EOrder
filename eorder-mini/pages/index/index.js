// index.js
// 获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    staticUrl: "http://localhost:8080/eorder/static",
    leftMenuList: null,
    //左侧菜单的当前索引
    currentIdx: -2,
    //优惠券
    upto: 11,
    down: 22,
    //order栏title
    titles: ["点菜", "评价"],
    //内容滑块index
    currentTitleIdx: 0,

    //商品信息集合
    goodsList: {},
    //具体的Goods信息
    goods: {},
    //商品加入订单的数量
    orderNum: 5,
    //选规格页面显示状态
    goodsInfoShowState: false,
    //选规格页面
    goodsNum: 1,

    // selectedOptions: [],
    //选择的类型
    selectedOptions: {
      text: '',
      extra: 0
    },
    selectedOptionsIds: [],
    //购物车详情状态
    GoodsCartInfoShowState: false,
    cartShowState: true,
    animationData: {},

    pageNo: 1,
    pageSize: 15,
    //订单列表
    cartList: [],
    totalPrice: 0,
    userInfo: {},
    token: '',
    isLogin: false,
    coupon: [],
    commentList: [],
    commentSearchType: [{
        name: '全部评论',
        select: true
      },
      {
        name: '最新评论',
        select: false
      },
      {
        name: '好评',
        select: false
      },
      {
        name: '差评',
        select: false
      }
    ]
  },

  //发送商品列表请求
  requestGoodsList(data, url) {
    var _this = this;
    wx.request({
      url: app.globalData.rootUrl + url,
      method: "POST",
      data: data,
      success(e) {
        var res = e.data;
        if (res.success) {
          _this.setData({
            goodsList: res.data
          })
        }
      }
    })
  },

  //查询更多商品
  requestMoreGoodsList(data, url) {
    var _this = this;
    wx.request({
      url: app.globalData.rootUrl + url,
      method: "POST",
      data: data,
      success(e) {
        if (e.data.success) {
          _this.pushMoreGoods(e.data.data)
        }
      },
      complete() {
        wx.hideLoading()
      }
    })
  },

  /**
   * 按照类型上拉刷新
   */
  requestMoreGoodsListByType(pageNo, pageSize) {
    var _this = this;
    var currentIdx = this.data.currentIdx;
    var leftMenu = this.data.leftMenuList;
    wx.request({
      url: app.globalData.rootUrl + '/goods/listByType',
      method: "POST",
      data: {
        pageNo: pageNo,
        pageSize: pageSize,
        goodsType: leftMenu[currentIdx].id
      },
      success(e) {
        if (e.data.success) {
          _this.pushMoreGoods(e.data.data)
        }
      },
      complete() {
        wx.hideLoading()
      }
    })
  },

  //放入更多商品
  pushMoreGoods(pageData) {
    var goodsList = this.data.goodsList;
    goodsList.pageNum = pageData.pageNum;
    goodsList.pageSize = pageData.pageSize;
    goodsList.pages = pageData.pages;
    goodsList.total = pageData.total;

    var length = goodsList.data.length;
    if (goodsList.data[length - 1].type == pageData.data[0].type) {
      pageData.data[0].goods.forEach(o => {
        goodsList.data[length - 1].goods.push(o);
      })
      pageData.data.shift();
    }
    pageData.data.forEach(page => {
      goodsList.data.push(page)
    })

    this.setData({
      goodsList: goodsList
    })
  },

  //确认订单
  handle2BuyInfo(e) {
    var token = wx.getStorageSync('token')
    if (!token) {
      wx.showToast({
        title: '请先登录',
        icon: 'error'
      })
      this.setData({
        isLogin: false
      })
      return;
    }
    wx.navigateTo({
      url: '/pages/sub_order_page/order_info/order_info',
    })
  },

  //购物车点击事件
  handleCartBtnTap() {
    var state = this.data.GoodsCartInfoShowState;
    if (state) {
      this.hideModel()
    } else {
      this.showModel()
    }
  },
  //显示对话框
  showModel: function () {
    // 显示遮罩层
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()

    // var state=!this.data.GoodsCartInfoShowState;
    this.setData({
      animationData: animation.export(),
      GoodsCartInfoShowState: true
    })

    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation.export()
      })
    }.bind(this), 200)
  },

  //隐藏对话框
  hideModel: function () {
    // 隐藏遮罩层
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()
    this.setData({
      animationData: animation.export(),
    })
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation.export(),
        GoodsCartInfoShowState: false
      })
    }.bind(this), 200)
  },

  //处理类型点击
  handleSelectStateTap(e) {
    var index1 = e.currentTarget.dataset.index1;
    var index2 = e.currentTarget.dataset.index2;
    var goods = this.data.goods;
    // var goodsOptions = goods.goodsOptions;
    goods.goodsOptions[index1].goodsOptionItems.forEach(els => {
      els.selectedState = false;
    });
    goods.goodsOptions[index1].goodsOptionItems[index2].selectedState = true;
    this.setData({
      goods: goods
    })

    this.setSelectedOptions();
  },

  //设置已选择Text
  setSelectedOptions() {
    var goods = this.data.goods;
    var res = '';
    var extra = 0;
    var ids = []
    goods.goodsOptions.forEach(item1 => {
      item1.goodsOptionItems.forEach(item2 => {
        if (item2.selectedState) {
          ids.push(item2.id)
          res += "+" + item2.optionItem;
          extra += item2.extraPrice;
        }
      })
    })
    res = res.slice(1)
    this.setData({
      selectedOptions: {
        text: res,
        extra: extra
      },
      selectedOptionsIds: ids
    })
  },


  //加
  handleGoodsAddTap(e) {
    var num = this.data.goodsNum + 1;
    this.setData({
      goodsNum: num
    })
  },

  //减
  handleGoodsReduceTap(e) {
    var num = this.data.goodsNum > 0 ? this.data.goodsNum - 1 : 0;
    this.setData({
      goodsNum: num
    })
  },

  //商品详情界面的隐藏
  handleGoodsInfoHide(e) {
    var _this = this;
    // 隐藏遮罩层
    var animation = wx.createAnimation({
      duration: 500,
      timingFunction: "ease",
      delay: 0
    })
    _this.animation = animation
    animation.translateY(500).step()
    _this.setData({
      animationData: animation.export(),
    })

    setTimeout(function () {
      animation.translateY(0).step()
      _this.setData({
        animationData: animation.export(),
        selectedOptions: {
          text: "",
          extra: 0
        },
        goods: null,
        goodsNum: 1,
        goodsInfoShowState: false,
        cartShowState: true
      })
    }.bind(this), 200)

    wx.showTabBar()
  },

  //商品详情界面的显示
  handleGoodsInfoShow(e) {
    // console.log(e.currentTarget.dataset.goodsid)
    //请求具体Goods信息
    var _this = this;
    var goodsId = e.currentTarget.dataset.goodsid;
    this.requestGoods(goodsId);

    // 显示遮罩层
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(500).step()
    this.setData({
      animationData: animation.export(),
      goodsInfoShowState: true,
      cartShowState: false
    })
    setTimeout(function () {
      animation.translateY(0).step()
      _this.setData({
        animationData: animation.export()
      })
    }.bind(this), 200)

    wx.hideTabBar();

  },

  //查询商品
  requestGoods(goodsId) {
    var _this = this;
    wx.request({
      url: app.globalData.rootUrl + '/goods/getGoods',
      method: "GET",
      data: {
        goodsId: goodsId
      },
      success(e) {
        if (e.data.success) {
          var goods = e.data.data;
          _this.initOptions(goods)
          _this.setData({
            goods: goods
          })
        }
      }
    })
  },

  //初始化option数组的selectedState属性
  initOptions(goods) {
    var goodsOptions = goods.goodsOptions;

    for (var i = 0; i < goodsOptions.length; i++) {
      var goodsOptionItems = goodsOptions[i].goodsOptionItems;
      for (var j = 0; j < goodsOptionItems.length; j++) {
        goodsOptionItems[j].selectedState = false;
      }
    }
  },

  //点击减号时，减少orderNum数量
  handleReduceTap(e) {
    var goodsId = e.currentTarget.dataset.goodsId;
    this.removeCart(goodsId);
    this.calculateTotalPrice(this.data.cartList);
    this.updateCartList2Storage();
  },

  //点击加号时，减少orderNum数量
  handleAddTap(e) {
    var goods = e.currentTarget.dataset.goods;
    this.addCart(goods);
    this.calculateTotalPrice(this.data.cartList);
    this.updateCartList2Storage();
  },

  //增加购物车商品数量
  handleAddCartGoodsTap(e) {
    var goodsId = e.currentTarget.dataset.goodsId;
    var cartList = this.data.cartList;
    for (var i = 0; i < cartList.length; i++) {
      if (cartList[i].goodsId == goodsId) {
        var oldV = cartList[i].num;
        cartList[i].num = oldV + 1;
        this.setData({
          cartList: cartList
        })
        break;
      }
    }
    this.updateCartList2Storage();
    this.calculateTotalPrice(this.data.cartList);
  },

  //点击添加到购物车
  handleAdd2CartGoodsTap(e) {
    var goodsNum = this.data.goodsNum;
    var goods = this.data.goods;
    var selectedOptions = this.data.selectedOptions;
    var selectedOptionsIds = this.data.selectedOptionsIds;
    var cartList = this.data.cartList;

    var idx = -1;
    //购物车已经含有
    cartList.forEach((cart, index) => {
      if (cart.goodsId == goods.id) {
        idx = index;
      }
    })

    var num = goodsNum > 0 ? goodsNum : 1;
    if (idx != -1) {
      cartList[idx].num = num;
      cartList[idx].selectedOptions = selectedOptions;
    } else {
      //购物车中没有
      cartList.push({
        goodsId: goods.id,
        goods: goods,
        selectedOptions: selectedOptions,
        selectedOptionsIds: selectedOptionsIds,
        num: num
      })
    }

    this.setData({
      cartList: cartList
    })

    this.handleGoodsInfoHide();
    this.calculateTotalPrice(cartList)
    this.updateCartList2Storage();
  },

  /**
   * 添加购物车
   * @param {JSON} goods 商品
   * @param {JSON} options 选项
   */
  addCart(goods) {
    var cartList = this.data.cartList;
    //判断购物车中是否含有，含有就只修改数量
    for (var i = 0; i < cartList.length; i++) {
      if (cartList[i].goodsId == goods.id) {
        var oldV = cartList[i].num;
        cartList[i].num = oldV + 1;
        this.setData({
          cartList: cartList
        })
        return;
      }
    }
    //购物车中没有
    cartList.push({
      goodsId: goods.id,
      goods: goods,
      num: 1,
      selectedOptions: {
        text: "",
        extra: 0
      }
    })
    this.setData({
      cartList: cartList
    })
  },

  /**
   * 根据id移除购物车
   * @param {*} goodsId 商品id
   */
  removeCart(goodsId) {
    var cartList = this.data.cartList;

    //修改商品数量
    for (var i = 0; i < cartList.length; i++) {
      if (cartList[i].goodsId != goodsId) {
        continue;
      }
      var oldV = cartList[i].num;
      if (oldV - 1 == 0) {
        //删除i位置1个元素
        cartList.splice(i, 1);
      } else {
        cartList[i].num = oldV - 1;
      }
      this.setData({
        cartList: cartList
      })
      break;
    }
  },

  /**
   * 清空购物车
   */
  clearCart() {
    this.setData({
      cartList: []
    })
  },

  /**
   * 更新购物车缓存
   */
  updateCartList2Storage() {
    var cartList = this.data.cartList;
    wx.setStorage({
      key: "cartList",
      data: cartList,
      success() {
        console.log("add cartList to storage success")
      }
    })
  },

  //处理清空购物车点击事件
  handleClearCartTap() {
    this.clearCart();
    this.updateCartList2Storage();
  },

  //点击title时
  handleTitleTap(e) {
    // console.log(e);
    var index = e.currentTarget.dataset.index;
    this.setData({
      currentTitleIdx: index
    })
  },

  /**
   * 请求评论列表
   */
  requestCommentList() {
    var _this = this
    wx.request({
      url: app.globalData.rootUrl + '/user/comment/visit/list',
      method: "POST",
      data: {
        pageNo: _this.data.pageNo,
        pageSize: _this.data.pageSize
      },
      success(e) {
        var data = e.data.data
        if (data.data.length > 0) {
          data.data.forEach(comment=>{
            comment.avatarUrl = app.testUrl(comment.avatarUrl) ? comment.avatarUrl : app.globalData.serverPrefix+comment.avatarUrl;
          });
        }
        _this.setData({
          commentList: data
        })
      }
    })
  },

  /**
   * 滑块滑动时，修改title下的小条
   * @param {*} e 
   */
  handleSwiperChange(e) {
    // console.log(e.detail.current);
    var idx = e.detail.current;
    var cartShowState = idx == 1 ? false : true;

    this.setData({
      currentTitleIdx: idx,
      cartShowState: cartShowState
    })
    if (idx == 1) {
      this.requestCommentList()
    }
  },

  //左侧菜单栏点击事件
  handleItemTap(e) {
    /**
     * 1.获取被点击的标题身上的索引
     * 2.给data中的currentIdx赋值
     * 3.根据不同的索引进行搜索右侧商品
     */
    var index = e.currentTarget.dataset.index;
    var data = {
      pageNo: this.data.pageNo,
      pageSize: this.data.pageSize
    }
    if (index == -3) {
      //推荐

    } else if (index == -2) {
      //全部商品
      this.requestGoodsList(data, '/goods/list');
    } else if (index == -1) {
      //热门商品
      this.requestGoodsList(data, '/goods/list/hot');
    } else {
      var typeId = e.currentTarget.dataset.typeId;
      data.goodsType = typeId
      this.requestGoodsList(data, '/goods/listByType');
    }

    this.setData({
      currentIdx: index
    })
  },

  //查询商品类型列表
  requestGoodsTypeList() {
    var _this = this;
    wx.request({
      url: app.globalData.rootUrl + '/goods/type/list',
      method: "GET",
      success(e) {
        if (e.data.success) {
          _this.setData({
            leftMenuList: e.data.data
          })
        }
      }
    })
  },

  //商品滑块触底事件
  handleToBottom() {
    var goodsList = this.data.goodsList;
    var currentIdx = this.data.currentIdx;
    if (goodsList.pageNum >= goodsList.pages) {
      return;
    }
    app.showLoading("加载中")
    var data = {
      pageNo: goodsList.pageNum + 1,
      pageSize: goodsList.pageSize
    }
    if (currentIdx == -3) {
      //推荐
    } else if (currentIdx == -2) {
      //全部商品
      this.requestMoreGoodsList(data, '/goods/list');
    } else if (currentIdx == -1) {
      //热门商品
      this.requestMoreGoodsList(data, '/goods/list/hot');
    } else {
      //按类型上拉刷新
      var currentIdx = this.data.currentIdx;
      var leftMenu = this.data.leftMenuList;
      data.goodsType = leftMenu[currentIdx].id;
      // this.requestMoreGoodsListByType(goodsList.pageNum + 1, goodsList.pageSize);
      this.requestMoreGoodsList(data, '/goods/listByType');
    }
  },

  //评论滑块触底
  handleToCommentBottom() {
    var commentList = this.data.commentList;
    if (commentList.pageNum >= commentList.pages) {
      this.setData({
        reachedBottom: true
      })
      return;
    }
    app.showLoading("加载中")
    this.nextCommentPage(commentList.pageNum + 1, commentList.pageSize, '/user/comment/visit/list');
  },

  //下一页评论
  nextCommentPage(pageNo, pageSize, url) {
    var _this = this
    wx.request({
      url: app.globalData.rootUrl + url,
      method: "POST",
      data: {
        pageNo: pageNo,
        pageSize: pageSize
      },
      success(e) {
        var data = e.data.data //commentList
        data.data.forEach(comment=>{
          comment.avatarUrl = app.testUrl(comment.avatarUrl) ? comment.avatarUrl : app.globalData.serverPrefix+comment.avatarUrl;
        });
        _this.pushMoreComment(data);
      },
      complete() {
        wx.hideLoading()
      }
    })
  },

  // 加入更多评论
  pushMoreComment(commentList) {
    var comments = this.data.commentList;
    comments.pageNum = commentList.pageNum;
    comments.pageSize = commentList.pageSize;
    comments.pages = commentList.pages;
    comments.total = commentList.total;

    if (commentList.data.length > 0) {
      commentList.data.forEach(comment => {
        comment.avatarUrl = app.testUrl(comment.avatarUrl) ? comment.avatarUrl : app.globalData.serverPrefix+comment.avatarUrl;
        comments.data.push(comment)
      })
    }

    this.setData({
      commentList: comments
    })
  },

  //跳转到商品详情页面
  navigate2GoodsInfo(e) {
    var goodsId = e.currentTarget.dataset.goodsId;
    wx.navigateTo({
      url: '/pages/sub_index_page/goods_info/goods_info?goodsId=' + goodsId,
    })
  },

  /**
   * 得到购物车缓存数据
   */
  getCartListStorage() {
    var cartList = wx.getStorageSync('cartList');
    //缓存中为空时，初始化购物车为集合
    if (cartList == '') {
      cartList = [];
    }
    this.setData({
      cartList: cartList
    })
    this.calculateTotalPrice(cartList);
  },

  //获取userInfo缓存
  getUserInfo() {
    var userInfo = wx.getStorageSync('userInfo');
    return userInfo;
  },

  //初始化userInfo
  initUserInfo() {
    var user = this.getUserInfo();
    this.setData({
      userInfo: user
    })
  },

  /**
   * 计算总价
   * @param {Array} cartList 
   */
  calculateTotalPrice(cartList) {
    var total = 0;
    cartList.forEach(cart => {
      total += cart.selectedOptions.extra * cart.num + cart.goods.price * cart.num;
    })
    this.setData({
      totalPrice: total
    })
  },

  //获取优惠券，已登录用户
  getLoginCoupons() {
    var _this = this
    var token = _this.data.token;
    wx.request({
      url: app.globalData.rootUrl + '/coupon/get',
      method: "POST",
      header: {
        token: token
      },
      data: {
        pageNo: 0,
        pageSize: 10
      },
      success(e) {
        if (e.data.success) {
          _this.setData({
            coupon: e.data.data
          })
        } else {
          _this.showFail()
        }
      }
    })
  },

  //游客用户
  getVisitCoupons() {
    var _this = this;

    wx.request({
      url: app.globalData.rootUrl + '/coupon/visit/get',
      method: "POST",
      data: {
        pageNo: 0,
        pageSize: 10
      },
      success(e) {
        if (e.data.success) {
          _this.setData({
            coupon: e.data.data
          })
        } else {
          _this.showFail()
        }
      }
    })
  },

  showFail() {
    wx.showToast({
      title: '获取失败',
      icon: 'error'
    })
  },

  /**
   * 获取优惠券
   */
  getCoupons() {
    var token = app.getToken();
    if (token) {
      //已登录用户
      this.setData({
        token: token,
        isLogin: true
      })
      this.getLoginCoupons();
    } else {
      this.getVisitCoupons();
    }
  },

  //优惠券点击事件
  handleCouponTap() {
    wx.navigateTo({
      url: '/pages/sub_home_page/coupon/coupon',
    })
  },

  //评论分类
  handleCommentTap(e) {
    var idx = e.currentTarget.dataset.index;
    var commentSearchType = this.data.commentSearchType;

    commentSearchType.forEach(o => {
      o.select = false;
    })
    commentSearchType[idx].select = !commentSearchType[idx].select
    this.setData({
      commentSearchType: commentSearchType
    })

    this.requestCommentByTypes(idx);
  },

  //分类搜索评论
  requestCommentByTypes(type) {
    var _this = this
    wx.request({
      url: app.globalData.rootUrl + '/user/comment/visit/search',
      method: "POST",
      data: {
        pageNo: this.data.pageNo,
        pageSize: this.data.pageSize,
        type: type
      },
      success(e) {
        if (e.data.success) {
          _this.setData({
            commentList: e.data.data
          })
        }
      }
    })
  },

  handle2AboutUs() {
    wx.navigateTo({
      url: '/pages/sub_home_page/about-us/about-us',
    })
  },

  /**
   * 请求店铺信息
   */
  requestMerchantInfo() {
    var _this = this
    wx.request({
      url: app.globalData.rootUrl + '/user/visit/merchant/get',
      method: "GET",
      success(e) {
        if (e.data.success) {
          _this.setData({
            merchant: e.data.data
          })
        }
      }
    })
  },

  initData(){
    this.setData({
      serverPrefix:app.globalData.serverPrefix
    })
  },
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
    //全部商品
    var param = {
      pageNo: this.data.pageNo,
      pageSize: this.data.pageSize
    }
    this.initData();
    this.requestGoodsList(param, '/goods/list');
    this.requestGoodsTypeList();
    this.getCartListStorage();
    this.requestMerchantInfo();
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.initUserInfo();
    this.getCartListStorage();
    this.getCoupons();
    app.checkToken()
  },

})