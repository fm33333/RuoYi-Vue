package com.fxcourse.classschedule.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fxcourse.common.annotation.Excel;
import com.fxcourse.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 课表对象 class_schedule
 * 
 * @author fming
 * @date 2024-10-07
 */
public class ClassSchedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 课程id */
    @Excel(name = "课程id")
    private Integer id;

    /** 是否单双周课程（0：否，1：单周，2：双周） */
    @Excel(name = "单双周课程", readConverterExp = "0=否,1=单周,2=双周")
    private Integer singleDoubleWeek;

    /** 班级id */
    @Excel(name = "班级id")
    private String classId;

    /** 课时id */
    @Excel(name = "课时id")
    private String classHourId;

    /** 老师id */
    @Excel(name = "老师id")
    private String teacherId;

    /** 科目id */
    @Excel(name = "科目id")
    private String subjectId;

    /** 修改时间 */
    private Date modifyTime;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setSingleDoubleWeek(Integer singleDoubleWeek) 
    {
        this.singleDoubleWeek = singleDoubleWeek;
    }

    public Integer getSingleDoubleWeek() 
    {
        return singleDoubleWeek;
    }
    public void setClassId(String classId) 
    {
        this.classId = classId;
    }

    public String getClassId() 
    {
        return classId;
    }
    public void setClassHourId(String classHourId) 
    {
        this.classHourId = classHourId;
    }

    public String getClassHourId() 
    {
        return classHourId;
    }
    public void setTeacherId(String teacherId) 
    {
        this.teacherId = teacherId;
    }

    public String getTeacherId() 
    {
        return teacherId;
    }
    public void setSubjectId(String subjectId) 
    {
        this.subjectId = subjectId;
    }

    public String getSubjectId() 
    {
        return subjectId;
    }
    public void setModifyTime(Date modifyTime) 
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() 
    {
        return modifyTime;
    }
    public void setDeleted(Integer deleted) 
    {
        this.deleted = deleted;
    }

    public Integer getDeleted() 
    {
        return deleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("singleDoubleWeek", getSingleDoubleWeek())
            .append("classId", getClassId())
            .append("classHourId", getClassHourId())
            .append("teacherId", getTeacherId())
            .append("subjectId", getSubjectId())
            .append("modifyTime", getModifyTime())
            .append("createTime", getCreateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
