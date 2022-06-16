import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/en'
import router from './router'
import * as echarts from 'echarts';
import axios from 'axios'
// import VueAxios from 'vue-axios'
// var axios=require("axios")

axios.defaults.withCredentials = true

Vue.config.productionTip = false
Vue.use(ElementUI, { locale });

Vue.prototype.$axios = axios;
Vue.prototype.$echarts = echarts;

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
