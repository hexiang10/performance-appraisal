package com.performanceAppraisal.system.service;

import java.util.List;
import com.performanceAppraisal.system.domain.AssessItem;

/**
 * 考核项Service接口
 * 
 * @author wind
 * @date 2022-04-17
 */
public interface IAssessItemService 
{
    /**
     * 查询考核项
     * 
     * @param id 考核项主键
     * @return 考核项
     */
    public AssessItem selectAssessItemById(Long id);


    public List<AssessItem> selectAssessItemByIds(List<Long> ids);

    /**
     * 查询考核项列表
     * 
     * @param assessItem 考核项
     * @return 考核项集合
     */
    public List<AssessItem> selectAssessItemList(AssessItem assessItem);

    /**
     * 新增考核项
     * 
     * @param assessItem 考核项
     * @return 结果
     */
    public int insertAssessItem(AssessItem assessItem);

    /**
     * 修改考核项
     * 
     * @param assessItem 考核项
     * @return 结果
     */
    public int updateAssessItem(AssessItem assessItem);

    /**
     * 批量删除考核项
     * 
     * @param ids 需要删除的考核项主键集合
     * @return 结果
     */
    public int deleteAssessItemByIds(Long[] ids);

    /**
     * 删除考核项信息
     * 
     * @param id 考核项主键
     * @return 结果
     */
    public int deleteAssessItemById(Long id);
}
