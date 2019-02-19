<template>
  <div>
    <d2-container>
      <template slot="header">电表单元关系管理</template>
      <br>
      <!---->
      <table-comb
        name="电表单元关系列表"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :search-model="tableMainSearchModel"
        :get-action="$api.energy.meterUnitRel.list"
        :get-action-where="getActionWhere"
        :autoFetch="false"
      >
        <!--基础查询-->
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
          <el-input
            placeholder="搜索设备名称/设备编号"
            prefix-icon="el-icon-search"
            clearable
            v-model="props.form.searchKey"
            style="width: 210px; margin-left: 10px;"
          >
          </el-input>
          <el-button class="fr" @click="exportExcel">EXCEL导出</el-button>
        </template>
        <!--高级查询-->
        <template slot="advancedSearchForm" slot-scope="props">
          <el-row>
            <el-col :span="8">
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
            <el-col :span="8">
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
            <el-col :span="8">
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
            <el-col :span="8">
              <el-form-item label="公摊设备：">
                <el-select
                  v-model="props.form.isPooled"
                  @change="changeStation"
                  placeholder="请选择"
                  filterable
                >
                  <el-option
                    v-for="item in pooledOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="设备状态：">
                <el-select
                  v-model="props.form.deviceStatus"
                  @change="changeStation"
                  placeholder="请选择"
                  filterable
                >
                  <el-option
                    v-for="item in deviceStatuses"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="拉合闸状态：">
                <el-select
                  v-model="props.form.runStatus"
                  @change="changeStation"
                  placeholder="请选择"
                  filterable
                >
                  <el-option
                    v-for="item in runStatuses"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column prop="corpName" label="操作">
            <template slot-scope="props">
              <el-button type="text" size="mini" @click="openDialog(props.row)">编辑</el-button>
            </template>
          </el-table-column>
          <el-table-column width="180px" prop="deviceNum" label="设备编号"></el-table-column>
          <el-table-column width="180px" prop="displayName" label="设备名称"></el-table-column>
          <el-table-column prop="deviceStatus" label="设备状态"></el-table-column>
          <el-table-column prop="isPooled" label="公摊设备"></el-table-column>
          <el-table-column width="200px" prop="office" label="地点"></el-table-column>
          <el-table-column width="150px" prop="levelName" label="区域">
            <template slot-scope="props">
              {{(props.row.levelName||'-')+'/'+(props.row.level2Name||'-')}}
            </template>
          </el-table-column>
          <el-table-column width="100px" prop="station" label="单元"></el-table-column>
        </template>
      </table-comb>
      <template slot="footer"></template>
    </d2-container>

    <el-dialog title="编辑" :visible.sync="showSaveDialog">
      <el-form
        label-width="100px"
        :model="editDialogCustomer"
        :rules="rules"
        ref="saveForm">
        <el-form-item label="设备编号" >
          {{editDialogCustomer.deviceNum}}
        </el-form-item>
        <el-form-item label="设备名称"  prop="displayName">
          <el-input
            placeholder="设备名称"
            style="width: 214px"
            clearable
            v-model="editDialogCustomer.displayName"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="地点：">
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
        <el-form-item label="地区：">
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
    name: "MeterUnitRel",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      //如果填写了地点就必须填写单元
      let changeStation = (rule, value, callback)=>{
        if(this.editDialogCustomer.officeId && value ===''){
          callback(new Error('请选择单元'));
        }
        callback();
      };
      return {
        tableMainSearchModelBase:{
          corpName: ''
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
        //是否公摊
        pooledOptions:[
          {value: '', label: '全部'},
          {value: '0', label: '否'},
          {value: '1', label: '是'}
        ],
        //设备状态
        deviceStatuses:[
          {value: '0', label: '正常'},
          {value: '1', label: '作废'},
          {value: '2', label: '未启用'},
          {value: '3', label: '故障'},
          {value: '4', label: '离线'},
        ],
        //拉合闸状态
        runStatuses:[
          {value: '0', label: '开闸'},
          {value: '1', label: '合闸'}
        ],
        tableData: [],
        showSaveDialog:false,
        editDialogCustomer:{
          officeId:'',
          regionId:[],
          stationId:'',
          id:'',
          officeOptions:[],
          regionOptions:[],
          stationOptions:[],
        },
        rules:{
          displayName: [
            { required: true, message: '设备名称不能为空', trigger: 'blur' }
          ],
          stationId: [
            {validator: changeStation,trigger: 'blur'  }
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
        console.log(item);
        this.showSaveDialog = true;
        //移除校验结果并重置字段值
        this.$refs.saveForm && this.$refs.saveForm.clearValidate();
        //新增不传item 编辑要传入item
        this.editDialogCustomer = {
          ...item,
          regionId:[item.levelId||'',item.level2Id||''],
          officeOptions:[],
          regionOptions:[],
          stationOptions:[],
        }
        //地点，地区，单元初始化
        this.initEditDialogCustomerOptions()

      },
      initEditDialogCustomerOptions(){
        let siteId = this.$refs.tableMain.searchModelDataBase.siteId;
        let officeId = this.editDialogCustomer.officeId;
        let regionId = this.editDialogCustomer.regionId;
        let stationId = this.editDialogCustomer.stationId;
        this.$api.merch.region.officeOptions(siteId).then(res => {
          this.editDialogCustomer.officeOptions = res.data;
        });
        if(!officeId){
          return
        }
        this.$api.merch.station.regionOptions(officeId).then(res => {
          this.editDialogCustomer.regionOptions = res.data;
        });
        if(!regionId[0]){
          return
        }
        this.$api.merch.station.stationOptions(regionId,officeId).then(res => {
          this.editDialogCustomer.stationOptions = res.data;
        });
      },
      //获取value 获取 lable
      getLabelByValue(list,value){
        console.log(value)
        let arr =  list.filter(item=>{
          return item.value == value;
        });
        if(!value || arr.length==0){
          return ''
        };
        return arr[0].label;
      },
      //保存或者新增费项
      save(){
        //验证
        console.log(this.editDialogCustomer);
        this.$refs.saveForm.validate((valid) => {
          console.log(valid)
          if (valid) {
            let vo = {
              ...this.editDialogCustomer
            };

            delete vo.officeOptions;
            delete vo.regionOptions;
            delete vo.stationOptions;

            vo.levelId = vo.regionId[0];
            vo.level2Id = vo.regionId[1];
            delete vo.regionId;

            this.$api.energy.meterUnitRel.save(vo).then(res => {
              if(res.code==0){
                let message = '修改成功！'
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
      //导出
      exportExcel(){
        if(this.$refs.tableMain.tableData.length==0){
          this.$message.error('没有可以导出的数据');
          return;
        }
        let vo = this.$refs.tableMain.getPageVo();
        console.log(vo);
        this.$api.energy.meterUnitRel.exportExcel(vo)
      },
      //切换站点
      changeSite(siteId){
        this.$api.merch.region.officeOptions(siteId).then(res => {
          this.officeOptions =  this.editDialogCustomer.officeOptions  = res.data;
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
          this.$refs.tableMain.searchModelData.stationId='';
          //刷新列表
          this.$refs.tableMain.fetchData();
        });
      },
      //切换单元
      changeStation(stationId){
        this.$refs.tableMain.fetchData();
      },

      //新增或者编辑里面的级联
      changeOfficeForSave(officeId,callback){
        this.$api.merch.station.regionOptions(officeId).then(res => {
          let editDialogCustomer = this.editDialogCustomer;
          editDialogCustomer.regionOptions = res.data;
          //清空查询条件后面的级联 区域，单元
          editDialogCustomer.regionId=[];
          editDialogCustomer.stationId='';
          editDialogCustomer.stationOptions=[];
          if(callback){
            callback()
          }
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
      //新增或者编辑里面的级联 end
    },
    mounted() {
      //获取站点列表
      setTimeout(()=>{
        this.$api.merch.region.siteOptions(this.unionId).then(res => {
          let siteId = res.data[0].siteId
          this.$refs.tableMain && (this.$refs.tableMain.searchModelDataBase.siteId = siteId)
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
</style>
