<template>
  <div>
    <d2-container>
      <template slot="header">账单收款管理</template>
      <el-row>
        <el-col :span="12">
          <div class="grid-content-top">
            <p class="key">未付清账单</p>
            <p class="value">{{(totalData.payStatusUnPayNum + totalData.payStatusPartPayedNum)||0}}个</p>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="grid-content-top">
            <p class="key">未收款金额</p>
            <p class="value">{{(totalData.payStatusUnPayAmt + totalData.payStatusPartPayedAmt)||0}}元</p>
          </div>
        </el-col>
      </el-row>
      <!---->
      <table-comb
        name="账单列表"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :search-model="tableMainSearchModel"
        :get-action="$api.energy.billManage.billReceivelist"
        :get-action-where="getActionWhere"
        :autoFetch="false"
        :remove-action="$api.energy.billManage.delete"
        checkedDispalyName="billNo"
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
            placeholder="账单号/客户名称/联系人/电话"
            prefix-icon="el-icon-search"
            clearable
            v-model="props.form.searchKey"
            style="width: 240px; margin-left: 10px;"
          >
          </el-input>
          <el-button v-if="pointAble" type="success" class="fr ml10" @click="exportExcel()">导出表格</el-button>
          <el-button type="success" class="fr" @click="reminderPayBill(false)">批量催缴</el-button>
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
              <el-form-item label="缴费状态：">
                <el-select
                  v-model="props.form.payStatus"
                  @change="changeStation"
                  placeholder="请选择"
                  filterable
                >
                  <el-option
                    v-for="item in payStatusOption"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="欠费记录：">
                <el-select
                  v-model="props.form.arrearageStatus"
                  @change="changeStation"
                  placeholder="请选择"
                  filterable
                >
                  <el-option
                    v-for="item in arrearageStatusOption"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="应收日期：">
                <el-date-picker
                  style="width:214px"
                  v-model="props.form.billStartEndDate"
                  type="daterange"
                  range-separator="~"
                    start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column fixed="left" type="selection" :selectable="function(row, index){ return row.payStatus!=='01'}" width="35"></el-table-column>
          <el-table-column fixed="left" prop="corpName" label="操作" :width="pointAble ? '220px':'180px'">
            <template slot-scope="props">
              <el-button type="text" size="mini" @click="detail(props.row)">查看账单</el-button>
              <el-button type="text" size="mini" @click="openRecord(props.row)">记录</el-button>
              <template v-if="props.row.payStatus!=='01'">
                <el-button type="text" size="mini" @click="openSaveDialog(props.row)">缴费</el-button>
                <el-button type="text" size="mini" @click="reminderPayBill(props.row)">催缴</el-button>
                <el-button v-if="pointAble" type="text" size="mini" @click="billPaymentNotice(props.row)">打印</el-button>
              </template>
            </template>
          </el-table-column>
          <el-table-column width="170px" prop="billNo" label="业务流水号"></el-table-column>
          <el-table-column prop="payStatusDesc" label="缴费状态"></el-table-column>
          <el-table-column width="200px" prop="officeName"   label="地点"></el-table-column>
          <el-table-column prop="levelDesc" width="150px" label="区域">
            <template slot-scope="props">
              {{props.row.levelDesc||'-'}} | {{props.row.level2Desc||'-'}}
            </template>
          </el-table-column>
          <el-table-column prop="stationName" label="单元" width="100px"></el-table-column>
          <el-table-column width="120px" prop="billMonthStr" label="月份"></el-table-column>
          <el-table-column prop="feeTypeDesc" label="费项"></el-table-column>
          <el-table-column prop="receivableAmt" label="应收金额">
            <template slot-scope="props">
              {{props.row.receivableAmt| NumFormat}}
            </template>
          </el-table-column>
          <el-table-column width="120px" prop="receivableDate" label="应收日期">
            <template slot-scope="props">
              {{props.row.receivableDate| dateFormat('yyyy-MM-dd')}}
            </template>
          </el-table-column>
          <el-table-column prop="corpName" width="200px" label="客户名称"></el-table-column>
          <el-table-column prop="telephone" width="150px" label="联系人">
            <template slot-scope="props">
              {{(props.row.corpContacts||'-')+'/'+(props.row.telephone||'-')}}
            </template>
          </el-table-column>
        </template>
      </table-comb>
      <template slot="footer"></template>
    </d2-container>

    <el-dialog title="缴费" :visible.sync="showSaveDialog" >
      <el-form label-width="100px" :model="editDialogCustomer" :rules="rules" ref="saveForm">
        <div class="bill-info">
          <el-row>
            <el-col :span="12">
              <el-form-item label="地点：">
                {{editDialogCustomer.officeName}}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="企业客户：">
                {{editDialogCustomer.corpName}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="区域：">
                {{editDialogCustomer.levelDesc||'-'}} | {{editDialogCustomer.level2Desc||'-'}}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系人：">
                {{editDialogCustomer.corpContacts}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row >
            <el-col :span="12">
              <el-form-item label="单元：">
                {{editDialogCustomer.stationName}}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系人电话：" >
                {{editDialogCustomer.telephone }}
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <table class="dialog-table">
          <tr>
            <th>月份</th>
            <th>应收日期</th>
            <th>费项</th>
            <th>应收金额</th>
            <th>已缴金额</th>
            <th>未缴金额</th>
          </tr>
          <tr>
            <td>{{editDialogCustomer.billMonthStr}}</td>
            <td>{{editDialogCustomer.receivableDate | dateFormat('yyyy-MM-dd')}}</td>
            <td>{{editDialogCustomer.feeTypeDesc}}</td>
            <td>{{editDialogCustomer.receivableAmt|NumFormat}}</td>
            <td>{{editDialogCustomer.payAmt|NumFormat}}</td>
            <td><span class="col-red">{{ editDialogCustomer.maxAmt | NumFormat }}</span></td>
          </tr>
        </table>
        <el-form-item label="本次缴费：" prop="amt" >
          <template>
            <el-input
              :placeholder="editDialogCustomer.chahPlaceholder"
              clearable
              v-model="editDialogCustomer.amt"
              style="width: 240px; margin-left: 10px;"
            >
            </el-input>
          </template>
        </el-form-item>
        <el-form-item label="备注：">
          <el-input
            type="textarea"
            :rows="2"
            placeholder="可输入缴费方式或其他转账单号"
            v-model="editDialogCustomer.remark">
          </el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">保 存</el-button>
        <el-button @click="showSaveDialog = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="缴费记录" :visible.sync="showRecordDialog" >
      <el-form label-width="120px" :model="recordVo" >
        <div class="bill-info">
          <el-row>
            <el-col :span="12">
              <el-form-item label="地点：">
                {{recordVo.officeName}}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="业务流水号：">
                {{recordVo.billNo}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="区域：">
                {{recordVo.levelDesc||'-'}} | {{recordVo.level2Desc||'-'}}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系人：">
                {{recordVo.corpContacts}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row >
            <el-col :span="12">
              <el-form-item label="单元：">
                {{recordVo.stationName}}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系人电话：" >
                {{recordVo.telephone }}
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <div class="line" style="margin-bottom: 10px"></div>
        <div class="record-list"
          v-loading="recordVo.recordLoading"
          element-loading-text="缴费记录加载中"
        >
          <div v-if="recordVo.payRecVOS && recordVo.payRecVOS.length>0">
            <div class="record-item"
               v-for="item in recordVo.payRecVOS"
               :key="item.billId"
            >
              <div class="record-title">
                操作人员：<span class="user"> {{item.createUserName}}</span>
                操作时间：<span class="paytime">{{item.paydate }} {{item.paytime }}</span>
              </div>
              <table class="dialog-table">
                <tr>
                  <th>金额</th>
                  <th>支付流水号</th>
                  <th>第三方流水号</th>
                  <th>备注</th>
                </tr>
                <tr>
                  <td>{{item.billFee}}</td>
                  <td>{{item.externalId||'-'}}</td>
                  <td>{{item.thirdPayId||'-'}}</td>
                  <td><div v-html="item.remark"></div></td>
                </tr>
              </table>
            </div>
          </div>
          <div style="line-height: 50px; text-align: center" v-else>
            暂无缴费记录
          </div>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showRecordDialog = false">关 闭</el-button>
      </div>
    </el-dialog>

    <el-dialog title="查看账单" :visible.sync="showDetailDialog" class="details-info">
      <el-form label-width="120px">
        <div class="bill-info">
          <el-row>
            <el-col :span="12">
              <el-form-item label="地点：">
                {{currentBill.officeName}}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="业务流水号：">
                {{currentBill.billNo}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="区域：">
                {{currentBill.levelDesc||'-'}} | {{currentBill.level2Desc||'-'}}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系人：">
                {{currentBill.corpContacts}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row >
            <el-col :span="12">
              <el-form-item label="单元：">
                {{currentBill.stationName}}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系人电话：" >
                {{currentBill.telephone }}
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <table class="dialog-table">
          <tr>
            <th>月份</th>
            <th>应收日期</th>
            <th>费项</th>
            <th>应收金额</th>
            <th>已缴金额</th>
            <th>未缴金额</th>
          </tr>
          <tr>
            <td>{{currentBill.billMonthStr}}</td>
            <td>{{currentBill.receivableDate | dateFormat('yyyy-MM-dd')}}</td>
            <td>{{currentBill.feeTypeDesc}}</td>
            <td>{{currentBill.receivableAmt|NumFormat}}</td>
            <td>{{currentBill.payAmt|NumFormat}}</td>
            <td><span class="col-red">{{ currentBill.maxAmt | NumFormat }}</span></td>
          </tr>
        </table>
        <el-row >
          <el-col :span="12">
            <el-form-item label="缴费状态：">
              {{currentBill.payStatusDesc}}
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showDetailDialog = false">关 闭</el-button>
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
    name: "BillAdd",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      let checkAmt = (rule, value, callback)=>{
        let isCash=/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
        if(value ===''){
          return callback(new Error('请输入金额'));
        }
        if(!isCash.test(value)){
          return callback(new Error('输入金额有误，最多保留两位小数'));
        }
        if(parseFloat(value)==0){
          return callback(new Error('至少需要缴费0.01元'));
        }
        if(parseFloat(value)>this.editDialogCustomer.maxAmt){
          return callback(new Error(`最多缴费${this.editDialogCustomer.maxAmt}元`));
        }
        callback();
      };
      return {
        totalData:{
          payStatusUnPayNum:0,
          payStatusUnPayAmt:0
        },
        tableMainSearchModelBase:{
          siteId:'',
          searchKey: ''
        },
        tableMainSearchModel:{
          officeId:'',
          regionId:[],
          stationId:'',
          payStatus:'',
          arrearageStatus:'',
          billMonth:'',

        },
        siteOptions:[],
        officeOptions:[],
        regionOptions:[],
        regionProps:{
          value: 'id',
          label:'name',
          children: 'childLists'
        },
        corpDisabled:true,
        templateTypeOptions:[
          {value: '00', label: '通用'},
          {value: '01', label: '多电价阶段'},
        ],
        payStatusOption:[
          {value: '', label: '全部'},
          {value: '00', label: '未付款'},
          {value: '01', label: '已付款'},
          {value: '02', label: '部分付款'},
        ],
        arrearageStatusOption:[
          {value: '', label: '全部'},
          {value: '0', label: '未欠费'},
          {value: '1', label: '已欠费'},

        ],
        stationOptions:[],
        feeTypesOptions:[],
        tableData: [],
        payLoading:false,
        activeName:'first',
        currentBillActiveName:'first',
        showSaveDialog:false,
        showRecordDialog:false,
        showDetailDialog:false,
        currentBill:{
          remarkVo:[]
        },
        recordVo:{
          recordLoading:false
        },
        editDialogCustomer:{},
        rules:{
          amt: [
            {required: true, validator: checkAmt,trigger: 'blur' }
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

      pointAble(){
        //允许显示打印按钮的id 中锐以及开发、测试、beta测试平台才显示打印按钮
        let allowUnionIds = ['1CPVSN7TBJQS0501140A0000E5B9EBAC',"P0000000000000000000000000013","5976adf8fc584cde8e41639927e1008d","1BGQA0ADE000150B10AC000009486760"];
       return allowUnionIds.indexOf(this.$route.params.unionId) != -1;
      }
  },
    methods: {
      //打开编辑弹窗
      openSaveDialog(item){
        this.showSaveDialog = true;
        //移除校验结果并重置字段值
        this.$refs.saveForm && this.$refs.saveForm.clearValidate();
        //新增不传item 编辑要传入item
        console.log(item);
        let maxAmt = (item.receivableAmt - item.payAmt).toFixed(2);
        let chahPlaceholder = `请输入金额（未缴${maxAmt}元）`;
        this.editDialogCustomer = {
          ...item,
          remark:'',
          chahPlaceholder,
          amt:maxAmt,
          maxAmt
        }
      },
      //打开记录弹窗
      openRecord(item){
        this.showRecordDialog = true;
        this.recordVo = {
          ...item,
          recordLoading:true
        }
        //获取记录列表
        this.$api.energy.billManage.detail(item.id).then(res =>{
          this.recordVo.recordLoading = false;
          if(res.code==0){
            this.recordVo.payRecVOS = res.data.payRecVOS;
          }else{
            this.$message.error(res.message);
          }
        })

      },
      //导出Excel
      exportExcel(){
        if(this.$refs.tableMain.tableData.length==0){
          this.$message.error('没有可以导出的数据');
          return;
        }
        let billVos = this.$refs.tableMain.checkedItems;
        //判断是否选择了记录
        if(billVos.length==0){
          this.$message.error('请先勾选需要导出的记录');
          return;
        }
        this.$api.energy.billManage.exportExcel(billVos.map(item=>{
          return item.id;
        }).join(','))
      },
      //查看详情
      detail(item){
        console.log(item);
        this.showDetailDialog = true;
        this.currentBill = item;
        this.currentBill.billStartEndDate = `${new Date(item.billStartDate).format('yyyy-MM-dd')} - ${new Date(item.billEndDate).format('yyyy-MM-dd')}`;
        this.currentBill.maxAmt = (item.receivableAmt - item.payAmt).toFixed(2);
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
      //缴费
      save(){
        //验证
        console.log(this.editDialogCustomer);
        if(this.payLoading){
          this.$message.error('已发出请求，请勿重复提交');
          return false;
        }
        this.$refs.saveForm.validate((valid) => {
          console.log(valid)
          if (valid) {
            let _vo = {
              id:this.editDialogCustomer.id,
              amt:this.editDialogCustomer.amt,
              remark:this.editDialogCustomer.remark,
            };
            this.payLoading=true;
            this.$api.energy.billManage.payment(_vo).then(res => {
             setTimeout(()=>{
               this.payLoading=false;
             },500);
              if(res.code==0){
                let message = '缴费成功！'
                this.showSaveDialog = false;
                this.$message({
                  message,
                  type: 'success'
                });
                //更新列表
                this.getTotal();
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
      //催缴
      reminderPayBill(item){
        let ids = [];
        if(item){
          ids = [item.id]
        }else{
          this.$refs.tableMain.checkedItems.map(item =>{
            if(item.payStatus!=='01'){
              ids.push(item.id)
            }
          });
        }
        if(ids.length==0){
          this.$message.error('未选择任何需催缴记录');
          return false;
        }
        let vo = {
          ids
        }
        this.$api.energy.billManage.reminderPayBill(vo).then(res => {
          if(res.code==0){
            let message = '催缴成功！'
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
      },
      //打印
      billPaymentNotice(item){
        this.$api.energy.billManage.billPaymentNotice(item.id)
      },
      //切换站点
      changeSite(siteId){
        this.getTotal(siteId);
        this.$api.merch.region.officeOptions(siteId).then(res => {
          this.officeOptions  = res.data;
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
      //获取账单审核状态统计
      getTotal(siteId){
        if(!siteId){
          siteId = this.$refs.tableMain.searchModelDataBase.siteId;
        }
        this.$api.energy.billManage.countBillPayData(siteId).then(res=>{
          this.totalData =  res.data;
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
      changeStationForSave(stationId){
        let siteId = this.editDialogCustomer.siteId;
        //获取企业信息
        this.$api.tet.stationCorp.getStationCurrCorpInfo(stationId).then(res=>{
          this.editDialogCustomer.corpName = res.data.corpName;
          this.editDialogCustomer.corpId = res.data.corpName;
          this.editDialogCustomer.corpContacts = res.data.corpContacts;
          this.editDialogCustomer.telephone = res.data.telephone;
        })

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

          //获取费项列表
          this.$api.tet.feeType.getFeeTypesAndAuditStatus().then(res =>{
            console.log(res)
            this.feeTypesOptions = res.data;
          })
      },0)
    }
  };
</script>


<style type="text/scss" lang="scss" scoped>
  .grid-content-top{
     text-align: center;
    margin-bottom: 20px;
    .key{
      font-size: 14px;
      margin: 0;
      color:#aaa;
    }
    .value{
      font-size: 24px;
      font-weight: 700;
    }
  }
  .line{ width: 100%; height: 1px;background: #ddd; overflow: hidden; margin:0 0 20px 0}
  .el-upload__tip{ color: #E6A23C}

  .details-info .el-form-item{margin-bottom: 0;}
  .dialog-table{ width: 100%; text-align: center; border-collapse: collapse; border: none; line-height: 35px;}
  .dialog-table th{ background: #ddd}
  .remark-list-title{ font-size:16px; padding: 0 0 20px 0}
  .dialog-table{
    text-align:center;
    border-collapse: collapse;
    border:#bfdcf9 solid 1px;
    line-height: 32px;
    margin-bottom: 15px;
    th{ background: #ddeeff;border:#bfdcf9 solid 1px;width: 120px;}
    td{ border:#bfdcf9 solid 1px;}
  }
  .col-red{ color: #f00}
  .bill-info .el-form-item{ margin-bottom: 0}
  .record-list{
    min-height: 150px;
    .record-item{
      margin-bottom:15px;
      .record-title{
        font-size: 14px;
        line-height: 30px;
        margin-bottom: 10px;
        .user{
          color: #0e3d95;
          padding-right: 30px;
        }
        .paytime{
          color: #999;
        }
      }
    }
  }
</style>
<style>
  .el-tag{ margin-right: 10px; cursor: pointer;}
  .el-dialog__body{ padding: 0 20px;}
</style>
