<template>
  <div class="course-card" @click="handleCardClick(item)">
    <div class="course-img"  :style="colorArr[item.id%16]">
      <span class="title">{{ item.courseName }}</span>
    </div>
    <div class="course-content" >
      <div class="info">
        <div style="color: #04befe">
          <i class="el-icon-user-solid"/><span>{{item.createBy}}</span>
        </div>
        <div style="color: #F76B1C">
          <el-tag :type="item.isPublic ? 'success':'primary'" size="mini">{{item.isPublic ? '共享课':'班级课'}}</el-tag>
          <i class="el-icon-star-on"/><span>{{item.usageNums}}</span>
        </div>
      </div>
      <div class="desc">
        <span class="title">{{ item.details }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CourseCard",
  props:{
    item:{
      type:Object
    }
  },
  data() {
    return {
      colorArr:[
        'background-color: #FEE140;background-image: linear-gradient(90deg, #FEE140 0%, #FA709A 100%);',
        'background-color: #FEE140;background-image: linear-gradient(120deg, #FEE140 0%, #FA709A 100%);',
        'background-color: #FEE140;background-image: linear-gradient(40deg, #FEE140 0%, #FA709A 100%);',
        'background-color: #FA8BFF;background-image: linear-gradient(45deg, #FA8BFF 0%, #2BD2FF 52%, #2BFF88 90%);',
        'background-color: #FBDA61;background-image: linear-gradient(95deg, #FBDA61 0%, #FF5ACD 100%);',
        'background-color: #FBDA61;background-image: linear-gradient(45deg, #FBDA61 0%, #FF5ACD 100%);',
        'background-color: #FF9A8B;background-image: linear-gradient(90deg, #FF9A8B 0%, #FF6A88 55%, #FF99AC 100%);',
        'background-color: #FF9A8B;background-image: linear-gradient(45deg, #FF9A8B 0%, #FF6A88 55%, #FF99AC 100%);',
        'background-color: #FEE140;background-image: linear-gradient(65deg, #FEE140 0%, #FA709A 100%);',
        'background-color: #FEE140;background-image: linear-gradient(190deg, #FEE140 0%, #FA709A 100%);',
        'background-color: #FEE140;background-image: linear-gradient(10deg, #FEE140 0%, #FA709A 100%);',
        'background-color: #FA8BFF;background-image: linear-gradient(65deg, #FA8BFF 0%, #2BD2FF 52%, #2BFF88 90%);',
        'background-color: #FBDA61;background-image: linear-gradient(25deg, #FBDA61 0%, #FF5ACD 100%);',
        'background-color: #FBDA61;background-image: linear-gradient(75deg, #FBDA61 0%, #FF5ACD 100%);',
        'background-color: #FF9A8B;background-image: linear-gradient(30deg, #FF9A8B 0%, #FF6A88 55%, #FF99AC 100%);',
        'background-color: #FF9A8B;background-image: linear-gradient(85deg, #FF9A8B 0%, #FF6A88 55%, #FF99AC 100%);',
      ]

    }
  },
  methods:{
    handleCardClick(item){
      console.log("xxx")
      this.$emit('click',item)
    }
  }
}
</script>

<style scoped>
.course-card {

  /*position: relative;*/
  flex: 20%;
  max-width: 20%;
  height: 30%;
  margin: 0 24px 24px 0;
  background: #FFFFFF;
  box-shadow: 0 6px 10px 0 #5f656926;
  border-radius: 8px;
  /*float: left;*/
  transition: all .2s;
}

.course-card .course-img{
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 200px;
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
  /*background-color: #04befe;*/
}

.course-card .course-img .title{
  font-size: 24px;
  font-weight: bold;
  word-wrap: break-word;
}

.course-card .course-content{
  padding: 10px;
  line-height: 1.6;
}
.course-card .course-content .info{
  display: flex;
  justify-content: space-between;
}
.course-card .course-content .desc{
  color: #565659;
  width:248px;
  white-space:nowrap;
  overflow:hidden;
  text-overflow:ellipsis;
}
</style>
