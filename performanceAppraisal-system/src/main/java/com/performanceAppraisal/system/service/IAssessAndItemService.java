package com.performanceAppraisal.system.service;

import java.util.List;
import com.performanceAppraisal.system.domain.AssessAndItem;

/**
 * 考核项中间Service接口
 * 
 * @author wind
 * @date 2022-04-18
 */
public interface IAssessAndItemService 
{
    /**
     * 查询考核项中间
     * 
     * @param id 考核项中间主键
     * @return 考核项中间
     */
    public AssessAndItem selectAssessAndItemById(Long id);

    /**
     * 查询考核项中间列表
     * 
     * @param assessAndItem 考核项中间
     * @return 考核项中间集合
     */
    public List<AssessAndItem> selectAssessAndItemList(AssessAndItem assessAndItem);

    /**
     * 新增考核项中间
     * 
     * @param assessAndItem 考核项中间
     * @return 结果
     */
    public int insertAssessAndItem(AssessAndItem assessAndItem);

    /**
     * 修改考核项中间
     * 
     * @param assessAndItem 考核项中间
     * @return 结果
     */
    public int updateAssessAndItem(AssessAndItem assessAndItem);

    /**
     * 批量删除考核项中间
     * 
     * @param ids 需要删除的考核项中间主键集合
     * @return 结果
     */
    public int deleteAssessAndItemByIds(Long[] ids);

    /**
     * 删除考核项中间信息
     * 
     * @param id 考核项中间主键
     * @return 结果
     */
    public int deleteAssessAndItemById(Long id);
}
