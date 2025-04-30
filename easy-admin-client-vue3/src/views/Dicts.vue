<template>
  <div class="p-4">
    <!-- 查询表单 -->
    <el-form inline @submit.prevent="getDicts" class="mb-4">
      <el-form-item label="字典代码">
        <el-input v-model="searchForm.dictCode" placeholder="请输入代码"/>
      </el-form-item>
      <el-form-item label="字典名称">
        <el-input v-model="searchForm.dictName" placeholder="请输入名称"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getDicts">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>

    <el-button type="primary" @click="openDialog()">新增字典</el-button>
    <el-button type="danger" @click="batchDelete">批量删除</el-button>

    <!-- 表格 -->
    <el-table :data="dictList" class="mt-4" border @selection-change="handleSelectionChange">
      <!-- 多选列 -->
      <el-table-column type="selection" width="50"/>
      <el-table-column prop="dictId" label="ID" width="80"/>
      <el-table-column prop="dictCode" label="代码"/>
      <el-table-column prop="dictName" label="名称"/>
      <el-table-column prop="description" label="描述"/>
      <el-table-column prop="enable" label="启用" width="100">
        <template #default="scope">
          <el-switch v-model="scope.row.enable" disabled/>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间"/>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="openDialog(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteDict(scope.row.dictId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        :current-page="page"
        :page-size="limit"
        :total="total"
        @current-change="handlePageChange"
        layout="total, prev, pager, next, jumper"
    />

    <!-- 弹窗表单 -->
    <el-dialog :title="form.dictId ? '编辑字典' : '新增字典'" v-model="dialogVisible" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="字典代码" prop="dictCode">
          <el-input v-model="form.dictCode"/>
        </el-form-item>
        <el-form-item label="字典名称" prop="dictName">
          <el-input v-model="form.dictName"/>
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="form.description"/>
        </el-form-item>
        <el-form-item label="启用">
          <el-switch v-model="form.enable"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveDict">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {reactive, ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import dictApi from '@/api/sys/dict'

const dictList = ref([])
const selectedIds = ref([]) // 存储选中的 dictId 列表
const page = ref(1)
const limit = ref(5)
const total = ref(0)
const dialogVisible = ref(false)

const searchForm = ref({
  dictCode: '',
  dictName: ''
})

const form = reactive({
  dictId: null,
  dictCode: '',
  dictName: '',
  description: '',
  enable: true
})

const formRef = ref()
const rules = {
  dictCode: [{required: true, message: '请输入字典代码', trigger: 'blur'}],
  dictName: [{required: true, message: '请输入字典名称', trigger: 'blur'}]
}

// 查询分页
const getDicts = async () => {
  try {
    const res = await dictApi.getDicts(
        page.value,
        limit.value,
        searchForm.value.dictCode,
        searchForm.value.dictName
    )
    dictList.value = res.data.data
    total.value = Number(res.data.count || 0) // ← 确保是数字
  } catch (err) {
    console.error('查询失败', err)
  }
}

// 新增或编辑
const openDialog = (row = null) => {
  if (row) {
    Object.assign(form, row)
  } else {
    Object.assign(form, {
      dictId: null,
      dictCode: '',
      dictName: '',
      description: '',
      enable: true
    })
  }
  dialogVisible.value = true
}

// 保存
const saveDict = () => {
  formRef.value.validate(async (valid) => {
    if (!valid) return
    try {
      if (form.dictId) {
        await dictApi.updateDict({...form}) // 编辑
      } else {
        await dictApi.addDict({...form}) // 新增
      }
      ElMessage.success(form.dictId ? '更新成功' : '新增成功')
      dialogVisible.value = false
      await getDicts()
    } catch (err) {
      ElMessage.error('保存失败')
    }
  })
}

// 删除
const deleteDict = (id) => {
  ElMessageBox.confirm('确定删除该字典？', '提示', {type: 'warning'})
      .then(async () => {
        await dictApi.deleteDict(id)
        ElMessage.success('删除成功')
        getDicts()
      })
      .catch(() => ElMessage.info('取消删除'))
}

// 重置
const resetSearch = () => {
  searchForm.value.dictCode = ''
  searchForm.value.dictName = ''
  page.value = 1
  getDicts()
}

// 分页
const handlePageChange = (val) => {
  page.value = val
  getDicts()
}

// 处理多选
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.dictId)
}
const batchDelete = () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请至少选择一条记录')
    return
  }

  ElMessageBox.confirm('确定删除选中的字典？', '提示', {type: 'warning'})
      .then(async () => {
        try {
          await dictApi.batchDelete(selectedIds.value)
          ElMessage.success('批量删除成功')
          getDicts() // 刷新列表
          selectedIds.value = [] // 清空选中
        } catch (err) {
          ElMessage.error('删除失败')
        }
      })
      .catch(() => ElMessage.info('取消删除'))
}
// 页面初始化
getDicts()
</script>

<style scoped>
.mb-4 {
  margin-bottom: 16px;
}

.mt-4 {
  margin-top: 16px;
}
</style>
