<template>
  <div>
    <d2-container>
      <template slot="header">能耗开通管理</template>
      <br>
      <!---->
      <table-comb
        name="企业列表"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :get-action="$api.energy.ecaSystemSiteRel.getEcaPageData"
        :get-action-where="getActionWhere"
        custom-id="corpId"
        checkedDispalyName="corpName"
      >
        <!--高级查询-->
        <template slot="baseSearchForm" slot-scope="props">
          <el-input
            placeholder="搜索企业名称"
            prefix-icon="el-icon-search"
            clearable v-model="props.form.corpName"
            style="width: 160px; margin-left: 10px;"
          >
          </el-input>
          <el-button class="fr" @click="batchOpenEcaService()">批量开通</el-button>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="corpName" label="企业名称"></el-table-column>
          <el-table-column prop="openFlag" label="状态">
              <template slot-scope="props">{{props.row.openFlag==1?'已开通':'未开通'}}</template>
          </el-table-column>
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
    name: "EnergyOpen",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      return {
        tableMainSearchModelBase:{
          corpName: ''
        },
        tableData: [],
        showLabelDialog:false,
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
      // 批量开通
      batchOpenEcaService(){
        let checkedItemIds = [];
        this.$refs.tableMain.checkedItems.map(function(item){
          if(item.openFlag==0){
            checkedItemIds.push(item.corpId);
          }
        })
        console.log('批量开通Ids',checkedItemIds);

        if(checkedItemIds.length==0){
          this.$message.error('没有选中未开通的企业');
          return false;
        }

        this.$api.energy.ecaSystemSiteRel.batchOpenEcaService({
          corpIds:checkedItemIds
        }).then(res => {
          if(res.code==0){
            let message = '开通成功！'
            this.$message({
              message,
              type: 'success'
            });
            //更新列表
            this.$refs.tableMain.fetchData()
          }else{
            this.$message.error(res.message);
          }

        });

      },
    },
    mounted() {
      const _this = this;
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .el-tag{ margin-right: 10px; cursor: pointer;}
  .el-dialog__body{ padding: 0 20px;}
</style>
