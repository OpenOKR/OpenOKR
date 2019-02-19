<template>
  <div>
    <d2-container>
      <template slot="header">物业收款账户管理</template>
      <br>
      <!---->
      <table-comb
        name="企业列表"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :get-action="$api.tet.officeAccount.getOfficeAccountList"
        :get-action-where="getActionWhere"
        :remove-action="$api.tet.officeAccount.deleteOfficeAccount"
        :autoFetch="false"
      >
        <!--高级查询-->
        <template slot="baseSearchForm" slot-scope="props">
          <el-select
            v-model="props.form.siteId"
            @change="changeSite"
            filterable
            placeholder="请选择站点"
            class="mr10"
            style="width: 300px;">
            <el-option
              v-for="item in siteOptions"
              :key="item.siteId"
              :label="item.name"
              :value="item.siteId">
            </el-option>
          </el-select>
          <el-select
            v-model="props.form.officeId"
            @change="changeOffice"
            placeholder="请选择地点"
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
          <el-button class="fr" @click="openDialog()">添加收款账户</el-button>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column fixed="left" label="操作">
            <template slot-scope="props">
              <el-button type="text" size="mini" @click="openDialog(props.row)">编辑</el-button>
              <el-button type="text" size="mini" @click="deleteFeeTypeAudit(props.row)">删除</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="description" label="地点"></el-table-column>
          <el-table-column prop="accountVOs" label="费用细项">
              <template slot-scope="props">
                 {{props.row.accountVOs.length>=1 ? props.row.accountVOs[0].payServiceDesc:'' }}
              </template>
          </el-table-column>
          <el-table-column prop="accountVOs" label="服务商名称">
              <template slot-scope="props">
                  {{props.row.accountVOs.length>=1 ? props.row.accountVOs[0].partnerName:'' }}
              </template>
          </el-table-column>
        </template>
      </table-comb>
      <template slot="footer"></template>
    </d2-container>

    <el-dialog :title="editDialogCustomer.id ?'编辑收款账户':'添加收款账户'" :visible.sync="showSaveDialog">
      <el-form label-width="100px" :model="editDialogCustomer" :rules="rules" ref="saveForm">
        <el-form-item label="地点：" prop="officeId">
            <template  v-if="!editDialogCustomer.id">
              <el-select
                v-model="editDialogCustomer.officeId"
                @change="changeOfficeForSave"

                placeholder="请选择"
                filterable>
                <el-option
                  v-for="item in editDialogCustomer.officeOptions"
                  :key="item.id"
                  :label="item.description"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </template>
            <template v-else >
                {{editDialogCustomer.description}}
            </template>
        </el-form-item>
        <el-form-item label="费项：" prop="feeTypes">
          <template v-if="editDialogCustomer.feeTypesOptions.length==0">
            请先选择站点和地点
          </template>
            <template v-else >
              <el-checkbox-group  v-model="editDialogCustomer.feeTypes">
                <el-checkbox
                  v-for="item in editDialogCustomer.feeTypesOptions"
                  :key="item.id"
                  :label="item.id"
                  :disabled="item.checked=='1'"
                >{{item.description}}
                </el-checkbox>
              </el-checkbox-group>
            </template>
        </el-form-item>
        <el-form-item label="服务商：" prop="partnerId">
            <template  v-if="!editDialogCustomer.id">
              <el-select
                v-model="editDialogCustomer.partnerId"
                placeholder="请选择"
                filterable
              >
                <el-option
                  v-for="item in editDialogCustomer.serviceOptions"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </template>
            <template v-else >
                {{editDialogCustomer.accountVOs.length>0 ? editDialogCustomer.accountVOs[0].partnerName :''}}
            </template>
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
      let checkpartnerId = (rule, value, callback)=>{
        console.log(this.editDialogCustomer.id)
        if(this.editDialogCustomer.id){

          callback();
          return;
        }
        if (!value) {
          return callback(new Error('请选择'));
        }
        callback();
      };

      return {
        tableMainSearchModelBase:{
          siteId:'',
          officeId:''
        },
        siteOptions:[],
        officeOptions:[],
        tableData: [],
        showSaveDialog:false,
        editDialogCustomer:{
          officeId:'',
          feeTypes:[],
          id:'',
          officeOptions:[],
          feeTypesOptions:[],
          serviceOptions:[]
        },
        rules:{
          officeId:[
            { required: true, message: '请至少选择一个', trigger: 'change' }
          ],
          feeTypes: [
            { type: 'array', required: true, message: '请至少选择一个', trigger: 'change' }
          ],
          partnerId:[
            {  validator: checkpartnerId, trigger: 'change' }
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
        let siteId = this.$refs.tableMain.searchModelDataBase.siteId;
        console.log(item)
        //新增不传item 编辑要传入item
        this.showSaveDialog = true;
        //移除校验结果并重置字段值
        this.$refs.saveForm && this.$refs.saveForm.clearValidate();
        //新增不传item 编辑要传入item
        if(item){
          this.editDialogCustomer = {
            ...item,
            siteId,
            officeId:item.id,
            feeTypes:[],
            partnerId:item.accountVOs[0].partnerId,
            feeTypesOptions:[]
          }
          //获取费项列表
          this.changeOfficeForSave(item.id);
        }else{
          this.editDialogCustomer = {
            siteId,
            officeId:'',
            feeTypes:[],
            partnerId:'',
            officeOptions:this.officeOptions,
            feeTypesOptions:[],
            serviceOptions:[]
          }
        }
        this.loadService(siteId);
      },
      //保存或者新增费项
      save(){
        //验证
        this.$refs.saveForm.validate((valid) => {
          console.log(valid)
          if (valid) {
            let item = {
              parkId:this.editDialogCustomer.siteId,
              officeId:this.editDialogCustomer.officeId,
              partnerId:this.editDialogCustomer.partnerId,
              //partnerName:'',
              feeTypes:this.editDialogCustomer.feeTypes,
            }
            this.$api.tet.officeAccount.saveFeeTypeAudit(item).then(res => {
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
      deleteFeeTypeAudit(item){
        //this.$refs.tableMain.remove(item.id);
        let officeId =item.id, partnerId = item.accountVOs[0].partnerId;


        this.$msgbox({
          title: '提示',
          message: `确定要删除该数据？`,
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(action => {
          if (action === 'confirm') {
            this.$api.tet.officeAccount.deleteOfficeAccount(officeId,partnerId).then(res=>{
              if(res.code==0){
                this.$message.success(`删除数据成功`);
                this.$refs.tableMain.fetchData();
              }else{
                this.$message.error(res.message)
              }
            });

          }
        })



      },
      //切换站点
      changeSite(siteId){
        this.$api.merch.region.officeOptions(siteId).then(res => {
          this.officeOptions = res.data;
          //清空地点
          this.$refs.tableMain.searchModelDataBase.officeId='';
          //刷新列表
          this.$refs.tableMain.fetchData();
        });
      },
      //切换地点
      changeOffice(officeId){
        this.$api.merch.station.regionOptions(officeId).then(res => {
          //刷新列表
          this.$refs.tableMain.fetchData();
        });
      },
      //获取服务商列表
      loadService(siteId){
        this.$api.tet.officeAccount.loadService(siteId).then(res=>{
          this.editDialogCustomer.serviceOptions = res.data;
          console.log(this.editDialogCustomer.serviceOptions);
        })
      },
      changeOfficeForSave(officeId){
        this.$api.tet.officeAccount.getOfficeFeeTypes(officeId).then(res => {
          let editDialogCustomer = this.editDialogCustomer;
          editDialogCustomer.feeTypesOptions = res.data;
          //清空查询条件后面的级联 区域，单元
          editDialogCustomer.feeTypes=[];
          res.data.map(item=>{
            if(item.status==='0'){
              editDialogCustomer.feeTypes.push(item.id)
            }
          })
          console.log(editDialogCustomer);
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
</style>
