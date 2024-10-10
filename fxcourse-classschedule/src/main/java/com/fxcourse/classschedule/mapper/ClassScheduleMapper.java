package com.fxcourse.classschedule.mapper;

import java.util.List;
import com.fxcourse.classschedule.domain.ClassSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * fx课表Mapper接口
 * 
 * @author fming
 * @date 2024-10-09
 */
@Mapper
public interface ClassScheduleMapper 
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
     * 删除fx课表
     * 
     * @param id fx课表主键
     * @return 结果
     */
    public int deleteClassScheduleById(Integer id);

    /**
     * 批量删除fx课表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClassScheduleByIds(Integer[] ids);

    /**
     * 根据id获取课程信息
     *
     * @param id
     * @return
     */
    ClassSchedule getById(@Param("id") Integer id);

    /**
     * 根据课时id获取该课时的所有课（如周一第3节的所有课程）
     * @param classHourId
     * @return
     */
    List<ClassSchedule> getByClassHourId(@Param("classHourId") String classHourId);

    /**
     * 根据班级id获取课表信息
     * @param classId
     * @return
     */
    List<ClassSchedule> getByClassId(@Param("classId") String classId);
}
