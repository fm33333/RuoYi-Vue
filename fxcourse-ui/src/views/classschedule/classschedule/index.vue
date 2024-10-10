<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="id" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单双周课程" label-width="120px" prop="singleDoubleWeek">
        <el-select v-model="queryParams.singleDoubleWeek" placeholder="请选择单双周课程" clearable>
          <el-option
            v-for="dict in course_week_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="班级" prop="classId">
        <!-- <el-select v-model="queryParams.classId" placeholder="请选择班级" clearable>
          <el-option
            v-for="dict in course_class"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select> -->
        <el-input
          v-model="queryParams.classId"
          placeholder="请输入班级"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课时" prop="classHourId">
        <el-input
          v-model="queryParams.classHourId"
          placeholder="请输入课时"
          clearable
          @keyup.enter="handleQuery"
        />
        <!-- <el-select v-model="queryParams.classHourId" placeholder="请选择课时" clearable>
          <el-option
            v-for="courseTime in courseTimeList"
            :key="courseTime.name"
            :label="courseTime.name"
            :value="courseTime.value"
          />
        </el-select> -->
      </el-form-item>
      <el-form-item label="老师" prop="teacherId">
        <el-input
          v-model="queryParams.teacherId"
          placeholder="请输入老师"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="科目" prop="subjectId">
        <el-input
          v-model="queryParams.subjectId"
          placeholder="请输入科目"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        <el-button type="primary" icon="Search" color="green" @click="handleQueryChange">换课</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['classschedule:classschedule:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['classschedule:classschedule:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['classschedule:classschedule:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['classschedule:classschedule:export']"
        >导出</el-button>
      </el-col>
      <!-- TODO: 替换为换课的接口 -->
      <!-- <el-form-item>
        <el-button 
          type="primary" 
          icon="Search" 
          plain
          :disabled="single"
          @click="handleQueryChange"
        >换课</el-button>
      </el-form-item> -->
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="classscheduleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column v-model="queryParams.id" label="课程id" align="center" prop="id" />
      <el-table-column label="单双周课程" align="center" prop="singleDoubleWeek">
        <template #default="scope">
          <dict-tag :options="course_week_type" :value="scope.row.singleDoubleWeek"/>
        </template>
      </el-table-column>
      <el-table-column label="班级" align="center" prop="classId" />
      <el-table-column label="课时" align="center" prop="classHourId" />
      <el-table-column label="老师" align="center" prop="teacherId" />
      <el-table-column label="科目" align="center" prop="subjectId" />
      <el-table-column label="修改时间" align="center" prop="modifyTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.modifyTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <!-- <el-button link type="primary" icon="Search" @click="handleUpdate(scope.row)" v-hasPermi="['classschedule:classschedule:change']">换课</el-button> -->
          <!-- <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['classschedule:classschedule:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['classschedule:classschedule:remove']">删除</el-button> -->
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改fx课表对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="classscheduleRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="单双周课程" prop="singleDoubleWeek">
          <el-select v-model="form.singleDoubleWeek" placeholder="请选择单双周课程">
            <el-option
              v-for="dict in course_week_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="classId">
          <el-select v-model="form.classId" placeholder="请选择班级">
            <el-option
              v-for="dict in course_class"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课时" prop="classHourId">
          <el-input v-model="form.classHourId" placeholder="请输入课时" />
        </el-form-item>
        <el-form-item label="老师" prop="teacherId">
          <el-input v-model="form.teacherId" placeholder="请输入老师" />
        </el-form-item>
        <el-form-item label="科目" prop="subjectId">
          <el-input v-model="form.subjectId" placeholder="请输入科目" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Classschedule">
// TODO: 导入接口
import { 
  listClassschedule, listChangeClassschedule,
  getClassschedule, delClassschedule, addClassschedule, updateClassschedule
} from "@/api/classschedule/classschedule";

const { proxy } = getCurrentInstance();
const { course_class, course_week_type } = proxy.useDict('course_class', 'course_week_type');

const classscheduleList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    singleDoubleWeek: null,
    classId: null,
    classHourId: null,
    teacherId: null,
    subjectId: null,
    deleted: null
  },
  rules: {
    singleDoubleWeek: [
      { required: true, message: "单双周课程不能为空", trigger: "change" }
    ],
    classId: [
      { required: true, message: "班级不能为空", trigger: "change" }
    ],
    classHourId: [
      { required: true, message: "课时不能为空", trigger: "blur" }
    ],
    teacherId: [
      { required: true, message: "老师不能为空", trigger: "blur" }
    ],
    subjectId: [
      { required: true, message: "科目不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

// const courseTimeList=ref([
//   { name: '周一第1节', value: 1 },
//   { name: '周一第2节', value: 2 },
//   { name: '周一第3节', value: 3 },
//   { name: '周一第4节', value: 4 },
//   { name: '周一第5节', value: 5 },
//   { name: '周一第6节', value: 6 },
//   { name: '周一第7节', value: 7 }
// ]);

/** 查询fx课表列表 */
function getList() {
  loading.value = true;
  listClassschedule(queryParams.value).then(response => {
    classscheduleList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    singleDoubleWeek: null,
    classId: null,
    classHourId: null,
    teacherId: null,
    subjectId: null,
    modifyTime: null,
    createTime: null,
    deleted: null
  };
  proxy.resetForm("classscheduleRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 查找可换课列表操作 */
function handleQueryChange() {
  queryParams.value.pageNum = 1;
  loading.value = true;
  // TODO: 替换接口
  listChangeClassschedule(queryParams.value).then(response => {
    classscheduleList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加fx课表";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getClassschedule(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改fx课表";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["classscheduleRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateClassschedule(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addClassschedule(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除fx课表编号为"' + _ids + '"的数据项？').then(function() {
    return delClassschedule(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('classschedule/classschedule/export', {
    ...queryParams.value
  }, `classschedule_${new Date().getTime()}.xlsx`)
}

getList();
</script>
