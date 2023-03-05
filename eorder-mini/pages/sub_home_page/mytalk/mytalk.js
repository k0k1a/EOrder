// pages/mytalk/mytalk.js 我的点评页面
var app = getApp();

Page({


  data: {
    showflag: false, //显示空页面
    pageNo: 1,
    pageSize: 10,
    commentList: [],
  },

  onLoad(options) {
    this.setData({
      serverPrefix:app.globalData.serverPrefix
    })
    this.initComment();
  },

  initComment() {
    var _this = this
    wx.request({
      url: app.globalData.rootUrl + '/user/comment/list',
      method: "POST",
      header: {
        token: app.getToken()
      },
      data: {
        pageNo: _this.data.pageNo,
        pageSize: _this.data.pageSize
      },
      success(e) {
        if(e.data.success){
          var data = e.data.data
          var show = data.total > 0
          _this.setData({
            commentList: data,
            showflag: show
          })
        }
      }
    })
  },

  nextCommentPage(pageNo, pageSize, url) {
    var _this = this
    wx.request({
      url: app.globalData.rootUrl + url,
      method: "POST",
      header: {
        token: app.getToken()
      },
      data: {
        pageNo: pageNo,
        pageSize: pageSize
      },
      success(e) {
        var data = e.data.data //commentList
        _this.pushMoreComment(data);
      }
    })
  },

  pushMoreComment(commentList) {
    var comments = this.data.commentList;
    comments.pageNum = commentList.pageNum;
    comments.pageSize = commentList.pageSize;
    comments.pages = commentList.pages;
    comments.total = commentList.total;

    commentList.data.forEach(o => {
      comments.data.push(o)
    })
    this.setData({
      commentList: comments
    })
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var commentList = this.data.commentList;
    if (commentList.pageNum == commentList.pages) {
      this.setData({
        reachedBottom: true
      })
      return;
    }
    this.nextCommentPage(commentList.pageNum+1, commentList.pageSize, '/user/comment/list');
  },

})