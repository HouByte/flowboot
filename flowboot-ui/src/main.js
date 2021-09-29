import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
import Ajax from "./ajax"
import Pagination from "@/components/Pagination";
import iconPicker from 'vue-fontawesome-elementui-icon-picker';
import directive from './directive' //directive
import {getConfigValue} from "@/api/config";
import {
  addDateRange,
  download,
  handleTree,
  parseTime,
  resetForm,
  selectDictLabel,
  selectDictLabels
} from "@/utils/tools";

Vue.config.productionTip = false

require("../mock/mock.js")

Vue.use(Element);
Vue.use(iconPicker);
Vue.use(Ajax)
Vue.use(directive)
Vue.component('Pagination', Pagination)

// 全局方法挂载
Vue.prototype.getConfigValue = getConfigValue
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
