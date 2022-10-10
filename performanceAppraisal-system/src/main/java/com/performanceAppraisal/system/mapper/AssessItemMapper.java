package com.performanceAppraisal.system.mapper;

import java.util.List;
import com.performanceAppraisal.system.domain.AssessItem;

/**
 * 考核项Mapper接口
 * 
 * @author wind
 * @date 2022-04-17
 */
public interface AssessItemMapper 
{
    /**
     * 查询考核项
     * 
     * @param id 考核项主键
     * @return 考核项
     */
    public AssessItem selectAssessItemById(Long id);

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
     * 删除考核项
     * 
     * @param id 考核项主键
     * @return 结果
     */
    public int deleteAssessItemById(Long id);

    /**
     * 批量删除考核项
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAssessItemByIds(Long[] ids);


    public List<AssessItem> selectAssessItemByIds(Long[] ids);
}
