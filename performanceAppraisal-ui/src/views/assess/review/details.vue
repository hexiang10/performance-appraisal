<template>
  <div class="app-container">
    <!-- 添加或修改绩效自评对话框 -->
      <el-descriptions title="考核说明" :column="3" border>
        <el-descriptions-item label="考核标题">{{form.title}}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{form.startDate}}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{form.endDate}}</el-descriptions-item>
        <el-descriptions-item label="考核说明" :span="3">{{form.remark}}</el-descriptions-item>
        <el-descriptions-item label="评分标准" :span="3">{{form.standard}}</el-descriptions-item>
        <el-descriptions-item label="总分">{{form.score}}</el-descriptions-item>
      </el-descriptions>
      <el-divider></el-divider>
      <h3>考核项</h3>
        <template v-for="(item, index) in form.assessItems">
          <el-descriptions title="" :column="3" border>
            <el-descriptions-item label="序号" :span="3">{{index+1}}</el-descriptions-item>
            <el-descriptions-item label="考核项标题">{{item.title}}</el-descriptions-item>
            <el-descriptions-item label="分值">{{item.score}}</el-descriptions-item>
            <el-descriptions-item label="自评权重">{{item.ratio}}</el-descriptions-item>
            <el-descriptions-item label="考核标准" :span="3">{{item.standard}}</el-descriptions-item>
            <el-descriptions-item label="考核说明" :span="3">{{item.remark}}</el-descriptions-item>
            <el-descriptions-item label="自评分" >{{item.selfScore}}</el-descriptions-item>
            <el-descriptions-item label="审批分" >{{item.reviewScore}}</el-descriptions-item>
          </el-descriptions>
        </template>

      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="加分项" prop="filemodule">
          <transition-group class="upload-file-list el-upload-list el-upload-list--text" name="el-fade-in-linear" tag="ul">
            <li :key="file.url" class="el-upload-list__item ele-upload-list__item-content" v-for="(file, index) in form.fileList">
              <el-link :href="`${baseUrl}${file.url}`" :underline="false" target="_blank">
                <span class="el-icon-document"> {{ getFileName(file.name) }} </span>
              </el-link>
            </li>
          </transition-group>
        </el-form-item>
      </el-form>
    <h3>总分</h3>
    <el-descriptions title="" :column="3" border>
      <el-descriptions-item label="自评总分">{{form.selfNum}}</el-descriptions-item>
      <el-descriptions-item label="审批总分">{{form.reviewNum}}</el-descriptions-item>
      <el-descriptions-item label="总分">{{form.total}}</el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
import { listStaff, getStaff, delStaff, addStaff, updateStaff } from "@/api/assess/staff";
import $ from 'jquery';
export default {
  name: "StaffDetails",
  props:{
    query:{
      type:Object,
      default:{}
    }
  },
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
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
      // 绩效自评表格数据
      staffList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        assessTitle: null
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getInfo(this.query);
  },
  methods: {
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        return name.slice(name.lastIndexOf("/") + 1);
      } else {
        return "";
      }
    },
    changeSelfVal() {
      this.$forceUpdate();
    },
    /** 查询绩效自评列表 */
    getList() {
      this.loading = true;
      listStaff(this.queryParams).then(response => {
        this.staffList = response.rows;
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
        userId: null,
        assessId: null,
        deptId: null,
        itemArr: null,
        self: null,
        selfState: null,
        selfNum: null,
        review: null,
        reviewState: null,
        reviewNum: null,
        total: null,
        fileId: null,
        declare: null,
        remark: null,
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
      this.title = "添加绩效自评";
    },
    getInfo(row){
      this.reset();
      const id = row.id;
      getStaff(id).then(response => {
        console.log(response.data);
        this.form = response.data;
        this.form.id = row.id;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getStaff(id).then(response => {
        this.form = response.data;
        this.form.id = row.id;
        /*this.form.assessItems.forEach(item =>{
          item.self = 0
        })

        this.form.userId = row.userId;
        this.form.assessId = row.assessId;
        this.form.deptId = row.deptId;*/
        this.open = true;
        this.title = "绩效自评";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let selfNum = 0;
          let weightList =$("[id^=input-weight-]");
          weightList.each(function (index,el){
            selfNum+= new Number($(el).val())
          });
          this.form.remark = null;
          this.form.reviewNum = Number(selfNum.toFixed(2));
          updateStaff(this.form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
          /*if (this.form.id != null) {
            updateStaff(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStaff(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }*/
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除绩效自评编号为"' + ids + '"的数据项？').then(function() {
        return delStaff(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('assess/staff/export', {
        ...this.queryParams
      }, `staff_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped lang="scss">
.upload-file-uploader {
  margin-bottom: 5px;
}
.upload-file-list .el-upload-list__item {
  border: 1px solid #e4e7ed;
  line-height: 2;
  margin-bottom: 10px;
  position: relative;
}
.upload-file-list .ele-upload-list__item-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: inherit;
}
.ele-upload-list__item-content-action .el-link {
  margin-right: 10px;
}
</style>
