package com.performanceAppraisal.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.performanceAppraisal.common.annotation.Excel;
import com.performanceAppraisal.common.core.domain.BaseEntity;

/**
 * 考核发布对象 t_assess_issue
 * 
 * @author wind
 * @date 2022-04-17
 */
public class AssessIssue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 考核项目id */
    @Excel(name = "考核项目id")
    private Long assessId;

    /** 考核名称 */
    @Excel(name = "考核名称")
    private String assessTitle;

    /** 考核部门id */
    @Excel(name = "考核部门id")
    private Long deptId;

    /** 考核部门名称 */
    @Excel(name = "考核部门名称")
    private String deptName;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 发布状态（0未发布 1发布） */
    @Excel(name = "发布状态", readConverterExp = "0=未发布,1=发布")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAssessId(Long assessId) 
    {
        this.assessId = assessId;
    }

    public Long getAssessId() 
    {
        return assessId;
    }
    public void setAssessTitle(String assessTitle) 
    {
        this.assessTitle = assessTitle;
    }

    public String getAssessTitle() 
    {
        return assessTitle;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setDeptName(String deptName) 
    {
        this.deptName = deptName;
    }

    public String getDeptName() 
    {
        return deptName;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("assessId", getAssessId())
            .append("assessTitle", getAssessTitle())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("status", getStatus())
            .toString();
    }
}
