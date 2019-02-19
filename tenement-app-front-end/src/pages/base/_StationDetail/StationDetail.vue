<template>
  <d2-container v-loading="loading">
    <template slot="header">
      <i title="后退" class="el-icon-back" @click="goBack"></i>
      {{actionName}}单元
    </template>

    <el-form ref="formMain" :model="formData" :rules="formRules" label-position="right" class="form-main" label-width="100px">
      <el-form-item label="园区">
        <el-select
          v-model="formData.siteId"
          style="width: 100%;">
          <el-option
            v-for="item in siteOptions"
            :key="item.siteId"
            :label="item.name"
            :value="item.siteId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="地点">
        <el-select
          v-model="formData.officeId"
          style="width: 100%;">
          <el-option
            v-for="item in officeOptions"
            :key="item.id"
            :label="item.industryName"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属区域：" prop="area">
        <el-cascader v-model="formData.area" :options="areaOoptions"></el-cascader>
      </el-form-item>
      <el-form-item label="名称：" prop="name">
        <el-input v-model="formData.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="数量：" prop="number">
        <el-input-number v-model="formData.number" :min="1"></el-input-number>
      </el-form-item>
      <el-form-item label="类型：" prop="type">
        <el-select v-model="value" placeholder="请选择">
          <el-option
            v-for="item in typeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="面积：" prop="acreage">
        <el-input v-model="formData.acreage" placeholder="请输入面积"></el-input>
      </el-form-item>

    </el-form>

    <template slot="footer">
      <el-button type="primary" @click="submit"><i class="iconfont icon-save"></i> 保存</el-button>
      <el-button @click="goBack">取消</el-button>
    </template>
  </d2-container>
</template>

<script>
  import formMixin from "@/mixins/form.mixin";
  import { cloneDeep } from 'lodash';

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "StationDetail",
    components: {},
    mixins: [
      formMixin
    ],
    data() {
      return {
        getActionWhere:{},
        formData: {
          name: "",
          childNameLists: [],
          imgList: [],
          officeId: "",
          siteId: "",
        },
        siteOptions: [],
        officeOptions: [],
      };
    },
    computed: {
      unionId() {
        return this.$route.params.unionId
      },
      siteId() {
        return this.$route.params.siteId
      },
      officeId() {
        return this.$route.params.officeId
      },
      // 表单规则
      formRules() {
        let _this = this;
        return {
          siteId: [
            {required: true, message: '请选择园区'},
          ],
          officeIdId: [
            {required: true, message: '请选择地点'},
          ],
          name: [
            {required: true, message: '请输入一级区域'},
          ],
          childNameLists: [
            {
              validator: (rule, value, callback) => {
                if (!value || value.length === 0) {
                  callback(new Error('请输入二级区域'));
                } else {
                  callback();
                }
              }
            },
          ],
        };
      }
    },
    methods: {
      handleSiteChange(value) {
        const _this = this;
        // 加载地点选项
        this.$api.merch.region.officeOptions(value).then(res => {
          _this.officeOptions = res.data;
        });
      },
      handleUploadSuccess(res, file, fileList) {
        this.formData.imgList.push({name: "", url: res.data.url});
      },
      /**
       * 提交客户
       */
      submit() {
        let _this = this;
        _this.loading = true;

        this.$refs.formMain.validate(function (valid) { // 验证表单
          if (valid) {
            if (_this.id) {
              // 编辑状态
              let submitData = cloneDeep(_this.formData);

              // 转换数据
              submitData.imgList = submitData.imgList.map(item => item.url)
              _this.$api.merch.update(submitData).then(res => {
                _this.$message.success('更新客户成功！');
                _this.loading = false;
                _this.goBack();
              }).catch(function () {
                _this.loading = false;
              });
            }
          } else {
            return false;
          }
        });

      }
    },
    mounted() {
      let _this = this;

      // 加载园区选项
      this.$api.merch.region.siteOptions(this.unionId).then(res => {
        _this.siteOptions = res.data;
      });
      // 加载地点选项
      // this.$api.merch.region.officeOptions(value).then(res => {
      //   _this.officeOptions = res.data;
      // });

      if (this.$route.params.id) {
        this.$api.merch.region.detail(this.$route.params.id).then(res => {
          // 转换数据
          res.data.imgList = res.data.imgList.map(item => {
            return {
              name: "",
              url: item
            }
          })
          _this.formData = res.data;
          // 查询地点下拉框的值
          _this.$api.merch.region.officeOptions(_this.formData.siteId).then(res => {
            _this.officeOptions = res.data;
            _this.loading = false;
          });
        });
      } else {
        this.loading = false;
      }
      // const _this = this;
      // const unionId = util.getUrlParam("unionId");
    }
  };
</script>
