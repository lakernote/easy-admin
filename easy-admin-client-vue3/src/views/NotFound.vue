<template>
  <div class="not-found">
    <h1>404</h1>
    <h2>页面不存在</h2>
    <p>您访问的页面不存在或已被删除</p>
    <router-link to="/">返回首页</router-link>
  </div>
</template>

<script>
</script>

<style scoped>
.not-found {
  text-align: center;
  padding: 50px 0;
}

h1 {
  font-size: 80px;
  color: #f56c6c;
}
</style>