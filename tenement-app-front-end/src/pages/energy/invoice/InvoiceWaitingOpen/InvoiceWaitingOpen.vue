<template>
  <div>
    <d2-container>
      <template slot="header">待开单据</template>
      <br>
      <!---->
      <table-comb
        name="企业列表"
        ref="tableMain"
        :autoFetch="false"
        :search-model-base="tableMainSearchModelBase"
        :get-action="$api.energy.invoice.invoiceWaitingOpen.list"
        :get-action-where="getActionWhere"
      >
        <!--基础查询-->
        <template slot="baseSearchForm" slot-scope="props">
              <el-select
                v-model="props.form.siteId"
                filterable
                placeholder="请选择站点"
                @change="changeSite"
                style="width: 300px;">
                <el-option
                  v-for="item in siteOptions"
                  :key="item.siteId"
                  :label="item.name"
                  :value="item.siteId">
                </el-option>
              </el-select>
              <span class="ml10">发票类型：</span>
              <el-radio-group v-model="props.form.invoiceType" class="ml10">
                <el-radio :label="1">专票</el-radio>
                <el-radio :label="2">普票</el-radio>
              </el-radio-group>
              <el-input
                placeholder="发票抬头"
                prefix-icon="el-icon-search"
                clearable
                v-model="props.form.buyerName"
                style="width: 200px;"
                class="ml10">
              </el-input>
          <el-button class="fr" @click="bacthGoToInvoice()">去开票</el-button>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column type="selection" width="55px"></el-table-column>
          <el-table-column fixed="left" label="操作" width="120px">
            <template slot-scope="props">
              <el-button type="text" size="mini" @click="revokeInvoice(props.row)">撤销</el-button>
              <el-tooltip  v-if="props.row.errorInfo"  effect="dark" placement="top">
                <div slot="content">上次开票失败！<el-button  type="text" size="mini" @click="showErrorMessage(props.row)">查看失败原因</el-button></div>
                <el-button type="text" size="mini" @click="goToInvoice(props.row)">重新开票</el-button>
              </el-tooltip>
              <el-button v-else type="text" size="mini" @click="goToInvoice(props.row)">去开票</el-button>
            </template>
          </el-table-column>
          <el-table-column
            prop="billNo"
            label="业务流水号">
          </el-table-column>
          <el-table-column
            prop="applyTimeStr"
            width="120px"
            label="申请日期">
          </el-table-column>
          <el-table-column
            prop="invoiceType"
            width="60px"
            label="类型">
          </el-table-column>
          <el-table-column
            prop="noTaxMoney"
            width="120px"
            label="不含税金额">
          </el-table-column>
          <el-table-column
            prop="tax"
            width="100px"
            label="税额">
          </el-table-column>
          <el-table-column
            prop="taxRate"
            width="80px"
            label="税率">
          </el-table-column>
          <el-table-column
            prop="totalMoney"
            width="120px"
            label="价税合计">
          </el-table-column>
          <el-table-column
            prop="buyerName"
            label="发票抬头企业/个人">
          </el-table-column>
        </template>
      </table-comb>

      <template slot="footer"></template>
    </d2-container>
    <el-dialog title="错误信息" :visible.sync="errorDialog">
      <div style="padding: 20px 0; line-height: 30px; ">{{errorMessageVo.errorInfo}}</div>
    </el-dialog>
  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";
  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "InvoiceWaitingOpen",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      return {
        tableMainSearchModelBase:{
          buyerName: '',
          siteId: '',
          invoiceType: 1,
        },
        tableData: [],
        siteOptions: [],
        errorMessageVo:{},
        errorDialog:false,
      };
    },
    computed: {
      getActionWhere(){
        return {
          unionId : this.unionId
        }
      },
      unionId() {
        return this.$route.params.unionId
      },
    },
    methods: {
      //切换站点
      changeSite(siteId){
          //刷新列表
          this.$refs.tableMain.fetchData();
      },
      //撤销操作
      revokeInvoice(item){
          console.log(item.row);
          const _this = this;
          this.$msgbox({
              title: '提示',
              message: `是否撤销当前发票？`,
              showCancelButton: true,
              confirmButtonText: '确定',
              cancelButtonText: '取消'
          }).then(action => {
              if (action === 'confirm') {
              this.$api.energy.invoice.invoiceWaitingOpen.revokeInvoice(item.id).then(res => {
                  if (res.code === 0) {
                  _this.$message.success(`发票撤销成功`)
                  //更新列表
                  _this.$refs.tableMain.fetchData()
              } else {
                  _this.$message.error(res.message);
              }
          }).catch(() => {
                  _this.$message.error(`撤销当前发票异常`)
          })
          }
      }).catch(() => {
          })
      },
      // 去开票
      goToInvoice(item){
        let checkedItemIds = [];
        checkedItemIds.push(item.id);
        console.log('去开票Ids',checkedItemIds);

        if(checkedItemIds.length==0){
          this.$message.error('待开单据Id为空');
          return false;
        }

        this.$api.energy.invoice.invoiceWaitingOpen.checkInvoiceStatus({
          idList:checkedItemIds
        }).then(res => {
          if(res.code==0){
            this.$api.energy.invoice.invoiceWaitingOpen.batchGoToInvoice({
              idList:checkedItemIds
            }).then(res => {
              if(res.code==0){
                if(res.data.reqUrl != null && res.data.reqParam != null) {
                  this.$api.energy.invoice.invoiceWaitingOpen.jumpToInvoicePreview(res.data.reqUrl,res.data.reqParam);
                } else {
                  this.$message.error('发票预览链接异常！');
                }
                //更新列表
                this.$refs.tableMain.fetchData()
              }else{
                this.$message.error(res.message);
              }

            });
          }else{
            this.$message.error(res.message);
            //更新列表
            this.$refs.tableMain.fetchData()
          }

        });
      },
      //显示错误信息
      showErrorMessage(item){
        this.errorDialog = true;
        this.errorMessageVo = item;
      },
      // 批量去开票
      bacthGoToInvoice(){
        let checkedItemIds = [];
        this.$refs.tableMain.checkedItems.map(function(item){
           checkedItemIds.push(item.id);
        })
        console.log('去开票Ids',checkedItemIds);

        if(checkedItemIds.length==0){
          this.$message.error('没有选中待开单据');
          return false;
        }

        this.$api.energy.invoice.invoiceWaitingOpen.checkInvoiceStatus({
          idList:checkedItemIds
        }).then(res => {
          if(res.code==0){
            this.$api.energy.invoice.invoiceWaitingOpen.batchGoToInvoice({
              idList:checkedItemIds
            }).then(res => {
              if(res.code==0){
                if(res.data.reqUrl != null && res.data.reqParam != null) {
                  this.$api.energy.invoice.invoiceWaitingOpen.jumpToInvoicePreview(res.data.reqUrl,res.data.reqParam);
                } else {
                  this.$message.error('发票预览链接异常！');
                }
                //更新列表
                this.$refs.tableMain.fetchData()
              }else{
                this.$message.error(res.message);
              }
            });

          }else{
            this.$message.error(res.message);
            //更新列表
            this.$refs.tableMain.fetchData()
          }

        });
      },
    },
    mounted() {
      //获取站点列表
      setTimeout(()=>{
              this.$api.energy.invoice.config.siteOptions(this.unionId).then(res => {
          let siteId = res.data[0].siteId
          this.$refs.tableMain.searchModelDataBase.siteId = siteId;
          this.siteOptions = res.data;
          this.changeSite(siteId);
        });
      },0)
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .el-tag{ margin-right: 10px; cursor: pointer;}
  .el-dialog__body{ padding: 0 20px;}
</style>
