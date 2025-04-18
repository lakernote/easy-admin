<template>
  <div>
    <h1>用户管理</h1>
    <!-- 新增用户按钮，点击打开新增用户对话框 -->
    <el-button type="primary" @click="openCreateDialog">新增用户</el-button>
    <!-- 用户列表表格，展示用户数据 -->
    <el-table :data="users" stripe>
      <el-table-column prop="id" label="ID"/>
      <el-table-column prop="name" label="姓名"/>
      <el-table-column prop="email" label="邮箱"/>
      <el-table-column label="操作">
        <template #default="scope">
          <!-- 编辑按钮，点击打开编辑用户对话框 -->
          <el-button type="warning" size="small" @click="openEditDialog(scope.row)">编辑</el-button>
          <!-- 删除按钮，点击删除用户 -->
          <el-button type="danger" size="small" @click="deleteUser(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增用户对话框 -->
    <el-dialog :visible.sync="createDialogVisible" title="新增用户">
      <template #content>
        <!-- 新增用户表单 -->
        <el-form :model="newUser" ref="createFormRef" label-width="80px">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="newUser.name" placeholder="请输入姓名"/>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="newUser.email" placeholder="请输入邮箱"/>
          </el-form-item>
        </el-form>
      </template>
      <template #footer>
        <!-- 取消按钮，关闭对话框 -->
        <el-button @click="createDialogVisible = false">取消</el-button>
        <!-- 确定按钮，调用创建用户方法 -->
        <el-button type="primary" @click="createUser">确定</el-button>
      </template>
    </el-dialog>
    <!-- 编辑用户对话框 -->
    <el-dialog :visible.sync="editDialogVisible" title="编辑用户">
      <template #content>
        <!-- 编辑用户表单 -->
        <el-form :model="editedUser" ref="editFormRef" label-width="80px">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="editedUser.name" placeholder="请输入姓名"/>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="editedUser.email" placeholder="请输入邮箱"/>
          </el-form-item>
        </el-form>
      </template>
      <template #footer>
        <!-- 取消按钮，关闭对话框 -->
        <el-button @click="editDialogVisible = false">取消</el-button>
        <!-- 确定按钮，调用更新用户方法 -->
        <el-button type="primary" @click="updateUser">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue';

// 用户数据列表，初始包含两个示例用户
const users = ref([
  {id: 1, name: '张三', email: 'zhangsan@example.com'},
  {id: 2, name: '李四', email: 'lisi@example.com'}
]);

// 新增用户对话框显示状态
const createDialogVisible = ref(false);
// 编辑用户对话框显示状态
const editDialogVisible = ref(false);

// 新增用户表单数据
const newUser = ref({name: '', email: ''});
// 编辑用户表单数据
const editedUser = ref({id: 0, name: '', email: ''});

// 新增用户表单引用，用于表单验证
const createFormRef = ref < InstanceType < typeof import('element-plus')['ElForm'] >> ();
// 编辑用户表单引用，用于表单验证
const editFormRef = ref < InstanceType < typeof import('element-plus')['ElForm'] >> ();

// 打开新增用户对话框的方法
const openCreateDialog = () => {
  createDialogVisible.value = true;
};

// 打开编辑用户对话框的方法，传入要编辑的用户数据
const openEditDialog = (user: any) => {
  editedUser.value = {...user};
  editDialogVisible.value = true;
};

// 创建用户的方法
const createUser = () => {
  // 对新增用户表单进行验证
  createFormRef.value?.validate((valid) => {
    if (valid) {
      // 生成新用户的 ID
      const newId = Math.max(...users.value.map(u => u.id)) + 1;
      // 将新用户添加到用户列表中
      users.value.push({...newUser.value, id: newId});
      // 关闭新增用户对话框
      createDialogVisible.value = false;
      // 清空新增用户表单数据
      newUser.value = {name: '', email: ''};
    }
  });
};

// 更新用户的方法
const updateUser = () => {
  // 对编辑用户表单进行验证
  editFormRef.value?.validate((valid) => {
    if (valid) {
      // 查找要更新的用户在列表中的索引
      const index = users.value.findIndex(u => u.id === editedUser.value.id);
      if (index !== -1) {
        // 更新用户数据
        users.value[index] = {...editedUser.value};
      }
      // 关闭编辑用户对话框
      editDialogVisible.value = false;
    }
  });
};

// 删除用户的方法，传入要删除的用户 ID
const deleteUser = (id: number) => {
  // 查找要删除的用户在列表中的索引
  const index = users.value.findIndex(u => u.id === id);
  if (index !== -1) {
    // 从用户列表中删除该用户
    users.value.splice(index, 1);
  }
};
</script>