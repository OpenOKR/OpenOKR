<template>
  <transition name="edit">
    <d2-container v-show="isVisible" v-loading="loading" class="edit-panel">
      <template slot="header">
        <i title="后退" class="el-icon-back" @click="back"></i>
        {{actionName}}{{pageTypeName}}
      </template>

      <el-form
        ref="formMain"
        :model="formData"
        :rules="formRules"
        label-position="right"
        label-width="100px"
        class="form-main">
        <el-form-item label="园区">
          <el-input :value="siteName" disabled></el-input>
        </el-form-item>
        <el-form-item label="地点">
          <el-input :value="officeName" disabled></el-input>
        </el-form-item>
        <el-form-item label="所属区域：" prop="region1">
          <el-cascader
            :options="regionOptions"
            v-model="region"
            :props="{label:'name', value:'id', children:'childLists'}"
            clearable
            change-on-select
            style="width: 100%;">
          </el-cascader>
        </el-form-item>
        <el-form-item label="名称：" prop="name">
          <el-input v-model="formData.name" placeholder="请输入名称"></el-input>
        </el-form-item>
        <!--<el-form-item label="数量：" prop="staCount">-->
          <!--<el-input-number v-model="formData.staCount" :min="1" :max="99"></el-input-number>-->
        <!--</el-form-item>-->
        <el-form-item label="物业类型：">
          <el-select
            v-model="formData.propertyId"
            default-first-option
            filterable
            clearable
            style="width: 100%;">
            <el-option
              v-for="item in propertyOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="pageTypeName + '类型'">
          <el-select
            v-model="formData.typeId"
            default-first-option
            filterable
            clearable
            style="width: 100%;">
            <el-option
              v-for="item in stationTypeOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="面积：" prop="proportion">
          <el-input v-model="formData.proportion" placeholder="请输入面积">
            <template slot="append">㎡</template>
          </el-input>
        </el-form-item>
      </el-form>

      <template slot="footer">
        <el-button type="primary" @click="submit"><i class="iconfont icon-save"></i> 保存</el-button>
        <el-button @click="back">取消</el-button>
      </template>
    </d2-container>
  </transition>
</template>

<script>
  import formMixin from "@/mixins/form.mixin";
  import { cloneDeep } from 'lodash';

  export default {
    name: "StationDetail",
    components: {},
    mixins: [
      formMixin
    ],
    props: {
      isCar: Number,
      pageTypeName: String,
      siteName: String,
      officeName:String,
      regionOptions: {
        type: Array,
        default: []
      },
      stationTypeOptions: {
        type: Array,
        default: []
      },
      propertyOptions: {
        type: Array,
        default: []
      }
    },
    data() {
      return {
        isVisible: false,
        formData: {
          officeId: "",
          siteId: "",
          name: "",
          region1: "",
          region2: "",
          typeId: "",
          proportion: "",
          // staCount: 0,
          // unit: "㎡",
        },
      };
    },
    computed: {
      region: {
        set(value) {
          this.formData.region1 = value[0]
          this.formData.region2 = value[1]
        },
        get() {
          return [this.formData.region1, this.formData.region2]
        },
      },
      // 表单规则
      formRules() {
        return {
          name: [
            {required: true, message: '请输入名称'},
          ],
          typeId: [
            {required: true, message: '请选择类型'},
          ],
          propertyId: [
            {required: true, message: '请选择物业类型'},
          ],
          proportion: [
            {required: true, message: '请输入面积'},
          ],
          // staCount: [
          //   {required: true, message: '请输入数量'},
          // ],
        };
      }
    },
    methods: {
      // 打开弹框，初始化数据
      open(vo) {
        // let _this = this;
        this.isVisible = true;
        this.loading = false;
        this.formData = Object.assign({}, this.originFormData, vo);
      },
      back() {
        this.isVisible = false
        this.$refs.formMain.resetFields();
      },
      /**
       * 提交客户
       */
      submit() {
        let _this = this;
        _this.loading = true;

        this.$refs.formMain.validate(function (valid) { // 验证表单
          if (valid) {
            // 转换数据
            let submitData = cloneDeep(_this.formData);
            if (_this.formData.id) {
              // 编辑状态
              _this.$api.merch.station.update(submitData).then(res => {
                if (res.code === 0) {
                  _this.$message.success(`更新${_this.pageTypeName}成功！`);
                  _this.$emit("ok");
                  _this.back();
                } else {
                  _this.$message.error(`更新${_this.pageTypeName}失败！`);
                }
                _this.loading = false;
              }).catch(function () {
                _this.$message.error(`更新${_this.pageTypeName}失败！`);
                _this.loading = false;
              });
            } else {
              // 新增状态
              _this.$api.merch.station.insert(submitData).then(res => {
                if (res.code === 0) {
                  _this.$message.success(`新增${_this.pageTypeName}成功！`);
                  _this.$emit("ok");
                  _this.back();
                } else {
                  _this.$message.error(`新增${_this.pageTypeName}失败！`);
                }
                _this.loading = false;
              }).catch(function () {
                _this.loading = false;
              });
            }
          } else {
            _this.loading = false;
          }
        });

      }
    }
  };
</script>
