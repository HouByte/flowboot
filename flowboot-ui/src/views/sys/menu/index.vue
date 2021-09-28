<template>
  <div>

    <el-form :inline="true">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </el-form-item>
    </el-form>

    <el-table
        :data="tableData"
        style="width: 100%;margin-bottom: 20px;"
        row-key="menuId"
        border
        stripe
        @row-click="rowClick"
        @selection-change="handleSelectionChange"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">

      <el-table-column
          prop="menuName"
          label="名称"
          sortable
          width="180">
      </el-table-column>
      <el-table-column
          prop="perms"
          label="权限编码"
          align="center"
          width="180">
      </el-table-column>

      <el-table-column prop="icon" label="图标" align="center" width="60">
        <template slot-scope="scope">
          <i :class="scope.row.icon"></i>
        </template>
      </el-table-column>

      <el-table-column prop="menuType" label="类型" align="center" width="80">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.menuType === 'M'">目录</el-tag>
          <el-tag size="small" v-else-if="scope.row.menuType === 'C'" type="success">菜单</el-tag>
          <el-tag size="small" v-else-if="scope.row.menuType === 'F'" type="info">按钮</el-tag>
        </template>

      </el-table-column>

      <el-table-column prop="path" label="菜单URL"/>
      <el-table-column prop="component" label="菜单组件"/>
      <el-table-column prop="status" label="状态" align="center" width="80">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" @change="handleStatusChange(scope.row,scope.row.status)"/>
        </template>
      </el-table-column>
      <el-table-column prop="icon" label="操作" align="center" width="280">
        <template slot-scope="scope">
          <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>

          <template>
            <el-popconfirm title="这是一段内容确定删除吗？" @confirm="handleDelete(scope.row.id)">
              <el-button type="text" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </template>
      </el-table-column>

    </el-table>


<!--    &lt;!&ndash;新增对话框&ndash;&gt;-->
<!--    <el-dialog-->
<!--        title="提示"-->
<!--        :visible.sync="dialogVisible"-->
<!--        width="600px"-->
<!--        :before-close="handleClose">-->

<!--      <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px" class="demo-editForm">-->

<!--        <el-form-item label="上级菜单" prop="parentId">-->
<!--          <el-select v-model="editForm.parentId" placeholder="请选择上级菜单">-->
<!--            <template v-for="item in tableData">-->
<!--              <el-option :label="item.name" :value="item.id"></el-option>-->
<!--              <template v-for="child in item.children">-->
<!--                <el-option :label="child.name" :value="child.id">-->
<!--                  <span>{{ "- " + child.name }}</span>-->
<!--                </el-option>-->
<!--              </template>-->
<!--            </template>-->
<!--          </el-select>-->
<!--        </el-form-item>-->

<!--        <el-form-item label="菜单名称" prop="name" label-width="100px">-->
<!--          <el-input v-model="editForm.menuName" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->

<!--        <el-form-item label="权限编码" prop="perms" label-width="100px">-->
<!--          <el-input v-model="editForm.perms" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->

<!--        <el-form-item label="图标" prop="icon" label-width="100px">-->
<!--          <icon-picker v-model="editForm.icon"></icon-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="菜单URL" prop="path" label-width="100px">-->
<!--          <el-input v-model="editForm.path" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->

<!--        <el-form-item label="菜单组件" prop="component" label-width="100px">-->
<!--          <el-input v-model="editForm.component" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->

<!--        <el-form-item label="类型" prop="type" label-width="100px">-->
<!--          <el-radio-group v-model="editForm.type">-->
<!--            <el-radio :label=0>目录</el-radio>-->
<!--            <el-radio :label=1>菜单</el-radio>-->
<!--            <el-radio :label=2>按钮</el-radio>-->
<!--          </el-radio-group>-->
<!--        </el-form-item>-->

<!--        <el-form-item label="状态" prop="statu" label-width="100px">-->
<!--          <el-radio-group v-model="editForm.statu">-->
<!--            <el-radio :label=0>禁用</el-radio>-->
<!--            <el-radio :label=1>正常</el-radio>-->
<!--          </el-radio-group>-->
<!--        </el-form-item>-->

<!--        <el-form-item label="排序号" prop="orderNum" label-width="100px">-->
<!--          <el-input-number v-model="editForm.orderNum" :min="1" label="排序号">1</el-input-number>-->
<!--        </el-form-item>-->


<!--        <el-form-item>-->
<!--          <el-button type="primary" @click="submitForm('editForm')">立即创建</el-button>-->
<!--          <el-button @click="resetForm('editForm')">重置</el-button>-->
<!--        </el-form-item>-->
<!--      </el-form>-->

<!--    </el-dialog>-->

    <!-- 添加或修改菜单对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="680px" append-to-body>
      <el-form ref="editForm" :model="editForm" :rules="editFormRules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级菜单">
              <treeselect
                  v-model="editForm.parentId"
                  :options="menuOptions"

                  :show-count="true"
                  placeholder="选择上级菜单"
              />
<!--              :normalizer="normalizer"-->
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单类型" prop="menuType">
              <el-radio-group v-model="editForm.menuType">
                <el-radio label="M">目录</el-radio>
                <el-radio label="C">菜单</el-radio>
                <el-radio label="F">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item v-if="editForm.menuType !== 'F'" label="菜单图标">
              <icon-picker v-model="editForm.icon"></icon-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="menuName">
              <el-input v-model="editForm.menuName" placeholder="请输入菜单名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="editForm.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="editForm.menuType != 'F'">
              <span slot="label">
                <el-tooltip content="选择是外链则路由地址需要以`http(s)://`开头" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                是否外链
              </span>
              <el-switch v-model="editForm.isFrame" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="editForm.menuType != 'F'" prop="path">
              <span slot="label">
                <el-tooltip content="访问的路由地址，如：`user`，如外网地址需内链访问则以`http(s)://`开头" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                路由地址
              </span>
              <el-input v-model="editForm.path" placeholder="请输入路由地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="editForm.menuType == 'C'">
            <el-form-item prop="component">
              <span slot="label">
                <el-tooltip content="访问的组件路径，如：`system/user/index`，默认在`views`目录下" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                组件路径
              </span>
              <el-input v-model="editForm.component" placeholder="请输入组件路径" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="editForm.menuType != 'M'">
              <el-input v-model="editForm.perms" placeholder="请输入权限标识" maxlength="100" />
              <span slot="label">
                <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasPermi('system:user:list')`)" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                权限字符
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="editForm.menuType != 'F'">
              <span slot="label">
                <el-tooltip content="选择隐藏则路由将不会出现在侧边栏，但仍然可以访问" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                显示状态
              </span>

                <el-switch v-model="editForm.visible" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="editForm.menuType != 'F'">
              <span slot="label">
                <el-tooltip content="选择停用则路由将不会出现在侧边栏，也不能被访问" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                菜单状态
              </span>
              <el-switch v-model="editForm.status" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="editForm.menuType == 'C'">
              <span slot="label">
                <el-tooltip content="选择是则会被`keep-alive`缓存，需要匹配组件的`name`和地址保持一致" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                是否缓存
              </span>
              <el-switch v-model="editForm.isCache" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm('editForm')">确 定</el-button>
        <el-button @click="handleClose">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
import {deleteById, getMenuById, getMenuTreeOptions, getMenuTrees, saveMenu} from "@/api/menu";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Menu",
  components:{
    Treeselect
  },
  data() {
    return {
      dialogVisible: false,
      dialogTitle:'',
      editForm: {
        component: '',
        icon: '#',
        isCache: false,
        isFrame: false,
        menuName: '',
        menuType: 'M',
        orderNum: 0,
        parentId: undefined,
        path: '',
        perms: '',
        remark: '',
        status: true,
        visible: true
      },
      editFormRules: {
        menuName: [
          { required: true, message: "菜单名称不能为空", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "菜单顺序不能为空", trigger: "blur" }
        ],
        path: [
          { required: true, message: "路由地址不能为空", trigger: "blur" }
        ]
      },
      tableData: [],
      currentSelectRowId:undefined,
      menuOptions:[]
    }
  },
  created() {
    this.getMenuTree()

    getMenuTreeOptions().then(res => {
      this.menuOptions = res
    })
  },
  methods: {
    // 选择图标
    selected(name) {
      console.log(name)
    },
    getMenuTree() {
      getMenuTrees().then(res => {
        this.tableData = res
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          saveMenu(this.editForm.menuId?'update' : 'save', this.editForm)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success',
                  onClose:() => {
                    this.getMenuTree()
                  }
                });

                this.dialogVisible = false
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    handleSelectionChange(e){
      console.log(e)
    },
    rowClick(row){
      this.currentSelectRowId = row.menuId;
    },
    handleAdd(){
      this.resetForm()
      console.log(this.currentSelectRowId)
      this.editForm.parentId = this.currentSelectRowId
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.editForm = row
      this.dialogVisible = true
    },
    resetForm() {
      this.editForm ={
        component: '',
        icon: '#',
        isCache: false,
        isFrame: false,
        menuName: '',
        menuType: 'M',
        orderNum: 0,
        parentId: undefined,
        path: '',
        perms: '',
        remark: '',
        status: true,
        visible: true
      }
      this.dialogVisible = false
    },

    handleClose() {
      this.resetForm('editForm')
    },
    handleDelete(id) {
      deleteById(id).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose:() => {
            this.getMenuTree()
          }
        });

      })
    },
    handleStatusChange (row,on){

      let text = row.status === true ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.menuName + '"菜单吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {

      }).catch(function() {
        row.status = row.status === true ? false : true;
      });

    }
  }
}
</script>

<style scoped>

</style>
