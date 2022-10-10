package com.performanceAppraisal.system.service.impl;

import java.util.List;

import com.performanceAppraisal.common.exception.ServiceException;
import com.performanceAppraisal.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.performanceAppraisal.system.mapper.AssessIssueMapper;
import com.performanceAppraisal.system.domain.AssessIssue;
import com.performanceAppraisal.system.service.IAssessIssueService;
import org.springframework.util.CollectionUtils;

/**
 * 考核发布Service业务层处理
 * 
 * @author wind
 * @date 2022-04-17
 */
@Service
public class AssessIssueServiceImpl implements IAssessIssueService 
{
    @Autowired
    private AssessIssueMapper assessIssueMapper;

    /**
     * 查询考核发布
     * 
     * @param id 考核发布主键
     * @return 考核发布
     */
    @Override
    public AssessIssue selectAssessIssueById(Long id)
    {
        return assessIssueMapper.selectAssessIssueById(id);
    }

    /**
     * 查询考核发布列表
     * 
     * @param assessIssue 考核发布
     * @return 考核发布
     */
    @Override
    public List<AssessIssue> selectAssessIssueList(AssessIssue assessIssue)
    {
        return assessIssueMapper.selectAssessIssueList(assessIssue);
    }

    /**
     * 新增考核发布
     * 
     * @param assessIssue 考核发布
     * @return 结果
     */
    @Override
    public int insertAssessIssue(AssessIssue assessIssue)
    {
        assessIssue.setCreateTime(DateUtils.getNowDate());
        AssessIssue query = new AssessIssue();
        query.setAssessId(assessIssue.getAssessId());
        query.setDeptId(assessIssue.getDeptId());
        List ss = this.selectAssessIssueList(query);
        if(!CollectionUtils.isEmpty(ss)){
            throw new ServiceException("此考核已在该部门下发布，请勿重复发布");
        }
        return assessIssueMapper.insertAssessIssue(assessIssue);
    }

    /**
     * 修改考核发布
     * 
     * @param assessIssue 考核发布
     * @return 结果
     */
    @Override
    public int updateAssessIssue(AssessIssue assessIssue)
    {
        assessIssue.setUpdateTime(DateUtils.getNowDate());
        return assessIssueMapper.updateAssessIssue(assessIssue);
    }

    /**
     * 批量删除考核发布
     * 
     * @param ids 需要删除的考核发布主键
     * @return 结果
     */
    @Override
    public int deleteAssessIssueByIds(Long[] ids)
    {
        return assessIssueMapper.deleteAssessIssueByIds(ids);
    }

    /**
     * 删除考核发布信息
     * 
     * @param id 考核发布主键
     * @return 结果
     */
    @Override
    public int deleteAssessIssueById(Long id)
    {
        return assessIssueMapper.deleteAssessIssueById(id);
    }
}
