package com.performanceAppraisal.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.performanceAppraisal.common.annotation.Log;
import com.performanceAppraisal.common.core.controller.BaseController;
import com.performanceAppraisal.common.core.domain.AjaxResult;
import com.performanceAppraisal.common.core.domain.entity.SysUser;
import com.performanceAppraisal.common.core.page.TableDataInfo;
import com.performanceAppraisal.common.enums.BusinessType;
import com.performanceAppraisal.system.domain.AssessInfo;
import com.performanceAppraisal.system.domain.AssessItem;
import com.performanceAppraisal.system.domain.AssessStaff;
import com.performanceAppraisal.system.domain.vo.FileVo;
import com.performanceAppraisal.system.service.IAssessInfoService;
import com.performanceAppraisal.system.service.IAssessStaffService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 绩效自评Controller
 * 
 * @author wind
 * @date 2022-04-20
 */
@RestController
@RequestMapping("/assess/staff")
public class AssessStaffController extends BaseController
{
    @Autowired
    private IAssessStaffService assessStaffService;
    @Autowired
    private IAssessInfoService assessInfoService;

    /**
     * 查询绩效自评列表
     */
    @GetMapping("/list")
    public TableDataInfo list(AssessStaff assessStaff)
    {
        startPage();
        SysUser createUser = getLoginUser().getUser();
        List<AssessStaff> list = assessStaffService.selectAssessStaffList(assessStaff,createUser);
        return getDataTable(list);
    }


    /**
     * 查询绩效自评列表
     */
    @PreAuthorize("@ss.hasPermi('assess:staff:adminList')")
    @GetMapping("/adminList")
    public TableDataInfo adminList(AssessStaff assessStaff)
    {
        startPage();
        SysUser createUser = getLoginUser().getUser();
        createUser.setDeptId(10L);
        List<AssessStaff> list = assessStaffService.selectAssessStaffList(assessStaff,createUser);
        return getDataTable(list);
    }

    /**
     * 获取绩效自评详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        AssessInfo assessInfo =  new AssessInfo();
        if(null!=id && id!=0L){
            AssessStaff assessStaff =  assessStaffService.selectAssessStaffById(id);
            assessInfo = assessInfoService.selectAssessInfoById(assessStaff.getAssessId());
            if(StringUtils.isNotBlank(assessStaff.getReview())){
                assessInfo.setAssessItems(JSONArray.parseArray(assessStaff.getReview(),AssessItem.class));
            }else{
                assessInfo.setAssessItems(JSONArray.parseArray(assessStaff.getSelf(),AssessItem.class));
            }
            assessInfo.setItems(JSONArray.parseArray(assessStaff.getItemArr(),Long.class));
            assessInfo.setSelfNum(assessStaff.getSelfNum());
            assessInfo.setReviewNum(assessStaff.getReviewNum());
            assessInfo.setTotal(assessStaff.getTotal());
            assessInfo.setFileList(JSONArray.parseArray(assessStaff.getFileId(), FileVo.class));
            assessInfo.setStaffRemark(assessStaff.getRemark());
        }
        return AjaxResult.success(assessInfo);
    }

    /**
     * 新增绩效自评
     */
    @PreAuthorize("@ss.hasPermi('assess:staff:add')")
    @Log(title = "绩效自评", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AssessStaff assessStaff)
    {
        assessStaff.setSelfState("1");
        assessStaff.setReviewState("0");
        assessStaff.setDeclareState("0");
        assessStaff.setSelf(JSON.toJSONString(assessStaff.getAssessItems()));
        assessStaff.setItemArr(JSON.toJSONString(assessStaff.getItems()));
        assessStaff.setFileId(JSON.toJSONString(assessStaff.getFileList()));
        int result = assessStaffService.insertAssessStaff(assessStaff);
        return toAjax(result);
    }


    @Log(title = "绩效申诉", businessType = BusinessType.UPDATE)
    @PostMapping("/appeal")
        public AjaxResult appeal(@RequestBody AssessStaff assessStaff)
    {
        AssessStaff assessStaffY = assessStaffService.selectAssessStaffById(assessStaff.getId());
        assessStaffY.setRemark(assessStaff.getRemark());
        assessStaffY.setDeclareState(assessStaff.getDeclareState());
        if(assessStaff.getSelfNum()!=0){
            assessStaffY.setSelfNum(assessStaff.getSelfNum());
        }
        if(assessStaff.getReviewNum()!=0){
            assessStaffY.setReviewNum(assessStaff.getReviewNum());
        }
        if(assessStaff.getTotal()!=0){
            assessStaffY.setTotal(assessStaff.getTotal());
        }
        int result = assessStaffService.updateAssessStaff(assessStaffY);
        return toAjax(result);
    }

    /**
     * 修改绩效自评
     */
    @PreAuthorize("@ss.hasPermi('assess:staff:edit')")
    @Log(title = "绩效自评", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AssessStaff assessStaff)
    {
        assessStaff.setReviewState("1");
        assessStaff.setDeclareState("0");
        assessStaff.setReview(JSON.toJSONString(assessStaff.getAssessItems()));
        assessStaff.setTotal(assessStaff.getSelfNum()+assessStaff.getReviewNum());
        int result = assessStaffService.updateAssessStaff(assessStaff);
        return toAjax(result);
    }

    /**
     * 删除绩效自评
     */
    @PreAuthorize("@ss.hasPermi('assess:staff:remove')")
    @Log(title = "绩效自评", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(assessStaffService.deleteAssessStaffByIds(ids));
    }
}
