import errPage403 from "../../views/status-code/403"
import errPage404 from "../../views/status-code/404"
import errPage500 from "../../views/status-code/500"


const err403 = {
    path: '/403',
    name: 'err403',
    component: errPage403
}


const err404 = {
    path: '/404',
    name: 'err404',
    component: errPage404
}

const err500 = {
    path: '/500',
    name: 'err500',
    component: errPage500
}

export {
    err403,err404,err500
}