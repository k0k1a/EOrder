// pages/review/review.js
import {
  Http
} from '../../../utils/httpClient.js';
var app = getApp();
import {
  URL
} from '../../../utils/urlModel';

Page({

  /**
   * 页面的初始数据
   */
  data: {
    /**
     * 项目根路径
     */
    PROJECT_ROOT: URL.PROJECT_ROOT,
    /**
     * 文件上传暂存数组
     */
    pics: [],

    /**
     * 上传完成预览暂存数组
     */
    previewSrcs: [],

    /**
     * 当前选择店铺Id
     */
    shopId: '',

    /**
     * 当前页码
     */
    page: 1,

    /**
     * 是否有图
     * 1:无图评价 2：有图评价
     */
    type: 1,

    /**
     * 评分
     */
    score: 5,

    /**
     * 评价内容
     */
    content: '',

    /**
     * 服务打分
     */
    service: 5,
    /**
     * 口味打分
     */
    taste: 5,
    /**
     * 环境打分
     */
    environment: 5,

    /**
     * 根据评分显示字体
     */
    font_score: ['超级差', '很差', '差', '一般', '好', '非常好'],
    /**
     * 订单号
     */
    orderNum: '',

    /**
     * 此时是否可以提交
     * 用于验证提交规则
     */
    isSubmit: false,

  },

  initData(options) {
    // console.log(options);
    var orderNum = options.orderNum;

    this.setData({
      orderNum: orderNum,
      serverPrefix:app.globalData.serverPrefix
    })

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.initData(options);
  },

  //评价文本框改变
  changeText(e) {
    this.setData({
      content: e.detail.value
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  //获取token
  getToken() {
    var token = wx.getStorageSync('token');
    return token;
  },

  /**
   * 口味评分选中改变
   */
  tasteHandleChange: function (e) {
    this.setData({
      taste: e.detail.value
    })
  },

  /**
   * 口味评分选中改变
   */
  environmentHandleChange: function (e) {
    this.setData({
      environment: e.detail.value
    })
  },

  /**
   * 口味评分选中改变
   */
  serviceHandleChange: function (e) {
    this.setData({
      service: e.detail.value
    })
  },

  /**
   * 选择图片
   */
  selectImage: function () {
    var that = this,
      pics = this.data.pics;
    wx.chooseImage({
      count: 3, // 最多可以选择的图片张数，默认9
      sizeType: ['original', 'compressed'], // original 原图，compressed 压缩图，默认二者都有
      sourceType: ['album', 'camera'], // album 从相册选图，camera 使用相机，默认二者都有
      success: function (res) {
        var imgsrc = res.tempFilePaths;
        pics = pics.concat(imgsrc);
        that.setData({
          pics: pics,
          previewSrcs: []
        });
      },
      complete: function () {
        // complete
        that.uploadimgs();
      }
    })
  },

  /**
   * 多张图片图片上传调用
   */
  uploadimgs: function () { //这里触发图片上传的方法
    var pics = this.data.pics;
    this.uploadimg({
      url: app.globalData.fileUrl + "/upload", //上传的接口
      path: pics //这里是选取的图片的地址数组
    });
  },

  /**
   * 图片上传专用方法
   * @param data
   */
  uploadimg: function (data) {
    var that = this,
      i = data.i ? data.i : 0, //当前上传的哪张图片
      success = data.success ? data.success : 0; //上传成功的个数
    wx.uploadFile({
      url: data.url,
      filePath: data.path[i],
      name: 'file', //这里根据自己的实际情况改
      header: {
        token: that.getToken()
      },
      success: (resp) => {
        success++; //图片上传成功，图片上传成功的变量+1
        var data = JSON.parse(resp.data)
        if (!data.success) {
          wx.showToast({
            title: '上传失败'
          });
        } else {
          that.data.previewSrcs.push(data.data.fileDownloadUri);
          that.setData({
            previewSrcs: that.data.previewSrcs
          })
          //这里可能有BUG，失败也会执行这里,所以这里应该是后台返回过来的状态码为成功时，这里的success才+1
        }

      },
      complete: () => {
        i++; //这个图片执行完上传后，开始上传下一张
        var length = data.path.length;
        if (length != 0 && i == length) { //当图片传完时，停止调用
          that.data.pics.length = 0; //图片上传完成清空队列
        } else if (length != 0 && i != length) { //若图片还没有传完，则继续调用函数
          data.i = i;
          data.success = success;
          that.uploadimg(data);
        }
      }
    });
  },

  /**
   * 点击发布按钮
   */
  clickIssue(e) {
    if(this.data.content==''){
      app.showErrMsg("请输入评价内容")
      return;
    }

    var argu = {
      content: this.data.content,
      pics: this.data.previewSrcs, //需要把参数转化为字符串
      serviceScore: this.data.service,
      tasteScore: this.data.taste,
      envScore: this.data.environment,
      orderNum: this.data.orderNum //订单号
    }

    wx.request({
      url: app.globalData.rootUrl+'/user/comment',
      method:'POST',
      header:{
        token:this.getToken()
      },
      data: argu,
      success(e){
        //跳转
        var data=e.data;
        app.showMsg(data.message)
        if(data.success){
          setTimeout(function(){
            wx.redirectTo({
              url: '/pages/sub_home_page/mytalk/mytalk',
            })
          },500)
          
        }
      }
    })
    // httpClient.postPutEvaluate(argu).then((data) => {
    //   wx.showToast({
    //     title: data.msg
    //   })
    //   if (data.code == 200) {
    //     wx.navigateTo({
    //       url: "../mytalk/mytalk"
    //     })
    //   }
    // });
  }



})