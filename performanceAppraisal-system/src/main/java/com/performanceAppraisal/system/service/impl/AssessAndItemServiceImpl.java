package com.performanceAppraisal.system.service.impl;

import java.util.List;
import com.performanceAppraisal.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.performanceAppraisal.system.mapper.AssessAndItemMapper;
import com.performanceAppraisal.system.domain.AssessAndItem;
import com.performanceAppraisal.system.service.IAssessAndItemService;

/**
 * 考核项中间Service业务层处理
 * 
 * @author wind
 * @date 2022-04-18
 */
@Service
public class AssessAndItemServiceImpl implements IAssessAndItemService 
{
    @Autowired
    private AssessAndItemMapper assessAndItemMapper;

    /**
     * 查询考核项中间
     * 
     * @param id 考核项中间主键
     * @return 考核项中间
     */
    @Override
    public AssessAndItem selectAssessAndItemById(Long id)
    {
        return assessAndItemMapper.selectAssessAndItemById(id);
    }

    /**
     * 查询考核项中间列表
     * 
     * @param assessAndItem 考核项中间
     * @return 考核项中间
     */
    @Override
    public List<AssessAndItem> selectAssessAndItemList(AssessAndItem assessAndItem)
    {
        return assessAndItemMapper.selectAssessAndItemList(assessAndItem);
    }

    /**
     * 新增考核项中间
     * 
     * @param assessAndItem 考核项中间
     * @return 结果
     */
    @Override
    public int insertAssessAndItem(AssessAndItem assessAndItem)
    {
        assessAndItem.setCreateTime(DateUtils.getNowDate());
        return assessAndItemMapper.insertAssessAndItem(assessAndItem);
    }

    /**
     * 修改考核项中间
     * 
     * @param assessAndItem 考核项中间
     * @return 结果
     */
    @Override
    public int updateAssessAndItem(AssessAndItem assessAndItem)
    {
        return assessAndItemMapper.updateAssessAndItem(assessAndItem);
    }

    /**
     * 批量删除考核项中间
     * 
     * @param ids 需要删除的考核项中间主键
     * @return 结果
     */
    @Override
    public int deleteAssessAndItemByIds(Long[] ids)
    {
        return assessAndItemMapper.deleteAssessAndItemByIds(ids);
    }

    /**
     * 删除考核项中间信息
     * 
     * @param id 考核项中间主键
     * @return 结果
     */
    @Override
    public int deleteAssessAndItemById(Long id)
    {
        return assessAndItemMapper.deleteAssessAndItemById(id);
    }
}
