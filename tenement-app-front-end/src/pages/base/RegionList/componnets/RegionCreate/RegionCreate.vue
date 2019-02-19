<template>
  <transition name="edit">
    <d2-container v-show="isVisible" v-loading="loading" class="edit-panel">
      <template slot="header">
        <i title="后退" class="el-icon-back" @click="back"></i>
        创建区域
      </template>

      <el-form>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="园区">
              <el-input :value="siteName" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="地点">
              <el-input :value="officeName" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <transition-group name="fade-transverse" tag="div">
        <div class="form-group" v-for="(formItem,index) in formData" :key="index">
          <!--删除按钮-->
          <el-button type="danger" size="small" icon="el-icon-delete" circle @click="deleteItem(index)" class="btn-delete"></el-button>
          <!--表格-->
          <el-form
            :ref="'formMain' + index"
            :model="formItem"
            :rules="formRules"
            label-position="right"
            label-width="100px"
            class="form-main">

            <el-form-item label="一级区域：" prop="name">
              <el-input v-model="formItem.name" placeholder="请输入名称"></el-input>
            </el-form-item>

            <el-form-item label="二级区域：" prop="childLists">
              <el-select
                v-model="formItem.childLists"
                multiple
                filterable
                allow-create
                default-first-option
                style="width: 100%;"
                placeholder="请填写二级区域，可有多个值（点击回车完成输入）">
              </el-select>
            </el-form-item>

            <el-form-item label="区域平面图：" prop="httpPathList">
              <el-upload
                :action="$api.common.uploadUrl"
                :data="{targetDir:'region'}"
                :headers="{token:token}"
                list-type="picture-card"
                :file-list="formItem.httpPathList"
                :limit="999"
                :on-success="res => res.data && formItem.httpPathList.push({url:res.data.httppath})"
                :on-remove="(res, list) => formItem.httpPathList = list"
                :on-exceed="handleUploadExceed">
                <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
          </el-form>
        </div>
      </transition-group>
      <el-button type="primary" plain @click="createItem" style="width: 100%;">新增</el-button>

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
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "RegionCreate",
    components: {},
    mixins: [
      formMixin
    ],
    props:['siteId', 'siteName', 'officeId', 'officeName'],
    data() {
      return {
        isVisible: false,
        formData: [],
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
      },
      uploadData() {
        return {
          targetDir: 'region',
          token: this.token
        }
      }
    },
    methods: {
      // 打开弹框，初始化数据
      open(vo) {
        this.formData = [];
        this.createItem();
        this.$nextTick(()=>{
          this.isVisible = true;
          this.loading = false;
        })
      },
      back(){
        let _this = this
        this.isVisible = false
        this.formData = []
        _this.formData.forEach((item, index) => {
          _this.$refs['formMain' + index][0].resetFields();
        });
      },
      createItem() {
        this.formData.push({
          name: "",
          childLists: [],
          httpPathList: [],
          siteId: this.siteId,
          officeId: this.officeId,
        })
      },
      deleteItem(index) {
        this.formData.splice(index, 1);
      },
      /**
       * 提交客户
       */
      submit() {
        let _this = this;
        _this.loading = true;

        let formValid = true;
        this.formData.forEach(async (item, index) => {
          await new Promise(function (resolve) {
            _this.$refs['formMain' + index][0].validate(valid => {
              if (!valid) {
                formValid = false;
              }
              resolve();
            });
          })
        })
        if (formValid) {
          let submitData = cloneDeep(_this.formData);
          submitData.forEach(item => {
            item.httpPathList = item.httpPathList.map(item => item.url);
            item.childLists = item.childLists.map(item => {
              return {
                name: item
              }
            });
          });
          _this.$api.merch.region.insertMany(submitData).then((res) => {
            if (res.code === 0) {
              _this.$message.success('创建区域成功！');
              _this.$emit("ok");
              _this.back();
            } else {
              _this.$message.error(res.message);
            }
            _this.loading = false;
          }).catch(function () {
            _this.loading = false;
          })
        } else {
          _this.loading = false;
        }
      } // end submit
    }
  };
</script>


<style type="text/scss" lang="scss" scoped>
  .form-group {
    position: relative;
    padding: 20px 80px 20px 20px;
    border: 1px dashed #ddd;
    border-radius: 5px;
    margin-bottom: 20px;
    .btn-delete {
      /*opacity: 0;*/
      position: absolute;
      right: 20px;
      top: 20px;
      /*transition: opacity .2s;*/
    }
    /*&:hover .btn-delete{*/
    /*opacity: 1;*/
    /*}*/
  }
</style>
