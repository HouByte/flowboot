<template>
  <el-row type="flex" class="row" justify="center">
    <el-col :xl="6" :lg="7">
      <div class="left-container">
          <H2>欢迎使用Vue Admin</H2>
          <el-image src="http://s.bugio.cn/img/logo.jpg" style="height: 180px;width: 180px;"/>
          <H4>By Bugio </H4>
      </div>
    </el-col>
    <el-col :span="1">
      <div class="container">
        <el-divider class="divider" direction="vertical"/>
      </div>
    </el-col>
    <el-col :xl="6" :lg="7">
      <div class="container">
        <el-form :model="loginForm" status-icon :rules="rules" ref="form" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="username" style="width: 380px">
            <el-input v-model="loginForm.username" autocomplete="off" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" style="width: 380px">
            <el-input type="password" v-model="loginForm.password" autocomplete="off" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code" style="width: 380px;">
            <el-input v-model.number="loginForm.code" placeholder="请输入验证码" maxlength="5" style="width: 172px;float: left"></el-input>
            <el-image :src="captchaImg" class="code-img"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 300px" @click="submitForm('form')" :loading="loading">登入</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import {login,getCaptchaImages} from "@/api/user";

export default {
  name: "Login",

  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    return {
      loading: false,
      loginForm: {
        username: '',
        password: '',
        code: '',
        redirect: undefined
      },
      captchaImg:"",
      rules: {
        password: [
          { validator: validatePass, trigger: 'blur' }
        ],
        username: [
          { required: true, message: '请输入用户名称', trigger: 'blur' },
          { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      }
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      }
    }
  },
  created() {
    this.getCaptcha();
    this.redirect = this.$route.query && this.$route.query.redirect;
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/home" });
          }).catch(() => {
            this.loading = false;
            // if (this.captchaOnOff) {
            //
            // }

            this.getCaptcha();
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getCaptcha(){
      getCaptchaImages().then(res=>{
        this.captchaImg = res.captchaImg;
      })
    }
  }
}
</script>

<style scoped>
  .row{
    background-color: #fafafa;
    height: 100%;
    display: flex;
    align-items: center;
    text-align: center;
  }
  .divider{
    height: 200px;
  }

  .code-img{
    width: 100px;
    float: left;
    margin-top: 5px;
    margin-left: 8px;
    border-radius: 4px;
  }

</style>
