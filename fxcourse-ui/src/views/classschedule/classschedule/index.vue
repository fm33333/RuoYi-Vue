<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程id" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入课程id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单双周课程" prop="singleDoubleWeek">
        <el-select v-model="queryParams.singleDoubleWeek" placeholder="请选择单双周课程" clearable>
          <el-option
            v-for="dict in course_week_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="班级id" prop="classId">
        <el-select v-model="queryParams.classId" placeholder="请选择班级id" clearable>
          <el-option
            v-for="dict in course_class"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课时id" prop="classHourId">
        <el-input
          v-model="queryParams.classHourId"
          placeholder="请输入课时id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="老师id" prop="teacherId">
        <el-input
          v-model="queryParams.teacherId"
          placeholder="请输入老师id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="科目id" prop="subjectId">
        <el-input
          v-model="queryParams.subjectId"
          placeholder="请输入科目id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否删除" prop="deleted">
        <el-input
          v-model="queryParams.deleted"
          placeholder="请输入是否删除"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">换课</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
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
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['classschedule:classschedule:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="classscheduleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="课程id" align="center" prop="id" />
      <el-table-column label="单双周课程" align="center" prop="singleDoubleWeek">
        <template #default="scope">
          <dict-tag :options="course_week_type" :value="scope.row.singleDoubleWeek"/>
        </template>
      </el-table-column>
      <el-table-column label="班级id" align="center" prop="classId">
        <template #default="scope">
          <dict-tag :options="course_class" :value="scope.row.classId"/>
        </template>
      </el-table-column>
      <el-table-column label="课时id" align="center" prop="classHourId" />
      <el-table-column label="老师id" align="center" prop="teacherId" />
      <el-table-column label="科目id" align="center" prop="subjectId" />
      <el-table-column label="是否删除" align="center" prop="deleted" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['classschedule:classschedule:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['classschedule:classschedule:remove']">删除</el-button>
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

    <!-- 添加或修改课表对话框 -->
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
        <el-form-item label="班级id" prop="classId">
          <el-select v-model="form.classId" placeholder="请选择班级id">
            <el-option
              v-for="dict in course_class"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课时id" prop="classHourId">
          <el-input v-model="form.classHourId" placeholder="请输入课时id" />
        </el-form-item>
        <el-form-item label="老师id" prop="teacherId">
          <el-input v-model="form.teacherId" placeholder="请输入老师id" />
        </el-form-item>
        <el-form-item label="科目id" prop="subjectId">
          <el-input v-model="form.subjectId" placeholder="请输入科目id" />
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
import { listClassschedule, getClassschedule, delClassschedule, addClassschedule, updateClassschedule } from "@/api/classschedule/classschedule";

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
    id: null,
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
      { required: true, message: "班级id不能为空", trigger: "change" }
    ],
    classHourId: [
      { required: true, message: "课时id不能为空", trigger: "blur" }
    ],
    teacherId: [
      { required: true, message: "老师id不能为空", trigger: "blur" }
    ],
    subjectId: [
      { required: true, message: "科目id不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询课表列表 */
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
  title.value = "添加课表";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getClassschedule(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改课表";
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
  proxy.$modal.confirm('是否确认删除课表编号为"' + _ids + '"的数据项？').then(function() {
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
