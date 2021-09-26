import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '../views/index/Index'
import Home from '../views/home/Home'
import Login from '../views/login/Login.vue'
import {
  err403,
  err404,
  err500
} from "./modules/statusCode";
import {
  UserRePassword
} from "./modules/user"
import {
  getMenuNav
} from "@/api/menu";
import store from '@/store'
import {
  getToken
} from "@/utils/auth";
import {
  Message
} from "element-ui";


Vue.use(VueRouter)
// 不需要用户权限的路由表
const constantRoutes = [

]

const routes = [{
  path: '/',
  name: 'Index',
  redirect: '/home',
  component: Index,
  meta: {
    icon: 'el-icon-s-home',
    title: '首页'
  },
  children: [{
    path: '/home',
    name: 'Index',
    component: Home,
    meta: {
      icon: 'el-icon-s-home',
      title: '首页'
    },
  },
    UserRePassword
  ]
},
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  err403, err404, err500

]

//routes.push(status-code);
const router = new VueRouter({
  mode: 'history',
  routes
})

function generateRoutes() {
  getMenuNav().then(res=>{
    //获取路由
    store.commit('SET_MENU_LIST',res);
    //动态绑定路由
    let newRoutes = router.options.routes;

    res.forEach(menu => {
      if (menu.children){
        menu.children.forEach(e => {
          let router = menuToRoute(e);
          if (router){
            newRoutes[0].children.push(router);
          }
        })
      } else if (menu.children <= 0) {
        let router = menuToRoute(e);
        if (router){
          newRoutes[0].children.push(router);
        }
      }
    })
    //router.matcher = new VueRouter({ mode: 'history' }).matcher
    router.addRoutes(newRoutes);
    store.commit("changeRouteStatus", true)
  })
}
const whiteList = ['/login', '/auth-redirect', '/bind', '/register']

router.beforeEach((to, from, next) => {
  let hasRoute = store.state.menus.hasRoutes

  if (getToken()) {
    if (to.path == '/login') {
      next({
        path: '/'
      })

    } else {
      if (store.getters.permissions.length === 0) {
        // 判断当前用户是否已拉取完user_info信息
        //generateRoutes();

        store.dispatch('GetInfo').then(() => {
          //generateRoutes();
        }).catch(err => {
          console.log(err)
          // store.dispatch('Logout').then(() => {
          //   Message.error(err)
          //   next({
          //     path: '/login'
          //   })
          // })
        })
      } else {
        next()
      }
    }

  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      store.commit('RE_SET_STATE');
      next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页
    }
  }
  next()
})

const menuToRoute = (menu) => {
  if (!menu.component) {
    return null;
  }
  let route = {
    name: menu.name,
    path: menu.path,
    meta: {
      icon: menu.icon,
      title: menu.title
    }
  }

  route.component = () => import('@/views/' + menu.component + '.vue')
  return route;
}
export default router
