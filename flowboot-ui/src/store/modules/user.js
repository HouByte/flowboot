import { login, logout, getInfo } from '@/api/login'
import {getToken, setToken, removeToken, getPermissions, setPermissions, removePermissions} from '@/utils/auth'


const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    permissions: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
      setToken(token)
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions

      setPermissions(permissions);

    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      const password = userInfo.password
      const code = userInfo.code+''
      const uuid = userInfo.uuid
      return new Promise((resolve, reject) => {
        login({username, password, code, uuid}).then(res => {
          commit('RE_SET_STATE');
          commit('SET_TOKEN', res.token);
          commit('SET_PERMISSIONS', res.permissions)
          commit('SET_AVATAR',res.user.avatar)
          commit('SET_NAME', res.user.username)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
          console.log("getInfo --- ",res);
          // const user = res.user
          //
          // //process.env.VUE_APP_BASE_API
          // const avatar = user.avatar == "" ? require("@/assets/images/profile.jpg") :  user.avatar;
          // if (res.roles && res.roles.length > 0) { // 验证返回的roles是否是一个非空数组
          //   commit('SET_ROLES', res.roles)
          //   commit('SET_PERMISSIONS', res.permissions)
          // } else {
          //   commit('SET_ROLES', ['ROLE_DEFAULT'])
          // }
          // commit('SET_NAME', user.username)
          // commit('SET_AVATAR', avatar)
          resolve(res)
        }).catch(error => {
          console.log("请求用户信息错误",error)
          reject(error)
        })
      })
    },

    // 退出系统
    Logout({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_PERMISSIONS', [])
          removeToken();
          removePermissions();
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken();
        removePermissions();
        resolve()
      })
    }
  }
}

export default user
