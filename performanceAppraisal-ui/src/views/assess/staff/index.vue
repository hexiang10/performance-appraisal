<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考核标题" prop="title">
        <el-input
          v-model="queryParams.assessTitle"
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

    <el-table v-loading="loading" :data="staffList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" align="center" />
      <el-table-column label="标题" align="center" prop="assessTitle" />
      <el-table-column label="教师姓名" align="center" prop="nickName" />
      <el-table-column label="所属部门" align="center" prop="deptName" />
      <el-table-column label="自评总数" align="center" prop="selfNum" />
      <el-table-column label="审批总分" align="center" prop="reviewNum" />
      <el-table-column label="总分" align="center" prop="total" />
      <el-table-column label="状态" align="center"  >
        <template slot-scope="scope">
          <el-tag type="info" v-if="scope.row.selfState == '0'">未自评</el-tag>
          <el-tag v-if="scope.row.selfState == '1' && scope.row.reviewState == '0'">已自评,待审批</el-tag>
          <el-tag v-if="scope.row.reviewState == '1'">已审批</el-tag>
          <el-tag v-if="scope.row.declareState == '1'">已申诉,待审批</el-tag>
          <el-tag v-if="scope.row.declareState == '2'">申诉已审批</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.isSelf=='1' && scope.row.selfState == '0'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['assess:staff:add']"
          >自评</el-button>
          <el-button
            v-if="scope.row.isSelf=='1' && scope.row.reviewState == '1' && scope.row.declareState == '0'"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleAppeal(scope.row)"
            v-hasPermi="['assess:staff:add']"
          >申诉</el-button>
          <el-button
            v-if="scope.row.selfState == '1'"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDetails(scope.row)"
          >详情</el-button>

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

    <!-- 添加或修改绩效自评对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
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
            <el-descriptions-item label="自评分" :span="2">
              <el-input-number v-model="item.selfScore" :precision="1" controls-position="right" :step="0.5" :min="0" :max="item.score" @change="changeSelfVal($event)">
              </el-input-number>
            </el-descriptions-item>
           <el-descriptions-item label="加权分" >
              <el-input readonly :value="item.selfScore*(item.ratio/10)" :id="'input-weight-'+String(item.id)"></el-input>
            </el-descriptions-item>
          </el-descriptions>
        </template>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-input type="hidden" v-model="form.userId" placeholder="请输入用户id" />
          <el-input type="hidden" v-model="form.assessId" placeholder="请输入考核id" />
          <el-input type="hidden" v-model="form.deptId" placeholder="请输入部门id" />
        <el-form-item label="加分项" prop="filemodule">
          <FileUpload ref="filemodule"></FileUpload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="绩效详情" :visible.sync="detailsOpen" v-if="detailsOpen" width="800px" append-to-body>
      <staffDetails :query="detailsQuery"></staffDetails>
    </el-dialog>
    <el-dialog title="绩效申诉" :visible.sync="appealOpen" v-if="appealOpen" width="800px" append-to-body>
      <staffDetails :query="detailsQuery"></staffDetails>
      <template>
          <el-input  type="textarea" :rows="3" v-model="remark" placeholder="请输入申诉说明" />
        <el-button type="primary" @click="appealBtn">确 定</el-button>
        <el-button @click="cancelAppeal">取 消</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { listStaff, getStaff, delStaff, addStaff, updateStaff ,appeal } from "@/api/assess/staff";
import { getInfoAndItem } from "@/api/assess/info";
import fileUpload from "@/components/FileUpload/index.vue";
import staffDetails from "../review/details.vue"
import $ from 'jquery';
export default {
  name: "Staff",
  components :{fileUpload,staffDetails},
  data() {
    return {
      remark:null,
      detailsOpen:false,
      appealOpen:false,
      detailsQuery:{},
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
    this.getList();
  },
  methods: {
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
    cancelAppeal(){
      this.appealOpen = false;
      this.remark = null;
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
    handleDetails(row){
      this.detailsQuery = row;
      this.detailsOpen = true;
    },
    handleAppeal(row){
      this.detailsQuery = row;
      this.appealOpen = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const assessId = row.assessId;
      getInfoAndItem(assessId).then(response => {
        this.form = response.data;
        this.form.assessItems.forEach(item =>{
          item.self = 0
        })
        this.form.userId = row.userId;
        this.form.assessId = row.assessId;
        this.form.deptId = row.deptId;
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
          this.form.id = null;
          this.form.remark = null;
          this.form.selfNum = Number(selfNum.toFixed(2));
          this.form.fileList = this.$refs["filemodule"].fileList;
          addStaff(this.form).then(response => {
            this.$modal.msgSuccess("自评成功");
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
    appealBtn(){
      let remark = this.remark;
      let appealForm ={
        id:this.detailsQuery.id,
        declareState:'1',
        remark:null
      };
      if(remark){
        appealForm.remark = remark;
        console.log(appealForm);
        appeal(appealForm).then(response => {
          this.$modal.msgSuccess("申诉成功");
          this.appealOpen = false;
          this.remark = null;
          this.getList();
        });
      }else{
        this.$message.error('请输入申诉说明');
      }
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
