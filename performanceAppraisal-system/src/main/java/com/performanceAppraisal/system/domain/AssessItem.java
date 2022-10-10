package com.performanceAppraisal.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.performanceAppraisal.common.annotation.Excel;
import com.performanceAppraisal.common.core.domain.BaseEntity;

/**
 * 考核项对象 t_assess_item
 * 
 * @author wind
 * @date 2022-04-17
 */
public class AssessItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 考核标题 */
    @Excel(name = "考核标题")
    private String title;

    /** 考核标准 */
    @Excel(name = "考核标准")
    private String standard;

    /** 总分值 */
    @Excel(name = "总分值")
    private Integer score;

    /** 自评权重 */
    @Excel(name = "自评权重")
    private Integer ratio;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private Long selfScore;

    private Long reviewScore;

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
    public void setStandard(String standard) 
    {
        this.standard = standard;
    }

    public String getStandard() 
    {
        return standard;
    }
    public void setScore(Integer score) 
    {
        this.score = score;
    }

    public Integer getScore() 
    {
        return score;
    }
    public void setRatio(Integer ratio) 
    {
        this.ratio = ratio;
    }

    public Integer getRatio() 
    {
        return ratio;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    public Long getSelfScore() {
        return selfScore;
    }

    public void setSelfScore(Long selfScore) {
        this.selfScore = selfScore;
    }

    public Long getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(Long reviewScore) {
        this.reviewScore = reviewScore;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("standard", getStandard())
            .append("remark", getRemark())
            .append("score", getScore())
            .append("ratio", getRatio())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
