<template>
  <div class="passapply-area">
    <d2-container>
      <template slot="header">放行申请</template>
      <el-row>
        <el-col :span="8">
          <div class="grid-content-top">
            <p class="key">项目数</p>
            <p class="value">{{totalData.officeNum||0}}</p>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="grid-content-top">
            <p class="key">店铺数</p>
            <p class="value">{{totalData.shopNum||0}}</p>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="grid-content-top">
            <p class="key">放行申请</p>
            <p class="value">{{totalData.applyNum||0}}</p>
          </div>
        </el-col>
      </el-row>
      <!---->
      <table-comb
              name="放行申请列表"
              ref="tableMain"
              :search-model-base="tableMainSearchModelBase"
              :get-action="$api.mall.passApply.getPassApplyList"
              :get-action-where="getActionWhere"
              :afterFetchData="afterFetchData"
      >
        <!--基础查询-->
        <template slot="baseSearchForm" slot-scope="scope">
           <div class="fl fwb">放行统计汇总</div>
          申请时间:
          <el-date-picker
                  v-model="scope.form.searchStartEndDate"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  style="width: 250px;"
          >
          </el-date-picker>
              <el-input
                      placeholder="请输入项目名称"
                      prefix-icon="el-icon-search"
                      clearable
                      v-model="scope.form.officeName"
                      style="width: 270px; margin-left: 10px;"
              >
              </el-input>
              <el-button class="fr ml10" @click="exportExcel">EXCEL导出</el-button>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column
                  prop="officeName"
                  label="项目名称">
          </el-table-column>
          <el-table-column
                  prop="shopNum"
                  sortable="custom"
                  label="店铺数">
          </el-table-column>
          <el-table-column
                  prop="applyNum"
                  sortable="custom"
                  label="放行申请总数">
          </el-table-column>
          <el-table-column
                  prop="retailApplyNum"
                  sortable="custom"
                  label="零售类放行数">
          </el-table-column>
          <el-table-column
                  prop="decorateApplyNum"
                  sortable="custom"
                  label="装修类放行数">
          </el-table-column>
          <el-table-column label="操作" width="200px">
            <template slot-scope="props">
              <el-button type="text" size="mini" @click="openPassListDialog(props.row)">查看详情</el-button>
            </template>
          </el-table-column>
        </template>
      </table-comb>
      <template slot="footer"></template>
    </d2-container>
    <pass-apply-detail ref="passListDialog"></pass-apply-detail>

  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";
  import PassApplyDetail from "./componnets/PassApplyDetail";
  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "PassApplyList",
    components: {PassApplyDetail},
    mixins: [
      listMixin
    ],
    data() {
      return {
        totalData:{
          officeNum:0,
          shopNum:0,
          applyNum:0
        },
        tableMainSearchModelBase:{
          searchStartEndDate: '',
          officeName: '',
        },
      };
    },
    computed: {
        getActionWhere(){
            return {
              siteId : this.siteId
            }
        },
        siteId() {
            return this.$route.params.siteId
        },
  },
    methods: {
      //导出
      exportExcel(){
        if(this.$refs.tableMain.tableData.length==0){
          this.$message.error('没有可以导出的数据');
          return;
        }
        let vo = this.$refs.tableMain.getPageVo();
        vo.token = this.token;
        this.$api.mall.passApply.exportApplyStatistic(vo)
      },

      // 查看详情页面
      openPassListDialog(row) {
        let pageVo = this.$refs.tableMain.getPageVo();
        //如果选择了时间，要把时间带过去详情页面
        if(pageVo.searchStartEndDate){
          row.searchStartEndDate = pageVo.searchStartEndDate
        } else {
            delete row.searchStartEndDate
        }
        this.$refs.passListDialog.open(row);
      },
      afterFetchData(){
        let searchVo = this.$refs.tableMain.getPageVo();
        this.$api.mall.passApply.getTopStatisticData(searchVo).then(res => {
          this.totalData = res.data;
        });
      }
    },
    mounted() {
      
    }
  };
</script>


<style type="text/scss" lang="scss" scoped>
  .grid-content-top{
    text-align: center;
    margin-bottom: 20px;
    .key{
      font-size: 14px;
      margin: 0;
      color:#aaa;
    }
    .value{
      margin:10px 0;
      font-size: 24px;
      font-weight: 700;
    }
  }
  .el-col+.el-col .grid-content-top{border-left: 1px solid #e5e5e5;}
</style>
<style>
  .passapply-area .table-filter{text-align: right}
  .passapply-area .table-filter .fl{line-height: 40px}
  .passapply-area .table-filter .fwb{font-weight: bold}
  .passapply-area .d2-container-full__body{padding:0}
  .passapply-area .el-row{  background-color: #f2f9ff;
    border-radius: 3px;
    padding: 20px 0 0 0;}
  .passapply-area .m-table-comb{padding:20px 0 0 0}
</style>
