package com.performanceAppraisal.system.mapper;

import java.util.List;
import com.performanceAppraisal.system.domain.AssessAndItem;

/**
 * 考核项中间Mapper接口
 * 
 * @author wind
 * @date 2022-04-18
 */
public interface AssessAndItemMapper 
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
     * 删除考核项中间
     * 
     * @param id 考核项中间主键
     * @return 结果
     */
    public int deleteAssessAndItemById(Long id);

    /**
     * 批量删除考核项中间
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAssessAndItemByIds(Long[] ids);

    public int deleteAssessAndItemByAssessId(Long assessId);
}
