<template>
  <div>
    <d2-container>
      <template slot="header">模板类型配置</template>
      <br>
      <!---->
      <table-comb
        name="企业列表"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :get-action="$api.pim.type.queryPageData"
        :get-action-where="getActionWhere"
        :auto-fetch="false"
        :remove-action="$api.pim.type.deleteType">
        <!--高级查询-->
        <template slot="baseSearchForm" slot-scope="props">
          <el-select
            v-model="props.form.siteId"
            filterable
            placeholder="请选择站点"
            style="width: 300px; margin-right: 10px">
            <el-option
              v-for="item in siteOptions"
              :key="item.siteId"
              :label="item.name"
              :value="item.siteId"
            >
            </el-option>
          </el-select>
          <el-input
            placeholder="请输入关键字"
            prefix-icon="el-icon-search"
            clearable
            v-model="props.form.name"
            style="width: 200px;">
          </el-input>
          <el-button type="primary" icon="el-icon-plus" class="fr" @click="openDialog()">新增</el-button>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column
            fixed="left"
            label="操作">
            <template slot-scope="props">
              <el-button type="text" class="mr10" size="mini" @click="openDialog(props.row)">编辑</el-button>
              <el-button type="text" size="mini" @click="deleteItem(props.row)">删除</el-button>
            </template>
          </el-table-column>
          <el-table-column
            fixed="left"
            label="模板类型">
            <template slot-scope="props">
              <el-button type="text" size="mini" @click="openDetailDialog(props.row)"> {{props.row.name}}</el-button>
            </template>
          </el-table-column>
        </template>
      </table-comb>

      <template slot="footer"></template>
    </d2-container>
    <el-dialog :title="editDialogCustomer.id ?'编辑':'新增'" :visible.sync="showSaveDialog" width="600px">
      <el-form label-width="100px" :model="editDialogCustomer" :rules="rules" ref="saveForm">
        <el-form-item label="项目类型：" prop="name">
          <el-input placeholder="请输入项目类型名称" style="width: 200px;" v-model="editDialogCustomer.name" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveOrUpdate">保 存</el-button>
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
    name: "ProjectType",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      return {
        tableMainSearchModelBase:{
          name: '',
          siteId: '',
        },
        tableData: [],
        siteOptions: [],
        showSaveDialog:false,
        editDialogCustomer:{
        },
        defaultVo:{
          id:'',
          name:''
        },
        rules:{
          name: [
            { required: true, message: '项目类型名称必填', trigger: ['blur'] }
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
          this.editDialogCustomer ={
            ...this.defaultVo,
            unionId:this.unionId,
            siteId:this.$refs.tableMain.searchModelDataBase.siteId
          };
        }
        this.$refs.saveForm && this.$refs.saveForm.clearValidate();
      },
      deleteItem(item){
        this.$refs.tableMain.remove(item.id);
      },
      saveOrUpdate(){
        this.$refs.saveForm.validate((valid) => {
          if (valid) {
            let vo = {
              ...this.editDialogCustomer,
            };
            this.$api.pim.type.saveOrUpdate(vo).then(res => {
              if(res.code==0){
                let message = this.editDialogCustomer.id ? '修改成功！':'添加成功';
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
      }

    },
    mounted() {
      this.$api.merch.region.siteOptions(this.unionId).then(res => {
        let siteId = res.data[0].siteId
        this.$refs.tableMain.searchModelDataBase.siteId = siteId
        this.siteOptions = res.data;
        this.$refs.tableMain.fetchData();
      });
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .mb10{margin-bottom: 10px}
</style>
