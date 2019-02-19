<template>
  <div>
    <d2-container>
      <template slot="header" v-if="isPage">{{pageTypeName}}管理</template>
      <br>
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
                  @change="handleOfficeChange"
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
        name="单元管理"
        ref="tableMain"
        :search-model="tableMainSearchModel"
        :get-action="$api.merch.station.list"
        :get-action-where="this.tableBaseSearchModel"
        :auto-fetch="false"
        :remove-action="$api.merch.station.delete"
        :remove-batch-action="$api.merch.station.deleteMany">
        <!--高级查询-->
        <template slot="advancedSearchForm" slot-scope="props">
          <el-row>
            <el-col :span="6">
              <el-form-item :label="pageTypeName">
                <el-select
                  v-model="props.form.typeId"
                  filterable
                  clearable
                  placeholder="请选择"
                  style="width: 100%;">
                  <el-option
                    v-for="item in stationTypeOptions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="物业类型：">
                <el-select
                  v-model="props.form.propertyId"
                  filterable
                  clearable
                  style="width: 100%;">
                  <el-option
                    v-for="item in propertyOptions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="状态：">
                <el-select
                  v-model="props.form.status"
                  default-first-option
                  clearable
                  style="width: 100%;">
                  <el-option value="" label="全部"></el-option>
                  <el-option value="0" label="空置"></el-option>
                  <el-option value="1" label="使用"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="区域：">
                <el-cascader
                  :options="regionOptions"
                  v-model="props.form.region"
                  :props="{label:'name', value:'id', children:'childLists'}"
                  clearable
                  change-on-select>
                </el-cascader>
              </el-form-item>
            </el-col>
          </el-row>
        </template>
        <!--控制按钮-->
        <template slot="controls">
          <el-button type="primary" @click="openCreateDialog">
            <i class="iconfont icon-add"></i> 新增
          </el-button>
          <el-button @click="openImportDataForm">
            <i class="iconfont icon-import"></i> 批量导入
          </el-button>
          <el-button type="danger" @click="$refs.tableMain.removeBatch()">
            <i class="iconfont icon-delete"></i> 删除选中项
          </el-button>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            prop="name"
            sortable="custom"
            label="名称">
          </el-table-column>
          <el-table-column
            prop="typeName"
            :label="pageTypeName">
          </el-table-column>
          <el-table-column
            prop="propertyName"
            label="物业类型">
          </el-table-column>
          <el-table-column
            prop="regionLabelName"
            label="所属区域">
            <template slot-scope="props">
              <el-button-group>
                {{ (props.row.region1Name || '') + ' / ' + (props.row.region2Name || '') }}
              </el-button-group>
            </template>
          </el-table-column>
          <el-table-column
            prop="proportion"
            sortable="custom"
            label="面积">
            <template slot-scope="props">
              <el-button-group>
                {{ props.row.proportion + ' ㎡' }}
              </el-button-group>
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            sortable="custom"
            label="状态">
            <template slot-scope="props">
              <el-tag size="mini" :type="props.row.status === '0' ? 'info' : 'success'">{{props.row.status === '0' ? '空置' : '使用'}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTs"
            sortable="custom"
            label="创建时间"
            width="164">
            <template slot-scope="props">
              <el-button-group>
                {{ props.row.createTs | dateFormat }}
              </el-button-group>
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
                  type="danger" size="mini" v-if="props.row.status === '0'"
                  @click="$refs.tableMain.remove(props.row.id)"> 删除
                </el-button>
              </el-button-group>
            </template>
          </el-table-column>
        </template>
      </table-comb>

      <el-dialog :append-to-body="true" title="导入数据" :visible.sync="importDataDialogVisible">
        <el-upload
          class="upload-file"
          :data="tableBaseSearchModel"
          :headers="{token:token}"
          :action="$api.merch.station.importDataUrl"
          :on-success="handleUploadSuccess"
          accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, .csv"
          :show-file-list="false">
          <el-button size="small" type="primary">批量导入</el-button>
          <div slot="tip" class="el-upload__tip">请下载
            <a target="_blank" :href="downloadTemplateUrl">[{{pageTypeName}}导入模板]</a>，按照模板要求导入文件
          </div>
        </el-upload>
      </el-dialog>

      <template slot="footer" v-if="isPage"></template>
    </d2-container>

    <station-detail
      ref="detailDialog"
      :is-car="isCar"
      :page-type-name="pageTypeName"
      :siteName="siteName"
      :officeName="officeName"
      :regionOptions="regionOptions"
      :stationTypeOptions="stationTypeOptions"
      :propertyOptions="propertyOptions"
      @ok="handleUpdateSuccess">
    </station-detail>
  </div>
</template>

<script>
  import { find } from "lodash";
  import listMixin from "@/mixins/list.mixin";
  import StationDetail from "./componnets/StationDetail";

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "StationList",
    components: {StationDetail},
    mixins: [
      listMixin
    ],
    props: {
      uid: String,
      siteId: String,
      officeId: String,
      isCar: {
        type: Number,
        default: 0
      }
    },
    data() {
      return {
        isPage: true,
        importDataDialogVisible: false,
        tableBaseSearchModel: {
          siteId: "",
          officeId: "",
          isCar: 0
        },
        tableMainSearchModel: {
          typeId: "",
          status: "",
          region: [],
        },
        tableData: [],
        siteOptions: [], // 园区
        officeOptions: [], // 地点

        regionOptions: [], // 区域
        stationTypeOptions: [], // 单元类型
        propertyOptions: [], // 物业类型
        statusOptions: [], // 状态
      };
    },
    computed: {
      unionId() {
        return this.uid || this.$route.params.unionId
      },
      pageTypeName() {
        return this.isCar ? "车位" : "单元";
      },
      siteName() {
        return this.siteOptions && this.siteOptions.length > 0 && this.tableBaseSearchModel.siteId ? find(this.siteOptions, {siteId: this.tableBaseSearchModel.siteId}).name : null
      },
      officeName() {
        return this.officeOptions && this.officeOptions.length > 0 && this.tableBaseSearchModel.officeId ? find(this.officeOptions, {id: this.tableBaseSearchModel.officeId}).description : null
      },
      downloadTemplateUrl() {
        return this.$api.merch.station.templateUrl + `?siteId=${this.tableBaseSearchModel.siteId}&officeId=${this.tableBaseSearchModel.officeId}&isCar=${this.isCar}`;
      }
    },
    watch: {
      officeId() {
        if (this.siteId && this.officeId) {
          this.isPage = false;

          this.tableBaseSearchModel.siteId = this.siteId;
          this.tableBaseSearchModel.officeId = this.officeId;

          this.handleSiteChange(this.siteId); // 更新下拉框
          this.handleOfficeChange(this.officeId);
        }
      }
    },
    methods: {
      // 刷新数据，用于外部调用。例如选项卡情况下，园区数据变化了，这个页面的园区选项要变动
      refresh(){
        this.handleSiteChange(this.tableBaseSearchModel.siteId);
        this.handleOfficeChange(this.tableBaseSearchModel.officeId);
      },
      // 打开创建数据弹框，并传递数据，因为列表已经把数据都取出来了，这里直接复制过去
      openCreateDialog() {
        if (this.siteName && this.officeName) {
          this.$refs.detailDialog.open(this.tableBaseSearchModel);
        } else {
          this.$message.warning(`请先选择园区和地点`);
        }
      },
      openEditDialog(rowData) {
        if (this.siteName && this.officeName) {
          this.$refs.detailDialog.open(rowData);
        } else {
          this.$message.warning(`请先选择园区和地点`);
        }
      },
      openImportDataForm() {
        if (this.tableBaseSearchModel.siteId && this.tableBaseSearchModel.officeId) {
          this.importDataDialogVisible = true;
        } else {
          this.$message.warning(`请先选择园区和地点`);
          return false;
        }
      },
      formBaseSearch() {
        this.$refs.tableMain.fetchData();
      },
      handleSiteChange(value) {
        const _this = this;
        // 加载地点选项
        this.$api.merch.region.officeOptions(value).then(res => {
          _this.officeOptions = res.data;
          if (!res.data || res.data.length === 0) {
            _this.$message.warning('该园区没有地点选项');
          }
          // 默认选中第一条记录
          if (_this.isPage && res.data && res.data.length > 0) {
            _this.tableBaseSearchModel.officeId = res.data[0].id
            _this.handleOfficeChange(res.data[0].id)
            _this.formBaseSearch();
          } else if (!_this.isPage && _this.tableBaseSearchModel.officeId) {
            _this.formBaseSearch();
          }
        });
        this.$api.merch.station.propertyOptions(value).then(res => {
          _this.propertyOptions = res.data;
          if (!res.data || res.data.length === 0) {
            _this.$message.warning('该园区没有物业选项');
          }
        });
      },
      handleOfficeChange(value) {
        const _this = this;
        // 加载单元类型选项
        this.$api.merch.station.stationTypeOptions(value).then(res => {
          _this.stationTypeOptions = res.data;
          if (!res.data || res.data.length === 0) {
            // _this.$message.warning('该地点没有单元类型选项');
          }
        });
        // 加载区域选项
        this.$api.merch.station.regionOptions(value).then(res => {
          _this.regionOptions = res.data;
          if (!res.data || res.data.length === 0) {
            // _this.$message.warning('该地点没有区域选项');
          }
        });
      },
      handleUploadSuccess(response, file, fileList) {
        if (response.code === 0) {
          this.$message.success('导入成功');
          this.$refs.tableMain.fetchData();
          this.importDataDialogVisible = false;
          this.$emit('update');
        } else {
          this.$message.error(response.message);
        }
      },
      handleUpdateSuccess() {
        this.$refs.tableMain.fetchData();
        this.$emit('update');
      }
    },
    mounted() {
      const _this = this;
      this.tableBaseSearchModel.isCar = this.isCar ? 1 : 0;
      // 加载园区选项
      this.$api.merch.region.siteOptions(this.unionId).then(res => {
        _this.siteOptions = res.data;
      });
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .station-search {
    padding-top: 20px;
    margin-bottom: 20px;
    border-radius: 5px;
    border: 1px solid #b3d8ff;
    background-color: #ecf5ff;
  }
  .upload-file {
    display: inline-block;
  }
</style>
