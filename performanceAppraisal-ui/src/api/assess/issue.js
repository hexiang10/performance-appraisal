import request from '@/utils/request'

// 查询考核发布列表
export function listIssue(query) {
  return request({
    url: '/assess/issue/list',
    method: 'get',
    params: query
  })
}

// 查询考核发布详细
export function getIssue(id) {
  return request({
    url: '/assess/issue/' + id,
    method: 'get'
  })
}

// 新增考核发布
export function addIssue(data) {
  return request({
    url: '/assess/issue',
    method: 'post',
    data: data
  })
}

// 修改考核发布
export function updateIssue(data) {
  return request({
    url: '/assess/issue',
    method: 'put',
    data: data
  })
}

// 删除考核发布
export function delIssue(id) {
  return request({
    url: '/assess/issue/' + id,
    method: 'delete'
  })
}

// 考核发布
export function changeStatus(data) {
  return request({
    url: '/assess/issue/changeStatus',
    method: 'post',
    params: data
  })
}
