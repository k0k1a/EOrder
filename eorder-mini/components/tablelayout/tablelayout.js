Component({

    options: {
      multipleSlots: true // 在组件定义时的选项中启用多slot支持
    },
  
    /**
     * 组件的属性列表
     */
    properties: { 
        titles:{
          type:Array,
          value: ["点菜","评价"],
        }
    },
  
    /**
     * 组件的初始(内部)数据
     */
    data: {
  
      //标题 swiper-item 所在位置
      titleIndex: 0,
  
      //indiator 动画
      animation: "",
  
      //屏幕宽度 px
      screenWidth:"",
  
      //微信规定的屏幕宽度750 rpx
      wxScreenWidth:750,
  
      //指示器滑动范围宽度，单位宽度
      indicatorLayoutWidth:100,
      //swiper高度
      swiperHeight:"",
  
      //滑动状态：滑动到左边(1)、滑动到右边(2)、其他位置(0)
      scrollStatus:1,
  
      //swiper当前位置
      swiperIndex:0,
    },
  
    /**
     * 组件的方法列表
     */
    methods: {
      clickTitle(e) {
        //点击切换卡片
        var that = this;
  
        console.log(e.currentTarget.dataset.index);
  
        that.setData({
          swiperIndex: e.currentTarget.dataset.index
        });
      },
  
      swiperTrans:function (e) {
  
        var that = this;
  
        console.log("scroll-status " + that.data.scrollStatus)
  
        //todo 存在dx绝对值大于屏幕宽度的情况，会导致indicator移动到固定边界外，该情况真机是否也存在？
        // swipter位移 中间变量
        var dx;
        if (e.detail.dx >= 0)
          if (that.data.scrollStatus == 2)
            dx = that.data.screenWidth * that.data.titleIndex;
          else
            dx = e.detail.dx + that.data.screenWidth * that.data.titleIndex;
        else if (that.data.scrollStatus == 1)
          dx = 0
        else
          dx = e.detail.dx + that.data.screenWidth * that.data.titleIndex;
  
        //indicator与swipter之间移动比例
        var scale = (that.data.indicatorLayoutWidth / that.data.wxScreenWidth).toFixed(2);//保留两位小数，否则indicator有误差
        //indicator 位移
        console.log("dx " + dx)
        var ds = dx * scale;
        console.log("ds " + ds);
  
        this.transIndicator(ds);
      },
  
      /**
       * indicator 平移动画
       */
      transIndicator(x) {
        let option = {
          duration: 100,
          timingFunction: 'linear'
        };
  
        this.animation = wx.createAnimation(option);
        this.animation.translateX(x).step();
        this.setData({
          animation: this.animation.export()
        })
      },
  
      swiperAnimationfinish: function(e) {
        // console.log("e.detail.dx " + e.detail.current);//current:当前选中页面 0 ，1， 2
  
        var that = this;
        that.setData({
          titleIndex: e.detail.current
        });
  
        //计算指示器位移状态
        if (that.data.titleIndex == (that.data.titles.length-1)) {
          // console.log("move to the right")
          that.setData({ scrollStatus: 2 });
        } else if (that.data.titleIndex == 0) {
          // console.log("move to the left")
          that.setData({ scrollStatus: 1 });
        } else {
          that.setData({ scrollStatus: 0 });
        }
      }, 
  
      /**
       * current改变时，触发change事件
       */
      swiperChange: function(e){
        console.log("change " + e.detail.current)
      }
    },
  
    lifetimes: {
      attached() {
        // 初始化数据
        var that = this;
        that.setData({ 
          screenWidth: wx.getSystemInfoSync().screenWidth ,//px
         });
      }   
    },
  
  })