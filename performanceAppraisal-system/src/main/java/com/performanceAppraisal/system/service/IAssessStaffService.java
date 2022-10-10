package com.performanceAppraisal.system.service;

import java.util.List;

import com.performanceAppraisal.common.core.domain.entity.SysUser;
import com.performanceAppraisal.system.domain.AssessStaff;

/**
 * 绩效自评Service接口
 * 
 * @author wind
 * @date 2022-04-20
 */
public interface IAssessStaffService 
{
    /**
     * 查询绩效自评
     * 
     * @param id 绩效自评主键
     * @return 绩效自评
     */
    public AssessStaff selectAssessStaffById(Long id);

    /**
     * 查询绩效自评列表
     * 
     * @param assessStaff 绩效自评
     * @return 绩效自评集合
     */
    public List<AssessStaff> selectAssessStaffList(AssessStaff assessStaff, SysUser createUser);

    /**
     * 新增绩效自评
     * 
     * @param assessStaff 绩效自评
     * @return 结果
     */
    public int insertAssessStaff(AssessStaff assessStaff);

    /**
     * 修改绩效自评
     * 
     * @param assessStaff 绩效自评
     * @return 结果
     */
    public int updateAssessStaff(AssessStaff assessStaff);

    /**
     * 批量删除绩效自评
     * 
     * @param ids 需要删除的绩效自评主键集合
     * @return 结果
     */
    public int deleteAssessStaffByIds(Long[] ids);

    /**
     * 删除绩效自评信息
     * 
     * @param id 绩效自评主键
     * @return 结果
     */
    public int deleteAssessStaffById(Long id);
}
