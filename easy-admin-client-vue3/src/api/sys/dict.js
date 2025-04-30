import request from '@/utils/request'

export default {
    // 获取字典列表分页
    getDicts(page, limit, dictCode, dictName) {
        return request.get('/sys/dict', {
            params: {page, limit, dictCode, dictName}
        })
    },

    // 新增字典
    addDict(data) {
        return request.post('/sys/dict', data)
    },

    // 更新字典
    updateDict(data) {
        return request.post('/sys/dict', data)
    },

    // 删除字典
    deleteDict(id) {
        return request.delete(`/sys/dict/${id}`)
    },

    // 批量删除字典
    batchDelete(ids) {
        return request.delete(`/sys/dict/batch/${ids}`)
    }
}