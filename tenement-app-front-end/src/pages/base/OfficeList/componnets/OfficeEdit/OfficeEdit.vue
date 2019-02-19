<template>
  <transition name="edit">
    <d2-container v-show="isVisible" v-loading="loading" class="edit-panel">
      <template slot="header">
        <i title="后退" class="el-icon-back" @click="back"></i>
        {{actionName}}地点
      </template>

      <el-form
        ref="formMain"
        :model="formData"
        :rules="formRules"
        label-position="right"
        label-width="100px"
        class="form-main">
        <el-form-item label="所属园区">
          <el-select
            v-model="formData.siteId"
            filterable
            default-first-option
            placeholder="请选择园区"
            value-key="siteId"
            :disabled="isUpdate"
            style="width: 100%;">
            <el-option
              v-for="item in siteOptions"
              :key="item.siteId"
              :label="item.name"
              :value="item.siteId">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="地点名称：" prop="name">
          <el-input v-model="formData.name" placeholder="请输入地点名称"></el-input>
        </el-form-item>

        <el-form-item label="地址：" prop="addr">
          <el-cascader
            :options="addressOptions"
            v-model="cityCodeArr"
            style="margin-bottom: 10px;">
          </el-cascader>
          <el-input v-model="formData.addr" placeholder="请输入详细"></el-input>
        </el-form-item>

        <el-form-item label="产业属性：" prop="industryId">
          <el-select
            v-model="formData.industryId"
            multiple
            filterable
            allow-create
            default-first-option
            style="width: 100%;">
            <el-option
              v-for="item in industryOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="地点图片：" prop="httpPic">
          <el-upload
            :action="$api.common.uploadUrl"
            :data="{targetDir:'region'}"
            :headers="{token:token}"
            list-type="picture-card"
            :file-list="formData.httpPic"
            :limit="1"
            :on-success="res => res.data && this.formData.httpPic.push({url:res.data.httppath})"
            :on-remove="(res, list) => this.formData.httpPic = list"
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
  import { addressOptions, originAddress } from '@/libs/address';

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "OfficeEdit",
    components: {},
    mixins: [
      formMixin
    ],
    props:['siteOptions'],
    data() {
      return {
        isVisible: false,
        industryOptions: [],
        addressOptions: addressOptions,
        formData: {
          name: "",
          cityCode: "",
          cityStr: "",
          address: "",
          addr: "",
          industryId: [],
          httpPic: [],
          siteId: "",
        },
      };
    },
    computed: {
      address() {
        let provice, city, district = "";
        if (this.cityCodeArr) {
          if (this.cityCodeArr[0]) {
            provice = find(originAddress, {codevalue: this.cityCodeArr[0]}).codename
          }
          if (this.cityCodeArr[1]) {
            city = find(originAddress, {codevalue: this.cityCodeArr[1]}).codename
          }
          if (this.cityCodeArr[2]) {
            district = find(originAddress, {codevalue: this.cityCodeArr[2]}).codename
          }
        }
        return [provice, city, district]
      },
      cityCodeArr: {
        set(value) {
          // 返回数组的最后一个数即可
          this.formData.cityCode = value[value.length - 1];
          // this.formData.address = "对应的地区中文名字"
          this.formData.cityStr = this.formData.cityCode + "#" + this.address.join('/');
          return value;
        },
        get() {
          let res = [];
          let length = this.formData.cityCode ? this.formData.cityCode.length : 0;
          if (length) {
            // 把 086240203 这样的代码 拆成 ['08624', '0862402', '086240203']
            for (let i = 1; i <= (length - 3) / 2; i++) {
              res.push(this.formData.cityCode.substr(0, 3 + i * 2));
            }
          }
          return res;
        }
      },
      // 表单规则
      formRules() {
        let _this = this;
        return {
          siteId: [
            {required: true, message: '请选择园区'},
          ],
          name: [
            {required: true, message: '请输入地点名称'},
          ],
          addr: [
            {required: true, message: '请输入地址'},
          ],
          industryId: [
            {required: true, message: '请选择产业属性'},
          ],
        };
      }
    },
    methods: {
      // 打开弹框，初始化数据
      open(vo) {
        // let _this = this;
        this.formData = Object.assign({}, this.originFormData, vo);
        this.formData.httpPic = (this.formData.httpPic && this.formData.httpPic.length > 0) ? [{name: '', url: this.formData.httpPic}] : [];
        this.formData.industryId = (this.formData.industryId && typeof this.formData.industryId === 'string') ? this.formData.industryId.split(',') : [];
        this.$nextTick(() => {
          this.isVisible = true;
          this.loading = false;
        })
      },
      back() {
        this.isVisible = false
        this.$refs.formMain.resetFields();
      },
      handleUploadSuccess(res, file, fileList) {
        this.formData.httpPic.push({name: "", url: res.data.url});
        this.$message.error(res.message);
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
            submitData.httpPic = submitData.httpPic.map(item => item.url).join(',');
            submitData.address = _this.address.join('');
            submitData.industryId = submitData.industryId.join(',');
            // 编辑状态
            _this.$api.merch.office.update(submitData).then(res => {
              if (res.code === 0) {
                _this.$message.success(submitData.id ? '更新地点成功' : '创建地点成功！');
                _this.$emit("ok", submitData);
                _this.back();
              } else {
                _this.$message.error(res.message);
              }
              _this.loading = false;
            }).catch(function () {
              _this.$message.error(submitData.id ? '更新地点失败' : '创建地点失败！');
              _this.loading = false;
            });

          } else {
            _this.loading = false;
            return false;
          }
        });
      } // end submit
    },
    mounted() {
      let _this = this;
      this.$api.merch.office.industryOptions().then(res => {
        _this.industryOptions = res.data;
      });
    }
  };
</script>
