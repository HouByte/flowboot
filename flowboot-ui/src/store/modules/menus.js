import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default {
    state: {
        menuList: [],
        permList: [],

        hasRoutes: false,

        editableTabsValue: 'Index',
        editableTabs: [{
            title: '首页',
            name: 'Index',
        }]
    },
    mutations: {
        SET_MENU_LIST(state, menus) {
            state.menuList = menus
        },
        changeRouteStatus(state, hasRoutes) {
            state.hasRoutes = hasRoutes
        },

        addTab(state, tab) {
            if (tab.name.indexOf("/fly/my/") !== -1){
                tab.name="/fly/my/info";
                tab.title = "个人中心"
            }
            let index = state.editableTabs.findIndex(e => e.name === tab.name)
            console.log(index)
            if (index === -1) {
                state.editableTabs.push({
                    title: tab.title,
                    name: tab.name,
                });
            }

            state.editableTabsValue = tab.name;
        },

        RE_SET_STATE: (state) => {
            state.menuList = []
            state.hasRoutes = false
            state.editableTabsValue = 'Index'
            state.editableTabs = [{
                title: '首页',
                name: 'Index',
            }]
        }

    },
    actions: {

    },

}
