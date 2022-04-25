<template>
  <div>
    <!--          @node-click="handleNodeClick"-->
    <el-tree :data="trees" :props="defaultProps"  default-expand-all :expand-on-click-node="false" >
      <span class="custom-tree-node"  slot-scope="{ node, data }">
<!--        -->
        <div class="title" :style="'font-size:' +(36/node.level)+'px;'">
          <i v-if="node.level === 2" :style="node.level === 2 ? ' color: #04befe;':''" :class="getIcon(data.type)"/>
          <span v-if="node.level === 1" style="margin-left: 5px;">第{{data.sort}}章 - </span>
          <span v-if="node.level === 2" style="margin-left: 5px;">{{data.sort}}.</span>
          <span >{{  node.label }}</span>

        </div>
        <div class="action">
           <el-button v-if="node.level === 2 && data.type === 'V'" type="text" icon="el-icon-caret-right" size="mini" @click="playerVideo(data.path,data)"> 播放 </el-button>
           <el-button v-if="node.level === 2 && (data.type === 'I'||data.type === 'F')" type="text" icon="el-icon-view" size="mini" @click="handleNodeClick(data)"> 查看 </el-button>
           <el-button v-if="node.level === 2 && (data.type === 'T'||data.type === 's')" type="text" icon="el-icon-caret-right" size="mini" @click="playerVideo(data.path,data)"> 查看试卷 </el-button>
           <el-button v-if="node.level === 2 && (data.type === 'W'||data.type === 'E' ||data.type === 'P')"
                      type="text" icon="el-icon-bottom" size="mini" @click="handleNodeClick(data)"> 下载 </el-button>
           <el-button v-if="op === 'course'&&isOwn && node.level === 1" type="text" icon="el-icon-edit" size="mini" @click="handleChapter(data)"> 修改章节 </el-button>
           <el-button v-if="op === 'course'&&isOwn && node.level === 1" type="text" icon="el-icon-plus" size="mini" @click="handleResources(data.id)" > 添加资源 </el-button>
           <el-button v-if="op === 'course'&&isOwn && node.level === 2" type="text" icon="el-icon-plus" size="mini" @click="handleResources(data.id,data)"> 修改资源 </el-button>
           <el-button v-if="op === 'course'&&isOwn" type="text" icon="el-icon-delete" size="mini" @click="handleDelete(data)"> 删除资源 </el-button>
        </div>
      </span>
    </el-tree>
    <!-- 添加或修改课程资源对话框 -->
    <el-dialog :title="dialogChapterTitle" :visible.sync="dialogChapterVisible" width="300px" append-to-body>
      <el-form ref="chapterForm" :model="chapterForm" :rules="chapterRule" label-width="80px">
        <el-form-item label="章节序号" prop="sort">
          <el-input v-model="chapterForm.sort" placeholder="请输入章节序号" maxlength="30" />
        </el-form-item>
        <el-form-item label="章节名称" prop="name">
          <el-input v-model="chapterForm.name" placeholder="请输入章节名称" maxlength="30" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleSave('chapterForm',chapterForm)">确 定</el-button>
        <el-button @click="handleClose">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="dialogResourcesTitle" :visible.sync="dialogResourcesVisible" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="formRule" label-width="80px">
        <el-form-item label="所属章节" prop="pid">
          <el-select v-model="this.form.pid" placeholder="请选择" @change="handlePid">
            <el-option
                v-for="item in trees"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="章节序号" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入资源序号" maxlength="30" />
        </el-form-item>
        <el-form-item label="资源名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入资源名称" maxlength="30" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select  v-model="this.form.type" placeholder="请选择" @change="handleType">
            <el-option
                v-for="item in types"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上传" prop="rid">
          <el-upload
              class="upload-demo"
              :on-success="uploadSuccess"
              drag
              :limit="1"
              action="/api/CAI/resources/upload/file"
              :headers = "headers">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传图片(.jpg,.jpeg,.png,.gif)、视频(.mp4)、文档(.ppt/.pptx、.doc/.docx、.xls/.xlsx,.pdf)，且不超过20Mb</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleSave('form',form)">确 定</el-button>
        <el-button @click="handleClose">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog  :visible.sync="dialogImgVisible" width="600px" append-to-body>
      <el-image :src="imgUrl"></el-image>
    </el-dialog>
  </div>
</template>

<script>
import {deleteCourseResourcesById, saveCourseResources} from "@/api/course-resources";
import {getToken} from "@/utils/auth";

export default {
  name: "CourseCatalogue",
  props:{
    cid:{
      type:Number
    },
    trees:{
      type:Array
    },
    isOwn:{
      type:Boolean,
      default:false
    },
    op:{
      type:String,
      default:'classroom'
    },
    resPath:{
      requested:true,
      type:String,
      default:  'http://localhost:8081'
    }
  },
  data(){
    return {
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      dialogChapterTitle:'新增章节',
      dialogChapterVisible:false,
      chapterForm:{
        cid: undefined,
        name: '',
        rid: -1,
        pid: -1,
        sort: 1,
        type: 'C'
      },
      chapterRule:{
        name: [
          { required: true, message: "章节名称不能为空", trigger: "blur" },
          { min: 2, max: 30, message: '章节名称长度必须介于 2 和 30 之间', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: "序号不能为空", trigger: "blur" }
        ]
      },
      statusApply:null,
      dialogResourcesTitle:'新增资源',
      dialogResourcesVisible:false,
      form:{
        cid: undefined,
        name: '',
        rid: undefined,
        pid: undefined,
        sort: 1,
        type: "F"
      },
      formRule:{
        name: [
          { required: true, message: "章节名称不能为空", trigger: "blur" },
          { min: 2, max: 30, message: '章节名称长度必须介于 2 和 30 之间', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: "序号不能为空", trigger: "blur" }
        ],
        rid: [
          { required: true, message: "文件未上传或未上传成功", trigger: "blur" }
        ],
        type: [
          { required: true, message: "类型必须选择", trigger: "blur" }
        ],
        pid: [
          { required: true, message: "所属章节必须选择", trigger: "blur" }
        ]
      },
      /**
       * 类型：'V' 视频 ，'F' 文件，','S' 作业，‘T’ 测试/考试,'I' 图片,'D' 简介
       */
      types:[{
        value: 'F',
        label: '文件'
      }],
      headers: {
        Authorization: 'Bearer '+ getToken()  //从cookie里获取token，并赋值  Authorization ，而不是token
      },
      dialogImgVisible:false,
      imgUrl:'',
    }
  },
  methods:{
    getIcon(type){
      //'V' 视频 ，'P' PPT，'W' WORD ,‘E’ Excel ,'F' PDF , 'I' 图片,'H' Html
      switch (type){
        case 'V':
          return 'el-icon-video-play';
        case 'P':
          return 'el-icon-data-analysis';
        case 'W':
          return 'el-icon-document';
        case 'E':
          return 'el-icon-s-grid';
        case 'F':
          return 'el-icon-tickets';
        case 'I':
          return 'el-icon-picture-outline';
      }
    },
    handleClose(){
      this.dialogChapterVisible = false;
      this.dialogResourcesVisible = false;
      this.chapterForm={
        cid: undefined,
        name: '',
        rid: -1,
        pid: -1,
        sort: 1,
        type: "C"
      };

      this.form={
        cid: undefined,
        name: '',
        rid: null,
        pid: undefined,
        sort: 1,
        type: "F"
      };
    },
    handleChapter(data){
      this.dialogChapterTitle = '编辑章节';
      this.chapterForm = data;
      this.dialogChapterVisible = true;
    },
    handleChange(value) {
      this.chapterForm.sort = value
    },

    handleSave(formName,data){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          saveCourseResources(data.id?'update' : 'save', data)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success',
                  onClose:() => {
                    this.$emit("getTree")
                  }
                });

                this.dialogChapterVisible = false
                this.dialogResourcesVisible = false
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleResources(id,data){
      if (id === null || id === undefined){
        return
      }
      if (data === undefined){
        this.dialogResourcesTitle = '新增资源';
        this.form.pid = id
        this.form.cid = this.cid
      } else {
        this.dialogResourcesTitle = '编辑资源';
        this.form = data

      }
      this.dialogResourcesVisible = true;
    },
    uploadSuccess(response, file, fileList){
      console.log("e",response,",,xxxx")
      this.form.rid = response.data;
    },
    handlePid(e){
      this.form.pid = e
    },
    handleType(e){
      this.form.type = e
    },
    handleDelete(data){
      console.log(data)
      if (data.children && data.children.length>0 && data.type === 'C'){
        this.$message({
          message: '存在资源不允许删除目录',
          type: 'info'
        });
        return
      }

      var ids = []
      ids.push(data.id)
      let deleteForm = {
        ids:ids
      }
      deleteCourseResourcesById(deleteForm).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose:() => {
            this.$emit("getTree")
          }
        });
      })
    },
    handleNodeClick(e){

      var path =  this.resourcePath + e.path;
      //'V' 视频 ，'P' PPT，'W' WORD ,‘E’ Excel ,'F' PDF , 'I' 图片,'H' Html
      switch (e.type){
        case 'V':
          this.playerVideo(e.path,e);
          break
        case 'P':
        case 'W':
        case 'E':
        case 'F':
          window.open(path)
          break
        case 'I':
          this.imgUrl = path;
          this.dialogImgVisible = true;

          break
      }

    },
    playerVideo(path,video){
      this.$emit("playerVideo",path,video);
    }
  }
}
</script>

<style lang="less" scoped>
/deep/ .el-tree-node__label{
  font-size: 36px;
}

/deep/ .el-tree-node__content{
  height: 46px;
}
</style>
<style scoped>
.custom-tree-node{
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.custom-tree-node .action{
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-right: 10px;
}

</style>
