<template>
  <transition name="edit">
    <d2-container class="edit-panel" v-loading="loading" v-show="isVisible">
      <template slot="header">
        <i @click="back" class="el-icon-back" title="后退"></i>
        开票申请
      </template>
      <el-form
        :model="formData"
        :rules="formRules"
        class="form-main"
        label-position="right"
        label-width="200px"
        ref="formMain">
        <el-form-item label="购买方类型：" prop="buyerType">
          <el-radio-group v-model="formData.buyerType" @change="changeBuyerType" >
            <el-radio label="2" >企业</el-radio>
            <el-radio label="1" >个人</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="发票类型：" prop="invoiceType">
          <el-radio-group v-model="formData.invoiceType" >
            <el-radio
              v-for="item in invoiceTypeOptions"
              :label="item.label"
              :key="item.value"
              :disabled="item.disabled"
            >{{item.value}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="formData.buyerType=='2'" label="企业名称：" prop="corpName">
          <el-input class="form-input" v-model="formData.corpName" readonly disabled></el-input>
          <span v-show="showCorpWarning" class="notice-inline warning"><i class="el-icon-warning"></i>当前企业信息不完善<a class="ml10" @click="toCorpDetail" style="color: #409eff;cursor: pointer">去完善 ></a></span>
        </el-form-item>
        <el-form-item  v-else label="姓名：" prop="name">
          <el-input class="form-input" v-model="formData.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <template name="单个账单开票" v-if="formData.feeTypeList.length==1">
          <el-form-item label="货物或应税劳务、服务名称：" prop="remark">
            <el-input class="form-input" :value="formData.feeTypeList[0].name" readonly disabled></el-input>
            <el-button class="ml10" type="text" @click="toInvoiceConfig">开票费项设置 <i class="el-icon-arrow-right el-icon--right"></i></el-button>
          </el-form-item>
          <el-form-item label="本次开票总额（￥）" prop="invoiceAmt">
            <el-input  class="form-input" readonly disabled v-model="invoiceListTotal"></el-input>
            <span v-if="formData.feeTypeList[0].invoiceAmt>maxAmt" class="notice-inline warning"><i class="el-icon-warning"></i>当前开票金额大于单张限额：{{maxAmt|NumFormat}}元，系统自动拆分打印</span>
          </el-form-item>
          <el-form-item label="发票张数" prop="billIds">
            <el-input-number  v-model="formData.invoiceCount" controls-position="right" @change="invoiceNumberChange" :min="1"></el-input-number>
          </el-form-item>
          <el-form-item label="" prop="billIds">
            <el-table :data="formData.invoiceList" style="width:300px">
              <el-table-column prop="index" label="序号" width="100"></el-table-column>
              <el-table-column prop="value" label="金额（元）">
                <template slot-scope="scope">
                    <el-input
                        @keydown.native="checkValue($event,scope.row)"
                        @blur="checkValue($event,scope.row,true)"
                        @focus="clearError($event)"
                        v-model.number="scope.row.value">
                    </el-input>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </template>
        <template name="合并开票" v-if="formData.feeTypeList.length>1">
          <el-form-item label="明细：" prop="remark">
            <el-table
              :data="formData.feeTypeList"
              :show-summary="true"
              :summary-method="getSummaries"
              sum-text="价税合计 "
              style="width:700px">
              <el-table-column prop="name" label="货物或应税劳务、服务名称" width="260"></el-table-column>
              <el-table-column prop="invoiceAmt" label="金额（元）">
                  <template slot-scope="scope">
                    {{scope.row.invoiceAmt | NumFormat }}
                  </template>
              </el-table-column>
              <el-table-column prop="" label="税率">
                  <template slot-scope="scope">
                      {{formData.invoiceType=='1' ? scope.row.speTaxRate: scope.row.comTaxRate }}%
                  </template>
              </el-table-column>
              <el-table-column prop="invoiceAmt" label="税额">
                  <template slot-scope="scope">
                      {{(formData.invoiceType=='1' ? getInvoiceAmtRate(scope.row.invoiceAmt,scope.row.speTaxRate):getInvoiceAmtRate(scope.row.invoiceAmt,scope.row.comTaxRate)) | NumFormat }}
                  </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </template>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" :rows="5" placeholder="请输入备注" v-model="formData.remark"></el-input>
        </el-form-item>
        <el-form-item label="销售方" prop="sellerCorpId">
            <el-input class="form-input" :value="formData.serviceVo.text" readonly disabled></el-input>
          <el-button type="text" class="ml10" @click="toService">服务商开票信息维护 <i class="el-icon-arrow-right el-icon--right"></i></el-button>
        </el-form-item>
      </el-form>

      <template slot="footer">
        <el-button @click="submit" type="primary"><i class="iconfont icon-save"></i> 加入待开单据</el-button>
        <el-button @click="back">取消</el-button>
      </template>
    </d2-container>
  </transition>
</template>

<script>
  import formMixin from "@/mixins/form.mixin";
  import { cloneDeep, find } from 'lodash';

  export default {
    name: "InvoiceApplyCreate",
    components: {},
    mixins: [
      formMixin
    ],
    props: [],
    data() {
      return {
        siteId:'',
        isVisible: false,
        formData: {
          buyerType:'2',
          invoiceType:'1',
          name: "",
          siteId: "",
          billVos:[],
          invoiceList:[],
          invoiceCount:1,
        },
        // 购买方选项
        invoiceTypeOptions:[
          {label:'1',value:'增值税专用发票'},
          {label:'2',value:'增值税普通发票'}
        ],
        invoiceLimitVo:{

        },
        // 销售方选项
        serviceOptions: []
      };
    },
    computed: {
      // 表单规则
      formRules() {
        if(this.formData.buyerType=='2'){
          //企业开票验证
          return {
          }
        }else{
          //个人开票验证
          return {
          }
        }

      },
      unionId() {
        return this.$route.params.unionId
      },
      //单张开票最大限额
      maxAmt(){
        setTimeout(()=>{
          //监听maxAmt
          this.setInvoiceCount();
        },0)
        if(this.formData.invoiceType=='1'){
          //企业开票验证
          return this.formData.serviceVo.specialInviceLimit;
        }else{
          //个人开票验证
          return this.formData.serviceVo.normalInviceLimit;
        }
      },
      invoiceListTotal(){
        let _invoiceListTotal = 0;
        this.formData.invoiceList.map(function(item){
          _invoiceListTotal += parseFloat(item.value)||0;
        })
        return _invoiceListTotal;
      },
      //是否显示企业完善入口
      showCorpWarning(){
        //如果切换到企业则需要判断，如果是个人直接返回 false
        if(this.formData.buyerType=='2'){
          let taxVo = this.formData.taxInfo
          if(this.formData.invoiceType=='1'){
            //专票
            return !(
              this.formData.corpName
              && taxVo.identificationNumber
              && taxVo.corpAddress
              && taxVo.contactNumber
              && taxVo.openingBank
              && taxVo.bankNo
            )
          }else{
            return !(
              this.formData.corpName
              && taxVo.identificationNumber
            )
          }
        }else{
          return false;
        }
      }
    },
    watch:{
    },
    methods: {
      // 打开弹框，初始化数据
      open(vo) {
        console.log(vo);
        this.siteId = vo.siteId;
        this.formData = {
          ...vo.billVos,
          billIds:vo.billIds,
          buyerType:'2',
          invoiceType:vo.billVos.defaultTicketType,
          name: "",
          invoiceList:[],
          serviceVo:vo.serviceVo
        };

        if(this.formData.feeTypeList.length==1){
          //单个开票，可以拆分
          this.invoiceAmt = this.formData.feeTypeList[0].invoiceAmt;
          this.invoiceTypeOptions=[
            {label:'1',value:'增值税专用发票',disabled:false},
            {label:'2',value:'增值税普通发票',disabled:false}
          ];
        }else{
          //合并开票，需要根据合并总金额 和 服务商个人限额，服务商企业限额来显示
          this.changeBuyerType('2')
        }
        this.isVisible = true;
        this.loading = false;
      },
      //切换购买方类型
      changeBuyerType(type){
        console.log(type);
        //如果是合并开票，开票总额如果超过发票限额，则此项不可选，

        let overNormalInviceLimit =false,overspecialInviceLimit= false;

        if( this.formData.feeTypeList.length>1){
          let totalInvoiceAmt = 0;
          this.formData.feeTypeList.map(fee=>{
            totalInvoiceAmt += fee.invoiceAmt;
          })
          overNormalInviceLimit = totalInvoiceAmt > this.formData.serviceVo.normalInviceLimit;
          overspecialInviceLimit = totalInvoiceAmt > this.formData.serviceVo.specialInviceLimit;
        }else {
          if(type=='2'){
            this.invoiceTypeOptions=[
              {label:'1',value:'增值税专用发票',disabled:false},
              {label:'2',value:'增值税普通发票',disabled:false}
            ];
            this.formData.invoiceType='1'
          }else{
            this.invoiceTypeOptions=[
              {label:'2',value:'增值税普通发票',disabled:false}
            ];
            this.formData.invoiceType='2'
          }

          return;
        }
        if(type=='2'){
          //企业
          this.invoiceTypeOptions=[
            {label:'1',value:'增值税专用发票',disabled:overspecialInviceLimit},
            {label:'2',value:'增值税普通发票',disabled:overNormalInviceLimit}
          ];
          if(!this.invoiceTypeOptions[0].disabled){
            this.formData.invoiceType='1'
          }else if(!this.invoiceTypeOptions[1].disabled){
            this.formData.invoiceType='2'
          }else{
            this.formData.invoiceType=''
          }
        }else{
          this.invoiceTypeOptions=[
            {label:'2',value:'增值税普通发票',disabled:overNormalInviceLimit}
          ];
          if(!this.invoiceTypeOptions[0].disabled){
            this.formData.invoiceType='1'
          }else{
            this.formData.invoiceType=''
          }

        }
      },
      getInvoiceAmtRate(amt,rate){
        //税额=（价税金额）/(1+税率) *税率
        return (amt/(100+rate)*rate).toFixed(2);
      },
      //设置默认发票张数和默认金额，这个方法只有适合单个账单开票是使用
      setInvoiceCount(count){
        if(this.formData.feeTypeList.length>1){
          console.log('这个方法只有适合单个账单开票是使用');
          return false;
        }
        if(count){
          //设置总数
          this.formData.invoiceCount = count;
        }else{
          count = this.formData.invoiceCount =  Math.ceil(this.formData.feeTypeList[0].invoiceAmt/this.maxAmt);
        }
        //拆分金额
        let arr = Array(count);
        let invoiceAmt = this.formData.feeTypeList[0].invoiceAmt;
        let maxAmt= this.maxAmt;
        for(let i=0;invoiceAmt>0;i++){
          arr[i] = Math.min(invoiceAmt,maxAmt);
          invoiceAmt-= maxAmt;
        }
        this.formData.invoiceList = [];
        for(let i=0;i<count;i++){
          let _item = {index:i+1, value:arr[i]||0};
          this.formData.invoiceList.push(_item)
        }

      },
      back() {
        this.isVisible = false
        this.$refs.formMain.resetFields();
      },
      invoiceNumberChange(value){
        this.setInvoiceCount(value)
      },
      //检测发票额度
      checkValue($event,model,isBlur=false){
        let target = $event.target,oldValue=target.value;
        setTimeout(()=>{
          let value = target.value;
          //删干净的情况
          if(value==''){
           return;
          }
          let exp = /^(([1-9]\d*)|\d)(\.\d{0,2})?$/;

          if(isBlur){
            exp = /^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
          }
          if(!exp.test(value)){
            model.value = target.value = oldValue;
            if(isBlur){
              this.$message.error('输入金额有误');
              target.classList.add("error");
            }
          }
          //超过单张最大限额
          if(parseFloat(value)>this.maxAmt){
            this.$message.error('输入金额不能超过最大限额');
            model.value = target.value = oldValue;
          }
          //总额超过了
          if(this.invoiceListTotal>this.invoiceAmt){
            this.$message.error('开票总额不得大于可开票总额');
            model.value = target.value = oldValue;
          }
        },0)
      },
      clearError($event){
        $event.target.classList.remove("error");
      },
      getSummaries(param){
        const { columns, data } = param;
        const sums = [];
        columns.forEach((column, index) => {
          if (index === 0) {
            sums[index] = '价税合计';
            return;
          }
          const values = data.map(item => Number(item[column.property]));
          if (index==1) {
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr);
              if (!isNaN(value)) {
                return prev + curr;
              } else {
                return prev;
              }
            }, 0);
            sums[index] = sums[index].toFixed(2);
          } else {
            sums[index] = '--';
          }
        });

        return sums;
      },
      //跳转到企业信息维护
      toCorpDetail(){
        // if(top===window){
        //   this.$message.error('没有权限');
        // }else{
        //   top.addTab("detail_corp", "企业信息维护", top.App.baseDomain+ top.App.onlyouContextPath+"/corp/manage/corpDetail.htm?activeTab=2&corpId="+ this.formData.corpId, true);
        // }
        this.$message.info('请至售前售后-平台注册企业列表维护企业开票信息');
      },
      //开票费项设置
      toInvoiceConfig(){
        // if(top===window){
        //   //this.$message.error('没有权限');
        //   window.open(`${window.location.href.replace(/apply/g,'config')}&siteId=${this.siteId}`);
        // }else{
        //   top.addTab("detail_corp", "开票费项设置", `${window.location.href.replace(/apply/g,'config')}&siteId=${this.siteId}`, true);
        // }
        this.$message.info('请至开票管理-开票设置-开票费项设置 进行维护');
      },
      //
      toService(){
        // if(top===window){
        //   this.$message.error('没有权限');
        // }else{
        //   top.addTab("detail_service", "企业信息维护", top.App.baseDomain+ top.App.onlyouContextPath+"/corp/manage/init.htm?unionId="+ this.unionId, true);
        // }
        this.$message.info('请至服务商管理-服务商列表维护服务商开票信息');
      },
      //提交
      submit() {
        this.loading = true;
        this.$refs.formMain.validate((valid) =>{ // 验证表单
          if (valid) {
            let vo = {
              amtList: [],
              billIds:this.formData.billIds,
              //根据购买方类型决定是传入企业名称还是个人名称
              buyerName:this.formData.buyerType=='2'?this.formData.corpName:this.formData.name,
              buyerType: this.formData.buyerType,
              invoiceType:this.formData.invoiceType,
              remark: this.formData.remark,
              sellerCorpId: this.formData.serviceVo.corpId,
              siteId: this.siteId
            };

            if(this.formData.feeTypeList.length==1){
              //如果是单张开票，需要遍历 invoiceList
              let amtId = this.formData.feeTypeList[0].id;
              this.formData.invoiceList.map(function(item){
                vo.amtList.push({
                  id:amtId,
                  invoiceAmt:item.value
                })
              })
            }else{
              //如果是单张开票，需要遍历 feeTypeList
              this.formData.feeTypeList.map(function(item){
                vo.amtList.push({
                  id:item.id,
                  invoiceAmt:item.invoiceAmt
                })
              })
            }
            console.log(vo)
            this.$api.energy.invoice.apply.applyInvoice(vo).then(res => {
              this.loading = false;
              if(res.code==0){
                setTimeout(()=>{
                  this.isVisible = false;
                    this.$emit("ok");
                    this.back();
                },500)
                this.$message.success('提交成功！');
              }else{
                this.$message.error(res.message);
              }
            });

          } else {
            this.loading = false;
            return false;
          }
        });
      }
    },
    mounted() {

    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .form-input{ width: 300px}
</style>
<style>
    input.error{
        border: #f56c6c solid 1px;
    }
</style>
