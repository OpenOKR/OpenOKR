<template>
  <div>
    <d2-container v-loading="loading">
      <template slot="header">
        开票申请
      </template>
      <section class="m-site-select">
        站点名称：
        <el-select @change="changeSite" filterable placeholder="站点名称" style="width: 300px;" v-model="siteId">
          <el-option :key="item.siteId" :label="item.name" :value="item.siteId" v-for="item in siteOptions"></el-option>
        </el-select>
      </section>

      <transition name="fade">
        <statistical-data-panel :data-list="statisticalData" v-show="siteId"/>
      </transition>
      <br>
      <!---->
      <transition name="fade">
        <table-comb
          :autoFetch="false"
          :get-action="$api.energy.invoice.apply.list"
          :get-action-where="getActionWhere"
          :search-model-base="tableMainSearchModelBase"
          :search-model="tableMainSearchModel"
          checked-dispaly-name="billNo"
          @on-remove-success="refreshStaticData"
          name="开票记录列表"
          ref="tableMain"
          v-show="siteId">
          <!--基础查询-->
          <template slot="baseSearchForm" slot-scope="scope">
            <el-input
              clearable
              placeholder="业务流水号/客户名称/联系人/电话"
              prefix-icon="el-icon-search"
              style="width: 300px; margin-right: 10px;"
              v-model="scope.form.searchKey">
            </el-input>
          </template>
            <!--高级查询-->
            <template slot="advancedSearchForm" slot-scope="props">
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="费项：">
                            <el-select
                                v-model="props.form.feeTypeId"
                                clearable
                                placeholder="请选择">
                                <el-option
                                    v-for="item in feeTypeOptions"
                                    :key="item.value"
                                    :label="item.text"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="账单月：">
                            <el-date-picker
                                v-model="props.form.billMonth"
                                type="month"
                                format="yyyy-MM"
                                style="width: 150px"
                                value-format="yyyyMM"
                                placeholder="选择月">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="申请状态：">
                            <el-select
                                    v-model="props.form.applyStatus"
                                    clearable
                                    placeholder="请选择">
                                <el-option label="全部" value=""></el-option>
                                <el-option label="未申请" value="0"></el-option>
                                <el-option label="已申请" value="1"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="账单状态：">
                            <el-select
                                v-model="props.form.payStatus"
                                clearable
                                placeholder="请选择">
                                <el-option label="全部" value=""></el-option>
                                <el-option label="部分付款" value="02"></el-option>
                                <el-option label="已付款" value="01"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
            </template>
          <!--控制按钮-->
          <template slot="controls">
              <el-tooltip class="item" effect="dark" content="将多笔费项开具在同一张发票上" placement="top">
                  <el-button  @click="invoiceApply(false)">合并开票</el-button>
              </el-tooltip>
            <el-button @click="applyMulti">批量申请</el-button>
          </template>
          <!--表格-->
          <template slot="tableColumns">
            <el-table-column fixed="left" type="selection" :selectable="function(row, index){ return row.applyStatus=='0'}" width="35"></el-table-column>
            <el-table-column fixed="left" label="操作" width="150px">
              <template slot-scope="{row}">
                  <!-- 开票状态   、申请状态 、支付状态
                  申请开票 ： 部分开票 + 未申请
                  查看发票： 已开票 或者 部分开票-->
                <el-button-group>
                  <el-button type="text" v-if="row.invoicedStatus === '1' || row.applyStatus=='0'" @click="invoiceApply(row)">申请开票</el-button>
                  <el-button style="margin-left: 10px" type="text" v-if="row.invoicedStatus === '1' || row.invoicedStatus === '2'" @click="viewInvoice(row)">查看发票</el-button>
                </el-button-group>
              </template>
            </el-table-column>
            <el-table-column label="业务流水号" prop="billNo" width="180"></el-table-column>
            <el-table-column label="费项" prop="feeTypeName" width="100"></el-table-column>
            <el-table-column label="客户名称" prop="corpName" width="250"></el-table-column>
            <el-table-column label="开票状态" width="80">
              <template slot-scope="{row}">
                <el-button-group>
                  <el-tag type="info" v-if="row.applyStatus === '0'">未申请</el-tag>
                  <el-tag v-if="row.applyStatus === '1'">已申请</el-tag>
                </el-button-group>
              </template>
            </el-table-column>
            <el-table-column label="账单缴费状态" width="110">
              <template slot-scope="{row}">
                <el-button-group>
                  <el-tag type="info" v-if="row.payStatus === '00'">未付款</el-tag>
                  <el-tag v-if="row.payStatus === '01'">已付款</el-tag>
                  <el-tag type="warning" v-if="row.payStatus === '02'">部分付款</el-tag>
                </el-button-group>
              </template>
            </el-table-column>
            <el-table-column label="账单金额" prop="receivableAmt" width="100px">
                <template slot-scope="{row}">
                    {{row.receivableAmt | NumFormat}}
                </template>
            </el-table-column>
            <el-table-column label="可开票金额" prop="invoiceAmt" width="100px">
                <template slot-scope="{row}">
                    {{row.invoiceAmt | NumFormat}}
                </template>
            </el-table-column>
            <el-table-column label="应收日期" prop="receivableDate" width="100px">
              <template slot-scope="{row}">
                <el-button-group>
                  <span v-if="row.receivableDate > 0">{{ new Date(row.receivableDate) | dateFormat('yyyy-MM-dd') }}</span>
                </el-button-group>
              </template>
            </el-table-column>
              <el-table-column label="账单月" prop="billMonth" width="100px">
              </el-table-column>
            <el-table-column label="计费周期" width="200px">
              <template slot-scope="{row}">
                <span v-if="row.billStartDate && row.billEndDate">
                {{ new Date(row.billStartDate) | dateFormat('yyyy-MM-dd') }} ~
                {{ new Date(row.billEndDate) | dateFormat('yyyy-MM-dd') }}
                </span>
              </template>
            </el-table-column>
          </template>
        </table-comb>
      </transition>

      <template slot="footer"></template>
    </d2-container>

    <!--单个申请开票，合并申请开票-->
    <invoice-apply-create ref="createDialog" @ok="handleCreateSuccess"></invoice-apply-create>

    <!--批量开票-->
    <invoice-applyAll-create ref="createAllDialog" @ok="handleCreateSuccess"></invoice-applyAll-create>

    <!--如果是多个销售方，则需要先选择销售方-->
    <el-dialog title="选择销售方" :visible.sync="showServiceDialog" width="500px">
      <div style="padding: 30px 0">
        <el-form
          :model="serviceVo"
          ref="serviceForm">
          <el-form-item label="发票销售方：">
            <el-select
              v-model="serviceVo.corpId"
              placeholder="请选择"
              style="width: 350px"
              @change="changeService"
            >
              <el-option
                v-for="item in serviceOptions"
                :key="item.value"
                :label="item.text"
                :value="item.value"
                class="service-item"
              >
                <div class="service-item-div">
                  <p class="title">{{item.text}}</p>
                  <p class="desc">
                    <span>专票限额：{{item.specialInviceLimit|NumFormat }}元</span>
                    <span>普票限额：{{item.normalInviceLimit |NumFormat }}元</span>
                  </p>
                </div>
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>

    <!--查看发票-->
    <invoice-list ref="invoiceListDialog" @ok="handleCreateSuccess"></invoice-list>

  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";
  import StatisticalDataPanel from "@/components/StatisticalDataPanel";
  import InvoiceApplyCreate from "./componnets/InvoiceApplyCreate";
  import InvoiceApplyAllCreate from "./componnets/InvoiceApplyAllCreate";
  import InvoiceList from "./componnets/InvoiceList";

  export default {
    name: "InvoiceApply",
    components: {StatisticalDataPanel, InvoiceApplyCreate,InvoiceApplyAllCreate,InvoiceList},
    mixins: [
      listMixin
    ],
    data() {
      return {
        loading:false,
        siteId: null, // 站点ID
        statisticalData: null, // 统计数据
        tableMainSearchModelBase: {
          siteId: '',
          searchKey: '',

        },
        showDialog:false,
        tableMainSearchModel: {
          feeTypeId:'',
          applyStatus:'',
          billMonth:'',
          payStatus:''
        },
        tableData: [],
        siteOptions: [],
        feeTypeOptions:[],
        invoiceTypeOptions: [],
        currBillVo:{},
        serviceOptions:[],
        showServiceDialog:false,
        serviceVo:{
          corpId:'',
        },
        //选择服务商弹窗类型，“1”表示单独开票或者合并开票， “2” 批量开票
        changeDialogType:'',
      };
    },
    computed: {
      getActionWhere() {
        return {
          unionId: this.unionId
        }
      },
      unionId() {
        return this.$route.params.unionId
      }
    },
    methods: {
      changeSite(siteId) {
        // 加载统计数据
        this.$refs.tableMain && (this.$refs.tableMain.searchModelDataBase.siteId = siteId);
        this.$nextTick(() => {
          this.refreshStaticData();
          this.$refs.tableMain.fetchData();
        })
      },
      refreshStaticData() {
        const _this = this;
        this.$api.energy.invoice.apply.queryStatistics(this.siteId).then(res => {
          let result = [];
          if (res.data) {
            result = [
              {
                name: '已开发票',
                value: res.data.invoiced,
                unit: '项'
              },
              {
                name: '待开发票',
                value: res.data.unInvoiced,
                unit: '项'
              }
            ]
          }
          _this.statisticalData = result;
        })
      },
      // 申请开票,合并开票
      invoiceApply(item){
        this.changeDialogType =1;
        this.serviceVo.corpId='';
        if(item){
          //保存单独开票的信息，方便选择服务商时候使用
          this.currBillVo = item;
        }else{
          //清除单独开票的信息
          this.currBillVo = {id:null};
          let billVos = this.$refs.tableMain.checkedItems;
          //判断是否选择了记录，
          if(billVos.length==0){
            this.$message.error('请先勾选需要合并开票的费项');
            return;
          }
          console.log(billVos)
          //判断是否是同一个客户
          let firstBill = billVos[0],isSameCorp=true;
          billVos.map(billItem=>{
            if(firstBill.corpId != billItem.corpId){
              isSameCorp = false;
            }
          })
          if(!isSameCorp){
            this.$message.error('请选择同一个客户的费项进行合并开票');
            return;
          }
        }
        this.loadService();

      },
      //申请开票,合并开票 选择服务商
      changeService(value){
        let serviceVo = this.serviceOptions.filter(service=>{
          return service.value == this.serviceVo.corpId;
        })[0];
        if(this.changeDialogType==1){
          //单独开票或者合并开票
          if( this.currBillVo.id){
            this.showServiceDialog = false;
            //跳转到开票页面
            let vo = {
              siteId: this.siteId,
              corpId:this.currBillVo.corpId,
              billIds:[this.currBillVo.id],
            }

            this.$api.energy.invoice.apply.queryEnergyInfo(vo).then(res => {
              if(res.code==0){
                this.$refs.createDialog.open({
                  siteId:this.siteId,
                  billVos:res.data,
                  serviceVo,
                  billIds:[this.currBillVo.id],
                })
              }else{
                this.$message.error(res.message);
              }
            });

          }else{
            let invoiceTotal = 0;
            let billVos = this.$refs.tableMain.checkedItems;
            billVos.map(billItem=>{
              invoiceTotal += billItem.invoiceAmt;
            });
            //开票总额超过最大限额
            if(invoiceTotal > serviceVo.normalInviceLimit && invoiceTotal > serviceVo.specialInviceLimit ){
              this.serviceVo.corpId='';
              this.$message.error('合并开票总额不得超过最大限额');
              return;
            }
            //请求数据
            let vo = {
              siteId: this.siteId,
              corpId: billVos[0].corpId,
              billIds:billVos.map(bill=>{
                return bill.id
              }),
            }
            this.$api.energy.invoice.apply.queryEnergyInfo(vo).then(res => {
              if(res.code==0){
                this.showServiceDialog = false;
                this.$refs.createDialog.open({
                  siteId:this.siteId,
                  billVos:res.data,
                  serviceVo,
                  billIds:billVos.map(bill=>{
                    return bill.id
                  })
                })
              }else{
                this.$message.error(res.message);
              }
            });

          }
        }else if(this.changeDialogType==2){
          //跳转到批量开票页面
          let billVos = this.$refs.tableMain.checkedItems;
          this.showServiceDialog = false;
          this.$refs.createAllDialog.open({
            siteId:this.siteId,
            billIds:billVos.map(bill=>{
              return bill.id
            }),
            sellerCorpId:serviceVo.corpId,
          });

        }

      },
      // 批量申请
      applyMulti() {
        this.changeDialogType =2;
        this.serviceVo.corpId='';
        let billVos = this.$refs.tableMain.checkedItems;
        //判断是否选择了记录
        if(billVos.length==0){
          this.$message.error('请先勾选需要合并开票的费项');
          return;
        }
        this.loadService();

      },
      handleCreateSuccess(){
        this.changeSite(this.siteId)
      },
      //获取销售方列表
      loadService(){
        this.loading = true;
        //获取销售方列表
        this.$api.common.loadService(this.unionId).then(res => {
          if(res.code==0){
            let corpIds = res.data.map(corp=>{
              return corp.value
            });
            this.$api.energy.invoice.apply.queryCorpEtaxInfo(corpIds).then(response => {
              /*this.$message.error('获取单张发票最大限额失败！');
              response = {
                "code": 0,
                "data":[
                  {
                    "normalInviceLimit": 70,
                    "pageNormMonthInvoiceLimit": 400,
                    "pageProfMonthInvoiceLimit": 400,
                    "specialInviceLimit": 400,
                    corpId:corpIds[0],
                  },
                ],
                "message": "string",
                "success": true
              }*/
              if(response.code==0){
                let _serviceOptions = [];
                response.data.map(responseItem=>{
                  res.data.map(resItem=>{
                    if(resItem.value==responseItem.corpId){
                      _serviceOptions.push({
                        ...resItem,
                        ...responseItem
                      })
                    }
                  })
                });
                this.serviceOptions =_serviceOptions;
                //如果只有一个服务商，则直接选择这个服务商，如果超过一个，则由用户选择
                if(this.serviceOptions.length==1){
                  this.serviceVo.corpId = this.serviceOptions[0].corpId;
                  this.changeService(this.serviceVo.corpId);
                }else{
                  this.showServiceDialog = true;
                }
                this.loading = false;
              }else{
                this.$message.error(response.message);
                this.loading = false;
              }
            });
          }else{
            this.$message.error(res.message);
            this.loading = false;
          }
        });
      },
      viewInvoice(item){
        this.$refs.invoiceListDialog.open(item)
      },

    },
    mounted() {
      const _this = this;

      //获取站点列表
      setTimeout(()=>{
        this.$api.energy.invoice.config.siteOptions(this.unionId).then(res => {
          console.log(res);
          this.siteOptions = res.data;
          let siteId = res.data[0].siteId;
          this.siteId = siteId;
          this.changeSite(siteId);
        });
        this.$api.energy.invoice.apply.getFeeTypeList().then(res => {
          this.feeTypeOptions = res.data;
        });
      },0)

    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .el-dialog__body {
    padding: 0 20px;
  }

  .m-site-select {
    background-color: #f9f9f9;
    border-bottom: solid 1px #eee;
    margin: -20px -20px 20px -20px;
    padding: 10px;
  }
  .service-item{
    height: 40px;
    p{ margin: 0;}
    .service-item-div{
      .title{
        line-height: 22px;
        font-size: 14px;
      }
      .desc{
        line-height: 15px;
        font-size: 12px;
        color: #aaa;
        span{
          margin-right: 15px;
        }
      }
    }
  }
</style>
