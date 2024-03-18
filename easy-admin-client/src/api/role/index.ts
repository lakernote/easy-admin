import request from '@/axios'

export const getRoleListApi = () => {
  return request.get({ url: '/v1/sys/role' })
}
