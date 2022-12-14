module.exports = {
    lintOnSave: false,
    devServer: {
        /* 自动打开浏览器 */
        open: false,
        /* 设置为0.0.0.0则所有的地址均能访问 */
        host: '0.0.0.0',
        // host: "localhost",
        port: 8080,
        https: false,
        hotOnly: false,
        /* 使用代理 */
        proxy: {
            '/api': {
                /* 目标代理服务器地址 */
                target: 'http://localhost:8888/',
                /* 允许跨域 */
                changeOrigin: true,
                pathRewrite:{
                    '^/api':''
                }
            },
        },
    },
}