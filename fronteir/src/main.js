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


router.beforeEach(async (to, from, next) => {
    if (to.path) {
      if (window._hmt) {
    // eslint-disable-next-line
    window._hmt.push(['_trackPageview', to.fullPath]);
  }
}
next();
  });
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')