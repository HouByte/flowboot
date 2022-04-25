<template>
  <div class="center-container">
    <div class="search" >
      <el-input v-if="isSearch" placeholder="请输入内容" v-model="searchForm.keyword">
        <i class="el-icon-search el-input__icon" slot="suffix" @click="getList"/>
      </el-input>
      <slot >

      </slot>
    </div>
   <div class="course-container">

      <CourseCard :item="item" v-for="item in list" :key="item.id"  @click="handleCardClick"/>


    </div>
    <pagination v-show="total>0" :total="total" :page.sync="searchForm.page" :page-sizes="pageSizes" :limit.sync="searchForm.limit"  @pagination="getList"/>
  </div>
</template>

<script>
import CourseCard from "@/components/CourseCard/index"

export default {
  name: "CourseIndex",
  components:{
    CourseCard
  },
  props:{
    list:{
      type:Array,
      default:[]
    },
    isSearch:{
      type:Boolean,
      default:true
    },
    searchForm:{
      type:Object,
      default: {
        page:1,
        limit:8
      }
    },
    total:{
      type:Number
    },
    op:{
      type:String,
      default:'course'
    }
  },
  data() {
    return {
      pageSizes:[8]
    }
  },
  methods:{
    handleCardClick(item){
      this.$router.push({ path: '/fly/course/center/desc',  query: { id: item.id,op:this.op,code:item.code } })
    },
    getList(){
      this.$emit('searchList')
    }
  }
}
</script>

<style scoped>

.course-container{
  width: 100%;
  margin: 30px;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}

.search{
  width: 600px;
  display: flex;
  align-items: center;
  margin-top: 50px;
}

.center-container{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

</style>
