package com.performanceAppraisal.system.controller;

import com.performanceAppraisal.common.annotation.Log;
import com.performanceAppraisal.common.core.controller.BaseController;
import com.performanceAppraisal.common.core.domain.AjaxResult;
import com.performanceAppraisal.common.core.page.TableDataInfo;
import com.performanceAppraisal.common.enums.BusinessType;
import com.performanceAppraisal.common.utils.poi.ExcelUtil;
import com.performanceAppraisal.system.domain.AssessAndItem;
import com.performanceAppraisal.system.domain.AssessInfo;
import com.performanceAppraisal.system.domain.AssessItem;
import com.performanceAppraisal.system.service.IAssessAndItemService;
import com.performanceAppraisal.system.service.IAssessInfoService;
import com.performanceAppraisal.system.service.IAssessItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 考核主Controller
 * 
 * @author wind
 * @date 2022-04-17
 */
@RestController
@RequestMapping("/assess/info")
public class AssessInfoController extends BaseController
{
    @Autowired
    private IAssessInfoService assessInfoService;

    @Autowired
    private IAssessAndItemService assessAndItemService;

    @Autowired
    private IAssessItemService assessItemService;

    /**
     * 查询考核主列表
     */
    @PreAuthorize("@ss.hasPermi('assess:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(AssessInfo assessInfo)
    {
        startPage();
        List<AssessInfo> list = assessInfoService.selectAssessInfoList(assessInfo);
        return getDataTable(list);
    }

    /**
     * 导出考核主列表
     */
    @PreAuthorize("@ss.hasPermi('assess:info:export')")
    @Log(title = "考核主", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AssessInfo assessInfo)
    {

        List<AssessInfo> list = assessInfoService.selectAssessInfoList(assessInfo);
        ExcelUtil<AssessInfo> util = new ExcelUtil<AssessInfo>(AssessInfo.class);
        util.exportExcel(response, list, "考核主数据");
    }

    /**
     * 获取考核主详细信息
     */
    @PreAuthorize("@ss.hasPermi('assess:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(assessInfoService.selectAssessInfoById(id));
    }

    @GetMapping(value = "/getInfoAndItem/{id}")
    public AjaxResult getInfoAndItem(@PathVariable("id") Long id)
    {
        AssessInfo assessInfo = assessInfoService.selectAssessInfoById(id);
        AssessAndItem queryItems = new AssessAndItem();
        queryItems.setAssessId(assessInfo.getId());
        List<AssessAndItem> andItemList = assessAndItemService.selectAssessAndItemList(queryItems);
        if(!CollectionUtils.isEmpty(andItemList)){
            assessInfo.setItems(andItemList.stream().map(AssessAndItem ::getItemId).collect(Collectors.toList()));
            List<AssessItem> assessItemList = assessItemService.selectAssessItemByIds(assessInfo.getItems());
            assessInfo.setAssessItems(assessItemList);
        }

        return AjaxResult.success(assessInfo);
    }

    /**
     * 新增考核主
     */
    @PreAuthorize("@ss.hasPermi('assess:info:add')")
    @Log(title = "考核主", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AssessInfo assessInfo)
    {
        return toAjax(assessInfoService.insertAssessInfo(assessInfo));
    }

    /**
     * 修改考核主
     */
    @PreAuthorize("@ss.hasPermi('assess:info:edit')")
    @Log(title = "考核主", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AssessInfo assessInfo)
    {
        return toAjax(assessInfoService.updateAssessInfo(assessInfo));
    }

    /**
     * 删除考核主
     */
    @PreAuthorize("@ss.hasPermi('assess:info:remove')")
    @Log(title = "考核主", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(assessInfoService.deleteAssessInfoByIds(ids));
    }
}
