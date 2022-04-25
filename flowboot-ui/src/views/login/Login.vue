<template>
  <div :class="'container ' + (isRegister?'sign-up-mode':'') ">
    <div class="form-warp">
      <form class="sign-in-form">
        <h1 class="form-title">飞行大数据</h1>
        <h2 class="form-title">登录</h2>
        <el-form :model="loginForm" status-icon :rules="rules" ref="form" class="login-form">
          <el-form-item prop="username" style="width: 100%">
            <el-input v-model="loginForm.username" style="border-radius: 20px" autocomplete="off" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password" style="width: 100%">
            <el-input type="password" v-model="loginForm.password" autocomplete="off"
                      placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="code" style="width: 100%">
            <el-input v-model.number="loginForm.code" placeholder="请输入验证码" maxlength="5"
                      style="width: 60%;float: left;"></el-input>
            <el-image :src="'data:image/jpg;base64,'+captchaImg" @click="getCaptcha" class="code-img"/>
          </el-form-item>
          <el-form-item prop="code" style="width: 100%">
            <el-checkbox v-model="loginForm.rememberMe" style="margin-left: 10px">记住密码</el-checkbox>
          </el-form-item>

        </el-form>
        <div class="submit-btn" @click="submitForm('form')">立即登录</div>
      </form>
      <form class="sign-up-form">
        <h1 class="form-title">飞行大数据</h1>
        <h2 class="form-title">注册</h2>
        <el-form :model="registerForm" status-icon :rules="rules" ref="registerForm" class="login-form">
          <el-form-item prop="username" style="width: 100%">
            <el-input v-model="registerForm.username" style="border-radius: 20px" autocomplete="off" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password" style="width: 100%">
            <el-input type="password" v-model="registerForm.password" autocomplete="off"
                      placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="code" style="width: 100%">
            <el-input v-model.number="registerForm.code" placeholder="请输入验证码" maxlength="5"
                      style="width: 60%;float: left;"></el-input>
            <el-image :src="'data:image/jpg;base64,'+captchaImg" @click="getCaptcha" class="code-img"/>
          </el-form-item>


        </el-form>
        <div class="submit-btn" @click="submitRegister('registerForm')">立即注册</div>
      </form>
    </div>
    <div class="desc-warp">
      <div class="desc-warp-item sign-up-desc">
        <div class="content">
          <button id="sign-up-btn" @click="switchRegister">注册</button>
        </div>
<!--        <img src="@/assets/images/log.svg" alt="">-->
      </div>
      <div class="desc-warp-item sign-in-desc">
        <div class="content">
          <button id="sign-in-btn" @click="switchLogin">登录</button>
        </div>
<!--        <img src="@/assets/images/register.svg" alt="">-->
      </div>
    </div>

  </div>
</template>

<script>
import {getCaptchaImages} from "@/api/login";
import {decrypt, encrypt} from '@/utils/jsencrypt'
import Cookies from "js-cookie";
import {Message} from "element-ui";
import {registerUser} from "@/api/user";

const container = document.querySelector('.container')
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
      isRegister:false,
      loading: false,
      loginForm: {
        username: '',
        password: '',
        code: '',
        uuid: '',
        rememberMe: false
      },
      registerForm: {
        username: '',
        password: '',
        code: '',
        uuid: ''
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
    switchLogin(){
      this.isRegister = false;
      setTimeout(()=>{
        this.getCaptcha();
      },1500);

    },
    switchRegister(){
      this.isRegister = true;
      setTimeout(()=>{
        this.getCaptcha();
      },1500);
    },
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
    submitRegister(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //this.loading = true;
          registerUser(this.registerForm).then(res=>{
            this.registerForm = {
              username: '',
              password: '',
              code: '',
              uuid: ''
            }
            this.$message.success("注册成功");
            this.switchLogin();
          }).catch(res=>{
            this.getCaptcha();
          })
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
        this.registerForm.uuid = res.uuid
      })
    }
  }
}
</script>

<style scoped lang="less">
/deep/ .el-input__inner {
  border-radius: 20px;
}
.code-img {
  width: 35%;
  height: 35px;
  float: left;
  margin-top: 5px;
  margin-left: 8px;
  border-radius: 8px;
}
</style>


<style scoped>



/**
  本页面CSS使用Minyoung分享模板
  ------原作者信息--------
  Author: Minyoung
  CreateAt: 2021年10月14日23:44:45
  License: MIT
 */
* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
  color: #333;
}

.container {
  position: relative;
  min-height: 100vh;
  width: 100%;
  overflow: hidden;
}
.container::before {
  content: " ";
  position: absolute;
  width: 2000px;
  height: 2000px;
  border-radius: 50%;
  background-color: #FFE53B;
  background-image: linear-gradient(270deg, #FFE53B 0%, #FF2525 74%);
  transition: 1.8s ease-in-out;
  z-index: 6;
  top: -10%;
  right: 48%;
  transform: translateY(-50%);
}


.container.sign-up-mode::before {
  transform: translate(100%, -50%);
}

.form-warp {
  width: 50%;
  position: absolute;
  z-index: 5;
  left: 75%;
  top: 50%;
  z-index: 5;
  transform: translate(-50%, -50%);
  display: grid;
  grid-template-columns: 1fr;
  transition: 1s 0.7s ease-in-out;
}
.form-warp form {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  gap: 20px;
  /* 将两个 form 布局在 grid 同一位置 */
  grid-row: 1 / 2;
  grid-column: 1 / 2;
  transition: all 0.2s 0.7s;
  opacity: 1;
  z-index: 4;
}
.form-title {
  color: #FF2525;
}
.form-warp .sign-up-form {
  opacity: 0;
  z-index: 3;
}
.container.sign-up-mode .form-warp {
  left: 25%;
}
.container.sign-up-mode .sign-in-form {
  opacity: 0;
  z-index: 3;
}
.container.sign-up-mode .sign-up-form {
  opacity: 1;
  z-index: 4;
}
input,
.submit-btn {
  min-width: 300px;
  outline: none;
  padding: 12px 30px;
  line-height: 1;
  font-size: 16px;
  border-radius: 60px;
  color: #333;
  background-color: #6267f513;
  border: none;
}
input::placeholder {
  color: #cccc;
}
.submit-btn {
  background-color: #FF2525;
  color: #FFF;
  text-align: center;
  min-width: 230px;
  font-size: initial;
  font-weight: bold;
  letter-spacing: 1.5px;
  cursor: pointer;
}

.desc-warp {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
}
.desc-warp-item {
  height:60%;
  justify-content: center;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  justify-content: space-around;
  text-align: center;
  text-align: center;
  padding: 3rem 17% 2rem 12%;
  z-index: 6;
}
/* 事件穿透 BEGIN */
.sign-in-desc {
  pointer-events: none;
}
.sign-up-mode .sign-in-desc {
  pointer-events: all;
}
.sign-up-mode .sign-up-desc {
  pointer-events: none;
}
/* 事件穿透 END */
.content {
  width: 100%;
  transition: transform 0.9s ease-in-out;
  transition-delay: .6s;
}
.sign-in-desc img,
.sign-in-desc .content {
  transform: translateX(800px);
}
.sign-up-mode .sign-in-desc img,
.sign-up-mode .sign-in-desc .content {
  transform: translateX(0);
}

.sign-up-mode .sign-up-desc img,
.sign-up-mode .sign-up-desc .content {
  transform: translateX(-800px);
}

button {
  outline: none;
  padding: 6px 8px;
  min-width: 150px;
  min-height: 50px;
  text-align: center;
  border-radius: 30px;
  border: 2px solid #FFF;
  background: none;
  color: #FFF;
  cursor: pointer;
  transition: all .3s ease;
}
button:active {
  background: rgba(255, 255, 255, .1);
}
img {
  width: 100%;
  display: block;
  transition: transform 0.9s ease-in-out;
  transition-delay: .5s;
}

/* 响应式 */
@media screen and (max-width: 870px) {
  .container::before {
    width: 1500px;
    height: 1500px;
    transform: translateX(-50%);
    left: 30%;
    bottom: 68%;
    right: initial;
    top: initial;
    transition: 2s ease-in-out;
  }
  .container.sign-up-mode::before {
    transform: translate(-50%, 100%);
    bottom: 32%;
    right: initial;
  }
  .form-warp {
    width: 100%;
    top: 75%;
    left: 50%;
    transform: translate(-50%, -100%);
    transition: 1s 0.8s ease-in-out;
  }
  .container.sign-up-mode .form-warp {
    top: 25%;
    left: 50%;
    transform: translate(-50%, 0);
  }
  img {
    width: 200px;
    transition: transform 0.9s ease-in-out;
    transition-delay: 0.7s;
  }
  .desc-warp {
    grid-template-columns: 1fr;
    grid-template-rows: 1fr 2fr 1fr;
  }
  .desc-warp-item {
    height:60%;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    padding: 2.5rem 8%;
    grid-column: 1 / 2;
  }
  .sign-in-desc {
    grid-row: 3 / 4;
  }

  .sign-in-desc img,
  .sign-in-desc .content {
    transform: translateY(800px);
  }

  .sign-up-mode .sign-in-desc img,
  .sign-up-mode .sign-in-desc .content {
    transform: translateY(0);
  }

  .sign-up-mode .sign-up-desc img,
  .sign-up-mode .sign-up-desc .content {
    transform: translateY(-800px);
  }
}

@media screen and (max-width: 570px) {
  .container::before {
    bottom: 72%;
    left: 50%;
  }
  img {
    display: none;
  }
}

</style>
