<template>
  <div>
    <d2-container>
      <template slot="header">
        开票记录查询
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
          :get-action="$api.energy.invoice.record.list"
          :get-action-where="getActionWhere"
          :search-model-base="tableMainSearchModelBase"
          @on-remove-success="refreshStaticData"
          name="开票记录列表"
          ref="tableMain"
          v-show="siteId">
          <!--高级查询-->
          <template slot="baseSearchForm" slot-scope="scope">
            <el-input
              clearable
              placeholder="购方名称关键字"
              prefix-icon="el-icon-search"
              style="width: 160px; margin-right: 10px;"
              v-model="scope.form.keyWord">
            </el-input>&emsp;
            发票类型：
            <el-select filterable clearable placeholder="发票类型" style="width: 190px;" v-model="scope.form.invoiceType">
              <el-option :key="item.value" :label="item.value" :value="item.key" v-for="item in invoiceTypeOptions"></el-option>
            </el-select>&emsp;
            开票时间：
            <el-date-picker
              format="yyyy-MM-dd HH:mm"
              placeholder="开始时间"
              style="width: 190px;"
              type="datetime"
              v-model="scope.form.startTimeStr">
            </el-date-picker>
            ~
            <el-date-picker
              format="yyyy-MM-dd HH:mm"
              placeholder="结束时间"
              style="width: 190px;"
              type="datetime"
              v-model="scope.form.endTimeStr">
            </el-date-picker>
          </template>
          <!--表格-->
          <template slot="tableColumns">
            <el-table-column
              fixed="left"
              label="操作"
              width="180">
              <template slot-scope="props">
                <el-button-group>
                  <el-button size="mini" v-if="props.row.invoiceStatus==1||props.row.invoiceStatus==8" @click="viewInvoice(props.row)">查看发票</el-button>
                  <el-button size="mini" v-if="props.row.invoiceStatus==1" @click="revokeInvoice(props.row)">发票作废</el-button>
                </el-button-group>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              label="发票状态"
              prop="invoiceStatusStr"
              width="80">
            </el-table-column>
            <el-table-column
              label="操作人"
              prop="operateUserName"
              width="100">
            </el-table-column>
            <el-table-column
              label="开票时间"
              prop="invoiceTimeStr"
              width="140">
            </el-table-column>
            <el-table-column
              label="发票号码"
              prop="invoiceNo"
              width="100">
            </el-table-column>
            <el-table-column
              label="发票类型"
              prop="invoiceTypeStr"
              width="100">
            </el-table-column>
            <el-table-column
              label="金额"
              prop="noTaxMoney"
              :formatter="formatter"
              width="200">
            </el-table-column>
            <el-table-column
              label="税额"
              prop="tax"
              :formatter="formatter"
              width="200">
            </el-table-column>
            <el-table-column
              label="价税合计"
              prop="totalMoney"
              :formatter="formatter"
              width="100">
            </el-table-column>
            <el-table-column
              label="购买方名称（发票抬头）"
              prop="buyerName"
              width="250">
            </el-table-column>
            <el-table-column
              align="center"
              label="购买方类型"
              prop="buyerTypeStr"
              width="100">
            </el-table-column>
          </template>
        </table-comb>
      </transition>

      <template slot="footer"></template>
    </d2-container>

    <el-dialog title="查看发票" :visible.sync="showDialog" width="90%"  >
      <iframe :src="viewInvoiceUrl" frameborder="0" id="contentIframe" style="width: 100%;height:400px;"></iframe>
    </el-dialog>

  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";
  import StatisticalDataPanel from "@/components/StatisticalDataPanel";

  export default {
    name: "InvoiceRecord",
    components: {StatisticalDataPanel},
    mixins: [
      listMixin
    ],
    data() {
      return {
        siteId: null, // 站点ID
        statisticalData: null, // 统计数据
        showDialog:false,
        viewInvoiceUrl:'',
        tableMainSearchModelBase: {
          siteId: '',
          keyWord: '',
          invoiceType: '',
          startTimeStr: '',
          endTimeStr: '',
        },
        tableData: [],
        siteOptions: [],
        invoiceTypeOptions: [],
        reg: /(?=(\B)(\d{3})+$)/g
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
      },
    },
    methods: {
      changeSite(siteId) {
        // 加载统计数据
        this.refreshStaticData()
        this.tableMainSearchModelBase = {
          siteId: siteId,
          keyWord: '',
          invoiceType: '',
          startTimeStr: '',
          endTimeStr: '',
        }
        this.$nextTick(() => {
          this.$refs.tableMain.fetchData(1);
        })
      },
      refreshStaticData() {
        const _this = this;
        this.$api.energy.invoice.record.queryCurrentMonthCollect(this.siteId).then(res => {
          let result = [];
          if (res.data) {
            result = [
              {
                name: '本月已开发票',
                value: `${res.data.currentMonthCount || 0}张`,
                unit: '张'
              },
              {
                name: '本月已开金额',
                value: `${res.data.currentMonthTotal || 0.00}万元`,//res.data.currentMonthTotal,
                unit: '万元'
              },
              {
                name: '本月增值税专用发票',
                value: `${res.data.specialCount || 0}张/${res.data.specialTotal || 0.00}万元`,
                unit: ''
              },
              {
                name: '本月增值税普通发票',
                value: `${res.data.generalCount || 0}张/${res.data.generalTotal || 0.00}万元`,
                unit: ''
              },
            ]
          }
          _this.statisticalData = result;
        })
      },
      viewInvoice(item){
          console.log(item);
          this.$api.energy.invoice.record.getInvoiceInfoUrlById(item.id).then(res => {
              console.log(item.row);
              //弹窗内嵌
              this.viewInvoiceUrl = res.data;
              //top.window.$.yqb_addTab("work_exception", "查看发票","https://www.baidu.com", !0);
              this.showDialog = true;


          });
      },
      revokeInvoice(item){
          console.log(item.row);
          const _this = this;
          this.$msgbox({
              title: '提示',
              message: `是否作废当前发票？`,
              showCancelButton: true,
              confirmButtonText: '确定',
              cancelButtonText: '取消'
          }).then(action => {
              if (action === 'confirm') {
                  this.$api.energy.invoice.record.revokeInvoiceById(item.id).then(res => {
                      if (res.code === 0) {
                          _this.$message.success(`发票作废已受理`)
                          _this.$refs.tableMain.fetchData(1);
                      } else {
                          _this.$message.error(res.message);
                      }
                  }).catch(() => {
                      _this.$message.error(`作废当前发票异常`)
                  })
              }
          }).catch(() => {
          })
      },
      formatter(row, column) {
          let s = row[column.property].toString();
          if(/[^0-9\.]/.test(s)) return "invalid value";
          s=s.replace(/^(\d*)$/,"$1.");
          s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
          s=s.replace(".",",");
          const re=/(\d)(\d{3},)/;
          while(re.test(s)){
              s=s.replace(re,"$1,$2");
          }
          s=s.replace(/,(\d\d)$/,".$1");
          return s.replace(/^\./,"0.");


          // const str = row[column.property].toString();
          // return str.replace(this.reg,",");
      },
    },
    mounted() {
      const _this = this;

      this.$api.energy.invoice.record.siteOptions(this.unionId).then(res => {
        _this.siteOptions = res.data;
      });
      this.$api.energy.invoice.record.getInvoiceType().then(res => {
        _this.invoiceTypeOptions = res.data;
      });
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
</style>
