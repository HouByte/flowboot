<template>


  <el-menu :default-active="this.$store.state.menus.editableTabsValue" class="el-menu-demo"
           mode="horizontal" @select="handleSelect" router
           background-color="#2932e1" text-color="#f0f0f4" active-text-color="#f36838">
    <el-menu-item v-if="title" class="menu-item" style="width: 200px;margin-right: 100px" index="/"  @click="selectMenu({name:'Index',title:'首页'})">
      <template slot="title">
<!--        <i class="el-icon-s-home"></i>-->
        <span slot="title">{{ title }}</span>
      </template>
    </el-menu-item>
    <el-menu-item class="menu-item" index="/"  @click="selectMenu({name:'Index',title:'首页'})">
      <template slot="title">
        <i class="el-icon-s-home"></i>
        <span slot="title">首页</span>
      </template>
    </el-menu-item>
    <component class="menu-item" v-if="item.visible&&item.menuType !='F'" v-for="(item,index) in menuList" :index="item.name" :key="index" :is="(item.menuType&&item.menuType === 'M')?'el-submenu':'el-menu-item'">
      <template slot="title" >
        <div class="item-title">
          <i :class="item.icon"></i>
          <span> {{ item.title }}</span>
        </div>
      </template>
      <el-menu-item v-if="menu.visible&&menu.menuType !='F'"  v-for="(menu,index) in item.children" :index="menu.name" :key="index+menu.name" @click="selectMenu(menu)">
        <i :class="menu.icon"></i>
        <span slot="title" style="margin-left: 10px">{{ menu.title }}</span>
      </el-menu-item>

    </component>

    <div style="float: right">
      <slot name="action"></slot>
    </div>
  </el-menu>



</template>

<script>
export default {
  name: "NavMenu",
  props:['title'],
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
    handleSelect(e){
      console.log("xxx ",e)
    },
    selectMenu(menu){
      this.$store.commit('addTab',menu);
    }
  }
}
</script>

<style scoped>
.el-menu-demo{
  width: 100%;
}
.menu-item{
  margin-left: 10px;
  width: 120px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  background-color: #2932e1;
}

.item-title{
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

</style>
