package com.fxcourse.classschedule.service.impl;

import java.util.List;
import com.fxcourse.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fxcourse.classschedule.mapper.ClassScheduleMapper;
import com.fxcourse.classschedule.domain.ClassSchedule;
import com.fxcourse.classschedule.service.IClassScheduleService;

/**
 * 课表Service业务层处理
 * 
 * @author fming
 * @date 2024-10-07
 */
@Service
public class ClassScheduleServiceImpl implements IClassScheduleService 
{
    @Autowired
    private ClassScheduleMapper classScheduleMapper;

    /**
     * 查询课表
     * 
     * @param id 课表主键
     * @return 课表
     */
    @Override
    public ClassSchedule selectClassScheduleById(Integer id)
    {
        return classScheduleMapper.selectClassScheduleById(id);
    }

    /**
     * 查询课表列表
     * 
     * @param classSchedule 课表
     * @return 课表
     */
    @Override
    public List<ClassSchedule> selectClassScheduleList(ClassSchedule classSchedule)
    {
        return classScheduleMapper.selectClassScheduleList(classSchedule);
    }

    /**
     * 新增课表
     * 
     * @param classSchedule 课表
     * @return 结果
     */
    @Override
    public int insertClassSchedule(ClassSchedule classSchedule)
    {
        classSchedule.setCreateTime(DateUtils.getNowDate());
        return classScheduleMapper.insertClassSchedule(classSchedule);
    }

    /**
     * 修改课表
     * 
     * @param classSchedule 课表
     * @return 结果
     */
    @Override
    public int updateClassSchedule(ClassSchedule classSchedule)
    {
        return classScheduleMapper.updateClassSchedule(classSchedule);
    }

    /**
     * 批量删除课表
     * 
     * @param ids 需要删除的课表主键
     * @return 结果
     */
    @Override
    public int deleteClassScheduleByIds(Integer[] ids)
    {
        return classScheduleMapper.deleteClassScheduleByIds(ids);
    }

    /**
     * 删除课表信息
     * 
     * @param id 课表主键
     * @return 结果
     */
    @Override
    public int deleteClassScheduleById(Integer id)
    {
        return classScheduleMapper.deleteClassScheduleById(id);
    }
}
