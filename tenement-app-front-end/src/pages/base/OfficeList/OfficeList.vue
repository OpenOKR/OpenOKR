<template>
  <div>
    <d2-container>
      <template slot="header">地点管理</template>
      <statistical-data-panel :data-list="statisticalData"/>
      <br>
      <!---->
      <table-comb
        name="地点列表"
        ref="tableMain"
        :search-model="tableMainSearchModel"
        :get-action="$api.merch.office.list"
        :get-action-where="{unionId:unionId}"
        :remove-action="$api.merch.office.delete"
        @on-remove-success="refreshOfficeStaticData">
        <!--高级查询-->
        <template slot="advancedSearchForm" slot-scope="props">
          <el-row>
            <el-col :span="8">
              <el-form-item label="所属园区">
                <el-select
                  v-model="props.form.siteIds"
                  multiple
                  filterable
                  default-first-option
                  placeholder="请选择园区"
                  style="width: 100%;">
                  <el-option
                    v-for="item in siteOptions"
                    :key="item.siteId"
                    :label="item.name"
                    :value="item.siteId">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </template>
        <!--控制按钮-->
        <template slot="controls">
          <el-button type="primary" @click="openEditDialog">
            <i class="iconfont icon-add"></i> 新增
          </el-button>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column
            width="360"
            label="地点名称">
            <template slot-scope="props">
              <div class="office-card">
                  <img :src="props.row.httpPic" class="thumb" v-if="props.row.httpPic"/>
                <div class="title">{{props.row.name}}</div>
                <div class="desc">{{props.row.address + props.row.addr}}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="siteName"
            label="所属园区">
          </el-table-column>
          <el-table-column
            prop="proportion"
            sortable="custom"
            label="物业面积(㎡)">
          </el-table-column>
          <el-table-column
            prop="stationCount"
            sortable="custom"
            label="单元数量">
          </el-table-column>
          <el-table-column
            prop="parkingCount"
            sortable="custom"
            label="车位数量">
          </el-table-column>
          <el-table-column
            width="220"
            label="操作">
            <template slot-scope="props">
              <el-button-group>
                <el-button size="mini" @click="openEditDialog(props.row)"> 编辑</el-button>
                <el-button size="mini" @click="openDetailDialog(props.row)"> 查看详情</el-button>
                <el-button type="danger" size="mini" @click="$refs.tableMain.remove(props.row.id)"> 删除</el-button>
              </el-button-group>
            </template>
          </el-table-column>
        </template>
      </table-comb>

      <template slot="footer"></template>
    </d2-container>

    <office-detail ref="detailDialog" @ok="handleUpdateSuccess" :siteOptions="siteOptions"></office-detail>
    <office-edit ref="editDialog" @ok="handleUpdateSuccess" :siteOptions="siteOptions"></office-edit>
  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";
  import StatisticalDataPanel from "@/components/StatisticalDataPanel";
  import OfficeEdit from "./componnets/OfficeEdit";
  import OfficeDetail from "./componnets/OfficeDetail";

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "OfficeList",
    components: {StatisticalDataPanel, OfficeEdit, OfficeDetail},
    mixins: [
      listMixin
    ],
    data() {
      return {
        statisticalData: null,
        tableMainSearchModel: {
          siteIds: [],
        },
        tableData: [],
        siteOptions: [],
        officeOptions: [],
      };
    },
    computed: {
      unionId() {
        return this.$route.params.unionId
      },
    },
    methods: {
      // 打开新增/编辑页面
      openEditDialog(vo) {
        this.$refs.editDialog.open(vo);
      },
      // 查看详情页面
      openDetailDialog(vo) {
        this.$refs.detailDialog.open(vo);
      },
      handleUpdateSuccess() {
        this.$refs.tableMain.fetchData();
        this.refreshOfficeStaticData();
      },
      refreshOfficeStaticData(){
        const _this = this;
        this.$api.merch.office.officeStatic(this.unionId).then(res => {
          let result = [];
          if (res.data) {
            result = [
              {
                name: '地点',
                value: res.data.totalCount,
                unit: '个'
              }, {
                name: '面积',
                value: res.data.proportionCount,
                unit: '㎡'
              }, {
                name: '单元数量',
                value: res.data.stationCount,
                unit: '个'
              }, {
                name: '车位数量',
                value: res.data.parkingCount,
                unit: '个'
              },
            ]
          }
          _this.statisticalData = result;
        })
      }
    },
    mounted() {
      const _this = this;
      // 加载统计数据
      this.refreshOfficeStaticData();

      this.$api.merch.region.siteOptions(this.unionId).then(res => {
        _this.siteOptions = res.data;
      });
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .office-card {
    display: block;
    .thumb {
      float: left; display: inline; width: 44px; height: 44px; margin-right: -44px;
    }
    .title {
      margin-left: 54px; line-height: 28px; font-size: 16px; color: #333;
    }
    .desc {
      margin-left: 54px; line-height: 16px; font-size: 12px; color: #999;
    }
  }
</style>
