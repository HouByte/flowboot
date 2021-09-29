import {ajax} from "../ajax";


export function getConfigById(id) {

    return ajax.get("/sys/config/info/"+id);
}

export function getConfigValue(key) {


    return ajax.get("/sys/config/get/"+key)
}


export function deleteConfigById(ids) {

    return ajax.postJson("/sys/config/delete",ids);
}

export function updatePerm(id,menuIds) {

    return ajax.post("/sys/config/perm/"+id,menuIds);
}

export function getConfigList(data) {
    return ajax.get("/sys/config/list",data);
}


export function saveConfig(action,data){
    return ajax.postJson("/sys/config/"+action,data);
}
