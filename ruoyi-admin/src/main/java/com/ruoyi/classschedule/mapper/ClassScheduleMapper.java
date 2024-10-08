package com.ruoyi.classschedule.mapper;

import java.util.List;
import com.ruoyi.classschedule.domain.ClassSchedule;

/**
 * 课表Mapper接口
 * 
 * @author fming
 * @date 2024-10-07
 */
public interface ClassScheduleMapper 
{
    /**
     * 查询课表
     * 
     * @param id 课表主键
     * @return 课表
     */
    public ClassSchedule selectClassScheduleById(Integer id);

    /**
     * 查询课表列表
     * 
     * @param classSchedule 课表
     * @return 课表集合
     */
    public List<ClassSchedule> selectClassScheduleList(ClassSchedule classSchedule);

    /**
     * 新增课表
     * 
     * @param classSchedule 课表
     * @return 结果
     */
    public int insertClassSchedule(ClassSchedule classSchedule);

    /**
     * 修改课表
     * 
     * @param classSchedule 课表
     * @return 结果
     */
    public int updateClassSchedule(ClassSchedule classSchedule);

    /**
     * 删除课表
     * 
     * @param id 课表主键
     * @return 结果
     */
    public int deleteClassScheduleById(Integer id);

    /**
     * 批量删除课表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClassScheduleByIds(Integer[] ids);
}
