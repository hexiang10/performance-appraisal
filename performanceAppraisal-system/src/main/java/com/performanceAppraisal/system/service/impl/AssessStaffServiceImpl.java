package com.performanceAppraisal.system.service.impl;

import java.util.List;

import com.performanceAppraisal.common.core.domain.entity.SysUser;
import com.performanceAppraisal.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.performanceAppraisal.system.mapper.AssessStaffMapper;
import com.performanceAppraisal.system.domain.AssessStaff;
import com.performanceAppraisal.system.service.IAssessStaffService;

import javax.annotation.Resource;

/**
 * 绩效自评Service业务层处理
 * 
 * @author wind
 * @date 2022-04-20
 */
@Service
public class AssessStaffServiceImpl implements IAssessStaffService 
{
    @Resource
    private AssessStaffMapper assessStaffMapper;

    /**
     * 查询绩效自评
     * 
     * @param id 绩效自评主键
     * @return 绩效自评
     */
    @Override
    public AssessStaff selectAssessStaffById(Long id)
    {
        return assessStaffMapper.selectAssessStaffById(id);
    }

    /**
     * 查询绩效自评列表
     * 
     * @param assessStaff 绩效自评
     * @return 绩效自评
     */
    @Override
    public List<AssessStaff> selectAssessStaffList(AssessStaff assessStaff, SysUser createUser)
    {
        assessStaff.setDeptId(createUser.getDeptId());
        List<AssessStaff> result = assessStaffMapper.selectAssessStaffList(assessStaff);
        result.forEach(item ->{
            if(item.getUserId().equals(createUser.getUserId())){
                item.setIsSelf("1");
            }
        });
        return result;
    }

    /**
     * 新增绩效自评
     * 
     * @param assessStaff 绩效自评
     * @return 结果
     */
    @Override
    public int insertAssessStaff(AssessStaff assessStaff)
    {
        assessStaff.setCreateTime(DateUtils.getNowDate());
        return assessStaffMapper.insertAssessStaff(assessStaff);
    }

    /**
     * 修改绩效自评
     * 
     * @param assessStaff 绩效自评
     * @return 结果
     */
    @Override
    public int updateAssessStaff(AssessStaff assessStaff)
    {
        assessStaff.setUpdateTime(DateUtils.getNowDate());
        return assessStaffMapper.updateAssessStaff(assessStaff);
    }

    /**
     * 批量删除绩效自评
     * 
     * @param ids 需要删除的绩效自评主键
     * @return 结果
     */
    @Override
    public int deleteAssessStaffByIds(Long[] ids)
    {
        return assessStaffMapper.deleteAssessStaffByIds(ids);
    }

    /**
     * 删除绩效自评信息
     * 
     * @param id 绩效自评主键
     * @return 结果
     */
    @Override
    public int deleteAssessStaffById(Long id)
    {
        return assessStaffMapper.deleteAssessStaffById(id);
    }
}
