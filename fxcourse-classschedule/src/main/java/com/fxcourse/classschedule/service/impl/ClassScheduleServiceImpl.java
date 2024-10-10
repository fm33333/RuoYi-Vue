package com.fxcourse.classschedule.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.fxcourse.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fxcourse.classschedule.mapper.ClassScheduleMapper;
import com.fxcourse.classschedule.domain.ClassSchedule;
import com.fxcourse.classschedule.service.IClassScheduleService;

/**
 * fx课表Service业务层处理
 * 
 * @author fming
 * @date 2024-10-09
 */
@Service
public class ClassScheduleServiceImpl implements IClassScheduleService 
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClassScheduleMapper classScheduleMapper;

    /**
     * 查询fx课表
     * 
     * @param id fx课表主键
     * @return fx课表
     */
    @Override
    public ClassSchedule selectClassScheduleById(Integer id)
    {
        return classScheduleMapper.selectClassScheduleById(id);
    }

    /**
     * 查询fx课表列表
     * 
     * @param classSchedule fx课表
     * @return fx课表
     */
    @Override
    public List<ClassSchedule> selectClassScheduleList(ClassSchedule classSchedule)
    {
        return classScheduleMapper.selectClassScheduleList(classSchedule);
    }

    /**
     * 新增fx课表
     * 
     * @param classSchedule fx课表
     * @return 结果
     */
    @Override
    public int insertClassSchedule(ClassSchedule classSchedule)
    {
        classSchedule.setCreateTime(DateUtils.getNowDate());
        return classScheduleMapper.insertClassSchedule(classSchedule);
    }

    /**
     * 修改fx课表
     * 
     * @param classSchedule fx课表
     * @return 结果
     */
    @Override
    public int updateClassSchedule(ClassSchedule classSchedule)
    {
        return classScheduleMapper.updateClassSchedule(classSchedule);
    }

    /**
     * 批量删除fx课表
     * 
     * @param ids 需要删除的fx课表主键
     * @return 结果
     */
    @Override
    public int deleteClassScheduleByIds(Integer[] ids)
    {
        return classScheduleMapper.deleteClassScheduleByIds(ids);
    }

    /**
     * 删除fx课表信息
     * 
     * @param id fx课表主键
     * @return 结果
     */
    @Override
    public int deleteClassScheduleById(Integer id)
    {
        return classScheduleMapper.deleteClassScheduleById(id);
    }

    /**
     * 获取可换课列表
     *   要求：1、找出可以和哪节课换，本人可以上哪节
     *        2、进阶逻辑：同一个班不能连上几节课/同一个班不能两天没有同一节课（具体需要再确认）
     * TODO：当前逻辑还不够完善，没有对单双周的逻辑处理；并且没有判断是否有连上多节课或者两天没有某节课的情况
     * @param id 待换课的课表id
     * @return
     */
    @Override
    public List<ClassSchedule> getChangeScheduleList(Integer id) {
        // 查出待换的课信息
        ClassSchedule waitToChange = classScheduleMapper.getById(id);
        logger.info(waitToChange.toString());
        Integer singleDoubleWeek = waitToChange.getSingleDoubleWeek();  // 单双周（0否，1单周，2双周）
        String classId = waitToChange.getClassId();     // 班级
        String classHourId = waitToChange.getClassHourId(); // 课时（周几第几节）
        String teacherId = waitToChange.getTeacherId(); // 老师
        String subjectId = waitToChange.getSubjectId(); // 科目

        // 查出该节课时的所有课表信息（后面用于判断被换课的老师在这一节课时中是否有课，有课则换不了）
        List<ClassSchedule> currentClassHourList = classScheduleMapper.getByClassHourId(classHourId);

        // 查出该班级的所有课表信息（换课肯定是本班内换吧）
        List<ClassSchedule> allClassScheduleList = classScheduleMapper.getByClassId(classId);
        // 遍历该班级课表，查找可换的课
        List<ClassSchedule> resultList = new ArrayList<>();   // 可换的课列表
        Iterator<ClassSchedule> iterator = allClassScheduleList.iterator();
        while (iterator.hasNext()) {
            ClassSchedule next = iterator.next();
            // 第7节课没课，跳过
            if(next.getTeacherId() == null || next.getSubjectId() == null) {
                continue;
            }
            // 同一老师的课，跳过
            if(waitToChange.getSubjectId().equals(next.getSubjectId())) {
                continue;
            }
            // 获取被换课的课时的所有课表信息，待换课老师在该课时不能有课
            List<ClassSchedule> nextClassHourList = classScheduleMapper.getByClassHourId(next.getClassHourId());
            if(isExistClassSchedule(teacherId, nextClassHourList)) {
                continue;
            }
            // 待换课的课时的所有课中，被换课老师也不能有课
            if(isExistClassSchedule(next.getTeacherId(), currentClassHourList)) {
                continue;
            }

            resultList.add(next);
        }
        return resultList;
    }

    /**
     * 判断老师teacher是否存在于课表csList中（即判断老师在该节课是否有其他班的课要上）
     * @param teacher
     * @param csList
     * @return
     */
    public boolean isExistClassSchedule(String teacher, List<ClassSchedule> csList) {
        Iterator<ClassSchedule> iterator = csList.iterator();
        while (iterator.hasNext()) {
            ClassSchedule next = iterator.next();
            if (teacher.equals(next.getTeacherId())) {
                return true;
            }
        }
        return false;
    }
}
