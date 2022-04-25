<template>
  <div class="app-container">
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.name"
            placeholder="名称"
            clearable
        >
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getRoleList" icon="el-icon-search">搜索</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-popconfirm title="这是确定批量删除吗？" @confirm="handleDelete(null)">
          <el-button type="danger"  icon="el-icon-delete" slot="reference" :disabled="delBtlStatu">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" ref="multipleTable" style="width: 100%" border stripe :data="tableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="角色名称"  align="center" prop="roleName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="权限字符"  align="center" prop="roleKey" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="备注" align="center" prop="remark" width="180"/>
      <el-table-column label="创建人" align="center" prop="createBy" width="120"/>
      <el-table-column label="更新人" align="center" prop="updateBy" width="120"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180"/>
      <el-table-column label="状态" align="center" prop="status" width="120">
        <template slot-scope="scope">
          <el-popconfirm :title="'确定'+(scope.row.status?'启用':'停用')+'吗？'" @confirm="handleStatusChange(scope.row)">
            <el-switch v-model="scope.row.status" slot="reference">
            </el-switch>
          </el-popconfirm>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" v-if="scope.row.roleId !== 1">
          <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:role:edit']"
          >修改</el-button>

          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.roleId)">
            <el-button size="mini" icon="el-icon-delete"  type="text" slot="reference" v-hasPermi="['system:role:remove']">删除</el-button>
          </el-popconfirm>
<!--          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:role:edit']">-->
<!--            <span class="el-dropdown-link">-->
<!--              <i class="el-icon-d-arrow-right el-icon&#45;&#45;right"></i>更多-->
<!--            </span>-->
<!--            <el-dropdown-menu slot="dropdown">-->
<!--              <el-dropdown-item command="handleDataScope" icon="el-icon-circle-check"-->
<!--                                v-hasPermi="['system:role:edit']">数据权限</el-dropdown-item>-->
<!--              <el-dropdown-item command="handleAuthUser" icon="el-icon-user"-->
<!--                                v-hasPermi="['system:role:edit']">分配用户</el-dropdown-item>-->
<!--            </el-dropdown-menu>-->
<!--          </el-dropdown>-->
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[10, 20, 50, 100]"
        :current-page="current"
        :page-size="size"
        :total="total">
    </el-pagination>


    <!--新增对话框-->
    <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">

      <el-form ref="form" :model="form" :rules="formRules" label-width="100px" class="demo-form">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item prop="roleKey">
          <span slot="label">
            <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasRole('admin')`)" placement="top">
              <i class="el-icon-forum"></i>
            </el-tooltip>
            权限字符
          </span>
          <el-input v-model="form.roleKey" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="角色顺序" prop="roleSort">
          <el-input-number v-model="form.roleSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" @change="handleStatusChange(scope.row)"/>
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand($event, 'menu')">展开/折叠</el-checkbox>
          <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll($event, 'menu')">全选/全不选</el-checkbox>
          <el-checkbox v-model="form.menuCheckStrictly" @change="handleCheckedTreeConnect($event, 'menu')">父子联动</el-checkbox>
          <el-tree
              class="tree-border"
              :data="menuOptions"
              show-checkbox
              ref="menu"
              node-key="id"
              :check-strictly="!form.menuCheckStrictly"
              empty-text="加载中，请稍后"
              :props="defaultProps"
          ></el-tree>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm('form')">确 定</el-button>
        <el-button @click="handleClose">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {deleteRoleById, getRoleList, saveRole, updateRoleStatus} from "@/api/role";
import {getMenuTreeOptions, getMenuTreeselect} from "@/api/menu";

export default {
  name: "Role",
  data() {
    return {
      searchForm: {},
      delBtlStatu: true,

      total: 0,
      size: 10,
      current: 1,

      dialogVisible: false,
      dialogTitle:'',
      form: {
        status:true
      },
      menuExpand:false,
      menuNodeAll:false,
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      menuOptions: [],
      loading:false,
      tableData: [],

      formRules: {
        roleName: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ],
        roleKey: [
          { required: true, message: "权限字符不能为空", trigger: "blur" }
        ],
        roleSort: [
          { required: true, message: "角色顺序不能为空", trigger: "blur" }
        ]
      },

      multipleSelection: [],

      permDialogVisible: false,
      permForm: {}

    }
  },
  created() {
    this.getRoleList()

    getMenuTreeOptions().then(res => {
      this.menuOptions = res
    })
  },
  methods: {
    getRoleList() {
      this.loading = true;
      getRoleList({
        params: {
          name: this.searchForm.name,
          page: this.current,
          limit: this.size
        }
      }).then(res => {
        this.tableData = res.rows
        this.total = res.total
        this.loading = false;
      })
    },
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

      this.delBtlStatu = val.length == 0
    },

    handleSizeChange(val) {
      this.size = val
      this.getRoleList()
    },
    handleCurrentChange(val) {
      this.current = val
      this.getRoleList()
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
      this.form = {}
      this.menuNodeAll = false;
      this.menuExpand = false;
      this.handleCheckedTreeNodeAll(null,'menu');
    },
    handleClose() {
      this.resetForm('form')
    },

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.menuIds = this.getMenuAllCheckedKeys();
          saveRole(this.form.roleId?'update' : 'save', this.form)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success'
                });

                this.dialogVisible = false
                this.getRoleList()
                this.resetForm(formName)
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    handleAdd() {
      this.dialogTitle = "添加"
      this.dialogVisible = true
    },

    handleUpdate(row) {
      this.form = row
      this.dialogTitle = "编辑"
      this.dialogVisible = true
      getMenuTreeselect(row.roleId).then(res=>{
        this.$nextTick(() => {
          let checkedKeys = res
          checkedKeys.forEach((v) => {
            this.$nextTick(()=>{
              this.$refs.menu.setChecked(v, true ,false);
            })
          })
        });
      })

    },
    handleDelete(id) {
      var ids = []

      if (id) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.roleId)
        })
      }

      deleteRoleById({ids}).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success'
        });
        this.getRoleList()
      })
    },
    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value, type) {
      if (type == 'menu') {
        let treeList = this.menuOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.menu.store.nodesMap[treeList[i].id].expanded = value;
        }
      } else if (type == 'dept') {
        let treeList = this.deptOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.dept.store.nodesMap[treeList[i].id].expanded = value;
        }
      }
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value, type) {
      if (type == 'menu') {
        this.$refs.menu.setCheckedNodes(value ? this.menuOptions: []);
      } else if (type == 'dept') {
        this.$refs.dept.setCheckedNodes(value ? this.deptOptions: []);
      }
    },
    // 树权限（父子联动）
    handleCheckedTreeConnect(value, type) {
      if (type == 'menu') {
        this.form.menuCheckStrictly = value ? true: false;
      } else if (type == 'dept') {
        this.form.deptCheckStrictly = value ? true: false;
      }
    },
    // 所有菜单节点数据
    getMenuAllCheckedKeys() {
      // 目前被选中的菜单节点
      let checkedKeys = this.$refs.menu.getCheckedKeys();
      // 半选中的菜单节点
      let halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    handleStatusChange(row){
      updateRoleStatus(row.roleId,row.status).then(res=>{
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose: () => {
            this.getRoleList()
          }
        });
      })
    },


  }
}
</script>

<style scoped>

.el-pagination {
  float: right;
  margin-top: 22px;
}

</style>
