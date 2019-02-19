<template>
  <div>
    <transition name="edit">
      <d2-container v-if="visible || isPage">
        <template slot="header" v-if="!isPage" style="position: relative">
          <i title="后退" class="el-icon-back" @click="close"></i>
          <span style="cursor: pointer" @click="close">返回</span>
          <div style="position: absolute; width: 400px; text-align: center; left: 50%; margin-left: -200px;top:20px;">园区类型设置</div>
        </template>
        <template v-else slot="header" >园区类型设置</template>
        <br>
        <!---->
        <table-comb
          name="园区类型"
          ref="tableMain"
          :get-action="$api.base.parkType.list"
          :remove-action="$api.base.parkType.delete"
          :get-action-where="this.tableBaseSearchModel"
          :auto-fetch="true" >
          <!--控制按钮-->
          <template slot="controls">
            <el-button type="primary" @click="openEditDialog">
              <i class="iconfont icon-add"></i> 新增
            </el-button>
          </template>
          <!--表格-->
          <template slot="tableColumns">
            <el-table-column prop="name" label="园区类型"></el-table-column>
            <el-table-column width="200" label="操作">
              <template slot-scope="props">
                <el-button-group>
                  <el-button type="danger" size="mini" @click="$refs.tableMain.remove(props.row.id)"> 删除</el-button>
                  <el-button size="mini" @click="openEditDialog(props.row)"> 编辑</el-button>
                </el-button-group>
              </template>
            </el-table-column>
          </template>
        </table-comb>
        <template slot="footer" v-if="isPage"></template>
        <el-dialog :title="dialogTitle" :visible.sync="openDetailDialog" width="600px">
          <br>
          <el-form label-width="100px" :model="currParkType" >
            <el-form-item label="园区类型">
              <el-input v-model="currParkType.name" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button type="primary" :loading="addBusy" :disabled="currParkType.name==''" @click="saveParkType">{{dialogType=='edit'?'修 改':'添 加'}}</el-button>
              <el-button @click="openDetailDialog = false">取 消</el-button>

          </div>
        </el-dialog>
      </d2-container>
    </transition>
  </div>
</template>

<script>
  import { find } from "lodash";
  import listMixin from "@/mixins/list.mixin";
  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "ParkType",
    mixins: [
      listMixin
    ],
    props:{
      visible:{
        default:false
      },
      isPage:{
        default:true
      }
    },
    data() {
      return {
        importDataDialogVisible: false,
        tableData: [],
        openDetailDialog:false,
        dialogType:'edit',
        currParkType:{

        },
        addBusy:false
      };
    },
    computed: {
      unionId() {
        return this.$route.params.unionId
      },
      tableBaseSearchModel(){
        return {
          unionId:this.unionId,
        }
      },
      dialogTitle(){
        return this.dialogType=='edit'?'编辑园区类型':'新增园区类型'
      }
    },
    methods: {
      openEditDialog(detail){
        //打开新增弹出
        if(detail && detail.id){
          this.dialogType = 'edit';
          this.currParkType = {
            name:detail.name,
            id:detail.id
          }
        }else{
          this.dialogType = 'create';
          this.currParkType = {
            name:''
          }
        }
        this.openDetailDialog = true;
      },
      saveParkType(){
        const _this = this;
        this.addBusy = true;
        let detail = {...this.currParkType,unionId:this.unionId}
        this.$api.base.parkType.saveParkType(detail).then(res => {
          _this.addBusy = false;
          if(res.code==0){
            _this.openDetailDialog = false;
            _this.$refs.tableMain.search();
            _this.$message({
              message: this.dialogType=='edit'?'修改成功!':'添加成功!',
              type: 'success'
            });
          }else{
            _this.$message.error(res.message);
          }
        });
      },
      close(){
        this.$emit('update:visible', false);
      }
    },
    mounted() {
      const _this = this;
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .station-search {
    padding-top: 20px;
    margin-bottom: 20px;
    border-radius: 5px;
    border: 1px solid #b3d8ff;
    background-color: #ecf5ff;
  }
  .upload-file {
    display: inline-block;
  }
</style>
