<template>
  <transition name="edit">
    <d2-container v-show="isVisible" v-loading="loading" class="edit-panel">
      <template slot="header">
        <i title="后退" class="el-icon-back" @click="goBack()"></i>
        企业入驻记录
      </template>
      <el-row style="padding: 30px 0;" v-if="stationVo">
        <el-col :span="8">地点：{{stationVo.officeName}}</el-col>
        <el-col :span="8">区域：{{stationVo.levelName}} {{stationVo.level2Name}}</el-col>
        <el-col :span="8">单元：{{stationVo.stationName}}</el-col>

      </el-row>
      <table-comb
        name="企业入驻记录列表"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :get-action="$api.tet.stationCorp.stationHistory"
        :get-action-where="getActionWhere"
        :remove-action="$api.tet.stationCorp.deleteStationCorp"
        :autoFetch="false"
      >
        <template slot="baseSearchForm" slot-scope="props">
          <el-input
            placeholder="搜索企业名称"
            prefix-icon="el-icon-search"
            clearable
            v-model="props.form.searchKey"
            style="width: 160px; margin-left: 10px;"
          >
          </el-input>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column prop="corpName" label="企业名称"></el-table-column>
          <el-table-column prop="settleStartTime" label="入驻开始时间">
            <template slot-scope="props">
              {{new Date(props.row.settleStartTime) | dateFormat('yyyy-MM-dd')}}
            </template>
          </el-table-column>
          <el-table-column prop="settleEndTime" label="入驻结束时间">
            <template slot-scope="props">
              {{new Date(props.row.settleEndTime) | dateFormat('yyyy-MM-dd')}}
            </template>
          </el-table-column> `
          <el-table-column fixed="right" label="操作">
          <template slot-scope="props">
            <el-button type="text" size="mini" @click="deleteItem(props.row)">删除</el-button>
          </template>
        </el-table-column>
        </template>
      </table-comb>
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
        tableMainSearchModelBase:{
          searchKey:''
        },
        stationVo:null,
        stationId:null,
      };
    },
    computed: {
      getActionWhere(){
        return {
          unionId : this.unionId,
          stationId : this.stationId
        }
      },
      unionId() {
        return this.$route.params.unionId
      },
    },
    methods: {
      open(customer) {
        this.isVisible = true;
        this.loading = false;
        this.stationVo = customer;
        this.stationId = customer.stationId;
        setTimeout(()=>{
          this.$refs.tableMain.fetchData();
        },0)
      },
      deleteItem(item){
        this.$refs.tableMain.remove(item.id);
      },
      goBack(){
        this.isVisible = false;
        this.$emit('fetchData')
      },
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
</style>
