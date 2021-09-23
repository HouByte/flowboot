import {ajax} from "../ajax";
export function login(data) {
    return ajax.postJson("/login",data);
}

export function logout() {
    return ajax.postJson("/logout");
}

export function getInfo() {
    return ajax.get("/sys/userInfo");
}