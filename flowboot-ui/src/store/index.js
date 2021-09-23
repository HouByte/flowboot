import Vue from 'vue'
import Vuex from 'vuex'
import menus from "@/store/modules/menus";
import user from "@/store/modules/user";
import getters from "@/store/getters";
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    menus,
    user
  },
  getters
})
