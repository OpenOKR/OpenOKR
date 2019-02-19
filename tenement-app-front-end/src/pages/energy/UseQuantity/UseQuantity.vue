<template>
  <div>
    <d2-container>
      <template slot="header">电表电量管理</template>
      <br>
      <!---->
      <table-comb
        name="电表电量管理"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :search-model="tableMainSearchModel"
        :get-action="$api.energy.useQuantity.list"
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
            placeholder="搜索设备名称/设备编号/企业客户"
            prefix-icon="el-icon-search"
            clearable
            v-model="props.form.searchKey"
            style="width: 270px; margin-left: 10px;"
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
            <el-col :span="16">
              <el-form-item label="数据查询：">
                <el-date-picker
                  v-model="props.form.startEndTime"
                  type="daterange"
                  style="width: 220px"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column prop="corpName" label="操作">
            <template slot-scope="props">
              <el-button type="text" size="mini" @click="openDialog(props.row)">详情</el-button>
            </template>
          </el-table-column>
          <el-table-column width="180px" prop="deviceNum" label="设备编号"></el-table-column>
          <el-table-column width="180px" prop="displayName" label="设备名称"></el-table-column>
          <el-table-column width="200px" prop="officeName" label="地点"></el-table-column>
          <el-table-column width="150px" prop="levelName" label="区域">
            <template slot-scope="props">
              {{(props.row.levelName||'')+' '+(props.row.level2Name||'')}}
            </template>
          </el-table-column>
          <el-table-column width="100px" prop="stationName" label="单元"></el-table-column>
          <el-table-column width="200px" prop="corpName" label="企业客户"></el-table-column>
          <el-table-column prop="consumption"  width="100" label="用量(kwh)"></el-table-column>
          <el-table-column prop="rate" label="倍率"></el-table-column>
          <el-table-column prop="isPooledStr" label="公摊设备"></el-table-column>
          <el-table-column width="90px" prop="dataTime" label="数据时间">
            <template slot-scope="props">
              {{props.row.dataTime | dateFormat('yyyy-MM-dd')}}
            </template>
          </el-table-column>
        </template>
      </table-comb>
      <template slot="footer"></template>
    </d2-container>

    <el-dialog title="详情" :visible.sync="showSaveDialog">
      <div style="font-size: 16px; margin: 0 0 10px 0">数据时间：{{editDialogCustomer.dataTime | dateFormat('yyyy-MM-dd')}}</div>
      <table class="dialog-table">
        <tr>
          <th>用电量（kwh）</th>
          <th>表起码</th>
          <th>表止码</th>
          <th>倍率</th>
        </tr>
        <tr>
          <td>{{editDialogCustomer.consumption}}</td>
          <td>{{editDialogCustomer.beginMeterReading}}</td>
          <td>{{editDialogCustomer.endMeterReading}}</td>
          <td>{{editDialogCustomer.rate}}</td>
        </tr>
      </table>
      <table class="dialog-table">
        <tr>
          <th>正总表码</th>
          <th>正尖表码</th>
          <th>正峰表码</th>
          <th>正平表码</th>
          <th>正谷表码</th>
        </tr>
        <tr>
          <td>{{editDialogCustomer.totalPositiveMeterReading}}</td>
          <td>{{editDialogCustomer.tipPositiveMeterReading}}</td>
          <td>{{editDialogCustomer.peakPositiveMeterReading}}</td>
          <td>{{editDialogCustomer.plainPositiveMeterReading}}</td>
          <td>{{editDialogCustomer.valleyPositiveMeterReading}}</td>
        </tr>
      </table>
      <table class="dialog-table">
        <tr>
          <th>负总表码</th>
          <th>负尖表码</th>
          <th>负峰表码</th>
          <th>负平表码</th>
          <th>负谷表码</th>
        </tr>
        <tr>
          <td>{{editDialogCustomer.totalReverseMeterReading}}</td>
          <td>{{editDialogCustomer.tipReverseMeterReading}}</td>
          <td>{{editDialogCustomer.peakReverseMeterReading}}</td>
          <td>{{editDialogCustomer.plainReverseMeterReading}}</td>
          <td>{{editDialogCustomer.valleyReverseMeterReading}}</td>
        </tr>
      </table>
      <table class="dialog-table">
        <tr>
          <th>总有功功率</th>
          <th>总无功功率</th>
          <th>总正向有功</th>
          <th>总正向无功</th>
        </tr>
        <tr>
          <td>{{editDialogCustomer.totalActivePower}}</td>
          <td>{{editDialogCustomer.totalReactivePower}}</td>
          <td>{{editDialogCustomer.totalPositiveActivePower}}</td>
          <td>{{editDialogCustomer.totalPositiveReactivePower}}</td>
        </tr>
      </table>
      <table class="dialog-table">
        <tr>
          <th>总反向有功</th>
          <th>总反向无功</th>
        </tr>
        <tr>
          <td>{{editDialogCustomer.totalReverseActivePower}}</td>
          <td>{{editDialogCustomer.totalReverseReactivePower}}</td>
        </tr>
      </table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showSaveDialog = false">关 闭</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";

  //时间格式化
  Date.prototype.format = function (format) {
    var o = {
      "M+": this.getMonth() + 1, //month
      "d+": this.getDate(),    //day
      "h+": this.getHours(),   //hour
      "m+": this.getMinutes(), //minute
      "s+": this.getSeconds(), //second
      "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
      "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
      (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)if (new RegExp("(" + k + ")").test(format))
      format = format.replace(RegExp.$1,
        RegExp.$1.length == 1 ? o[k] :
          ("00" + o[k]).substr(("" + o[k]).length));
    return format;
  };

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "UseQuantity",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      return {
        tableMainSearchModelBase:{
          corpName: ''
        },
        tableMainSearchModel:{
          officeId:'',
          regionId:[],
          stationId:'',
          startEndTime:'',

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
          {value: '1', label: '作废'}
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
          description: [
            { required: true, message: '费用名称不能为空', trigger: 'blur' }
          ],
        }
      };
    },
    computed: {
      getActionWhere(){
        return {
          unionId : this.unionId,
          type:'00',
        }
      },
      unionId() {
        return this.$route.params.unionId
      },
    },
    methods: {
      //打开新增或编辑费项弹窗
      openDialog(item){
        this.showSaveDialog = true;
        //移除校验结果并重置字段值
        this.$refs.saveForm && this.$refs.saveForm.clearValidate();
        //新增不传item 编辑要传入item
        this.editDialogCustomer = {
          ...item
        }
        let pageVo =  this.$refs.tableMain.getPageVo();
        console.log(pageVo);
        if(pageVo.startEndTime.length==2){
          this.editDialogCustomer.dataStartTime = pageVo.startEndTime[0].format('yyyy-MM-dd');
          this.editDialogCustomer.dataEndTime = pageVo.startEndTime[1].format('yyyy-MM-dd');
        }
        //地点，地区，单元初始化
        this.changeOfficeForSave(item.officeId,function(){
          this.changeRegionForSave(item.regionId)
        })

      },
      setstartEndTime(){
        this.editDialogCustomer.st
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
            this.$api.energy.meterUnitRel.save(this.editDialogCustomer).then(res => {
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
        this.$api.energy.useQuantity.exportExcel(vo)
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
          this.stationOptions = [];
          this.$refs.tableMain.searchModelData.stationId='';
          this.stationOptions = res.data;
          //清空查询条件后面的级联 区域，单元

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
  .dialog-table{
    text-align:center;
    border-collapse: collapse;
    border:#bfdcf9 solid 1px;
    line-height: 32px;
    margin-bottom: 15px;
    th{ background: #ddeeff;border:#bfdcf9 solid 1px;width: 120px;}
    td{ border:#bfdcf9 solid 1px;}
  }
</style>
