package com.performanceAppraisal.system.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.performanceAppraisal.system.domain.vo.FileVo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.performanceAppraisal.common.annotation.Excel;
import com.performanceAppraisal.common.core.domain.BaseEntity;

/**
 * 考核主对象 t_assess_info
 * 
 * @author wind
 * @date 2022-04-17
 */
public class AssessInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 总分 */
    @Excel(name = "总分")
    private Integer score;

    /** 评分标准 */
    @Excel(name = "评分标准")
    private String standard;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 考核项集合 */
    private List<Long> items;

    /** 考核项集合 */
    private List<AssessItem> assessItems;

    private List<FileVo> fileList;

    private double selfNum;

    private double reviewNum;

    private double total;

    private String staffRemark;

    public List<AssessItem> getAssessItems() {
        return assessItems;
    }

    public void setAssessItems(List<AssessItem> assessItems) {
        this.assessItems = assessItems;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }
    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public Date getEndDate() 
    {
        return endDate;
    }
    public void setScore(Integer score) 
    {
        this.score = score;
    }

    public Integer getScore() 
    {
        return score;
    }
    public void setStandard(String standard) 
    {
        this.standard = standard;
    }

    public String getStandard() 
    {
        return standard;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
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

    public List<FileVo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileVo> fileList) {
        this.fileList = fileList;
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

    public String getStaffRemark() {
        return staffRemark;
    }

    public void setStaffRemark(String staffRemark) {
        this.staffRemark = staffRemark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("remark", getRemark())
            .append("score", getScore())
            .append("standard", getStandard())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }


}
