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
import com.performanceAppraisal.system.domain.AssessAndItem;
import com.performanceAppraisal.system.service.IAssessAndItemService;
import com.performanceAppraisal.common.utils.poi.ExcelUtil;
import com.performanceAppraisal.common.core.page.TableDataInfo;

/**
 * 考核项中间Controller
 * 
 * @author wind
 * @date 2022-04-18
 */
@RestController
@RequestMapping("/assess/itemTmp")
public class AssessAndItemController extends BaseController
{
    @Autowired
    private IAssessAndItemService assessAndItemService;

    /**
     * 查询考核项中间列表
     */
    @PreAuthorize("@ss.hasPermi('assess:itemTmp:list')")
    @GetMapping("/list")
    public TableDataInfo list(AssessAndItem assessAndItem)
    {
        startPage();
        List<AssessAndItem> list = assessAndItemService.selectAssessAndItemList(assessAndItem);
        return getDataTable(list);
    }

    /**
     * 导出考核项中间列表
     */
    @PreAuthorize("@ss.hasPermi('assess:itemTmp:export')")
    @Log(title = "考核项中间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AssessAndItem assessAndItem)
    {
        List<AssessAndItem> list = assessAndItemService.selectAssessAndItemList(assessAndItem);
        ExcelUtil<AssessAndItem> util = new ExcelUtil<AssessAndItem>(AssessAndItem.class);
        util.exportExcel(response, list, "考核项中间数据");
    }

    /**
     * 获取考核项中间详细信息
     */
    @PreAuthorize("@ss.hasPermi('assess:itemTmp:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(assessAndItemService.selectAssessAndItemById(id));
    }

    /**
     * 新增考核项中间
     */
    @PreAuthorize("@ss.hasPermi('assess:itemTmp:add')")
    @Log(title = "考核项中间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AssessAndItem assessAndItem)
    {
        return toAjax(assessAndItemService.insertAssessAndItem(assessAndItem));
    }

    /**
     * 修改考核项中间
     */
    @PreAuthorize("@ss.hasPermi('assess:itemTmp:edit')")
    @Log(title = "考核项中间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AssessAndItem assessAndItem)
    {
        return toAjax(assessAndItemService.updateAssessAndItem(assessAndItem));
    }

    /**
     * 删除考核项中间
     */
    @PreAuthorize("@ss.hasPermi('assess:itemTmp:remove')")
    @Log(title = "考核项中间", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(assessAndItemService.deleteAssessAndItemByIds(ids));
    }
}
