import request from '@/utils/request'

// 查询课表列表
export function listClassschedule(query) {
  return request({
    url: '/classschedule/classschedule/list',
    method: 'get',
    params: query
  })
}

// 查询课表详细
export function getClassschedule(id) {
  return request({
    url: '/classschedule/classschedule/' + id,
    method: 'get'
  })
}

// 新增课表
export function addClassschedule(data) {
  return request({
    url: '/classschedule/classschedule',
    method: 'post',
    data: data
  })
}

// 修改课表
export function updateClassschedule(data) {
  return request({
    url: '/classschedule/classschedule',
    method: 'put',
    data: data
  })
}

// 删除课表
export function delClassschedule(id) {
  return request({
    url: '/classschedule/classschedule/' + id,
    method: 'delete'
  })
}
