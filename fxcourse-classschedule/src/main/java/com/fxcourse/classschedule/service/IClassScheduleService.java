package com.fxcourse.classschedule.service;

import java.util.List;
import com.fxcourse.classschedule.domain.ClassSchedule;

/**
 * fx课表Service接口
 * 
 * @author fming
 * @date 2024-10-09
 */
public interface IClassScheduleService 
{
    /**
     * 查询fx课表
     * 
     * @param id fx课表主键
     * @return fx课表
     */
    public ClassSchedule selectClassScheduleById(Integer id);

    /**
     * 查询fx课表列表
     * 
     * @param classSchedule fx课表
     * @return fx课表集合
     */
    public List<ClassSchedule> selectClassScheduleList(ClassSchedule classSchedule);

    /**
     * 新增fx课表
     * 
     * @param classSchedule fx课表
     * @return 结果
     */
    public int insertClassSchedule(ClassSchedule classSchedule);

    /**
     * 修改fx课表
     * 
     * @param classSchedule fx课表
     * @return 结果
     */
    public int updateClassSchedule(ClassSchedule classSchedule);

    /**
     * 批量删除fx课表
     * 
     * @param ids 需要删除的fx课表主键集合
     * @return 结果
     */
    public int deleteClassScheduleByIds(Integer[] ids);

    /**
     * 删除fx课表信息
     * 
     * @param id fx课表主键
     * @return 结果
     */
    public int deleteClassScheduleById(Integer id);

    /**
     * 查询可换课的课程列表
     * @param id
     * @return
     */
    List<ClassSchedule> getChangeScheduleList(Integer id);
}
