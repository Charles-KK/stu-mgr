import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import VCharts from 'v-charts'
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/reset.css'
import './assets/css/index.css'
import global from './global/global'

Vue.config.productionTip = false
Vue.use(ElementUI,{size: 'small'})
Vue.use(global)
// Vue.use(VCharts)

import {request} from "./network/request";

Vue.prototype.$request = request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
