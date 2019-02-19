<template>
  <div>
    <d2-container>
      <template slot="header">物业费项管理</template>
      <br>
      <!---->
      <table-comb
        name="物业费项类型列表"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :get-action="$api.tet.feeType.getFeeTypeList"
        :get-action-where="getActionWhere"
        :remove-action="$api.tet.feeType.deleteFeeType"
      >
        <!--高级查询-->
        <template slot="baseSearchForm" slot-scope="props">
          <el-input placeholder="搜索费项名称" prefix-icon="el-icon-search" clearable v-model="props.form.description" style="width: 160px; margin-left: 10px;"></el-input>
          <el-button class="fr" @click="openFeeTypeDialog()">添加自定义费项</el-button>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column prop="corpName" label="操作" width="100px">
            <template slot-scope="props" >
              <template v-if="props.row.source!='0'">
                <el-button type="text" size="mini" @click="openFeeTypeDialog(props.row)">编辑</el-button>
                <el-button type="text" size="mini" @click="deleteFeeType(props.row)">删除</el-button>
              </template>
              <template v-else ></template>
            </template>
          </el-table-column>
          <el-table-column prop="description" label="费项名称"></el-table-column>
          <el-table-column prop="sourceName" label="费项来源"></el-table-column>
        </template>
      </table-comb>
      <template slot="footer"></template>
    </d2-container>

    <el-dialog :title="editDialogCustomer.id ?'编辑费项':'添加自定义费项'" :visible.sync="showSaveDialog">
      <el-form label-width="100px" :model="editDialogCustomer" :rules="rules" ref="saveForm">
        <el-form-item label="费用名称"  prop="description">
          <el-input v-model="editDialogCustomer.description" placeholder="请输入费用名称"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveFeeType">保 存</el-button>
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
    name: "FeeType",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      return {
        tableMainSearchModelBase:{
          corpName: ''
        },
        tableData: [],
        showSaveDialog:false,
        editDialogCustomer:{
          description:'',
          source:1,
          type:2,
          id:''
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
          unionId : this.unionId
        }
      },
      unionId() {
        return this.$route.params.unionId
      },
    },
    methods: {
      //打开新增或编辑费项弹窗
      openFeeTypeDialog(item){
        this.showSaveDialog = true;
        //移除校验结果并重置字段值
        this.$refs.saveForm && this.$refs.saveForm.clearValidate();
        //新增不传item 编辑要传入item
        this.editDialogCustomer = item||{description:'',source:1,type:2,};
      },
      //保存或者新增费项
      saveFeeType(){
        //验证
        console.log(this.editDialogCustomer);
        this.$refs.saveForm.validate((valid) => {
          console.log(valid)
          if (valid) {
            this.$api.tet.feeType.saveFeeType(this.editDialogCustomer).then(res => {
              if(res.code==0){
                let message = this.editDialogCustomer.id?'修改成功！':'添加成功！'
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
      deleteFeeType(item){
        this.$refs.tableMain.remove(item.id);
      }
    },
    mounted() {
      const _this = this;
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .el-tag{ margin-right: 10px; cursor: pointer;}
  .el-dialog__body{ padding: 0 20px;}
</style>
