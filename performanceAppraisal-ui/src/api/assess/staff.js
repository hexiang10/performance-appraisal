import request from '@/utils/request'

// 查询绩效自评列表
export function listStaff(query) {
  return request({
    url: '/assess/staff/list',
    method: 'get',
    params: query
  })
}

export function adminList(query) {
  return request({
    url: '/assess/staff/adminList',
    method: 'get',
    params: query
  })
}


// 查询绩效自评详细
export function getStaff(id) {
  return request({
    url: '/assess/staff/' + id,
    method: 'get'
  })
}

// 新增绩效自评
export function addStaff(data) {
  return request({
    url: '/assess/staff',
    method: 'post',
    data: data
  })
}

// 修改绩效自评
export function updateStaff(data) {
  return request({
    url: '/assess/staff',
    method: 'put',
    data: data
  })
}

export function appeal(data) {
  return request({
    url: '/assess/staff/appeal',
    method: 'post',
    data: data
  })
}


// 删除绩效自评
export function delStaff(id) {
  return request({
    url: '/assess/staff/' + id,
    method: 'delete'
  })
}
