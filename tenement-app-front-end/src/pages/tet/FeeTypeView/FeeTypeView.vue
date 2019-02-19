<template>
  <div>
    <d2-container>
      <template slot="header">物业费项</template>
      <br>
      <!---->
      <table-comb
        name="物业费项类型列表"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :get-action="$api.tet.feeType.getFeeTypeList"
        :get-action-where="getActionWhere"
        :remove-action="$api.tet.feeType.deleteFeeType"
      >
        <!--高级查询-->
        <template slot="baseSearchForm" slot-scope="props">
          <el-input placeholder="搜索费项名称" prefix-icon="el-icon-search" clearable v-model="props.form.description" style="width: 160px; margin-left: 10px;"></el-input>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column prop="description" label="费项名称"></el-table-column>
          <el-table-column prop="sourceName" label="费项来源"></el-table-column>
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
    name: "FeeType",
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
    },
    mounted() {
      const _this = this;
    }
  };
</script>

