<template>
  <el-menu
      :default-active="this.$store.state.menus.editableTabsValue"
      class="el-menu-vertical"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
  >
    <router-link to="/home">
      <el-menu-item index="Index"  @click="selectMenu({name:'Index',title:'首页'})">
        <template slot="title">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </template>
      </el-menu-item>
    </router-link>
    <router-link :to="(item.children&&item.children.length>0)?'':item.path"   v-for="(item,index) in menuList">
      <component class="menu-item" :index="item.name" :key="index" :is="(item.children&&item.children.length>0)?'el-submenu':'el-menu-item'">
        <template slot="title" >
          <i :class="item.icon"></i>
          <span>{{ item.title }}</span>
        </template>
        <router-link :to="menu.path" v-for="(menu) in item.children">
          <el-menu-item :index="menu.name" :key="index+menu.name" @click="selectMenu(menu)">
            <i :class="menu.icon"></i>
            <span slot="title">{{ menu.title }}</span>
          </el-menu-item>
        </router-link>

      </component>
    </router-link>
  </el-menu>



</template>

<script>
export default {
  name: "SizeMenu",
  data(){
    return {

    }
  },
  computed:{
    menuList:{
      get () {
        return this.$store.state.menus.menuList
      }
    }
  },
  methods:{
    selectMenu(menu){
      this.$store.commit('addTab',menu);
    }
  }
}
</script>

<style scoped>
.el-menu-vertical {
  height: 100%;
}
a {
  text-decoration: none;
}

.router-link-active {
  text-decoration: none;
}

.router-link{
  text-decoration: none;
}
</style>