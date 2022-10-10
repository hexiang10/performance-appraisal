package com.performanceAppraisal.system.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.performanceAppraisal.common.utils.DateUtils;
import com.performanceAppraisal.system.domain.AssessAndItem;
import com.performanceAppraisal.system.mapper.AssessAndItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.performanceAppraisal.system.mapper.AssessInfoMapper;
import com.performanceAppraisal.system.domain.AssessInfo;
import com.performanceAppraisal.system.service.IAssessInfoService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 考核主Service业务层处理
 * 
 * @author wind
 * @date 2022-04-17
 */
@Service
public class AssessInfoServiceImpl implements IAssessInfoService 
{
    @Resource
    private AssessInfoMapper assessInfoMapper;

    @Resource
    private AssessAndItemMapper assessAndItemMapper;

    /**
     * 查询考核主
     * 
     * @param id 考核主主键
     * @return 考核主
     */
    @Override
    public AssessInfo selectAssessInfoById(Long id)
    {
        AssessInfo assessInfo = assessInfoMapper.selectAssessInfoById(id);
        AssessAndItem query = new AssessAndItem();
        query.setAssessId(id);
        List<AssessAndItem> items = assessAndItemMapper.selectAssessAndItemList(query);
        assessInfo.setItems(items.stream().map( AssessAndItem ::getItemId).collect(Collectors.toList()));
        return assessInfo;
    }

    /**
     * 查询考核主列表
     * 
     * @param assessInfo 考核主
     * @return 考核主
     */
    @Override
    public List<AssessInfo> selectAssessInfoList(AssessInfo assessInfo)
    {
        return assessInfoMapper.selectAssessInfoList(assessInfo);
    }

    /**
     * 新增考核主
     * 
     * @param assessInfo 考核主
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertAssessInfo(AssessInfo assessInfo)
    {
        assessInfo.setCreateTime(DateUtils.getNowDate());
        int result = assessInfoMapper.insertAssessInfo(assessInfo);
        List<Long> items =  assessInfo.getItems();
        items.forEach(item ->{
            AssessAndItem assessAndItem = new AssessAndItem();
            assessAndItem.setItemId(item);
            assessAndItem.setAssessId(assessInfo.getId());
            assessAndItemMapper.insertAssessAndItem(assessAndItem);
        });
        return result;
    }

    /**
     * 修改考核主
     * 
     * @param assessInfo 考核主
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAssessInfo(AssessInfo assessInfo)
    {
        assessInfo.setUpdateTime(DateUtils.getNowDate());
        int result = assessInfoMapper.updateAssessInfo(assessInfo);
        assessAndItemMapper.deleteAssessAndItemByAssessId(assessInfo.getId());
        List<Long> items =  assessInfo.getItems();
        items.forEach(item ->{
            AssessAndItem assessAndItem = new AssessAndItem();
            assessAndItem.setItemId(item);
            assessAndItem.setAssessId(assessInfo.getId());
            assessAndItemMapper.insertAssessAndItem(assessAndItem);
        });
        return result;
    }

    /**
     * 批量删除考核主
     * 
     * @param ids 需要删除的考核主主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAssessInfoByIds(Long[] ids)
    {
        for (Long id : ids) {
            assessAndItemMapper.deleteAssessAndItemByAssessId(id);
        }
        return assessInfoMapper.deleteAssessInfoByIds(ids);
    }

    /**
     * 删除考核主信息
     * 
     * @param id 考核主主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAssessInfoById(Long id)
    {
        assessAndItemMapper.deleteAssessAndItemByAssessId(id);
        return assessInfoMapper.deleteAssessInfoById(id);
    }
}
