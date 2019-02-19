<template>
  <div>
    <d2-container>
      <template slot="header">收支明细</template>
      <br>
      <!---->
      <table-comb
        name="收支明细"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :search-model="tableMainSearchModel"
        :get-action="$api.energy.payRec.list"
        :get-action-where="getActionWhere"
        :autoFetch="false"
      >
        <!--基础查询-->
        <template slot="baseSearchForm" slot-scope="props">
          <el-select
            v-model="props.form.siteId"
            @change="changeSite"
            filterable
            placeholder="请选择站点"
            style="width: 300px;">
            <el-option
              v-for="item in siteOptions"
              :key="item.siteId"
              :label="item.name"
              :value="item.siteId">
            </el-option>
          </el-select>
          <el-input
            placeholder="业务流水号/支付流水号/客户名称/联系人/电话"
            prefix-icon="el-icon-search"
            clearable
            v-model="props.form.searchKey"
            style="width: 360px; margin-left: 10px;"
          >
          </el-input>
          <el-button class="fr" @click="exportExcel">详情导出</el-button>
        </template>
        <!--高级查询-->
        <template slot="advancedSearchForm" slot-scope="props">
          <el-row>
            <el-col :span="8">
              <el-form-item label="缴费时间：">
                <el-date-picker
                  v-model="props.form.startEndTime"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  style="width: 280px;"
                  end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="支付方式：">
                <el-select
                  v-model="props.form.payMode"
                  @change="changeSite"
                  filterable
                  placeholder="请选择"
                  style="width: 220px;">
                  <el-option
                    v-for="item in payModeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column width="180px" prop="billNo" label="业务流水号"></el-table-column>
          <el-table-column width="180px" prop="externalId" label="支付流水号"></el-table-column>
          <el-table-column width="180px" prop="thirdPayId" label="第三方流水号"></el-table-column>
          <el-table-column width="250px" prop="corpName" label="客户名称"></el-table-column>
          <el-table-column width="150px" prop="telephone" label="联系人">
              <template slot-scope="props">
                  {{(props.row.corpContacts||'--')+'/'+(props.row.telephone||'--')}}
              </template>
          </el-table-column>
          <el-table-column prop="billFee" label="付款金额"></el-table-column>
          <el-table-column prop="paydate"  width="160" label="缴费时间">
              <template slot-scope="props">
                  {{props.row.paydate + ' '+ props.row.paytime}}
              </template>
          </el-table-column>
          <el-table-column prop="payModeStr" label="支付方式"></el-table-column>
        </template>
      </table-comb>
      <template slot="footer"></template>
    </d2-container>
  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "PayRec",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      return {
        tableMainSearchModelBase:{
          siteId: '',
          searchKey:'',
        },
        tableMainSearchModel:{
          startEndTime:'',
          transType:'',

        },
        siteOptions:[],
        payModeOptions:[
          {value: '', label: '全部'},
          {value: '0', label: '现金'},
          {value: '1', label: '支付宝'},
          {value: '2', label: '银行转账'},
          {value: '3', label: '微信支付'},
          {value: '4', label: '余额支付'},
          {value: '5', label: '线下支付'},
        ],
        tableData: [],
        showSaveDialog:false,
      };
    },
    computed: {
      getActionWhere(){
        return {
          unionId : this.unionId,
        }
      },
      unionId() {
        return this.$route.params.unionId
      },
    },
    methods: {

      //导出
      exportExcel(){
        let vo = this.$refs.tableMain.getPageVo();
        console.log(vo);
        this.$api.energy.payRec.exportExcel(vo)
      },
      //切换站点
      changeSite(siteId){
        this.$api.merch.region.officeOptions(siteId).then(res => {
          this.$refs.tableMain.fetchData();
        });
      },
    },
    mounted() {
      //获取站点列表
      setTimeout(()=>{
        this.$api.merch.region.siteOptions(this.unionId).then(res => {
          let siteId = res.data[0].siteId
          this.$refs.tableMain && (this.$refs.tableMain.searchModelDataBase.siteId = siteId)
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
