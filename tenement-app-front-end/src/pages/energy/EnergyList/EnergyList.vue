<template>
  <div>
    <d2-container>
      <template slot="header">能耗系统管理</template>
      <br>
      <!---->
      <table-comb
        name="企业列表"
        ref="tableMain"
        :autoFetch="false"
        :search-model-base="tableMainSearchModelBase"
        :get-action="$api.energy.ecaSystemSiteRel.list"
        :get-action-where="getActionWhere"
        :remove-action="$api.energy.ecaSystemSiteRel.delete"
      >
        <!--高级查询-->
        <template slot="baseSearchForm" slot-scope="props">
          <el-select
            v-model="props.form.siteId"
            filterable
            placeholder="请选择站点"
            @change="changeSite"
            style="width: 300px;">
            <el-option
              v-for="item in siteOptions"
              :key="item.siteId"
              :label="item.name"
              :value="item.siteId">
            </el-option>
          </el-select>
          <el-input
            placeholder="搜索能耗系统名称"
            prefix-icon="el-icon-search"
            clearable
            v-model="props.form.searchKey"
            style="width: 160px; margin-left: 10px;">
          </el-input>
          <el-button type="primary" icon="el-icon-plus" class="fr" @click="openDialog()">新增</el-button>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column fixed="left" label="操作">
            <template slot-scope="props">
              <template  v-if="props.row.isSyncArchives === '1'">
                <el-button type="text" size="mini" @click="syncDeviceList(props.row)">同步设备</el-button>
                <el-button  type="text" size="mini" @click="syncTenementInfo(props.row)">基础档案下发</el-button>
              </template>
              <template v-else>
                <el-button type="text" size="mini" @click="openDialog(props.row)">编辑</el-button>
                <el-button type="text" size="mini" @click="deleteItem(props.row)">删除</el-button>
              </template>
            </template>
          </el-table-column>
          <el-table-column
            prop="mfrsName"
            label="能耗厂商">
          </el-table-column>
          <el-table-column
            prop="parkSystemName"
            label="系统名称">
          </el-table-column>
          <el-table-column
            prop="total"
            label="表数量">
          </el-table-column>
        </template>
      </table-comb>

      <template slot="footer"></template>
    </d2-container>

    <el-dialog :title="editDialogCustomer.id ?'编辑':'新增'" :visible.sync="showSaveDialog" width="600px">
      <el-form label-width="140px" :model="editDialogCustomer" :rules="rules" ref="saveForm">
        <el-form-item v-if="editDialogCustomer.mfrsName" label="能耗厂商：" prop="mfrsName">
          {{editDialogCustomer.mfrsName}}
        </el-form-item>
        <el-form-item v-else label="能耗厂商：" prop="systemId">
          <el-select v-model="editDialogCustomer.systemId" placeholder="请选择">
            <el-option
              v-for="item in mfrsOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="系统名称：" prop="parkSystemName">
          <el-input placeholder="请输入" v-model="editDialogCustomer.parkSystemName" ></el-input>
        </el-form-item>
        <el-form-item label="访问URL：" prop="url">
          <el-input placeholder="请输入" v-model="editDialogCustomer.url" ></el-input>
        </el-form-item>
        <el-form-item label="网关URL：" prop="gatewayUrl">
          <el-input placeholder="请输入" v-model="editDialogCustomer.gatewayUrl" ></el-input>
        </el-form-item>
        <el-form-item label="外部系统ID：" prop="outSysId">
          <el-input placeholder="请输入" v-model="editDialogCustomer.outSysId" ></el-input>
        </el-form-item>
        <el-form-item label="验签私钥：" prop="privateKey">
          <el-input placeholder="请输入" v-model="editDialogCustomer.privateKey" ></el-input>
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
    name: "EnergyList",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      return {
        tableMainSearchModelBase:{
          searchKey: '',
          siteId: '',
        },
        tableData: [],
        siteOptions: [],
        mfrsOptions:[],
        showSaveDialog:false,
        editDialogCustomer:{
        },
        defaultVo:{
          id:'',
          systemId:'',
          parkSystemName:'',
          url:'',
          gatewayUrl:'',
          outSysId:'',
          privateKey:'',
        },
        rules:{
          systemId: [
            { required: true, message: '请选择能耗产商', trigger: ['blur', 'change'] }
          ],
          parkSystemName: [
            { required: true, message: '请输入系统名称', trigger: 'blur' }
          ],
          url: [
            { required: true, message: '请输入访问URL', trigger: 'blur' }
          ],
          gatewayUrl: [
            { required: true, message: '请输入网关URL', trigger: 'blur' }
          ],
          outSysId: [
            { required: true, message: '请输入外部系统ID', trigger: 'blur' }
          ],
          privateKey: [
            { required: true, message: '请输入签名验证私钥', trigger: 'blur' }
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
      // 打开编辑或者新增弹窗
      openDialog(item) {
        this.showSaveDialog = true;
        //移除校验结果并重置字段值
        if(item){
          this.editDialogCustomer= {...item};
        }else{
          this.editDialogCustomer =this.defaultVo;
        }
        this.$refs.saveForm && this.$refs.saveForm.clearValidate();

      },
      //删除记录
      deleteItem(item){
        this.$refs.tableMain.remove(item.id);
      },
      //切换站点
      changeSite(siteId){
          //刷新列表
          this.$refs.tableMain.fetchData();
      },
      //新增或者编辑记录
      save(){
        this.$refs.saveForm.validate((valid) => {
          if (valid) {
            this.editDialogCustomer.siteId = this.$refs.tableMain.searchModelDataBase.siteId;
            this.editDialogCustomer.unionId = this.unionId;
            this.$api.energy.ecaSystemSiteRel.save(this.editDialogCustomer).then(res => {
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
      //同步设备
      syncDeviceList(item){
        this.$api.energy.ecaSystemSiteRel.syncDeviceList(item.id).then(res => {
          if(res.code==0){
            let message = '同步成功';
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
      //基础档案库下发
      syncTenementInfo(item){
            this.$api.energy.ecaSystemSiteRel.sendLocationInfo(item.siteId,item.outSysId,item.parkSystemName).then(res => {
                if(res.code==0){
                let message = '下发成功';
                this.$message({
                    message,
                    type: 'success'
                });
            }else{
                this.$message.error(res.message);
            }
        });
      }

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
        this.$api.energy.ecaSystemSiteRel.getEcaSystemList().then(res => {
          this.mfrsOptions = res.data;
        });

      },0)
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .el-tag{ margin-right: 10px; cursor: pointer;}
  .el-dialog__body{ padding: 0 20px;}
</style>
