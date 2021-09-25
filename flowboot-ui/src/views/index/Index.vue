<template>
  <el-container>
    <el-aside width="200px">
      <SizeMenu/>
    </el-aside>
    <el-container>
      <el-header>
        <span style="font-size: 18px;font-weight: bold">Flow Boot 快速开发流平台</span>
        <div class="header-acatar">
          <el-avatar size="medium"
                     :src="userInfo.avatar"></el-avatar>
          <el-dropdown>
            <span class="el-dropdown-link">
              {{ userInfo.username }} <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>
                <router-link :to="{name:'UserRePassword'}">修改密码</router-link>
              </el-dropdown-item>
              <el-dropdown-item divided @click.native="logout">退出登入</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-link style="color: #f1f6f6;" href="https://gitee.com/Vincent-Vic/vue-admin" target="_blank">gitee</el-link>
        </div>
      </el-header>
      <el-main>
        <Tabs/>
        <div style="margin: 0 15px">
          <router-view/>
        </div>
      </el-main>
      <el-footer>Footer</el-footer>
    </el-container>
  </el-container>
</template>

<script>
import SizeMenu from "@/components/SizeMenu"
import Tabs from "@/components/Tabs";
import {getUserInfo,logout} from "@/api/user"
import {Message} from "element-ui";
export default {
  name: "Index",
  components:{
    SizeMenu,Tabs
  },
  data(){

    return {
      userInfo:{
        id:0,
        username:this.$store.getters.name,
        avatar:this.$store.getters.avatar
      }
    }
  },
  methods:{
    getUserInformation(){
      this.$store.dispatch("GetInfo").then((res) => {
        this.userInfo = res.user;
        console.log(res)
      })
    },
    logout(){
      this.$store.dispatch("Logout").then(() => {
        this.$router.push({ path: "/login" });
      })
    }
  }
}
</script>

<style scoped>
.el-container {
  padding: 0;
  margin: 0;
  height: 100%;
}

.header-acatar {
  float: right;
  width: 210px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.el-dropdown-link {
  cursor: pointer;
  color: #f1f6f6;
}

.el-icon-arrow-down {
  font-size: 12px;
}



.el-header, .el-footer {
  text-align: center;
  line-height: 60px;
}

.el-header {
  background-color: #17B3A3;
  color: #ffffff;
}
.el-footer {
  background-color: #B3C0D1;
  color: #333;
}
.el-aside {
  background-color: #D3DCE6;
  color: #333;
  line-height: 200px;
}

.el-main {
  display: block;
  padding: 0px;

  /*background-color: #E9EEF3;*/
  color: #333;
  /*text-align: center;*/
}


.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
