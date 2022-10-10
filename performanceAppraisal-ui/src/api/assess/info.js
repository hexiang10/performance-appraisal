import request from '@/utils/request'

// 查询考核主列表
export function listInfo(query) {
  return request({
    url: '/assess/info/list',
    method: 'get',
    params: query
  })
}

// 查询考核主详细
export function getInfo(id) {
  return request({
    url: '/assess/info/' + id,
    method: 'get'
  })
}

// 新增考核主
export function addInfo(data) {
  return request({
    url: '/assess/info',
    method: 'post',
    data: data
  })
}

// 修改考核主
export function updateInfo(data) {
  return request({
    url: '/assess/info',
    method: 'put',
    data: data
  })
}

// 删除考核主
export function delInfo(id) {
  return request({
    url: '/assess/info/' + id,
    method: 'delete'
  })
}


export function getInfoAndItem(id) {
  return request({
    url: '/assess/info/getInfoAndItem/' + id,
    method: 'get'
  })
}
