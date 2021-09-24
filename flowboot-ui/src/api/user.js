import {ajax} from "../ajax";



export function getUserInfo() {
    return ajax.get("/sys/userInfo");
}

export function updatePass(data){
    return ajax.postJson('/sys/user/updatePass',data);
}


export function getUserById(id) {

    return ajax.get("/sys/user/info/"+id);
}


export function deleteUserById(ids) {

    return ajax.post("/sys/user/delete",ids);
}

export function updateUserRole(id,roleId) {

    return ajax.post("/sys/user/role/"+id,roleId);
}

export function getUserList(data) {
    return ajax.get("/sys/user/list",data);
}

export function saveUser(action,data){
    return ajax.postJson("/sys/user/"+action,data);
}
