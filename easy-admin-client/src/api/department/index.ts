import request from '@/axios'
import { DepartmentListResponse, DepartmentUserParams, DepartmentUserResponse } from './types'

// 部门表格
export const getDepartmentTableApi = (params: any) => {
  return request.get({ url: '/sys/dept/v1/list', params })
}
// 部门列表
export const getDepartmentApi = () => {
  return request.get<DepartmentListResponse>({ url: '/sys/dept/v1/list' })
}

export const getUserByIdApi = (params: DepartmentUserParams) => {
  return request.get<DepartmentUserResponse>({ url: '/mock/department/users', params })
}

export const deleteUserByIdApi = (ids: string[] | number[]) => {
  return request.post({ url: '/mock/department/user/delete', data: { ids } })
}

export const saveUserApi = (data: any) => {
  return request.post({ url: '/mock/department/user/save', data })
}

export const saveDepartmentApi = (data: any) => {
  return request.post({ url: '/sys/dept/v1', data })
}

export const deleteDepartmentApi = (ids: string[] | number[]) => {
  return request.post({ url: '/mock/department/delete', data: { ids } })
}
