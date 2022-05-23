// pages/search/search.js
// 获取应用实例
const app = getApp()

Page({

    /**
     * 页面的初始数据
     */
    data: {
        inputValue: '',
        goodsList: [],
        staticUrl: '',
        historyList: [],
        showHistoryList: [],
        historyShow: false,
        // 是否显示全部
        showAll:true,
        result:false,
        topList:[]
    },

    //处理输入
    handleInput(e) {
        var input = e.detail.value;
        this.setData({
            inputValue: input
        })
    },

    //点击搜索
    handleSearchTap(e) {
        var inputValue = this.data.inputValue;
        if (inputValue.trim().length <= 0) {
            return;
        }
        this.search(inputValue);
        this.add2History(inputValue);
    },

    /**
     * 搜索
     * @param {*} value 
     */
    search(value){
        var _this=this
        wx.showLoading({
            title: '搜索中',
        })
        wx.request({
            url: app.globalData.rootUrl + '/goods/search',
            method: "GET",
            data: {
                inputValue: value
            },
            success(e) {
                wx.hideLoading()
                _this.setData({
                    goodsList: e.data.data,
                    historyShow: false,
                    result:true
                })
            }
        })
    },

    //点击取消
    handleCancleTap() {
        wx.navigateBack({
            delta: 1,
        })
    },

    /**
     * 添加购物车
     * @param {*} input 搜索值
     */
    add2History(input) {
        var historyList = this.data.historyList;
        for (var i = 0; i < historyList.length; i++) {
            if(historyList[i]==input){
                return;
            }
        }
        historyList.push(input);
        this.setData({
            historyList: historyList
        })
        this.storeHistory()
    },

    /**
     * 删除历史记录
     * @param {*} index 
     */
    removeHistory(e) {
        var index = e.currentTarget.dataset.index;
        var historyList = this.data.historyList;
        historyList.splice(index, 1);
        this.setData({
            historyList: historyList,
            historyShow:historyList.length!=0
        })
        this.setSubShowHistoryList();
        this.storeHistory();
    },

    clearAll() {
        this.setData({
            historyList: [],
            showHistoryList: [],
            historyShow:false
        })
        wx.removeStorageSync('searchHistory')
    },

    //展示部分
    setSubShowHistoryList() {
        var historyList = this.data.historyList;
        var showHistoryList = [];

        var idx = historyList.length > 3 ? 3 : historyList.length;
        for (var i = 0; i < idx; i++) {
            showHistoryList.push(historyList[i]);
        }
        this.setData({
            showHistoryList: showHistoryList
        })
    },

    //全部历史
    setAllShowHistoryList() {
        this.setData({
            showAll: false,
            showHistoryList: this.data.historyList
        })
    },

    //缓存历史记录
    storeHistory() {
        var history = this.data.historyList;
        wx.setStorage({
            key: "searchHistory",
            data: history
        })
    },

    /**
     * 点击重新搜索
     * @param {*} e 
     */
    reSearch(e) {
        var value=e.currentTarget.dataset.value;
        this.search(value)
        this.add2History(value);
    },

    getTopList(){
        var _this=this
        wx.request({
          url: app.globalData.rootUrl+'/goods/search/top/10',
          method:"GET",
          success(e){
              if(e.data.success){
                  _this.setData({
                    topList:e.data.data
                  })
              }
          }
        })
    },

    initData() {
        var _this = this
        var staticUrl = app.globalData.staticUrl;
        this.setData({
            staticUrl: staticUrl,
            serverPrefix:app.globalData.serverPrefix
        })
        var historyList = wx.getStorageSync('searchHistory')
        if (historyList.length>0) {
            _this.setData({
                historyShow:true,
                historyList: historyList
            })
        }
        this.setSubShowHistoryList();
        this.getTopList();
    },

    handle2GoodsInfo(e){
        var goodsId = e.currentTarget.dataset.goodsId;
        wx.navigateTo({
          url: '/pages/sub_index_page/goods_info/goods_info?goodsId=' + goodsId,
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.initData()
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