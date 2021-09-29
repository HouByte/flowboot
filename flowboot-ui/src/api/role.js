import {ajax} from "../ajax";


export function getRoleById(id) {

    return ajax.get("/sys/role/info/"+id);
}




export function deleteRoleById(ids) {

    return ajax.postJson("/sys/role/delete",ids);
}

export function updatePerm(id,menuIds) {

    return ajax.post("/sys/role/perm/"+id,menuIds);
}

export function updateRoleStatus(id,data){
    return ajax.postForm('/sys/role/update/status/'+id, {status:data});
}

export function getRoleList(data) {
    return ajax.get("/sys/role/list",data);
}
export function roleSelectOptions() {
    return ajax.get("/sys/role/roleSelectOptions");
}

export function saveRole(action,data){
    return ajax.postJson("/sys/role/"+action,data);
}
