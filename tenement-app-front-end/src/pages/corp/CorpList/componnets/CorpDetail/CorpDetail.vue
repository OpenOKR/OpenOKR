<template>
  <transition name="edit">
    <d2-container v-show="isVisible" v-loading="loading" class="edit-panel">
      <template slot="header">
        <i title="后退" class="el-icon-back" @click="isVisible = false"></i>
        客户详情
      </template>

      <div class="customer-detail" v-if="formData">
        <div class="base-info">
          <div class="title">{{formData.corpInfoVO.corpName}}</div>
          <div class="tags">
            <el-row :gutter="24">
              <el-col :span="18">
                <el-tag>{{formData.corpInfoVO.industryDesc}}</el-tag>
                <el-tag>{{isActiveService ? '已认证' : '未认证'}}</el-tag>
                <el-tag v-for="labelName in formData.corpLabelNameList">{{labelName}}</el-tag>
              </el-col>
              <el-col :span="6">
                <el-button type="primary" @click="openEditLabelDialog ">标签管理</el-button>
              </el-col>
            </el-row>

          </div>
        </div>
        <el-tabs v-model="activeTabName" type="card" class="details-info">
          <el-tab-pane label="基本信息" name="first">
            <el-form label-width="140px">
              <div class="group">
                <div class="group-title">企业信息</div>
                <el-row class="mb10">
                  <el-col :span="8">
                    <el-form-item class="mb5" label="企业名称：">{{formData.corpInfoVO.corpName}}</el-form-item>
                  </el-col>
                  <!--<el-col :span="8">-->
                  <el-form-item class="mb5"  label="人员规模：">{{formData.corpInfoVO.scale}}</el-form-item>
                  <!--</el-col>-->
                  <el-col :span="8">
                    <el-form-item class="mb5"  label="所属行业：">{{formData.corpInfoVO.industryDesc}}</el-form-item>
                  </el-col>
                </el-row>
              </div>
              <div class="group">
                <div class="group-title">管理员信息</div>
                <el-row class="mb10">
                  <el-col :span="8">
                    <el-form-item class="mb5" label="用户名：">{{formData.manageVO.userName}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item class="mb5" label="管理员姓名：">{{formData.manageVO.displayName}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item class="mb5" label="管理员电话：">{{formData.manageVO.telephone}}</el-form-item>
                  </el-col>
                </el-row>
              </div>
              <div class="group">
                <div class="group-title">平台账号注册信息</div>
                <el-row class="mb10">
                  <el-col :span="8">
                    <el-form-item class="mb5" label="创建方式：">{{formData.unionInfo.createType}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item class="mb5" label="平台：">{{formData.unionInfo.unionName}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item class="mb5" label="注册园区：">{{formData.unionInfo.regSite}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item class="mb5" label="创建时间：">{{formData.unionInfo.createTsStr}}</el-form-item>
                  </el-col>
                  <!--<el-col :span="8">-->
                  <!--<el-form-item label="园区标签：">{{formData.unionInfo.siteLabel}}</el-form-item>-->
                  <!--</el-col>-->
                </el-row>
              </div>
              <div class="group">
                <div class="group-title">服务开通
                  <el-switch v-model="isActiveService" disabled></el-switch>
                  <el-alert class="mt5" title="当前服务可用，关闭后将禁用此企业账号功能" type="info" :closable="false"></el-alert>
                </div>
                <br>
                <el-row>
                  <el-col :span="24">
                    <el-tag type="info" v-for="(item, index) in openServices" :key="index">{{item}}</el-tag>
                  </el-col>
                </el-row>
                <br>
              </div>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="认证信息" name="second">
            <el-form label-width="140px">
              <div class="group">
                <div class="group-title">认证状态</div>
                <el-row>
                  <el-col :span="8">
                    <el-tag type="success" v-if="formData.certStatusVO.certStatus === '1'">已认证</el-tag>
                    <el-tag type="info" v-else>未认证</el-tag>
                  </el-col>
                </el-row>
                <br>
              </div>
              <div class="group">
                <div class="group-title">工商信息</div>
                <el-row>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="证照类型：">{{formData.businessInfoVO.unifiedSocialCreditCode ? '三证' : '三证合一'}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="统一社会信用代码：">{{formData.businessInfoVO.unifiedSocialCreditCode}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="营业执照图片："><a href="javascript:void(0);" @click="viewPic(formData.businessInfoVO.license15FullFileUrl)">点击查看</a></el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="法人姓名：">{{formData.businessInfoVO.juridicalPerson}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="法人证件号：">{{formData.businessInfoVO.juridicalPersonId}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="法人证件照："><a href="javascript:void(0);" @click="viewPic(formData.businessInfoVO.license04FullFileUrl)">点击查看</a></el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="企业类型：">{{formData.businessInfoVO.corpTypeStr}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="企业性质：">{{formData.businessInfoVO.typeCodeStr}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="工商登记机关：">{{formData.businessInfoVO.registerAuthority}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="注册资本：">{{formData.businessInfoVO.registerMoney}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="成立日期：">{{formData.businessInfoVO.foundTime | dateFormat}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="营业期限：">{{formData.businessInfoVO.registerMoney | dateFormat}} ~ {{formData.businessInfoVO.businessEndtime | dateFormat}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="企业注册地址：">{{formData.businessInfoVO.registerAddress}}</el-form-item>
                  </el-col>
                </el-row>
              </div>
              <div class="group">
                <div class="group-title">开票信息</div>
                <el-row>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="发票抬头：">{{formData.tickerOpeningVO.title}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="纳税人识别号：">{{formData.tickerOpeningVO.identificationNumber}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="联系人电话：">{{formData.tickerOpeningVO.contactNumber}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="单位地址：">{{formData.tickerOpeningVO.corpAddress}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="开户银行：">{{formData.tickerOpeningVO.openingBank}}</el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item  class="mb5" label="银行账号：">{{formData.tickerOpeningVO.bankNo}}</el-form-item>
                  </el-col>
                </el-row>
              </div>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>
      <el-dialog :append-to-body="true" :visible.sync="dialogPicVisible">
        <img :src="viewPicUrl" v-if="viewPicUrl" style="width: 100%;"/>
      </el-dialog>
      <el-dialog title="标签管理" :visible.sync="showLabelDialog" v-if="formData" :append-to-body="true">
        <el-form label-width="100px" :model="editDialogCustomer">
          <h2>{{formData.corpInfoVO.corpName}}</h2>
          <el-form-item class="mb10" label="系统标签">
            <el-checkbox v-for="(label,index) in editDialogCustomer.labels" v-if="label.labelType==='0'" :label="label.labelName" :key="index"  v-model="label.checkFlag"></el-checkbox>
          </el-form-item>
          <el-form-item label="自定义标签">
            <el-tag v-for="(label,index) in editDialogCustomer.labels" :key="index" @click.native="likeCheckbox(label)" v-if="label.labelType==='1'" :type="label.checkFlag?'danger':'info'">{{label.labelName}}</el-tag>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitLabels">保 存</el-button>
          <el-button @click="showLabelDialog = false">取 消</el-button>
        </div>
      </el-dialog>
    </d2-container>
  </transition>

</template>

<script>
  import formMixin from '@/mixins/form.mixin';

  export default {
    name: "CorpDetail",
    components: {},
    mixins: [
      formMixin
    ],
    data() {
      return {
        isVisible: false,
        activeTabName: "first",
        formData: null,
        dialogPicVisible: false,
        viewPicUrl: '',
        showLabelDialog:false,
        editDialogCustomer:{
            labels:[]
        }
      };
    },
    computed: {
      isActiveService() {
        return this.formData.openService.certStatus === '1'
      },
      openServices() {
        return this.formData.openService.openServices;
        // return this.formData.openService && this.formData.openService.openServices ? this.formData.openService.openServices.split(',') : [];
      }
    },
    methods: {
      open(customer) {
        let _this = this;
        this.$api.corp.detail(customer.id).then(res => {
          _this.formData = res.data;
          _this.formData.siteId = customer.siteId;
          _this.formData.unionId = customer.unionId;
          _this.isVisible = true;
          _this.loading = false;
        });
      },
      viewPic(url) {
        this.viewPicUrl = url;
        this.$nextTick(() => {
          this.dialogPicVisible = true;
        })
      },
      //标签管理
      openEditLabelDialog(){
          const _this = this;
          this.showLabelDialog=true;
          this.$api.corp.getCorpLabelList(_this.formData.corpInfoVO.corpId,_this.formData.siteId).then(res => {
              _this.editDialogCustomer.labels = res.data;
          });
      },
      //点击自定义标签
      likeCheckbox(label){
          label.checkFlag = !label.checkFlag;
      },
      //标签保存
      submitLabels(){
        const _this = this;
        const labelId = [];
        const labelName=[];
        this.editDialogCustomer.labels.map(function(item){
          if(item.checkFlag){
            labelId.push(item.id);
            labelName.push(item.labelName)
          }

        });
        this.$api.corp.saveCorpLabelList(_this.formData.corpInfoVO.corpId,labelId.join(','),this.formData.unionId,_this.formData.siteId).then(res => {
          if(res.code==0){
            _this.showLabelDialog = false;
            _this.$message({
              message: '修改成功！',
              type: 'success'
            });
            _this.formData.corpLabelNameList = labelName;
          }else{
            _this.$message.error(res.message);
          }
        });
      }
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .customer-detail {
    .base-info {
      margin-bottom: 20px;
      .title {
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 15px;
      }
      .tags {
        span {
          margin-right: 10px;
        }
      }
    }
    .details-info {
      .group {
        margin-bottom: 10px;
        border-bottom: solid 1px #eee;
        .group-title {
          line-height: 3;
          font-size: 14px;
          font-weight: bold;
          .el-alert {
            font-size: 12px;
            line-height: 1.5;
          }
        }
      }
    }
  }

  .el-tag {
    margin-right: 10px;
    cursor: pointer;
    vertical-align: middle;
  }

  .el-dialog__body {
    padding: 0 20px;
  }
  .mt5{margin-top: 5px}
  .mb0{margin-bottom: 0}
  .mb5{margin-bottom: 5px}
  .mb10{margin-bottom: 10px}
  .el-checkbox + .el-checkbox{margin-left:0;}
  .el-checkbox{margin-right: 20px}
</style>
