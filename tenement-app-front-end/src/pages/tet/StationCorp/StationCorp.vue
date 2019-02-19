<template>
  <div>
    <d2-container>
      <template slot="header">单元企业关系管理</template>
      <br>
      <!---->
      <table-comb
        name="单元企业关系列表"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :search-model="tableMainSearchModel"
        :get-action="$api.tet.stationCorp.list"
        :get-action-where="getActionWhere"
        custom-id="stationId"
        checkedDispalyName="stationName"
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
            placeholder="搜索企业名称"
            prefix-icon="el-icon-search"
            clearable
            v-model="props.form.searchKey"
            style="width: 160px; margin-left: 10px;"
          >
          </el-input>
          <el-button class="fr" @click="openDialog()">批量编辑</el-button>
        </template>
        <!--高级查询-->
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
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column fixed="left" label="操作" width="100px">
            <template slot-scope="props">
              <el-button type="text" size="mini" @click="openDialog(props.row)">编辑</el-button>
              <el-button type="text" size="mini" @click="showHistory(props.row)">记录</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="officeName" label="地点"></el-table-column>
          <el-table-column prop="levelInfoDesc" label="区域"></el-table-column>
          <el-table-column prop="stationName" label="单元"></el-table-column>
          <el-table-column prop="corpName" label="当前企业"></el-table-column>
          <el-table-column prop="telephone" label="联系人">
            <template slot-scope="props">
              {{(props.row.corpContacts||'--')+'/'+(props.row.telephone||'--')}}
            </template>
          </el-table-column>
        </template>
      </table-comb>
      <template slot="footer"></template>
    </d2-container>

    <el-dialog :title="editDialogCustomer.checkedItems.length>1 ?'批量编辑':'编辑'" :visible.sync="showSaveDialog">
      <el-form label-width="120px" :model="editDialogCustomer" :rules="rules" ref="saveForm">
        <template v-if="editDialogCustomer.checkedItems.length==1">
          <el-form-item label="地点：">
            {{editDialogCustomer.checkedItems[0].officeName}}
          </el-form-item>
          <el-form-item label="区域：">
            {{editDialogCustomer.checkedItems[0].levelInfoDesc}}
          </el-form-item>
          <el-form-item label="单元：">
            {{editDialogCustomer.checkedItems[0].stationName}}
          </el-form-item>
        </template>
        <template v-else>
          <el-form-item label="已选单元：">
            <span style="color:#409eff">列表中勾选的单元</span>
          </el-form-item>
        </template>
        <el-form-item label="企业：" prop="corpId">
          <el-select
            v-model="editDialogCustomer.corpName"
            @change="changeCorp"
            placeholder="请输入关键字查询企业"
            filterable
            remote
            clearable
            reserve-keyword
            :remote-method="getCorpList"
            :loading="corpLoading"
            class="w400"
          >
            <el-option
              v-for="item in corpList"
              :key="item.id"
              :label="item.name"
              :value="item.name">
              {{item.name}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="主管理员：">
          <span>{{editDialogCustomer.corpContacts||'--'}}</span>
        </el-form-item>
        <el-form-item label="联系方式：">
          <span>{{editDialogCustomer.telephone||'--'}}</span>
        </el-form-item>
        <el-form-item label="入驻开始时间：" prop="settleStartTime">
          <el-date-picker
            v-model="editDialogCustomer.settleStartTime"
            type="date"
            :picker-options="settleStartTimeOptions"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="入驻结束时间：" prop="settleEndTime">
          <el-date-picker
            v-model="editDialogCustomer.settleEndTime"
            type="date"
            :picker-options="settleEndTimeOptions"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">保 存</el-button>
        <el-button @click="showSaveDialog = false">取 消</el-button>
      </div>
    </el-dialog>

    <corp-history ref="historyDialog" @fetchData="handleUpdateSuccess"></corp-history>
  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";
  import CorpHistory from "../componnets/CorpHistory"

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "FeeAudit",
    components: {CorpHistory},
    mixins: [
      listMixin
    ],
    data() {
      return {
        tableMainSearchModelBase:{
          siteId:'',
          searchKey:''
        },
        tableMainSearchModel:{
          officeId:'',
          regionId:[],
          stationId:'',

        },
        siteOptions:[],
        officeOptions:[],
        regionOptions:[],
        stationOptions:[],
        regionProps:{
          value: 'id',
          label:'name',
          children: 'childLists'
        },
        corpList:[],
        tableData: [],
        showSaveDialog:false,
        //（批量）编辑弹出窗vo
        editDialogCustomer:{
          checkedItems:[],
          corpId:'',
          corpName:'',
          telephone:'',
          settleStartTime:'',
          settleEndTime:'',
        },
        corpLoading: false,
        //开始时间可选区域
        settleStartTimeOptions:{
          disabledDate: (time) => {
            let settleEndTime = this.editDialogCustomer.settleEndTime;
            if (settleEndTime) {
              return time.getTime() > settleEndTime;
            }
          }
        },
        //结束时间可选区域
        settleEndTimeOptions:{
          disabledDate: (time) => {
            let settleStartTime = this.editDialogCustomer.settleStartTime;
            if (settleStartTime) {
              return time.getTime() < settleStartTime;
            }
          }
        },
        rules:{
          corpId: [
            {required: true, message: '请填写企业', trigger: 'change' }
          ],
          settleStartTime:[
            {required: true, message: '入驻开始时间必填', trigger: 'blur' }
          ],
          settleEndTime:[
              {required: true, message: '入驻开始结束必填', trigger: 'blur' }
          ]
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
        let checkedItems = [];
        //清空企业下拉选项
        this.corpList=[];

        //批量编辑不传item 编辑要传入item
        if(item){
          checkedItems = [item];
        }else{
          //获取所选项
          checkedItems =this.$refs.tableMain.checkedItems;
        }
        console.log(checkedItems);
        this.getCorpList(checkedItems[0].corpName);
        this.editDialogCustomer = {
          checkedItems,
          corpId:'',
          corpName:'',
          telephone:'',
          settleStartTime:'',
          settleEndTime:'',
        }
        //未选中数据
        if(this.editDialogCustomer.checkedItems.length==0){
          this.$message.error('请选择至少一条记录');
          return;
        }

        if(this.editDialogCustomer.checkedItems.length==1){
          let _item =  this.editDialogCustomer.checkedItems[0];
          this.editDialogCustomer.corpName = _item.corpName;
          this.editDialogCustomer.corpId = _item.corpId;
          this.editDialogCustomer.corpContacts = _item.corpContacts;
          this.editDialogCustomer.telephone = _item.telephone;
          this.editDialogCustomer.settleStartTime = _item.settleStartTime;
          this.editDialogCustomer.settleEndTime = _item.settleEndTime;
        }
        this.editDialogCustomer.stationIds =this.editDialogCustomer.checkedItems.map(item=>{
          return item.stationId
        });
        this.showSaveDialog = true;
        //移除校验结果并重置字段值
        this.$refs.saveForm && this.$refs.saveForm.clearValidate();
      },
      //关闭单元入驻记录回调
      handleUpdateSuccess() {
        console.log('点击返回按钮')
        this.$refs.tableMain.fetchData();
      },
      //搜索企业
      getCorpList(searchKey) {
        this.corpLoading=true;
        let par ={
          pageSize:50,
          unionId:this.unionId
        };
        if (searchKey !== '') {
          par.searchKey = searchKey;
        }
        this.$api.corp.getCorpListBySearchKey(par).then(res => {
          this.corpLoading=false;
          this.$nextTick(()=>{
            this.corpList = res.data.data;
          });
        })
      },
      //保存或者新增费项
      save(){
        //验证
        this.$refs.saveForm.validate((valid) => {
          if (valid) {
            let vo = {
              corpId:this.editDialogCustomer.corpId,
              stationIds:this.editDialogCustomer.stationIds,
              settleStartTime:this.editDialogCustomer.settleStartTime,
              settleEndTime:this.editDialogCustomer.settleEndTime
            }
            this.$api.tet.stationCorp.saveStationCorp(vo).then(res => {
              if(res.code==0){
                let message ='修改成功';
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
      //查询单元企业入驻记录
      showHistory(item){
        this.$refs.historyDialog.open({...item,unionId:this.unionId});
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
      changeCorp(name){
        //根据企业名称查找企业id
        let corp = this.corpList.filter(item=>{
          return item.name == name;
        })[0];
        this.editDialogCustomer.corpId =corp.id;
        this.editDialogCustomer.corpContacts =corp.adminUserName;
        this.editDialogCustomer.telephone =corp.adminMobilePhone;
      },
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
  .w400{ width:400px;}
</style>
