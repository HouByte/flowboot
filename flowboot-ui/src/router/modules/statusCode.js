import errPage403 from "../../views/status-code/403"
import errPage404 from "../../views/status-code/404"
import errPage500 from "../../views/status-code/500"


const err403 = {
    path: '/403',
    title:'没有权限',
    name: 'err403',
    component: errPage403
}


const err404 = {
    path: '/404',
    title:'找不到页面',
    name: 'err404',
    component: errPage404
}

const err500 = {
    path: '/500',
    title:'服务错误',
    name: 'err500',
    component: errPage500
}

export {
    err403,err404,err500
}
