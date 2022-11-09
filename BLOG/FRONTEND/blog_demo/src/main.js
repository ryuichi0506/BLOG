import Vue from 'vue'
import App from './App.vue'
import router from './router'

// 自定义头部底部组件
import Mheader from './components/Mheader.vue'
import Mfooter from './components/Mfooter.vue'
// 详情页评论组件
import MComment from './components/Comment.vue'
// 音乐播放器组件
import MPlayer from './components/Aplayer.vue'

import './plugins/element.js'
import './plugins/fontawesome'
// 导入全局样式表
import './assets/css/global.css'

// 动态添加title
import VueWechatTitle from 'vue-wechat-title'

// 导入axios
import axios from 'axios'
// 配置请求的根路径
Vue.prototype.$http = axios
axios.defaults.baseURL = '/api'
Vue.config.productionTip = false

axios.interceptors.request.use(config => {
  console.log(config)
  return config
})
Vue.use(VueWechatTitle)

// 公用时间过滤器
Vue.filter('formatDate', (value, pattern) => {
  let date = new Date(value)
  let y = date.getFullYear()
  let MM = date.getMonth() + 1
  MM = MM < 10 ? ('0' + MM) : MM
  let d = date.getDate()
  d = d < 10 ? ('0' + d) : d
  let h = date.getHours()
  h = h < 10 ? ('0' + h) : h
  let m = date.getMinutes()
  m = m < 10 ? ('0' + m) : m
  let s = date.getSeconds()
  s = s < 10 ? ('0' + s) : s
  if (pattern === 'yyyy-MM-dd') {
    return y + '-' + MM + '-' + d
  } else if (pattern === 'yyyy-MM-dd hh:mm:ss') {
    return y + '-' + MM + '-' + d + ' ' + h + ':' + m + ':' + s
  }
})

// 注册自定义头部底部模板
Vue.component('my-header', Mheader)
Vue.component('my-footer', Mfooter)
Vue.component('my-comment', MComment)
Vue.component('my-aplayer', MPlayer)

router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
