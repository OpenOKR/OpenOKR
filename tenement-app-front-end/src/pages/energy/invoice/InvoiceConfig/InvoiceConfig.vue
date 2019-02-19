<template>
  <div>
    <d2-container>
      <el-tabs  v-model="activeName" @tab-click="tabChange">
        <el-tab-pane name="pane1" label="开票费项设置">
          <table-comb
            name="开票费项列表"
            ref="tableMain"
            :search-model-base="tableMainSearchModelBase"
            :get-action="$api.energy.invoice.config.list"
            :get-action-where="getActionWhere"
            :remove-action="$api.energy.invoice.config.logicDeleteById"
            custom-id="stationId"
            :autoFetch="false"
          >
            <!--基础查询-->
            <template slot="baseSearchForm" slot-scope="props">
              <el-select
                v-model="siteId"
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
              <el-button class="fr" @click="openSaveDialog()">添加</el-button>
            </template>
            <!--表格-->
            <template slot="tableColumns">
              <el-table-column fixed="left" label="操作">
                <template slot-scope="props">
                  <el-button type="text" size="mini" @click="openSaveDialog(props.row)">编辑</el-button>
                  <el-button type="text" size="mini" @click="deleteItem(props.row)">删除</el-button>
                </template>
              </el-table-column>
              <el-table-column prop="itemName" width="260" label="货物或应税劳务、服务名称"></el-table-column>
              <el-table-column prop="taxCode" label="税收分类编码"></el-table-column>
              <el-table-column prop="speTaxRate" label="专票税率">
                <template slot-scope="props">
                  {{props.row.speTaxRate}}%
                </template>
              </el-table-column>
              <el-table-column prop="comTaxRate" label="普票税率">
                <template slot-scope="props">
                  {{props.row.comTaxRate}}%
                </template>
              </el-table-column>
              <el-table-column prop="defaultTicketTypeStr" label="默认类型"></el-table-column>
              <el-table-column prop="feeRelStr" label="关联系统费项"></el-table-column>
            </template>
          </table-comb>
          <template slot="footer"></template>
        </el-tab-pane>
        <el-tab-pane name="pane2" label="开票备注设置">
          <div class="change-site">
            <el-select
              v-model="siteId"
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
          </div>
          <el-row style="margin-top: 20px;">
            <el-col :span="12">
              <el-form label-width="100px"  :model="remarkVo" :rules="rules" ref="remarkForm">
                <el-form-item label="默认备注：" prop="defaultText">
                  <el-input
                    type="textarea"
                    @click.native="setSelection($event)"
                    @keyup.native="setSelection($event)"
                    :rows="10"
                    placeholder="请输入内容"
                    style="width: 90%;min-height: 120px"
                    v-model="remarkVo.defaultText">
                  </el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="saveRemark">保 存</el-button>
                  <el-button @click="getPane2Page">取 消</el-button>
                </el-form-item>
              </el-form>
            </el-col>
            <el-col :span="12">
              <el-card class="box-card">
                <div slot="header" class="clearfix">
                  <span style="font-weight: bold">可选变量</span><span style="color:#666; font-size: 12px; margin-left: 15px">点击选择变量添加至左侧备注区域</span>
                </div>
                <div class="symbol-box">
                  <el-tag
                    v-for="item in invoiceAllBizVar"
                    :key="item"
                    @click.native="pushBizVar(item)"
                    style="margin-bottom: 15px;"
                  >{{item}}</el-tag>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane name="pane3" label="开票项合并设置">
          <div class="change-site">
            <el-select
              v-model="siteId"
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
          </div>
          <div class="combine-box">
            <p>同种“货物或应税劳务、服务名称”开票合并规则：</p>
            <el-form label-width="0"  :model="combineVo" :rules="rules" ref="combineForm">
              <el-form-item prop="combineRule">
                <el-radio-group v-model="combineVo.combineRule" >
                  <el-radio label="1">全部合并</el-radio>
                  <el-radio label="2">同一个账单月的费项合并，其余不合并</el-radio>
                  <el-radio label="3">全部不合并</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-form>
            <div style="margin-top: 50px">
              <el-button type="primary" @click="saveCombine">保 存</el-button>
              <el-button @click="getPane3Page">取 消</el-button>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane name="pane4" label="开票连打设置">
          <div class="change-site">
            <el-select
              v-model="siteId"
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
          </div>
          <el-form label-width="120px"  :model="batchVo" :rules="rules" ref="batchForm" style="margin-top: 20px;">
            <el-form-item label="连打限制张数：" prop="batchLimit">
              <el-input-number
                v-model="batchVo.batchLimit"
                controls-position="right"
                :min="1"
                style="width: 200px"
              ></el-input-number>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveBatch">保 存</el-button>
              <el-button @click="getPane4Page">取 消</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <template slot="footer"></template>
    </d2-container>

    <el-dialog :title="editVo.id ?'开票费项设置':'添加开票费项'" :visible.sync="showSaveDialog">
      <el-form label-width="220px" :model="editVo" :rules="rules" ref="saveForm">
        <el-form-item label="货物或应税劳务、服务名称：" prop="itemName">
          <el-input v-model="editVo.itemName" placeholder="请填写"></el-input>
        </el-form-item>
        <el-form-item label="税收分类编码：" prop="taxCode">
          <el-input v-model="editVo.taxCode" placeholder="请填写"></el-input>
        </el-form-item>
        <el-form-item label="增值税专票税率：" prop="speTaxRate">
          <el-input v-model="editVo.speTaxRate" placeholder="请填写小于100的整数">
            <template slot="append">%</template>
          </el-input>
        </el-form-item>
        <el-form-item label="增值税普票税率：" prop="comTaxRate">
          <el-input v-model="editVo.comTaxRate" placeholder="请填写小于100的整数">
            <template slot="append">%</template>
          </el-input>
        </el-form-item>
        <el-form-item label="默认开具发票：" prop="defaultTicketType" >
          <template>
            <el-radio v-model="editVo.defaultTicketType" label="1">增值税专用发票</el-radio>
            <el-radio v-model="editVo.defaultTicketType" label="2">增值税普通发票</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="关联费项：" prop="feeIdList" >
          <el-checkbox-group v-model="editVo.feeIdList">
            <el-checkbox
              v-for="item in feeRelVOList"
              :label="item.feeTypeId"
              :key="item.feeTypeId"
              :disabled="!item.aable"
            >
              {{item.feeTypeName}}
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
  import util from '@/libs/util'

  export default {
    name: "InvoiceHistoryList",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      let percentage = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('请填写'));
        }
        setTimeout(() => {
            if (value < 0 || value > 100 || !/^[1-9]\d*$/.test(value)) {
              callback(new Error('请填写0到100的整数'));
            }
            else {
              callback();
            }
        }, 10);
      };

      return {
        siteId:'',
        siteOptions:[],
        tableMainSearchModelBase:{

        },
        activeName:'pane1',
        showSaveDialog:false,
        editVo:{},
        feeRelVOList:[],
        rules:{
          itemName: [
            { required: true, message: '请填写', trigger: 'change' }
          ],
          taxCode: [
            { required: true, message: '请填写', trigger: 'change' }
          ],
          speTaxRate: [
            {required: true,   validator: percentage, trigger: 'blur' }
          ],
          comTaxRate: [
            { required: true,  validator: percentage, trigger: 'blur' }
          ],
          defaultTicketType: [
            { required: true, message: '请选择', trigger: 'change' }
          ],
          feeIdList: [
            {  type: 'array', required: true, message: '请至少选择一个费项', trigger: 'change' }
          ],
          defaultText: [
            { required: true, message: '请填写', trigger: 'blur' }
          ],
          combineRule: [
            { required: true, message: '请填写', trigger: 'change' }
          ],
          batchLimit: [
            { required: true, message: '请填写', trigger: 'blur' }
          ],
        },
        remarkVo:{
          defaultText:''
        },
        remarkSelection:{
          selectionStart:0,
          selectionEnd:0,
        },
        invoiceAllBizVar:[],
        combineVo:{
        },
        batchVo:{},
      };
    },
    computed: {
      getActionWhere() {
        return {
          unionId: this.unionId,
          siteId:this.siteId
        }
      },
      unionId() {
        return this.$route.params.unionId
      },
    },
    methods: {
      tabChange(tab, event){
        console.log(tab.name);
        switch (tab.name) {
          case 'pane1':
            this.$nextTick(() => {
              this.$refs.tableMain.fetchData();
            });

            break;
          case 'pane2':
            this.getPane2Page();
            break;
          case 'pane3':
            this.getPane3Page();
            break;
          case 'pane4':
            this.getPane4Page();
            break;
          default:
            break;
        }
      },
      changeSite(siteId) {
        this.tabChange({name:this.activeName})
      },
      //打开弹出窗
      openSaveDialog(item){
        this.showSaveDialog = true;
        this.$api.energy.invoice.config.queryFeeCheckBoxList(this.siteId).then(res=>{
          this.feeRelVOList = res.data.feeCheckBoxList;
          if(item){
            this.editVo = {
              ...item,
            }
            this.feeRelVOList.map(item2=>{
              if(item.feeIdList.indexOf(item2.feeTypeId)!=-1){
                item2.aable = true;
              }
            })
          }else{
            this.editVo = {
              id:null,
              itemName:'',
              feeIdList:[],
              taxCode:'',
              speTaxRate:'',
              comTaxRate:'',
              defaultTicketType:'1',
            };
          }
          console.log(this.editVo)
          //移除校验结果并重置字段值
          this.$nextTick(() => {
            this.$refs.saveForm && this.$refs.saveForm.clearValidate();
          });
        });
      },
      //保存或者新增
      save(){
        this.$refs.saveForm.validate((valid) => {
          if (valid) {
            //保存
            let vo ={
              ...this.editVo,
              unionId:this.unionId,
              siteId:this.siteId,
              feeRelVOList : this.feeRelVOList.filter(item=>{
                return this.editVo.feeIdList.indexOf(item.feeTypeId) !=-1
              })
            };
            delete vo.feeIdList;
            this.$api.energy.invoice.config.save(vo).then(res => {
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
      //删除
      deleteItem(item){
        this.$refs.tableMain.remove(item.id);
      },
      getPane2Page(){
        //获取该站点得到开票备注
        this.$api.energy.invoice.config.getVOBySiteId(this.siteId).then(res => {
          this.remarkVo.defaultText = res.data.defaultText;
          this.remarkVo.id = res.data.id;
          //移除校验
          this.$nextTick(() => {
            this.$refs.remarkForm && this.$refs.remarkForm.clearValidate();
          });
        });

        //获取站点可选变量
        this.$api.energy.invoice.config.getAllBizVar(this.siteId).then(res => {
          this.invoiceAllBizVar = res.data;
        });
      },
      getPane3Page(){
        //获取站点开票合并规则
        this.$api.energy.invoice.config.getCombine(this.siteId).then(res => {
          this.combineVo = res.data;
          this.combineVo.siteId=this.siteId;
          //移除校验
          this.$nextTick(() => {
            this.$refs.combineForm && this.$refs.combineForm.clearValidate();
          });
        });
      },
      getPane4Page(){
        //获取站点发票连打规则
        this.$api.energy.invoice.config.getBatch(this.siteId).then(res => {
          this.batchVo = res.data;
          this.batchVo.siteId=this.siteId;
          //移除校验
          this.$nextTick(() => {
            this.$refs.batchForm && this.$refs.batchForm.clearValidate();
          });
        });
      },
      //保存或者新增
      saveRemark(){
        this.$refs.remarkForm.validate((valid) => {
          if (valid) {
            //保存
            let vo ={
              id:this.remarkVo.id,
              defaultType:9,
              defaultText:this.remarkVo.defaultText,
              siteId:this.siteId,
            };
            this.$api.energy.invoice.config.saveRemark(vo).then(res => {
              if(res.code==0){
                let message ='保存成功';
                this.$message({
                  message,
                  type: 'success'
                });
                this.getPane2Page();
              }else{
                this.$message.error(res.message);
              }
            });
          } else {
            return false;
          }
        });
      },
      //插入变量
      pushBizVar(item){
        let remark =  this.remarkVo.defaultText;
        console.log(`{${item}}`);
        let itemHtml = '${'+ item +'}';
        if(remark){
          remark = remark.substring(0,this.remarkSelection.selectionStart) + itemHtml+ remark.substring(this.remarkSelection.selectionEnd);
          this.remarkVo.defaultText = remark
        }else{
          this.remarkVo.defaultText= itemHtml
        }

      },
      setSelection(e){
        this.remarkSelection = {
          selectionStart:e.target.selectionStart,
          selectionEnd:e.target.selectionEnd
        }
      },
      //开票规则保存
      saveCombine(){
        this.$refs.combineForm.validate((valid) => {
          if (valid) {
            //保存
            console.log(this.combineVo)
            this.$api.energy.invoice.config.saveCombine(this.combineVo).then(res => {
              if(res.code==0){
                let message ='保存成功';
                this.$message({
                  message,
                  type: 'success'
                });
                this.getPane3Page();
              }else{
                this.$message.error(res.message);
              }
            });
          } else {
            return false;
          }
        });
      },
      //开票限制张数
      saveBatch(){
        this.$refs.batchForm.validate((valid) => {
          if (valid) {
            //保存
            this.$api.energy.invoice.config.saveBatch(this.batchVo).then(res => {
              if(res.code==0){
                let message ='保存成功';
                this.$message({
                  message,
                  type: 'success'
                });
                this.getPane3Page();
              }else{
                this.$message.error(res.message);
              }
            });
          } else {
            return false;
          }
        });
      },
    },
    mounted() {
      setTimeout(()=>{
       this.$api.energy.invoice.config.siteOptions(this.unionId).then(res => {
         this.siteOptions = res.data;
         console.log(util.getUrlParam('siteId'));
         if(util.getUrlParam('siteId')){
           this.siteId = util.getUrlParam('siteId');
         }else{
           this.siteId = res.data[0].siteId;
         }
         this.changeSite(this.siteId);
       });
     },0)

    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .el-dialog__body {
    padding: 0 20px;
  }
  .symbol-box{ min-height: 120px;}
  .combine-box{ }
  .combine-box .el-radio{ display: block; margin:20px 0 20px 20px}
</style>

<style>
.el-input__inner{ padding-right: 0}
</style>