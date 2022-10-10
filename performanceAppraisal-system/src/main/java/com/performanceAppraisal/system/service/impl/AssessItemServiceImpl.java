package com.performanceAppraisal.system.service.impl;

import java.util.List;
import com.performanceAppraisal.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.performanceAppraisal.system.mapper.AssessItemMapper;
import com.performanceAppraisal.system.domain.AssessItem;
import com.performanceAppraisal.system.service.IAssessItemService;

import javax.annotation.Resource;

/**
 * 考核项Service业务层处理
 * 
 * @author wind
 * @date 2022-04-17
 */
@Service
public class AssessItemServiceImpl implements IAssessItemService 
{
    @Resource
    private AssessItemMapper assessItemMapper;

    /**
     * 查询考核项
     * 
     * @param id 考核项主键
     * @return 考核项
     */
    @Override
    public AssessItem selectAssessItemById(Long id)
    {
        return assessItemMapper.selectAssessItemById(id);
    }

    @Override
    public List<AssessItem> selectAssessItemByIds(List<Long> ids) {
        return assessItemMapper.selectAssessItemByIds(ids.toArray(new Long[]{}));
    }

    /**
     * 查询考核项列表
     * 
     * @param assessItem 考核项
     * @return 考核项
     */
    @Override
    public List<AssessItem> selectAssessItemList(AssessItem assessItem)
    {
        return assessItemMapper.selectAssessItemList(assessItem);
    }

    /**
     * 新增考核项
     * 
     * @param assessItem 考核项
     * @return 结果
     */
    @Override
    public int insertAssessItem(AssessItem assessItem)
    {
        assessItem.setCreateTime(DateUtils.getNowDate());
        return assessItemMapper.insertAssessItem(assessItem);
    }

    /**
     * 修改考核项
     * 
     * @param assessItem 考核项
     * @return 结果
     */
    @Override
    public int updateAssessItem(AssessItem assessItem)
    {
        assessItem.setUpdateTime(DateUtils.getNowDate());
        return assessItemMapper.updateAssessItem(assessItem);
    }

    /**
     * 批量删除考核项
     * 
     * @param ids 需要删除的考核项主键
     * @return 结果
     */
    @Override
    public int deleteAssessItemByIds(Long[] ids)
    {
        return assessItemMapper.deleteAssessItemByIds(ids);
    }

    /**
     * 删除考核项信息
     * 
     * @param id 考核项主键
     * @return 结果
     */
    @Override
    public int deleteAssessItemById(Long id)
    {
        return assessItemMapper.deleteAssessItemById(id);
    }
}
