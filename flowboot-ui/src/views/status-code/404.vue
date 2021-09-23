<template>
  <div class="err404">
    <el-result icon="error" title="404" subTitle="您访问的页面不见了 !!">
      <template slot="extra">
        <el-button type="primary" size="medium" @click="$router.push('/')">去首页</el-button>
      </template>
    </el-result>
  </div>
</template>

<script>
export default {
  name: "err404"
}
</script>

<style scoped>
  .err404 {
    margin: 350px auto;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
</style>