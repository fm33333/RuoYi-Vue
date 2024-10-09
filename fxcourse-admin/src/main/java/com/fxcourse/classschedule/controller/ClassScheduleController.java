package com.fxcourse.classschedule.controller;

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
import com.fxcourse.common.annotation.Log;
import com.fxcourse.common.core.controller.BaseController;
import com.fxcourse.common.core.domain.AjaxResult;
import com.fxcourse.common.enums.BusinessType;
import com.fxcourse.classschedule.domain.ClassSchedule;
import com.fxcourse.classschedule.service.IClassScheduleService;
import com.fxcourse.common.utils.poi.ExcelUtil;
import com.fxcourse.common.core.page.TableDataInfo;

/**
 * 课表Controller
 * 
 * @author fming
 * @date 2024-10-07
 */
@RestController
@RequestMapping("/classschedule/classschedule")
public class ClassScheduleController extends BaseController
{
    @Autowired
    private IClassScheduleService classScheduleService;

    /**
     * 查询课表列表
     */
    @PreAuthorize("@ss.hasPermi('classschedule:classschedule:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClassSchedule classSchedule)
    {
        startPage();
        List<ClassSchedule> list = classScheduleService.selectClassScheduleList(classSchedule);
        return getDataTable(list);
    }

    /**
     * 导出课表列表
     */
    @PreAuthorize("@ss.hasPermi('classschedule:classschedule:export')")
    @Log(title = "课表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClassSchedule classSchedule)
    {
        List<ClassSchedule> list = classScheduleService.selectClassScheduleList(classSchedule);
        ExcelUtil<ClassSchedule> util = new ExcelUtil<ClassSchedule>(ClassSchedule.class);
        util.exportExcel(response, list, "课表数据");
    }

    /**
     * 获取课表详细信息
     */
    @PreAuthorize("@ss.hasPermi('classschedule:classschedule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(classScheduleService.selectClassScheduleById(id));
    }

    /**
     * 新增课表
     */
    @PreAuthorize("@ss.hasPermi('classschedule:classschedule:add')")
    @Log(title = "课表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClassSchedule classSchedule)
    {
        return toAjax(classScheduleService.insertClassSchedule(classSchedule));
    }

    /**
     * 修改课表
     */
    @PreAuthorize("@ss.hasPermi('classschedule:classschedule:edit')")
    @Log(title = "课表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ClassSchedule classSchedule)
    {
        return toAjax(classScheduleService.updateClassSchedule(classSchedule));
    }

    /**
     * 删除课表
     */
    @PreAuthorize("@ss.hasPermi('classschedule:classschedule:remove')")
    @Log(title = "课表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(classScheduleService.deleteClassScheduleByIds(ids));
    }
}
