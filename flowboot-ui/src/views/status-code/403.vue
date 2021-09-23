<template>
  <div class="err403">
    <el-result icon="error" title="403" subTitle="您没有权限访问此页面 !!">
      <template slot="extra">
        <el-button type="primary" size="medium" @click="$router.push('/')">去首页</el-button>
      </template>
    </el-result>
  </div>
</template>

<script>
export default {
  name: "err403"
}
</script>

<style scoped>
.err403 {
  margin: 350px auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>