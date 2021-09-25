import { login, logout, getInfo } from '@/api/login'
import { getAccessToken, setAccessToken, removeAccessToken,getRefreshToken,setRefreshToken,removeRefreshToken } from '@/utils/auth'


const user = {
  state: {
    token: getAccessToken(),
    ref_token:getRefreshToken(),
    name: '',
    avatar: '',
    roles: [],
    permissions: []
  },

  mutations: {
    SET_ACCESS_TOKEN: (state, token) => {
      state.token = token
      setAccessToken(token)
    },
    SET_REFRESH_TOKEN: (state, ref_token) => {
      state.ref_token = ref_token
      setRefreshToken(ref_token)
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
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      console.log("login sss")
      const username = userInfo.username.trim()
      const password = userInfo.password
      const code = userInfo.code
      const uuid = userInfo.uuid
      return new Promise((resolve, reject) => {
        login({username, password, code, uuid}).then(res => {

          commit('SET_ACCESS_TOKEN', res.accessToken);
          commit('SET_REFRESH_TOKEN', res.refreshToken);
          commit('RE_SET_STATE');
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
          console.log("请求用户信息错误")
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
          removeAccessToken()
          removeRefreshToken()
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
        removeAccessToken()
        removeRefreshToken()
        resolve()
      })
    }
  }
}

export default user
