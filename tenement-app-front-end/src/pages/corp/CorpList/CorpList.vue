<template>
  <div>
    <d2-container>
      <template slot="header">园区客户管理</template>
      <statistical-data-panel :data-list="statisticalData"/>
      <br>
      <!---->
      <table-comb
        name="企业列表"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :search-model="tableMainSearchModel"
        :get-action="$api.corp.list"
        :get-action-where="getActionWhere"
        @on-remove-success="refreshStaticData">
        <!--高级查询-->
        <template slot="baseSearchForm" slot-scope="props">
          <el-input
            placeholder="请输入关键字"
            prefix-icon="el-icon-search"
            clearable
            v-model="props.form.searchKey"
            style="width: 160px; margin-right: 10px;">
          </el-input>
          <el-select
            v-model="props.form.siteId"
            filterable
            placeholder="请选择园区"
            style="width: 300px;">
            <el-option
              v-for="item in siteOptions"
              :key="item.siteId"
              :label="item.name"
              :value="item.siteId">
            </el-option>
          </el-select>
        </template>
        <template slot="advancedSearchForm" slot-scope="props">
          <el-row>
            <el-col :span="6">
              <el-form-item label="分类：">
                <el-select
                  v-model="props.form.type"
                  clearable
                  placeholder="请选择分类">
                  <el-option label="当前入驻" :value="1"></el-option>
                  <el-option label="历史客户" :value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="认证状态：">
                <el-select
                  v-model="props.form.certStatus"
                  clearable
                  placeholder="请选择分类">
                  <el-option label="未认证" :value="0"></el-option>
                  <el-option label="已认证" :value="1"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="行业：">
                <el-select
                  v-model="props.form.industryIds"
                  multiple
                  filterable
                  placeholder="请选择园区"
                  style="width: 100%;">
                  <el-option
                    v-for="item in industryOptions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column
            fixed="left"
            label="客户名称">
            <template slot-scope="props">
              <el-button type="text" size="mini" @click="openDetailDialog(props.row)"> {{props.row.name}}</el-button>
            </template>
          </el-table-column>
          <el-table-column
            prop="industryName"
            label="行业">
          </el-table-column>
          <el-table-column
            prop="userName"
            label="主管理员">
          </el-table-column>
          <el-table-column
            prop="telephone"
            label="联系电话">
          </el-table-column>
          <el-table-column
            prop="certStatus"
            label="企业认证">
            <template slot-scope="props">
              <el-tag :type="props.row.certStatus === '1' ? 'success' : 'info'" size="small">{{props.row.certStatusStr}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="fullName"
            label="入驻园区">
          </el-table-column>
          <el-table-column
            prop="customerTypeStr"
            label="分类">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作">
            <template slot-scope="props">
              <el-button-group>
                <el-button size="mini" @click="openAdmissionListDialog(props.row.id)"> 查看入驻详情</el-button>
                <el-button size="mini" @click="openEditLabelDialog(props.row)">标签管理</el-button>
              </el-button-group>
            </template>
          </el-table-column>
        </template>
      </table-comb>

      <template slot="footer"></template>
    </d2-container>

    <corp-detail ref="detailDialog" @ok="handleUpdateSuccess"></corp-detail>
    <corp-admission-list ref="admissionListDialog"></corp-admission-list>
    <!--<office-edit ref="editDialog" @ok="handleUpdateSuccess"></office-edit>-->

    <el-dialog title="标签管理" :visible.sync="showLabelDialog">
      <el-form label-width="100px" :model="editDialogCustomer">
        <h2>{{editDialogCustomer.name}}</h2>
        <el-form-item class="mb10" label="系统标签">
          <el-checkbox v-for="(label,index) in editDialogCustomer.labels" v-if="label.labelType==='0'" :label="label.labelName" :key="index"  v-model="label.checkFlag"></el-checkbox>
        </el-form-item>
        <el-form-item label="自定义标签">
          <el-tag v-for="(label,index) in editDialogCustomer.labels" :key="index" @click.native="likeCheckbox(label)" v-if="label.labelType==='1'" :type="label.checkFlag?'danger':'info'">{{label.labelName}}</el-tag>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitLabels">保 存</el-button>
        <el-button @click="showLabelDialog = false">取 消</el-button>

      </div>
    </el-dialog>

  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";
  import StatisticalDataPanel from "@/components/StatisticalDataPanel";
  import CorpDetail from "./componnets/CorpDetail";
  import CorpAdmissionList from "./componnets/CorpAdmissionList";

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "CorpList",
    components: {StatisticalDataPanel, CorpDetail, CorpAdmissionList},
    mixins: [
      listMixin
    ],
    data() {
      return {
        statisticalData: null,
        tableMainSearchModelBase:{
          searchKey: '',
          siteId: '',
        },
        tableMainSearchModel: {
          industryIds: [],
          type: '',
          certStatus:'',
        },
        tableData: [],
        siteOptions: [],
        officeOptions: [],
        industryOptions: [],
        showLabelDialog:false,
        editDialogCustomer:{
            name:'',
            corpId:'',
            labels:[]
        }
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
      // 查看详情页面
      openDetailDialog(item) {
        //列表的unionId 是null
        this.$refs.detailDialog.open({...item,unionId:this.unionId});
      },
      // 查看详情页面
      openAdmissionListDialog(id) {
        this.$refs.admissionListDialog.open(id);
      },
      handleUpdateSuccess() {
        this.$refs.tableMain.fetchData();
        this.refreshStaticData();
      },
      refreshStaticData(){
        const _this = this;
        this.$api.corp.corpStatic(this.unionId).then(res => {
          let result = [];
          if (res.data) {
            result = [
              {
                name: '园区客户总数',
                value: res.data.totalCount,
                unit: '个'
              },
              {
                name: '当前入驻',
                value: res.data.currentCount,
                unit: '个'
              },
              {
                name: '当前入驻核心企业',
                value: res.data.currentCoreCount,
                unit: '个'
              },
              {
                name: '历史客户',
                value: res.data.historicalCount,
                unit: '个'
              },
            ]
          }
          _this.statisticalData = result;
        })
      },
      //标签管理
      openEditLabelDialog(row){
          const _this = this;
          this.showLabelDialog=true;
          this.editDialogCustomer.name = row.name;
          this.editDialogCustomer.corpId = row.id;
          this.editDialogCustomer.siteId = row.siteId;
          this.$api.corp.getCorpLabelList(row.id,row.siteId).then(res => {
              _this.editDialogCustomer.labels = res.data;
          });
      },
      //点击自定义标签
      likeCheckbox(label){
        label.checkFlag = !label.checkFlag;
      },
      //标签保存
        submitLabels(){
          const _this = this;
          const labelId = [];
          this.editDialogCustomer.labels.map(function(item){
              if(item.checkFlag){
                  labelId.push(item.id);
              }

          });
          this.$api.corp.saveCorpLabelList(_this.editDialogCustomer.corpId,labelId.join(','),this.unionId,_this.editDialogCustomer.siteId).then(res => {
             if(res.code==0){
                 _this.showLabelDialog = false;
                 _this.$message({
                     message: '修改成功！',
                     type: 'success'
                 });
             }else{
                 _this.$message.error(res.message);
             }
          });
        }
    },
    mounted() {
      const _this = this;

      // 加载统计数据
      this.refreshStaticData()

      this.$api.corp.industryOptions().then(res => {
        _this.industryOptions = res.data;
      });
      this.$api.merch.region.siteOptions(this.unionId).then(res => {
        _this.siteOptions = res.data;
      });
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .el-tag{ margin-right: 10px; cursor: pointer;}
  .el-dialog__body{ padding: 0 20px;}
  .mb10{margin-bottom: 10px}
  .el-checkbox + .el-checkbox{margin-left:0;}
  .el-checkbox{margin-right: 20px}
</style>
