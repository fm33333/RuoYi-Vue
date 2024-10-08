<template>
  <div>
    <el-row :gutter="15">
      <el-form ref="formRef" :model="formData" :rules="rules" size="default" label-width="100px">
        <el-col :span="24">
          <el-row gutter="15">
            <el-col :span="12">
              <el-form-item label="课程名称" prop="field102">
                <el-input v-model="formData.field102" type="text" placeholder="请输入课程名称" clearable
                  :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="24">
          <el-row gutter="15">
            <el-col :span="24">
              <el-form-item label="多行文本" prop="field105">
                <el-input v-model="formData.field105" type="textarea" placeholder="请输入多行文本"
                  :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="24">
          <el-row gutter="15">
            <el-col :span="24">
              <el-form-item label="计数器" prop="field107">
                <el-input-number v-model="formData.field107" placeholder="计数器"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="24">
          <el-row gutter="15">
            <el-col :span="12">
              <el-form-item label="下拉选择" prop="field110">
                <el-select v-model="formData.field110" placeholder="请选择下拉选择" clearable
                  :style="{width: '100%'}">
                  <el-option v-for="(item, index) in field110Options" :key="index" :label="item.label"
                    :value="item.value" :disabled="item.disabled"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="24">
          <el-row gutter="15">
            <el-col :span="12">
              <el-form-item label="级联选择" prop="field111">
                <el-cascader v-model="formData.field111" :options="field111Options" :props="field111Props"
                  :style="{width: '100%'}" placeholder="请选择级联选择" clearable></el-cascader>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="24">
          <el-row gutter="15">
            <el-col :span="12">
              <el-form-item label="时间范围" prop="field113">
                <el-time-picker v-model="formData.field113" is-range format="HH:mm:ss" value-format="HH:mm:ss"
                  :style="{width: '100%'}" start-placeholder="开始时间" end-placeholder="结束时间" range-separator="至"
                  clearable></el-time-picker>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12">
          <el-row gutter="15">
            <el-col :span="12">
              <el-form-item label="上传" prop="field115" required>
                <el-upload ref="field115" :file-list="field115fileList" :action="field115Action"
                  :before-upload="field115BeforeUpload">
                  <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="计数器" prop="field116">
                <el-input-number v-model="formData.field116" placeholder="计数器"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="24">
          <el-form-item>
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
  </div>
</template>
<script setup>
import {
  ElMessage
}
from 'element-plus'
const formRef = ref()
const data = reactive({
  formData: {
    field102: undefined,
    field105: undefined,
    field107: 0,
    field110: undefined,
    field111: [],
    field113: null,
    field115: null,
    field116: undefined,
  },
  rules: {
    field102: [{
      required: true,
      message: '请输入课程名称',
      trigger: 'blur'
    }],
    field105: [{
      required: true,
      message: '请输入多行文本',
      trigger: 'blur'
    }],
    field107: [{
      required: true,
      message: '计数器',
      trigger: 'blur'
    }],
    field110: [{
      required: true,
      message: '请选择下拉选择',
      trigger: 'change'
    }],
    field111: [{
      required: true,
      type: 'array',
      message: '请至少选择一个field111',
      trigger: 'change'
    }],
    field113: [{
      required: true,
      message: '时间范围不能为空',
      trigger: 'change'
    }],
    field116: [{
      required: true,
      message: '计数器',
      trigger: 'blur'
    }],
  }
})
const {
  formData,
  rules
} = toRefs(data)
const field110Options = ref([{
  "label": "选项一",
  "value": 1
}, {
  "label": "选项二",
  "value": 2
}])
const field111Options = ref([])
// 上传请求路径
const field115Action = ref('https://jsonplaceholder.typicode.com/posts/')
// 上传文件列表
const field115fileList = ref([])
// props设置
const field111Props = ref({
  "multiple": false
})

function getField111Options() {
  // TODO 发起请求获取数据
  field111Options.value
}
/**
 * @name: 上传之前的文件判断
 * @description: 上传之前的文件判断，判断文件大小文件类型等
 * @param {*} file
 * @return {*}
 */
function field115BeforeUpload(file) {
  let isRightSize = file.size / 1024 / 1024 < 2
  if (!isRightSize) {
    ElMessage.error('文件大小超过 2MB')
  }
  return isRightSize
}
/**
 * @name: 表单提交
 * @description: 表单提交方法
 * @return {*}
 */
function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) return
    // TODO 提交表单
  })
}
/**
 * @name: 表单重置
 * @description: 表单重置方法
 * @return {*}
 */
function resetForm() {
  formRef.value.resetFields()
}

</script>
<style>
.el-upload__tip {
  line-height: 1.2;
}

</style>
