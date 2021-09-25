import Cookies from 'js-cookie'

const TokenKey = 'Authority-token'
const PermissionsKey = 'Authority-Permissions'
const UserKey = 'User'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getUser() {
  let user = Cookies.get(UserKey);
  console.log(user)
  return user === undefined ?null:JSON.parse(user)
}

export function setUser(user) {
  return Cookies.set(UserKey,JSON.stringify(user))
}

export function removeUser() {
  return Cookies.remove(UserKey)
}

export function getPermissions() {
  let permi = Cookies.get(PermissionsKey);
  return permi === undefined ? null: JSON.parse()
}

export function setPermissions(permission) {
  return Cookies.set(PermissionsKey, JSON.stringify(permission))
}

export function removePermissions() {
  return Cookies.remove(PermissionsKey)
}
