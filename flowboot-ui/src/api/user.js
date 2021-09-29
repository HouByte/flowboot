import {ajax} from "../ajax";




export function updatePass(data){
    return ajax.postJson('/sys/user/updatePass',data);
}
export function updateUserStatus(id,data){
    return ajax.postForm('/sys/user/update/status/'+id, {status:data});
}


export function getUserById(id) {

    return ajax.get("/sys/user/info/"+id);
}


export function deleteUserById(data) {

    return ajax.postJson("/sys/user/delete",data);
}

export function updateUserRole(id,roleId) {

    return ajax.postForm("/sys/user/role/"+id,roleId);
}

export function getUserList(data) {
    return ajax.get("/sys/user/list",data);
}

export function saveUser(action,data){
    return ajax.postJson("/sys/user/"+action,data);
}
