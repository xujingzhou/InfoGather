// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
// 引入elementui
import 'element-ui/lib/theme-chalk/index.css'
import formatDate from './utils/formatDate.js'

import axios from 'axios'
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios)

// 环境设置
var ishttps = 'https:' == document.location.protocol ? true: false;
if(process.env.NODE_ENV === 'production') {
	if(ishttps) {
		axios.defaults.baseURL = "https://education.dten.dev:8081/v1"; // "https://18.162.113.79:8081/v1";
	} 
	else {
		axios.defaults.baseURL = "http://18.162.113.79:8089/v1";
	}
}
else {
	if(ishttps) {
		axios.defaults.baseURL = "https://127.0.0.1:8081/v1";
	} 
	else {
		axios.defaults.baseURL = "http://127.0.0.1:8089/v1";
	}
}
axios.defaults.timeout = 10000;

Vue.config.productionTip = false

// 在vue中使用elementui
Vue.use(ElementUI);

/* eslint-disable no-new */
var vm = new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
