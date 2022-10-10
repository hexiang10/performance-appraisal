<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考核标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入考核标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['assess:item:add']"
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
          v-hasPermi="['assess:item:edit']"
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
          v-hasPermi="['assess:item:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="itemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" align="center" />
      <el-table-column label="考核标题" align="center" prop="title" />
      <el-table-column label="考核标准" align="center" prop="standard" />
      <el-table-column label="考核说明" align="center" prop="remark" />
      <el-table-column label="总分值" align="center" prop="score" />
      <el-table-column label="自评权重" align="center" prop="ratio" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['assess:item:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['assess:item:remove']"
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

    <!-- 添加或修改考核项对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考核标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入考核标题" />
        </el-form-item>
        <el-form-item label="考核标准" prop="standard">
          <el-input  type="textarea" :rows="3" v-model="form.standard" placeholder="请输入考核标准" />
        </el-form-item>
        <el-form-item label="考核说明" prop="remark">
          <el-input  type="textarea" :rows="3" v-model="form.remark" placeholder="请输入考核说明" />
        </el-form-item>
        <el-form-item label="总分值" prop="score">
          <el-input-number v-model="form.score" :min="1" :max="100" :step="1" placeholder="请输入总分值" />
        </el-form-item>
        <el-form-item label="自评权重" prop="ratio">
          <el-input-number v-model="form.ratio" :min="1" :max="10" :step="1" placeholder="请输入自评权重" />
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
import { listItem, getItem, delItem, addItem, updateItem } from "@/api/assess/item";

export default {
  name: "Item",
  data() {
    return {
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
      // 考核项表格数据
      itemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        standard: null,
        score: null,
        ratio: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: '请输入考核标题', trigger: 'blur' }
        ],
        standard: [
          { min: 3, max: 200, message: '长度在 3 到 200 个字符', trigger: 'blur' },
          { required: true, message: '请输入考核标准', trigger: 'blur' }
        ],
        remark: [
          { min: 3, max: 200, message: '长度在 3 到 200 个字符', trigger: 'blur' },
          { required: true, message: '请输入考核说明', trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询考核项列表 */
    getList() {
      this.loading = true;
      listItem(this.queryParams).then(response => {
        this.itemList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        title: null,
        standard: null,
        remark: null,
        score: null,
        ratio: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考核项";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getItem(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改考核项";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateItem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addItem(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除考核项编号为"' + ids + '"的数据项？').then(function() {
        return delItem(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('assess/item/export', {
        ...this.queryParams
      }, `item_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
