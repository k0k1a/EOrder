// pages/sub_index_page/goods_info/goods_info.js
// 获取应用实例
const app = getApp()

Page({

    /**
     * 页面的初始数据
     */
    data: {
        currentTitleIdx: 0,
        staticUrl: "",
        // 商品
        goods: {},
        goodsNum: 1,
        animationData: {},
        goodsInfoShowState: false,
        //选择的类型
        selectedOptions: {
            text: '',
            extra: 0
        },
        cartList: []
    },

    handleSwiperChange(e) {
        var idx = e.detail.current;
        this.setData({
            currentTitleIdx: idx
        })
    },

    handleTitleTap(e) {
        // console.log(e.currentTarget.dataset.idx)
        var index = e.currentTarget.dataset.idx;
        this.setData({
            currentTitleIdx: index
        })
    },

    //查询商品
    requestGoods(goodsId) {
        var _this = this
        wx.request({
            url: app.globalData.rootUrl + '/goods/getGoods',
            method: "GET",
            data: {
                goodsId: goodsId
            },
            success(e) {
                if (e.data.success) {
                    var goods = e.data.data;
                    _this.setData({
                        goods: goods
                    })
                }
            }
        })
    },

    //商品详情界面的显示
    handleGoodsInfoShow(e) {
        var _this = this;
        // 显示遮罩层
        var animation = wx.createAnimation({
            duration: 200,
            timingFunction: "linear",
            delay: 0
        })
        _this.animation = animation
        animation.translateY(500).step()
        _this.setData({
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
                goodsInfoShowState: false,
            })
        }.bind(this), 200)

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
        var ids=[]
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
            setSelectedOptionsIds:ids
        })
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
        this.updateCartList2Storage();
        wx.navigateBack()
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
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        var goodsId = options.goodsId;
        this.requestGoods(goodsId)
        this.getCartListStorage();
        this.setData({
            staticUrl: app.globalData.staticUrl,
            serverPrefix:app.globalData.serverPrefix
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