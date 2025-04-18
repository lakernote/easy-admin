<template>
  <div class="dashboard">
    <!-- 数据统计卡片 -->
    <div class="statistic-cards">
      <el-card class="statistic-card">
        <template #header>
          <div class="card-header">用户总数</div>
        </template>
        <div class="card-content">1000</div>
      </el-card>
      <el-card class="statistic-card">
        <template #header>
          <div class="card-header">今日新增用户</div>
        </template>
        <div class="card-content">10</div>
      </el-card>
      <el-card class="statistic-card">
        <template #header>
          <div class="card-header">订单总数</div>
        </template>
        <div class="card-content">500</div>
      </el-card>
      <el-card class="statistic-card">
        <template #header>
          <div class="card-header">今日新增订单</div>
        </template>
        <div class="card-content">5</div>
      </el-card>
    </div>

    <!-- 通知区域 -->
    <el-card class="notice-card">
      <template #header>
        <div class="card-header">系统通知</div>
      </template>
      <el-list :data="notices" :size="8">
        <template #item="{ item }">
          <el-list-item>
            {{ item }}
          </el-list-item>
        </template>
      </el-list>
    </el-card>
  </div>
</template>

<script setup>
import {ref} from 'vue';

// 模拟通知数据
const notices = ref([
  '系统将于今晚 12 点进行维护，请提前做好准备。',
  '新的功能模块已上线，欢迎体验。',
  '请及时更新您的个人信息。'
]);
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.statistic-cards {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin-bottom: 20px;
}

.statistic-card {
  flex: 1 1 calc(25% - 20px);
  min-width: 200px;
}

.card-header {
  font-size: 16px;
  font-weight: bold;
}

.card-content {
  font-size: 24px;
  text-align: center;
  margin-top: 10px;
}

.notice-card {
  height: 200px;
}
</style>