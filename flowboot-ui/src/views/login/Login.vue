<template>
  <div class="login-page" >
    <div class="box">
      <div class="container">
        <div class="left">
          <div class="left-container">
            <span style="font-size: 26px;font-weight: bold;margin-bottom: 30px">Flow Boot 快速开发流平台</span>
            <el-image src="http://s.bugio.cn/img/logo.jpg" style="height: 180px;width: 180px;border-radius: 80px;"/>
            <H4>By Bugio </H4>
          </div>
        </div>

        <div>
          <div class="login-container">
            <el-form :model="loginForm" status-icon :rules="rules" ref="form" class="login-form">
              <el-form-item prop="username" style="width: 100%">
                <el-input v-model="loginForm.username" autocomplete="off" placeholder="请输入用户名"></el-input>
              </el-form-item>
              <el-form-item prop="password" style="width: 100%">
                <el-input type="password" v-model="loginForm.password" autocomplete="off"
                          placeholder="请输入密码"></el-input>
              </el-form-item>
              <el-form-item prop="code" style="width: 100%">
                <el-input v-model.number="loginForm.code" placeholder="请输入验证码" maxlength="5"
                          style="width: 60%;float: left"></el-input>
                <el-image :src="'data:image/jpg;base64,'+captchaImg" @click="getCaptcha" class="code-img"/>
              </el-form-item>
              <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
              <el-form-item>
                <el-button type="primary" style="width:100%;" @click="submitForm('form')" :loading="loading">登入
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {getCaptchaImages} from "@/api/login";
import { encrypt, decrypt } from '@/utils/jsencrypt'
import Cookies from "js-cookie";
import {Message} from "element-ui";
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
        uuid: '',
        rememberMe: false
      },
      redirect: undefined,
      captchaImg: "",
      rules: {
        password: [
          {validator: validatePass, trigger: 'blur'}
        ],
        username: [
          {required: true, message: '请输入用户名称', trigger: 'blur'},
          {min: 4, max: 18, message: '长度在 4 到 18 个字符', trigger: 'blur'}
        ],
        code: [
          {required: true, message: '请输入验证码', trigger: 'blur'}
        ]
      }
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      }
    }
  },
  created() {
    this.getCaptcha();
    this.redirect = this.$route.query && this.$route.query.redirect;
    this.getCookie();
  },
  methods: {
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({path: this.redirect || "/home"});
          }).catch((res) => {
            this.loading = false;
            // if (this.captchaOnOff) {
            //
            // }
            console.log(res)
            Message.error(res.data.data)
            if (res.data.data !=="验证码错误"){
              Cookies.remove("username");
              Cookies.remove("password");
              this.loginForm.username=''
              this.loginForm.password=''
            }

            this.loginForm.code=''
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
    getCaptcha() {
      getCaptchaImages().then(res => {
        console.log(res);
        this.captchaImg = res.img;
        this.loginForm.uuid = res.uuid;
      })
    }
  }
}
</script>

<style scoped>
.login-page{
  width: 100%;
  height: 100%;
  background:url("../../assets/images/login-background.jpg");
}
.login-form {
  width: 300px;
  margin-left: 50px;
}

.box {
  background-color: #ffffff;
  height: 100%;
  display: flex;
  align-items: center;
  text-align: center;
}

.left {
  height: 100%;
  width: 50%;
  background-color: rgba(50, 177, 177, 0.94);
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  border-top-left-radius:9px;
  border-bottom-left-radius: 9px;
}

.left-container {
  padding: 30px;
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  color: white;
}

.login-container {
  text-align: left;

}

.divider {
  height: 200px;
}

.code-img {
  width: 35%;
  height: 35px;
  float: left;
  margin-top: 5px;
  margin-left: 8px;
  border-radius: 4px;
}

.box {
  background-color: rgba(255, 255, 255, 0.55);
  width: 800px;
  height: 400px;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
  border-radius: 9px;
}

.container{
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  text-align: center;
}

</style>
