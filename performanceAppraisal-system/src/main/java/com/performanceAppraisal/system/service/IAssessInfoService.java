package com.performanceAppraisal.system.service;

import java.util.List;
import com.performanceAppraisal.system.domain.AssessInfo;

/**
 * 考核主Service接口
 * 
 * @author wind
 * @date 2022-04-17
 */
public interface IAssessInfoService 
{
    /**
     * 查询考核主
     * 
     * @param id 考核主主键
     * @return 考核主
     */
    public AssessInfo selectAssessInfoById(Long id);

    /**
     * 查询考核主列表
     * 
     * @param assessInfo 考核主
     * @return 考核主集合
     */
    public List<AssessInfo> selectAssessInfoList(AssessInfo assessInfo);

    /**
     * 新增考核主
     * 
     * @param assessInfo 考核主
     * @return 结果
     */
    public int insertAssessInfo(AssessInfo assessInfo);

    /**
     * 修改考核主
     * 
     * @param assessInfo 考核主
     * @return 结果
     */
    public int updateAssessInfo(AssessInfo assessInfo);

    /**
     * 批量删除考核主
     * 
     * @param ids 需要删除的考核主主键集合
     * @return 结果
     */
    public int deleteAssessInfoByIds(Long[] ids);

    /**
     * 删除考核主信息
     * 
     * @param id 考核主主键
     * @return 结果
     */
    public int deleteAssessInfoById(Long id);
}
