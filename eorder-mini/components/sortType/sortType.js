// components/sortType/sortType.js
Component({
    /**
     * 组件的属性列表
     */
    properties: {
        selectedState:{
            type:Boolean,
            value:false
        }
    },

    /**
     * 组件的初始数据
     */
    data: {

    },

    /**
     * 组件的方法列表
     */
    methods: {
        handleTap(e){
            var state=this.data.selectedState;
            this.setData({
                selectedState:!state
            })
        }
    }
})
