<template>
    <div>
        <d2-container>
            <template slot="header">
                当前园区：
                <el-select v-model="siteId" filterable placeholder="请选择园区" style="width: 300px;" @change="changeSite">
                    <el-option  v-for="item in siteOptions" :key="item.siteId" :label="item.name" :value="item.siteId"></el-option>
                </el-select>
            </template>
            <statistical-data-panel :data-list="statisticalData"/>
            <div class="h2">
                <span class="mr10">自定义标签:</span>
                <el-input class="w200 mr10" placeholder="请输入自定义标签" v-model="addLabelName" clearable></el-input>
                <el-button type="primary" :loading="addBusy" :disabled="addLabelName==''" @click="addLabel">添加</el-button>
            </div>
            <div class="mt20">
                <el-tag v-for="label in labels"  :key="label.labelName" closable :type="label.id" @close="judgeCorpLabelIfUsed(label.id)" >{{label.labelName}}</el-tag>
            </div>
            <template slot="footer"></template>
        </d2-container>
    </div>
</template>


<script>
  import listMixin from "@/mixins/list.mixin";
  import StatisticalDataPanel from "@/components/StatisticalDataPanel";

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "CustomerLabel",
    components: {StatisticalDataPanel},
    mixins: [
      listMixin
    ],
    data() {
      return {
        statisticalData: null,
        siteId:'',
        labelType:1,
        siteOptions: [],
        labels:[],
        addLabelName:'',
        addBusy:false,
        dialogVisible:false,
        dialogMessage:''
      }
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
    watch:{
      siteId:function(){
        this.getCorpLabel();
      },
    },
    methods: {
      //切换站点
      changeSite(siteId){
        this.siteId = siteId;
      },
      addLabel(){
        const _this = this;
        this.addBusy = true;
        this.$api.corp.savePcsrCorpLabel(this.addLabelName, this.labelType, this.unionId, this.siteId).then(res => {
          this.addBusy = false;
          if(res.code==0){
            _this.addLabelName = '';
            _this.getCorpLabel();
          }else{
            _this.$message.error(res.message);
          }

        });
      },
      judgeCorpLabelIfUsed(id){
        const _this = this;
        this.$api.corp.judgeCorpLabelIfUsed(id).then(res => {
            if(res.data==0){
              _this.deleteCorpLabel(id);
            }else{
              _this.$confirm(`该标签已经被${res.data}家企业引用，是否删除？`).then(_ => {
                _this.deleteCorpLabel(id);
                }).catch(_ => {});
            }
        });
      },
      deleteCorpLabel(id){
        const _this = this;
        this.$api.corp.deleteCorpLabel(id).then(res => {
          if(res.code==0){
            _this.getCorpLabel();
          }else{
            _this.$message.error(res.message);
          }

        });

      },
      //获取自定义标签列表
      getCorpLabel(){
        const _this = this;
        this.$api.corp.getCorpLabel(this.siteId,this.labelType).then(res => {
          _this.labels = res.data;
        });
      }
    },
    created(){
      const _this = this;
      //获取园区列表
      this.$api.merch.region.siteOptions(this.unionId).then(res => {
        _this.siteOptions = res.data;
        _this.siteId = res.data[0].siteId;
        //站点id设置成功之后会watch 到 siteId 变化，触发视图更新
      });
    },
    mounted() {
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
    .customer-detail {
        .base-info {
            margin-bottom: 20px;
            .title {
                font-size: 20px;
                font-weight: bold;
                margin-bottom: 15px;
            }
            .tags {
                span {
                    margin-right: 10px;
                }
            }
        }
        .details-info {
            .group {
                margin-bottom: 10px;
                border-bottom: solid 1px #eee;
                .group-title {
                    line-height: 3;
                    font-size: 14px;
                    font-weight: bold;
                    .el-alert {
                        font-size: 12px;
                        line-height: 1.5;
                    }
                }
            }
        }
    }

    .el-tag {
        margin-right: 10px;
        cursor: pointer;
    }

    .el-dialog__body {
        padding: 0 20px;
    }
</style>
