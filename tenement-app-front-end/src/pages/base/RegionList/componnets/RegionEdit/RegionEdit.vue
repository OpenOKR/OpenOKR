<template>
  <transition name="edit">
    <d2-container v-show="isVisible" v-loading="loading" class="edit-panel">
      <template slot="header">
        <i title="后退" class="el-icon-back" @click="back"></i>
        编辑区域
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

        <el-form-item label="一级区域：" prop="name">
          <el-input v-model="formData.name" placeholder="请输入名称"></el-input>
        </el-form-item>

        <el-form-item label="二级区域：" prop="childLists">
          <el-select
            v-model="formData.childLists"
            multiple
            filterable
            allow-create
            default-first-option
            style="width: 100%;"
            placeholder="请填写二级区域，可有多个值（点击回车完成输入）">
            <el-option
              v-for="item in childListOptions"
              :key="item.id"
              :label="item.name"
              :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="区域平面图：" prop="httpPathList">
          <el-upload
            :action="$api.common.uploadUrl"
            :data="{targetDir:'region'}"
            :headers="{token:token}"
            list-type="picture-card"
            :file-list="formData.httpPathList"
            :limit="999"
            :on-success="res => res.data && this.formData.httpPathList.push({url:res.data.httppath})"
            :on-remove="(res, list) => this.formData.httpPathList = list"
            :on-exceed="handleUploadExceed">
            <i class="el-icon-plus"></i>
          </el-upload>
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
  import { cloneDeep, find } from 'lodash';

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "RegionEdit",
    components: {},
    mixins: [
      formMixin
    ],
    props: ['siteName', 'officeName'],
    data() {
      return {
        isVisible: false,
        childListOptions: [],
        formData: {
          name: "",
          childLists: [],
          httpPathList: [],
          officeId: "",
          siteId: "",
        },
      };
    },
    computed: {
      // 表单规则
      formRules() {
        let _this = this;
        return {
          name: [
            {required: true, message: '请输入一级区域'},
          ]
        };
      }
    },
    methods: {
      // 打开弹框，初始化数据
      open(vo) {
        // let _this = this;
        this.childListOptions = cloneDeep(vo.childLists);
        this.formData = Object.assign({}, this.originFormData, vo);
        this.formData.childLists = this.formData.childLists ? this.formData.childLists.map(item => item.name) : [];
        this.formData.httpPathList = this.formData.httpPathList ? this.formData.httpPathList.map(item => {
          return {name: '', url: item}
        }) : [];
        this.$nextTick(() => {
          this.isVisible = true;
          this.loading = false;
        })
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
            let submitData = cloneDeep(_this.formData);
            // 转换数据
            submitData.httpPathList = submitData.httpPathList.map(item => item.url)
            submitData.childLists = submitData.childLists.map(item => {
              let optionItem = find(_this.childListOptions, {name: item});
              let result = {name:item};
              if(optionItem){
                result.id = optionItem.id;
              }
              return result;
            });

            // 编辑状态
            if (_this.id) {
              _this.$api.merch.region.update(submitData).then(res => {
                if (res.code === 0) {
                  _this.$message.success('更新区域成功！');
                  _this.$emit("ok");
                  _this.back();
                } else {
                  _this.$message.error(res.message);
                }
                _this.loading = false;
              }).catch(function () {
                _this.$message.error('更新区域失败！');
                _this.loading = false;
              });
            }

          } else {
            _this.loading = false;
            return false;
          }
        });
      } // end submit
    }
  };
</script>
