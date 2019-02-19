<template>
  <div>
    <d2-container>
      <template slot="header" v-if="isPage">区域管理</template>
      <div class="station-search" v-if="isPage">
        <el-form
          ref="formBase"
          :model="tableBaseSearchModel"
          label-position="right"
          class="form-main"
          label-width="100px">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="园区：">
                <el-select
                  v-model="tableBaseSearchModel.siteId"
                  filterable
                  default-first-option
                  @change="handleSiteChange"
                  :disabled="!isPage"
                  placeholder="请选择园区"
                  style="width: 100%;">
                  <el-option
                    v-for="item in siteOptions"
                    :key="item.siteId"
                    :label="item.name"
                    :value="item.siteId"
                    value-key="siteId">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="地点：">
                <el-select
                  v-model="tableBaseSearchModel.officeId"
                  filterable
                  default-first-option
                  :disabled="!isPage"
                  placeholder="请选择地点"
                  style="width: 100%;">
                  <el-option
                    v-for="item in officeOptions"
                    :key="item.id"
                    :label="item.description"
                    :value="item.id"
                    value-key="id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8" v-if="isPage">
              <el-button type="primary" @click="formBaseSearch">查询</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!---->
      <table-comb
        name="区域管理"
        ref="tableMain"
        :get-action="$api.merch.region.list"
        :get-action-where="this.tableBaseSearchModel"
        :auto-fetch="false"
        :remove-action="$api.merch.region.delete">
        <!--高级查询-->
        <template slot="advancedSearchForm" slot-scope="props">
        </template>
        <!--控制按钮-->
        <template slot="controls">
          <el-button type="primary" @click="openCreateDialog">
            <i class="iconfont icon-add"></i> 新增
          </el-button>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column
            label="区域名称">
            <template slot-scope="props">
              <div class="region-card">
                <img :src="props.row.httpPathList[0]" :alt="props.row.name" v-if="props.row.httpPathList && props.row.httpPathList.length > 0" class="thumb"/>
                <div class="title">{{props.row.name}}</div>
                <div class="desc">{{props.row.childLists.map(item => item.name).join(' | ')}}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            width="160"
            label="操作">
            <template slot-scope="props">
              <el-button-group>
                <el-button
                  size="mini"
                  @click="openEditDialog(props.row)"> 编辑
                </el-button>
                <el-button
                  type="danger" size="mini"
                  @click="$refs.tableMain.remove(props.row.id)"> 删除
                </el-button>
              </el-button-group>
            </template>
          </el-table-column>
        </template>
      </table-comb>

      <template slot="footer" v-if="isPage"></template>
    </d2-container>

    <!--创建页面-->
    <region-create
      ref="createDialog"
      :siteId="tableBaseSearchModel.siteId"
      :siteName="siteName"
      :officeId="tableBaseSearchModel.officeId"
      :officeName="officeName"
      @ok="handleUpdateSuccess">
    </region-create>
    <!--编辑页面-->
    <region-edit
      ref="editDialog"
      :siteName="siteName"
      :officeName="officeName"
      @ok="handleUpdateSuccess">
    </region-edit>
  </div>
</template>

<script>
  import { find } from "lodash";
  import listMixin from "@/mixins/list.mixin";
  import RegionEdit from "./componnets/RegionEdit";
  import RegionCreate from "./componnets/RegionCreate";

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "RegionList",
    components: {RegionCreate, RegionEdit},
    mixins: [
      listMixin
    ],
    props: ['siteId', 'officeId', 'uid'],
    data() {
      return {
        isPage: true,
        // 业务需求，把园区地点的检索放在顶部使用，作为基础查询
        tableBaseSearchModel: {
          siteId: "",
          officeId: "",
        },
        tableData: [], // 表格数据
        siteOptions: [], // 园区
        officeOptions: [], // 地点
      };
    },
    computed: {
      unionId() {
        return this.uid || this.$route.params.unionId
      },
      // 留给编辑和新增页使用，点击新增的时候必须要选择了园区和地点，（可以理解为在这个园区和地点下新增数据）
      siteName() {
        return this.siteOptions && this.siteOptions.length > 0 && this.tableBaseSearchModel.siteId ? find(this.siteOptions, {siteId: this.tableBaseSearchModel.siteId}).name : null
      },
      // 同上
      officeName() {
        return this.officeOptions && this.officeOptions.length > 0 && this.tableBaseSearchModel.officeId ? find(this.officeOptions, {id: this.tableBaseSearchModel.officeId}).description : null
      }
    },
    watch: {
      officeId() {
        if (this.siteId && this.officeId) {
          this.isPage = false;
          this.tableBaseSearchModel.siteId = this.siteId;
          this.tableBaseSearchModel.officeId = this.officeId;
          this.handleSiteChange(this.siteId); // 更新下拉框
        }
      }
    },
    methods: {
      // 刷新数据，用于外部调用。例如选项卡情况下，园区数据变化了，这个页面的园区选项要变动
      refresh() {
        this.handleSiteChange(this.tableBaseSearchModel.siteId);
      },
      // 打开新增页面
      openCreateDialog() {
        // 必须提前选择园区和地点
        if (this.siteName && this.officeName) {
          this.$refs.createDialog.open(this.tableBaseSearchModel);
        } else {
          this.$message.warning(`请先选择园区和地点`);
        }
      },
      // 打开编辑页面
      openEditDialog(rowData) {
        if (this.siteName && this.officeName) {
          this.$refs.editDialog.open(rowData);
        } else {
          this.$message.warning(`请先选择园区和地点`);
        }
      },
      // 查询数据
      formBaseSearch() {
        this.$refs.tableMain.fetchData();
      },
      // 园区地点级联
      handleSiteChange(value) {
        const _this = this;
        // 加载地点选项
        this.$api.merch.region.officeOptions(value).then(res => {
          _this.officeOptions = res.data;
          if (_this.isPage && res.data && res.data.length > 0) {
            _this.tableBaseSearchModel.officeId = res.data[0].id
            _this.formBaseSearch();
          } else if (!_this.isPage && _this.tableBaseSearchModel.officeId) {
            _this.formBaseSearch();
          }
        });
      },
      handleUpdateSuccess() {
        this.$refs.tableMain.fetchData();
        this.$emit('update');
        console.log('handleUpdateSuccess');
      },
    },
    mounted() {
      const _this = this;
      // const unionId = util.getUrlParam("unionId");
      // 加载园区选项
      this.$api.merch.region.siteOptions(this.unionId).then(res => {
        _this.siteOptions = res.data;
      });
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .region-card {
    display: block;
    .thumb {
      float: left; display: inline; width: 44px; height: 44px; margin-right: 10px;
    }
    .title {
      line-height: 28px; font-size: 16px; color: #333;
    }
    .desc {
      line-height: 16px; font-size: 12px; color: #999;
    }
  }
  .station-search {
    padding-top: 20px;
    margin-bottom: 20px;
    border-radius: 5px;
    border: 1px solid #b3d8ff;
    background-color: #ecf5ff;
  }
</style>
