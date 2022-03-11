
module.exports = {

    configureWebpack: {

        // other webpack options to merge in ...

    },

    // devServer Options don't belong into `configureWebpack`

    devServer: {

        // public: '/',

        // hot: true,

        // disableHostCheck: true,
       
        // BASE_API: '"http://localhost:8080"'
        publicPath:"/",
        hot: true,//hot配置是否启用模块的热替换功能，devServer的默认行为是在发现源代码被变更后，通过自动刷新整个页面来做到事实预览，开启hot后，将在不刷新整个页面的情况下通过新模块替换老模块来做到实时预览
        open: false, // 使用浏览器自动打开
        https: false,
        port: 9405

    }



};


