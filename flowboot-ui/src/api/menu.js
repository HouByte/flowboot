import {ajax} from "../ajax";
export function getMenuNav() {
    return ajax.get("/sys/menu/nav");
}

export function getMenuById(id) {

    return ajax.get("/sys/menu/info/"+id);
}

export function getMenuTreeselect(id) {

    return ajax.get("/sys/menu/roleMenuTreeselect/"+id);
}

export function deleteById(id) {

    return ajax.post("/sys/menu/delete/"+id);
}

export function getMenuTrees() {
    return ajax.get("/sys/menu/tree");
}

export function getMenuTreeList() {
    return ajax.get("/sys/menu/list");
}

export function saveMenu(action,data){
    return ajax.postJson("/sys/menu/"+action,data);
}
