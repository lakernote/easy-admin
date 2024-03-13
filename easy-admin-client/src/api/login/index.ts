import request from '@/axios'
import type { UserType } from './types'

interface RoleParams {
  roleName: string
}
// 登录
export const loginApi = (data: UserType): Promise<IResponse<UserType>> => {
  return request.post({ url: '/sys/auth/v1/login', data })
}
// 登出
export const loginOutApi = (): Promise<IResponse> => {
  return request.delete({ url: '/sys/auth/v1/logout' })
}

export const getUserListApi = ({ params }: AxiosConfig) => {
  return request.get<{
    code: string
    data: {
      list: UserType[]
      total: number
    }
  }>({ url: '/mock/user/list', params })
}

export const getAdminRoleApi = (
  params: RoleParams
): Promise<IResponse<AppCustomRouteRecordRaw[]>> => {
  return request.get({ url: '/sys/menu/v1/asideMenus', params })
}

export const getTestRoleApi = (params: RoleParams): Promise<IResponse<string[]>> => {
  return request.get({ url: '/mock/role/list2', params })
}
