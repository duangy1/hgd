import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/en'
import router from './router'
import * as echarts from 'echarts';
import axios from 'axios'
import FileSaver from 'file-saver'
import XLSX from 'xlsx'
// import VueAxios from 'vue-axios'
// var axios=require("axios")

axios.defaults.withCredentials = true

Vue.config.productionTip = false
Vue.use(ElementUI, { locale },FileSaver,XLSX);

Vue.prototype.$axios = axios;
Vue.prototype.$echarts = echarts;
// Vue.prototype.$fileSaver = FileSaver;
// Vue.prototype.$xlsx = XLSX;

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
router.afterEach( ( ) => {
  setTimeout(()=>{
          //百度统计
          var _hmt = _hmt || [];
          (function() {
              //每次执行前，先移除上次插入的代码
              document.getElementById('baidu_tj') && document.getElementById('baidu_tj').remove();
              var hm = document.createElement("script");
              hm.src = "https://hm.baidu.com/hm.js?xxxx";
              hm.id = "baidu_tj"
              var s = document.getElementsByTagName("script")[0];
              s.parentNode.insertBefore(hm, s);
          })();
  },0);
} );
