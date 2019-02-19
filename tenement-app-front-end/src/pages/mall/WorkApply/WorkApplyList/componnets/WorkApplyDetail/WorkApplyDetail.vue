<template>
  <transition name="edit">
    <d2-container class="edit-panel" v-show="isVisible" v-loading="loading">
      <template slot="header">
        <i title="后退" class="el-icon-back" @click="back"></i>
        加班申请详情
      </template>
      <div class="applydetail-area">
        <el-row>
          <el-col :span="8">
            <div class="grid-content-top">
              <p class="key">加班申请数量</p>
              <p class="value">{{totalData.applyNum||0}}</p>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content-top">
              <p class="key">经营类加班</p>
              <p class="value">{{totalData.operateApplyNum||0}}</p>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content-top">
              <p class="key">工程类加班</p>
              <p class="value">{{totalData.engineeringApplyNum||0}}</p>
            </div>
          </el-col>
        </el-row>
        <!---->
        <table-comb
                name="加班申请详情列表"
                ref="tableMain"
                :search-model-base="tableMainSearchModelBase"
                :get-action="$api.mall.workApply.getWorkApplyDetail"
                :get-action-where="getActionWhere"
                :auto-fetch="false"
                :afterFetchData="afterFetchData"

        >
          <!--基础查询-->
          <template slot="baseSearchForm" slot-scope="scope">
            项目：
            <el-select
                    v-model="officeId"
                    placeholder="请选择"
                    style="width:150px;">
              <el-option
                      v-for="item in officeOptions"
                      :key="item.id"
                      :label="item.description"
                      :value="item.id">
              </el-option>
            </el-select>
            <span class="ml10">业态：</span>
            <el-cascader
                    placeholder="请选择"
                    :props="shopTypeProps"
                    :options="shopTypeOptions"
                    v-model="scope.form.shopTypeId"
                    :change-on-select="true"
                    :debounce="400"
                    clearable
                    style="width:170px;">
            </el-cascader>

            <span class="ml10"> 申请时间：</span>
            <el-date-picker
                    v-model="scope.form.searchStartEndDate"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd"
                    style="width: 226px;"
            >
            </el-date-picker>
            <el-input
                    placeholder="请输入店铺名称"
                    prefix-icon="el-icon-search"
                    clearable
                    v-model="scope.form.shopName"
                    style="width: 160px; margin-left: 10px;"
            >
            </el-input>
            <el-button class="fr" @click="exportExcel">EXCEL导出</el-button>
          </template>
          <!--表格-->
          <template slot="tableColumns">
            <el-table-column
                    prop="officeName"
                    label="项目名称">
            </el-table-column>
            <el-table-column
                    prop="shopName"
                    sortable="custom"
                    label="店铺名称">
            </el-table-column>
            <el-table-column prop="shopTypeLevelOneName" label="业态">
              <template slot-scope="props">
                {{props.row.shopTypeLevelOneName||'-'}} / {{props.row.shopTypeLevelTwoName||'-'}}
              </template>
            </el-table-column>
            <el-table-column
                    prop="applyNum"
                    sortable="custom"
                    label="加班申请总数"
                    width="200px">
            </el-table-column>
            <el-table-column
                    prop="operateApplyNum"
                    sortable="custom"
                    label="经营类加班数"
                    width="200px">
            </el-table-column>
            <el-table-column
                    prop="engineeringApplyNum"
                    sortable="custom"
                    label="工程类加班数"
                    width="200px">
            </el-table-column>
          </template>
        </table-comb>
        <template slot="footer"></template>
      </div>
    </d2-container>

  </transition>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";
  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "WorkApplyDetail",
    mixins: [
      listMixin
    ],
    data() {
      return {
        officeId:null,
        totalData:{
          applyNum:0,
          operateApplyNum:0,
          engineeringApplyNum:0
        },
        tableMainSearchModelBase:{
          officeOptions: [],
          shopTypeId:[],
          searchStartEndDate: '',
          shopName: '',
        },
        officeOptions:[],
        isVisible:false,
        shopTypeOptions:[],
        shopTypeProps:{
          value: 'id',
          label:'typeName',
          children: 'childLists'
        },
      };
    },
    computed: {
      getActionWhere(){
        return {
          officeId : this.officeId
        }
      },
      siteId() {
        return this.$route.params.siteId
      },
    },
    methods: {
      open(vo) {
        this.isVisible = true;
        this.loading = false;
        this.officeId = vo.officeId;
        //如果选择了时间，要设置查询条件的时间
        this.$refs.tableMain.searchModelDataBase.searchStartEndDate = vo.searchStartEndDate||'';
        setTimeout(()=>{
          //获取项目列表
          this.$api.merch.region.officeOptions(this.siteId).then(res => {
            this.officeOptions = res.data;
          });
          this.getShopTypeOptions();
          this.$refs.tableMain.fetchData();
        },0)
      },
      back() {
        this.isVisible = false
        this.$emit("ok");
        this.$refs.tableMain.fetchData();
      },
      //导出
      exportExcel(){
        if(this.$refs.tableMain.tableData.length==0){
          this.$message.error('没有可以导出的数据');
          return;
        }
        let vo = this.$refs.tableMain.getPageVo();
        vo.token = this.token;
        this.$api.mall.workApply.exportApplyDetailStatistic(vo)
      },

      //获取业态列表
      getShopTypeOptions(){
        this.$api.mall.workApply.shopTypeOptions(this.siteId).then(res => {
          this.shopTypeOptions = res.data;
        });
      },
      afterFetchData(){
        let searchVo = this.$refs.tableMain.getPageVo();
        this.$api.mall.workApply.getDetailTopStatisticData(searchVo).then(res => {
          this.totalData = res.data;
        });
      }

    },
    mounted() {
      const _this = this;

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
  .workapply-area .applydetail-area .table-filter{text-align: left}
</style>
