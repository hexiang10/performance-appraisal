package com.performanceAppraisal.system.domain;

import com.performanceAppraisal.system.domain.vo.FileVo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.performanceAppraisal.common.annotation.Excel;
import com.performanceAppraisal.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 绩效自评对象 t_assess_staff
 * 
 * @author wind
 * @date 2022-04-20
 */
public class AssessStaff extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 考核id */
    @Excel(name = "考核id")
    private Long assessId;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    /** 考核项数组 */
    @Excel(name = "考核项数组")
    private String itemArr;

    /** 自评详分数 */
    @Excel(name = "自评详分数")
    private String self;

    /** 自评状态 */
    @Excel(name = "自评状态")
    private String selfState;

    /** 自评总数 */
    @Excel(name = "自评总数")
    private double selfNum;

    /** 审批分数 */
    @Excel(name = "审批分数")
    private String review;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private String reviewState;

    /** 审批总分 */
    @Excel(name = "审批总分")
    private double reviewNum;

    /** 总分 */
    @Excel(name = "总分")
    private double total;

    /** 附件id */
    @Excel(name = "附件id")
    private String fileId;

    /** 申报状态 */
    @Excel(name = "申报状态")
    private String declareState;

    /** 考核标题 */
    private String assessTitle;

    /** 用户名 */
    private String userName;

    /** 用户姓名 */
    private String nickName;

    /** 部门名称 */
    private String deptName;

    /** 是当前登录人 */
    private String isSelf;


    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private List<AssessItem> assessItems;

    private List<Long> items;

    private List<FileVo> fileList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setAssessId(Long assessId) 
    {
        this.assessId = assessId;
    }

    public Long getAssessId() 
    {
        return assessId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setItemArr(String itemArr) 
    {
        this.itemArr = itemArr;
    }

    public String getItemArr() 
    {
        return itemArr;
    }
    public void setSelf(String self) 
    {
        this.self = self;
    }

    public String getSelf() 
    {
        return self;
    }
    public void setSelfState(String selfState) 
    {
        this.selfState = selfState;
    }

    public String getSelfState() 
    {
        return selfState;
    }
    public void setReview(String review)
    {
        this.review = review;
    }

    public String getReview() 
    {
        return review;
    }
    public void setReviewState(String reviewState) 
    {
        this.reviewState = reviewState;
    }

    public String getReviewState() 
    {
        return reviewState;
    }
    public void setReviewNum(Long reviewNum) 
    {
        this.reviewNum = reviewNum;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    public String getDeclareState() {
        return declareState;
    }

    public void setDeclareState(String declareState) {
        this.declareState = declareState;
    }

    public String getAssessTitle() {
        return assessTitle;
    }

    public void setAssessTitle(String assessTitle) {
        this.assessTitle = assessTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(String isSelf) {
        this.isSelf = isSelf;
    }

    public List<AssessItem> getAssessItems() {
        return assessItems;
    }

    public void setAssessItems(List<AssessItem> assessItems) {
        this.assessItems = assessItems;
    }

    public List<Long> getItems() {
        return items;
    }

    public void setItems(List<Long> items) {
        this.items = items;
    }

    public double getSelfNum() {
        return selfNum;
    }

    public void setSelfNum(double selfNum) {
        this.selfNum = selfNum;
    }

    public double getReviewNum() {
        return reviewNum;
    }

    public void setReviewNum(double reviewNum) {
        this.reviewNum = reviewNum;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<FileVo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileVo> fileList) {
        this.fileList = fileList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("assessId", getAssessId())
            .append("deptId", getDeptId())
            .append("itemArr", getItemArr())
            .append("self", getSelf())
            .append("selfState", getSelfState())
            .append("selfNum", getSelfNum())
            .append("review", getReview())
            .append("reviewState", getReviewState())
            .append("reviewNum", getReviewNum())
            .append("total", getTotal())
            .append("fileId", getFileId())
            .append("declareState", getDeclareState())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
