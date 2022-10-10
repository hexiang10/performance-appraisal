<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考核名称" prop="assessTitle">
        <el-input
          v-model="queryParams.assessTitle"
          placeholder="请输入考核名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核部门" prop="deptId">
        <treeselect style="width: 10rem" v-model="queryParams.deptId" :options="deptOptions" :show-count="true" placeholder="请选择考核部门" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['assess:issue:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['assess:issue:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['assess:issue:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="issueList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" align="center" />
      <el-table-column label="考核名称" align="center" prop="assessTitle" />
      <el-table-column label="考核部门名称" align="center" prop="deptName" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="发布状态" align="center" prop="status" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '0'">未发布</el-tag>
          <el-tag v-if="scope.row.status == '1'" type="success">已发布</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status == '0'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="issueAssess(scope.row)"
            v-hasPermi="['assess:issue:edit']"
          >发布</el-button>
          <el-button v-if="scope.row.status == '0'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['assess:issue:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['assess:issue:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改考核发布对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-input type="hidden" v-model="form.assessTitle" placeholder="请输入考核项目id" />
        <el-form-item label="考核名称" prop="assessId">
          <el-select v-model="form.assessId" placeholder="请选择考核" @change="infoChange">
            <el-option
              v-for="item in infoList"
              :key="item.id"
              :label="item.title"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-input type="hidden" v-model="form.deptName" placeholder="请输入考核部门id" />
        <el-form-item label="考核部门" prop="deptId">
          <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择考核部门" @select="deptChange"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listIssue, getIssue, delIssue, addIssue, updateIssue ,changeStatus} from "@/api/assess/issue";
import { listInfo } from "@/api/assess/info";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Issue",
  components: { Treeselect },
  data() {
    return {
      // 部门树选项
      deptOptions: undefined,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 考核发布表格数据
      issueList: [],
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        assessId: null,
        assessTitle: null,
        deptId: null,
        deptName: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        assessId: [
          { required: true, message: '请选择考核', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '请选择考核部门', trigger: 'blur' }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    /** 查询考核发布列表 */
    getList() {
      this.loading = true;
      listIssue(this.queryParams).then(response => {
        this.issueList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getAssessList() {
      listInfo().then(response => {
        this.infoList = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        assessId: null,
        assessTitle: null,
        deptId: null,
        deptName: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null,
        status: "0"
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考核发布";
      this.getAssessList();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getIssue(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改考核发布";
        this.getAssessList();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateIssue(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addIssue(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除考核发布编号为"' + ids + '"的数据项？').then(function() {
        return delIssue(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    infoChange(changes){
      let filterItem = this.infoList.filter(function (items){return items.id==changes});
      if(filterItem){
        this.form.assessTitle = filterItem[0].title;
      }
    },
    deptChange(node){
      this.form.deptName = node.label;
    },
    issueAssess(row){
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认发布考核发布编号为"' + ids + '"的数据项？').then(function() {
        console.log(ids);
        return changeStatus({id:ids,status:'1'});

      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("发布成功");
      }).catch(() => {});
    }
  }
};
</script>
