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
import com.performanceAppraisal.system.domain.AssessItem;
import com.performanceAppraisal.system.service.IAssessItemService;
import com.performanceAppraisal.common.utils.poi.ExcelUtil;
import com.performanceAppraisal.common.core.page.TableDataInfo;

/**
 * 考核项Controller
 * 
 * @author wind
 * @date 2022-04-17
 */
@RestController
@RequestMapping("/assess/item")
public class AssessItemController extends BaseController
{
    @Autowired
    private IAssessItemService assessItemService;

    /**
     * 查询考核项列表
     */
    @GetMapping("/list")
    public TableDataInfo list(AssessItem assessItem)
    {
            startPage();
        List<AssessItem> list = assessItemService.selectAssessItemList(assessItem);
        return getDataTable(list);
    }

    /**
     * 导出考核项列表
     */
    @PreAuthorize("@ss.hasPermi('assess:item:export')")
    @Log(title = "考核项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AssessItem assessItem)
    {
        List<AssessItem> list = assessItemService.selectAssessItemList(assessItem);
        ExcelUtil<AssessItem> util = new ExcelUtil<AssessItem>(AssessItem.class);
        util.exportExcel(response, list, "考核项数据");
    }

    /**
     * 获取考核项详细信息
     */
    @PreAuthorize("@ss.hasPermi('assess:item:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(assessItemService.selectAssessItemById(id));
    }

    /**
     * 新增考核项
     */
    @PreAuthorize("@ss.hasPermi('assess:item:add')")
    @Log(title = "考核项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AssessItem assessItem)
    {
        return toAjax(assessItemService.insertAssessItem(assessItem));
    }

    /**
     * 修改考核项
     */
    @PreAuthorize("@ss.hasPermi('assess:item:edit')")
    @Log(title = "考核项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AssessItem assessItem)
    {
        return toAjax(assessItemService.updateAssessItem(assessItem));
    }

    /**
     * 删除考核项
     */
    @PreAuthorize("@ss.hasPermi('assess:item:remove')")
    @Log(title = "考核项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(assessItemService.deleteAssessItemByIds(ids));
    }
}
