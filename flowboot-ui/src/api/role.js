import {ajax} from "../ajax";


export function getRoleById(id) {

    return ajax.get("/sys/role/info/"+id);
}


export function deleteRoleById(ids) {

    return ajax.post("/sys/role/delete",ids);
}

export function updatePerm(id,menuIds) {

    return ajax.post("/sys/role/perm/"+id,menuIds);
}

export function getRoleList(data) {
    return ajax.get("/sys/role/list",data);
}

export function saveRole(action,data){
    return ajax.postJson("/sys/role/"+action,data);
}
