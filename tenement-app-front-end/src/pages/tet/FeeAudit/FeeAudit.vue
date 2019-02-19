<template>
  <div>
    <d2-container>
      <template slot="header">物业费项审核设置<span class="header-tip">*未添加项目默认为不需审核</span></template>
      <br>
      <!---->
      <table-comb
        name="企业列表"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :search-model="tableMainSearchModel"
        :get-action="$api.tet.feeAudit.getFeeTypeAuditList"
        :get-action-where="getActionWhere"
        :remove-action="$api.tet.feeAudit.deleteFeeTypeAudit"
        custom-id="stationId"
        :autoFetch="false"
      >
        <!--高级查询-->
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
          <el-button class="fr" @click="openDialog()">添加收款审核费项</el-button>
        </template>

        <template slot="advancedSearchForm" slot-scope="props">
          <el-row>
            <el-col :span="6">
              <el-form-item label="地点：">
                <el-select
                  v-model="props.form.officeId"
                  @change="changeOffice"
                  placeholder="请选择"
                  filterable
                >
                  <el-option
                    v-for="item in officeOptions"
                    :key="item.id"
                    :label="item.description"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="区域：">
                <el-cascader
                  placeholder="请选择"
                  :props="regionProps"
                  :options="regionOptions"
                  v-model="props.form.regionId"
                  :change-on-select="true"
                  :debounce="400"
                  @change="changeRegion">
                </el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="单元：">
                <el-select
                  v-model="props.form.stationId"
                  @change="changeStation"
                  placeholder="请选择"
                  filterable
                >
                  <el-option
                    v-for="item in stationOptions"
                    :key="item.id"
                    :label="item.staDescription"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </template>

        <!--表格-->
        <template slot="tableColumns">
          <el-table-column fixed="left" label="操作">
            <template slot-scope="props">
              <el-button type="text" size="mini" @click="openDialog(props.row)">编辑</el-button>
              <el-button type="text" size="mini" @click="deleteItem(props.row)">删除</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="officeName" label="地点"></el-table-column>
          <el-table-column prop="regionName" label="区域">
            <template slot-scope="props">
              {{(props.row.levelName||'-')+'/'+(props.row.level2Name||'-')}}
            </template>
          </el-table-column>
          <el-table-column prop="stationName" label="单元"></el-table-column>
          <el-table-column prop="feeTypeNames" label="费用细项"></el-table-column>
        </template>
      </table-comb>
      <template slot="footer"></template>
    </d2-container>

    <el-dialog :title="editDialogCustomer.stationId ?'编辑收款审核费项':'添加收款审核费项'" :visible.sync="showSaveDialog">
      <el-form label-width="100px" :model="editDialogCustomer" :rules="rules" ref="saveForm">
        <el-form-item label="站点：" prop="siteId">
          <el-select
            v-model="editDialogCustomer.siteId"
            @change="changeSiteForSave"
            placeholder="请选择"
            filterable
          >
            <el-option
              v-for="item in editDialogCustomer.siteOptions"
              :key="item.siteId"
              :label="item.name"
              :value="item.siteId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地点：" prop="officeId">
          <el-select
            v-model="editDialogCustomer.officeId"
            @change="changeOfficeForSave"
            placeholder="请选择"
            filterable
          >
            <el-option
              v-for="item in editDialogCustomer.officeOptions"
              :key="item.id"
              :label="item.description"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地区：" prop="regionId">
          <el-cascader
            placeholder="请选择"
            :props="regionProps"
            :change-on-select="true"
            :debounce="400"
            :options="editDialogCustomer.regionOptions"
            v-model="editDialogCustomer.regionId"
            @change="changeRegionForSave">
          </el-cascader>
        </el-form-item>
        <el-form-item label="单元：" prop="stationId">
          <el-select
            v-model="editDialogCustomer.stationId"
            @change="changeStationForSave"
            placeholder="请选择"
            filterable
          >
            <el-option
              v-for="item in editDialogCustomer.stationOptions"
              :key="item.id"
              :label="item.staDescription"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="费项：" prop="feeTypeIds">
          <template v-if="editDialogCustomer.feeTypesOptions.length==0">
            没有选择单元或者单元下没有费项
          </template>
          <el-checkbox-group v-else v-model="editDialogCustomer.feeTypeIds">
            <el-checkbox
              v-for="item in editDialogCustomer.feeTypesOptions"
              :label="item.id"
              :key="item.id"
            >{{item.description}}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">保 存</el-button>
        <el-button @click="showSaveDialog = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "FeeAudit",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      return {
        tableMainSearchModelBase:{
          siteId:''
        },
        tableMainSearchModel:{
          officeId:'',
          regionId:[],
          stationId:'',

        },
        siteOptions:[],
        officeOptions:[],
        regionOptions:[],
        regionProps:{
          value: 'id',
          label:'name',
          children: 'childLists'
        },
        stationOptions:[],
        tableData: [],
        showSaveDialog:false,
        editDialogCustomer:{
          siteId:'',
          officeId:'',
          regionId:[],
          stationId:'',
          feeTypeIds:[],
          id:'',
          siteOptions:[],
          officeOptions:[],
          regionOptions:[],
          feeTypesOptions:[]
        },
        rules:{
          feeTypeIds: [
            { type: 'array', required: true, message: '请至少选择一个', trigger: 'change' }
          ],
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
      //打开新增或编辑费项弹窗
      openDialog(item){
        console.log(item)
        var _this = this;
        this.showSaveDialog = true;
        //移除校验结果并重置字段值
        this.$refs.saveForm && this.$refs.saveForm.clearValidate();
        //新增不传item 编辑要传入item
        if(item){
          this.editDialogCustomer = {
            ...item,
            siteId:this.$refs.tableMain.searchModelDataBase.siteId,
            regionId:[item.levelId,item.level2Id],
            siteOptions:this.siteOptions,
            officeOptions:this.officeOptions,
            regionOptions:this.regionOptions,
            feeTypeIds:[],
            feeTypesOptions:[],
          }
          //获取
          this.initEditDialogCustomerOptions();
        }else{
          this.editDialogCustomer = {
            siteId:'',
            officeId:'',
            regionId:[],
            stationId:'',
            feeTypeIds:[],
            id:'',
            siteOptions:this.siteOptions,
            officeOptions:[],
            regionOptions:[],
            feeTypesOptions:[]
          }
        }
        console.log(this.editDialogCustomer)
      },
      initEditDialogCustomerOptions(){
        let siteId = this.editDialogCustomer.siteId;
        let officeId = this.editDialogCustomer.officeId;
        let regionId = this.editDialogCustomer.regionId;
        let stationId = this.editDialogCustomer.stationId;
        this.$api.merch.region.officeOptions(siteId).then(res => {
          this.editDialogCustomer.officeOptions = res.data;
        });
        this.$api.merch.station.regionOptions(officeId).then(res => {
          this.editDialogCustomer.regionOptions = res.data;
        });
        this.$api.merch.station.stationOptions(regionId,officeId).then(res => {
          this.editDialogCustomer.stationOptions = res.data;
        });
        this.$api.tet.feeType.getFeeTypesAndAuditStatus(siteId,stationId).then(res => {
          let editDialogCustomer = this.editDialogCustomer;
          editDialogCustomer.feeTypesOptions = res.data;
          //清空查询条件后面的级联 区域，单元
          editDialogCustomer.feeTypeIds=[];
          res.data.map(item=>{
            if(item.addAuditStatus==1){
              editDialogCustomer.feeTypeIds.push(item.id)
            }
          })
        });
      },
      //保存或者新增费项
      save(){
        //验证
        this.$refs.saveForm.validate((valid) => {
          if (valid) {
            let item = {
              unionId:this.unionId,
              siteId:this.editDialogCustomer.siteId,
              officeId:this.editDialogCustomer.officeId,
              region1:this.editDialogCustomer.regionId[0],
              stationId:this.editDialogCustomer.stationId,
              feeTypeIds:this.editDialogCustomer.feeTypeIds,
            }
            if(this.editDialogCustomer.id){
              item.id = this.editDialogCustomer
            }if(this.editDialogCustomer.regionId.length>=2){
              item.region2 = this.editDialogCustomer.regionId[1]
            }
            this.$api.tet.feeAudit.saveFeeTypeAudit(item).then(res => {
              if(res.code==0){
                let message = this.editDialogCustomer.id ?'修改成功！':'添加成功！';
                this.showSaveDialog = false;
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
          } else {
            return false;
          }
        });
      },
      //删除费项
      deleteItem(item){
        console.log(item)
        this.$refs.tableMain.remove(item.stationId);
      },
      //切换站点
      changeSite(siteId){
        this.$api.merch.region.officeOptions(siteId).then(res => {
          this.officeOptions = res.data;
          //清空查询条件后面的级联 地点，区域，单元
          let searchModelData = this.$refs.tableMain.searchModelData;
          searchModelData.officeId='';
          searchModelData.regionId=[];
          searchModelData.stationId='';
          this.regionOptions=[];
          this.stationOptions=[];
          //刷新列表
          this.$refs.tableMain.fetchData();

        });
      },
      //切换地点
      changeOffice(officeId){
        this.$api.merch.station.regionOptions(officeId).then(res => {
          this.regionOptions = res.data;
          //清空查询条件后面的级联 区域，单元
          let searchModelData = this.$refs.tableMain.searchModelData;
          searchModelData.regionId=[];
          searchModelData.stationId='';
          this.stationOptions=[];
          //刷新列表
          this.$refs.tableMain.fetchData();

        });
      },
      //切换区域
      changeRegion(regionIdArray){
        let officeId = this.$refs.tableMain.searchModelData.officeId
        this.$api.merch.station.stationOptions(regionIdArray,officeId).then(res => {
          this.stationOptions = res.data;
          //清空查询条件后面的级联 区域，单元
          this.tableMainSearchModel.stationId='';
          //刷新列表
          this.$refs.tableMain.fetchData();
        });
      },
      //切换单元
      changeStation(stationId){
        this.$refs.tableMain.fetchData();
      },
      //新增或者编辑里面的级联
      changeSiteForSave(siteId){
        this.$api.merch.region.officeOptions(siteId).then(res => {
          let editDialogCustomer = this.editDialogCustomer;
          editDialogCustomer.officeOptions = res.data;
          //清空查询条件后面的级联 地点，区域，单元
          editDialogCustomer.officeId='';
          editDialogCustomer.regionId=[];
          editDialogCustomer.stationId='';
          editDialogCustomer.regionOptions=[];
          editDialogCustomer.stationOptions=[];
        });
      },
      changeOfficeForSave(officeId){
        this.$api.merch.station.regionOptions(officeId).then(res => {
          let editDialogCustomer = this.editDialogCustomer;
          editDialogCustomer.regionOptions = res.data;
          //清空查询条件后面的级联 区域，单元
          editDialogCustomer.regionId=[];
          editDialogCustomer.stationId='';
          editDialogCustomer.stationOptions=[];
        });
      },
      changeRegionForSave(regionIdArray){
        let officeId = this.editDialogCustomer.officeId
        this.$api.merch.station.stationOptions(regionIdArray,officeId).then(res => {
          let editDialogCustomer = this.editDialogCustomer;
          editDialogCustomer.stationOptions = res.data;
          //清空查询条件后面的级联 区域，单元
          editDialogCustomer.stationId='';
        });
      },
      changeStationForSave(stationId){
        let siteId = this.editDialogCustomer.siteId
        this.$api.tet.feeType.getFeeTypesAndAuditStatus(siteId,stationId).then(res => {
          let editDialogCustomer = this.editDialogCustomer;
          editDialogCustomer.feeTypesOptions = res.data;
          //清空查询条件后面的级联 区域，单元
          editDialogCustomer.feeTypeIds=[];
          res.data.map(item=>{
            if(item.addAuditStatus==1){
              editDialogCustomer.feeTypeIds.push(item.id)
            }
          })
        });

      },
      //新增或者编辑里面的级联 end
    },
    mounted() {
      //获取站点列表
      setTimeout(()=>{
        this.$api.merch.region.siteOptions(this.unionId).then(res => {
          let siteId = res.data[0].siteId
          this.$refs.tableMain.searchModelDataBase.siteId = siteId
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

  .header-tip{ padding-left: 50px; color:#ddd; font-size: 12px;}
</style>
