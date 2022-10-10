package com.performanceAppraisal.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.performanceAppraisal.common.annotation.Excel;
import com.performanceAppraisal.common.core.domain.BaseEntity;

/**
 * 考核项中间对象 t_assess_and_item
 * 
 * @author wind
 * @date 2022-04-18
 */
public class AssessAndItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 考核id */
    @Excel(name = "考核id")
    private Long assessId;

    /** 考核项id */
    @Excel(name = "考核项id")
    private Long itemId;

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
    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("assessId", getAssessId())
            .append("itemId", getItemId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
