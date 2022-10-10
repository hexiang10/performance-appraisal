import request from '@/utils/request'

// 查询考核项列表
export function listItem(query) {
  return request({
    url: '/assess/item/list',
    method: 'get',
    params: query
  })
}

// 查询考核项详细
export function getItem(id) {
  return request({
    url: '/assess/item/' + id,
    method: 'get'
  })
}

// 新增考核项
export function addItem(data) {
  return request({
    url: '/assess/item',
    method: 'post',
    data: data
  })
}

// 修改考核项
export function updateItem(data) {
  return request({
    url: '/assess/item',
    method: 'put',
    data: data
  })
}

// 删除考核项
export function delItem(id) {
  return request({
    url: '/assess/item/' + id,
    method: 'delete'
  })
}
