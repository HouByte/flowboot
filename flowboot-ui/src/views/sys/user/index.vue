<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.keyword"
            placeholder="关键词"
            clearable
        >
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getUserList" icon="el-icon-search">搜索</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['sys:user:add']" >新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-popconfirm title="这是确定批量删除吗？" @confirm="handleDelete(null)">
          <el-button type="danger" icon="el-icon-delete" slot="reference" :disabled="delBtlStatu" >批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" border stripe @selection-change="handleSelectionChange">

      <el-table-column type="selection" width="55"/>
      <el-table-column label="头像" width="50">
        <template slot-scope="scope">
          <el-avatar size="small" :src="scope.row.avatar?scope.row.avatar :'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="userName" label="用户名" width="120"/>
      <el-table-column align="center" prop="nickName" label="昵称" width="120"/>
      <el-table-column align="center" prop="email" label="邮箱"/>
      <el-table-column align="center" prop="phone" label="手机号"/>
      <el-table-column align="center" prop="remark" label="备注"/>
      <el-table-column align="center" prop="status" width="70" label="状态">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status">
          </el-switch>
         </template>
      </el-table-column>
      <el-table-column align="center" prop="createTime" width="200" label="创建时间"/>
      <el-table-column align="center" prop="icon" width="360px" label="操作">
        <template slot-scope="scope" v-if="scope.row.userId !== 1">
          <el-button type="text" size="mini" icon="el-icon-edit" @click="handleEdit(scope.row.userId)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="这是一段内容确定删除吗？" @confirm="handleDelete(scope.row.userId)">
              <el-button type="text" size="mini" icon="el-icon-delete" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
          <el-divider direction="vertical"></el-divider>
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
                <span class="el-dropdown-link" style="font-size: 12px">
                  <i class="el-icon-d-arrow-right el-icon--right"></i>更多
                </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="repassHandle(scope.row.userId, scope.row.username)" icon="el-icon-key"
                                v-hasPermi="['system:user:resetPwd']">重置密码</el-dropdown-item>
              <el-dropdown-item command="roleHandle(scope.row.userId)" icon="el-icon-circle-check"
                                v-hasPermi="['system:user:edit']">分配角色</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>

    </el-table>

    <el-pagination
        v-show="total>0"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[10, 20, 50, 100]"
        :current-page="current"
        :page-size="size"
        :total="total">
    </el-pagination>


    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="600px" append-to-body>
      <el-form ref="editForm" :model="editForm" :rules="editFormRules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input v-model="editForm.nickName" placeholder="请输入用户昵称" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="editForm.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="editForm.phone" placeholder="请输入手机号码" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="editForm.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="editForm.userId == undefined" label="用户名称" prop="userName">
              <el-input v-model="editForm.userName" placeholder="请输入用户名称" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="editForm.userId == undefined" label="用户密码" prop="password">
              <el-input v-model="editForm.password" placeholder="请输入用户密码" type="password" maxlength="20" show-password/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-select v-model="editForm.sex" placeholder="请选择">
<!--                <el-option-->
<!--                    v-for="dict in sexOptions"-->
<!--                    :key="dict.dictValue"-->
<!--                    :label="dict.dictLabel"-->
<!--                    :value="dict.dictValue"-->
<!--                ></el-option>-->
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-switch
                  v-model="editForm.status">
              </el-switch>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="岗位">
              <el-select v-model="editForm.postIds" multiple placeholder="请选择">
<!--                <el-option-->
<!--                    v-for="item in postOptions"-->
<!--                    :key="item.postId"-->
<!--                    :label="item.postName"-->
<!--                    :value="item.postId"-->
<!--                    :disabled="item.status == 1"-->
<!--                ></el-option>-->
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色" >
              <el-select v-model="editForm.roleIds" multiple placeholder="请选择" :disabled="editForm.userId === 1">
                <el-option
                    v-for="item in roleOptions"
                    :key="item.roleId"
                    :label="item.roleName"
                    :value="item.roleId"
                    v-show="item.roleId === 1 ?  editForm.roleId === 1: true"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="editForm.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm('editForm')">确 定</el-button>
        <el-button @click="handleClose">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 分配权限对话框 -->
    <el-dialog title="分配角色" :visible.sync="roleDialogFormVisible" width="600px">

      <el-form :model="roleForm">
        <el-tree
            :data="roleTreeData"
            show-checkbox
            ref="roleTree"
            :check-strictly=checkStrictly
            node-key="id"
            :default-expand-all=true
            :props="defaultProps">
        </el-tree>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="roleDialogFormVisible=false">取 消</el-button>
        <el-button type="primary" @click="submitRoleHandle('roleForm')">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {deleteUserById, getUserById, getUserList, saveUser, updateUserRole} from "@/api/user";
import {getRoleList, roleSelectOptions} from "@/api/role";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Role",
  components:{
    Treeselect
  },
  data() {
    return {
      searchForm: {},
      delBtlStatu: true,

      total: 0,
      size: 10,
      current: 1,

      dialogVisible: false,
      dialogTitle:'新增',
      editForm: {
        status: true
      },
      roleOptions:[],
      deptOptions:[
        {
          "id":100,
          "label":"Bugio",
          "children":[
            {
              "id":101,
              "label":"Flowboot",
              "children":[
                {
                  "id":103,
                  "label":"研发部门"
                },
                {
                  "id":104,
                  "label":"市场部门"
                },
                {
                  "id":105,
                  "label":"测试部门"
                },
                {
                  "id":106,
                  "label":"财务部门"
                },
                {
                  "id":107,
                  "label":"运维部门"
                }
              ]
            },
            {
              "id":102,
              "label":"长沙分公司",
              "children":[
                {
                  "id":108,
                  "label":"市场部门"
                },
                {
                  "id":109,
                  "label":"财务部门"
                }
              ]
            }
          ]
        }
      ],


      loading:false,
      tableData: [],

      editFormRules: {
        username: [
          {required: true, message: '请输入用户名称', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ]
      },

      multipleSelection: [],

      roleDialogFormVisible: false,
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      roleForm: {},
      roleTreeData:  [],
      treeCheckedKeys: [],
      checkStrictly: true

    }
  },
  created() {
    this.getUserList()

    this.getRoleSelectOptions();

  },
  methods: {
    /**
     * 列表数据
     */
    getUserList() {
      this.loading = true;
      getUserList( {
        keyword: this.searchForm.keyword,
        page: this.current,
        limit: this.size
      }).then(res => {
        this.tableData = res.rows
        this.total = res.total
        this.loading = false;
      })
    },
    handleSizeChange(val) {
      this.size = val
      this.getUserList()
    },
    handleCurrentChange(val) {
      this.current = val
      this.getUserList()
    },

    /**
     * 表格行选择操作
     */
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      console.log("勾选")
      console.log(val)
      this.multipleSelection = val;
      console.log(this.multipleSelection)
      this.delBtlStatu = val.length == 0
    },
    //删除操作
    handleDelete(id) {

      var ids = []

      if (id) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.userId)
        })
      }

      let deleteForm = {
        ids:ids
      }
      deleteUserById(deleteForm).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose:() => {
            this.getUserList()
          }
        });
      })
    },

    /**
     * 新增修改表单操作
     */
    //重置表单
    resetForm(formName) {
      //this.$refs[formName].resetFields();
      this.dialogVisible = false
      this.editForm = {
        status: true
      }
    },
    //关闭表单
    handleClose() {
      this.resetForm('editForm')
    },
    getRoleSelectOptions(){
      roleSelectOptions().then(res=>{
        this.roleOptions = res;
      })
    },

    getRoleOptions(id){
      return this.roleOptions.filter(item => {
        if (id === 1){
          return true
        }
        if (item.roleId === 1){
          return false;
        }
      })
    },
    //提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          saveUser(this.editForm.userId?'update' : 'save', this.editForm)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success',
                  onClose:() => {
                    this.getUserList()
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

    /**
     * 添加按钮动作触发
     */
    handleAdd(){
      console.log("add")
      this.resetForm('editForm')
      this.dialogTitle = "新增";
      this.dialogVisible = true;
    },
    /**
     * 编辑按钮动作触发
     */
    handleEdit(id) {
      console.log(id)
      getUserById(id).then(res => {
        this.editForm = res

        this.dialogVisible = true
      })
    },


    roleHandle (id) {
      this.roleDialogFormVisible = true

      getUserById(id).then(res => {
        this.roleForm = res

        let roleIds = []
        res.sysRoles.forEach(row => {
          roleIds.push(row.id)
        })

        this.$refs.roleTree.setCheckedKeys(roleIds)
      })
    },
    submitRoleHandle(formName) {
      var roleIds = this.$refs.roleTree.getCheckedKeys()

      console.log(roleIds)

      updateUserRole(this.roleForm.id, roleIds).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose:() => {
            this.getUserList()
          }
        });

        this.roleDialogFormVisible = false
      })
    },
    repassHandle(id, username) {

      this.$confirm('将重置用户【' + username + '】的密码, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // this.$axios.post("/sys/user/repass", id).then(res => {
        //
        // })

        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose: () => {
          }
        });
      })
    }
  }
}
</script>

<style scoped>

.el-pagination {
  float: right;
  margin-top: 22px;
}

</style>
