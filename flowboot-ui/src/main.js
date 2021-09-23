import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
import Ajax from "./ajax"
import iconPicker from 'vue-fontawesome-elementui-icon-picker';
import directive from './directive' //directive


Vue.config.productionTip = false

require("../mock/mock.js")

Vue.use(Element);
Vue.use(iconPicker);
Vue.use(Ajax)
Vue.use(directive)
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
