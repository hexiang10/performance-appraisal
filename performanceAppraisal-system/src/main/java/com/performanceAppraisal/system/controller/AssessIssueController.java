package com.performanceAppraisal.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.performanceAppraisal.common.annotation.Log;
import com.performanceAppraisal.common.core.controller.BaseController;
import com.performanceAppraisal.common.core.domain.AjaxResult;
import com.performanceAppraisal.common.enums.BusinessType;
import com.performanceAppraisal.system.domain.AssessIssue;
import com.performanceAppraisal.system.service.IAssessIssueService;
import com.performanceAppraisal.common.utils.poi.ExcelUtil;
import com.performanceAppraisal.common.core.page.TableDataInfo;

/**
 * 考核发布Controller
 * 
 * @author wind
 * @date 2022-04-17
 */
@RestController
@RequestMapping("/assess/issue")
public class AssessIssueController extends BaseController
{
    @Autowired
    private IAssessIssueService assessIssueService;

    /**
     * 查询考核发布列表
     */
    @PreAuthorize("@ss.hasPermi('assess:issue:list')")
    @GetMapping("/list")
    public TableDataInfo list(AssessIssue assessIssue)
    {
        startPage();
        List<AssessIssue> list = assessIssueService.selectAssessIssueList(assessIssue);
        return getDataTable(list);
    }

    /**
     * 导出考核发布列表
     */
    @PreAuthorize("@ss.hasPermi('assess:issue:export')")
    @Log(title = "考核发布", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AssessIssue assessIssue)
    {
        List<AssessIssue> list = assessIssueService.selectAssessIssueList(assessIssue);
        ExcelUtil<AssessIssue> util = new ExcelUtil<AssessIssue>(AssessIssue.class);
        util.exportExcel(response, list, "考核发布数据");
    }

    /**
     * 获取考核发布详细信息
     */
    @PreAuthorize("@ss.hasPermi('assess:issue:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(assessIssueService.selectAssessIssueById(id));
    }

    /**
     * 新增考核发布
     */
    @PreAuthorize("@ss.hasPermi('assess:issue:add')")
    @Log(title = "考核发布", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AssessIssue assessIssue)
    {
        assessIssue.setCreateBy(getUsername());
        return toAjax(assessIssueService.insertAssessIssue(assessIssue));
    }

    /**
     * 修改考核发布
     */
    @PreAuthorize("@ss.hasPermi('assess:issue:edit')")
    @Log(title = "考核发布", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AssessIssue assessIssue)
    {
        return toAjax(assessIssueService.updateAssessIssue(assessIssue));
    }

    /**
     * 考核发布
     * @param id
     * @param status
     * @return
     */
    @PreAuthorize("@ss.hasPermi('assess:issue:edit')")
    @Log(title = "考核发布", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public AjaxResult changeStatus( Long id,String status)
    {
        AssessIssue assessIssue = assessIssueService.selectAssessIssueById(id);
        assessIssue.setStatus(status);
        return toAjax(assessIssueService.updateAssessIssue(assessIssue));
    }

    /**
     * 删除考核发布
     */
    @PreAuthorize("@ss.hasPermi('assess:issue:remove')")
    @Log(title = "考核发布", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(assessIssueService.deleteAssessIssueByIds(ids));
    }
}
