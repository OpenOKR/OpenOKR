<template>
  <div :style="{width:width}" class="form-edit-wrap">
    <div class="label-list">
      <h4 class="label-list-title">
        <i class="el-icon-tickets el-icon-home"></i>
        <span>一级页签</span>
        <el-button type="text" @click="addLabel" class="fr mr10">新增页签</el-button>
      </h4>
      <div class="labels">
        <draggable v-model=" pageVo.labels" @update="labelDragEnd">
          <transition-group >
            <div
              class="label-item"
              v-for="(item,index) in pageVo.labels"
              @click="changeLabel(index)"
              :key="'label'+index"
              :class="{active:index==currLabelIndex}">
              <span class="label-name" :title="item.name">{{item.name}}</span>
              <i @click="deleteLabel" class="el-icon-close label-delete"></i>
            </div>
          </transition-group>
        </draggable>
      </div>
    </div>
    <div class="label-detail">
      <h4 class="label-detail-title">表格内容</h4>
      <el-form label-width="100px" :model="currLabel"  ref="saveForm">
        <el-form-item label="页签：" class="label-name">
          <el-input placeholder="请输入页签名称" @blur="changeLabelName" :value="currLabel.name" ></el-input>
        </el-form-item>
        <div class="part-wrap">
          <div class="part-item" v-for="(partItem,index) in currLabel.parts">
            <el-row>
              <el-col :span="24" class="" style="padding:30px 20px 0 0">
                <el-form-item label="模块标题：">
                  <el-input placeholder="请输入模板标题" v-model="partItem.name"></el-input>
                </el-form-item>
              </el-col>
              <el-col class="mb20 field-item" v-for="(fieldItem,fieldIndex) in partItem.fields" :span="12*fieldItem.size">
                <el-input placeholder="请输入字段名称" v-model="fieldItem.name"></el-input>
                <el-row class="field-item-btns">
                  <el-button v-if="fieldIndex!=0" title="上移" icon="el-icon-caret-top" circle size="mini"></el-button>
                  <el-button title="下移" icon="el-icon-caret-bottom" circle  size="mini"></el-button>
                  <el-button title="编辑" icon="el-icon-edit-outline" circle  size="mini"></el-button>
                  <el-button title="删除" icon="el-icon-circle-close-outline" circle  size="mini"></el-button>
                </el-row>
              </el-col>
              <div class="field-item field-add-wrap">
                <el-col :span="12">
                  <el-button class="btn-part-add">+ 添加字段</el-button>
                </el-col>
              </div>
            </el-row>
            <i class="el-icon-close part-delete" title="删除模块"></i>
          </div>
          <div class="part-item part-add-wrap" style="margin-bottom: 0; padding-bottom: 40px">
            <el-button class="btn-part-add">+ 添加模块</el-button>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
  import formMixin from "@/mixins/form.mixin";
  import draggable from 'vuedraggable'
  export default {
    name: "FormEdit",
    components: {},
    mixins: [
      formMixin
    ],
    props: {
      value: Object,
      width:{
        type: String,
        default: '100%'
      },
    },
    components: {
      draggable
    },
    data() {
      return {
        currLabelIndex:0,
        pageVo:{},
        currLabel:{}
      };
    },
    computed: {
    },
    methods: {
      //新增页签
      addLabel(){
        this.pageVo.labels.push({
          name:'新页签',
          fields:[]
        },)
      },
      //切换页签
      changeLabel(index){
        this.currLabelIndex = index;
      },
      //拖拽标签
      labelDragEnd(evt){
        let currindex = this.currLabelIndex;
        let oldIndex = evt.oldIndex;
        let newIndex = evt.newIndex;

        if(currindex==oldIndex){
          this.currLabelIndex = newIndex;
          return
        }
        if(currindex==newIndex){
          this.currLabelIndex = oldIndex;
          return
        }
        if( currindex > oldIndex && currindex < newIndex ){
          this.currLabelIndex--
        }
        if(currindex < oldIndex &&  currindex > newIndex ){
          this.currLabelIndex++
        }
      },
      //删除标签
      deleteLabel(){

      },
      //修改标签名称不做成双向绑定，
      changeLabelName(event){
        if(!event.target.value){
          this.$message.error('页签不能为空');
          event.target.value=this.currLabel.name;
          return;
        }
        this.currLabel.name = event.target.value;
      },
    },
    watch: {
      currLabelIndex(val) {
        this.currLabel = this.pageVo.labels[val];
      }
    },
    mounted() {
      this.pageVo = this.value;
      this.currLabel = this.pageVo.labels[this.currLabelIndex];
    }
  };

</script>

<style type="text/scss" lang="scss" scoped>
  .form-edit-wrap{
    position: relative;
    border: #ccc solid 1px;
    padding: 0;
    font-size: 14px;
  }
  .label-list{
    position: absolute;
    width: 250px;
    height: 100%;
    left: 0;
    top: 0;
    .label-list-title{
      line-height: 40px;
      padding: 0;
      margin: 0;
      border-bottom: #ccc solid 1px;
      .el-icon-home{
        color:#409eff;
        margin: 0 10px;
      }
    };
    .label-item{
      width: 100%;
      line-height: 60px;
      height: 60px;
      cursor: move;
      .label-name{
        display: block;
        float: left;
        padding-left: 20px;
        width: 180px;
        overflow: hidden;
        height: 60px;
      }
      .label-delete{
        margin:20px 15px 0 0;
        float: right;
        transition: all .4s;
        line-height: 20px;
        text-align: center;
        width: 20px;
        height: 20px;
        border-radius: 50%;
        cursor: pointer;
      };
    }
    .label-item.active{
      color: #409eff;
    }
    .label-item:hover{
      background: #F2F6FC;
      .label-delete{
        color: #0c77f8;
        background: #d1dbe5;
      }
    }
  }
  .label-detail{
    min-height: 500px;
    margin-left: 250px;
    border-left: #ccc solid 1px;
    .label-detail-title{
      line-height: 40px;
      padding: 0 0 0 20px;
      margin: 0;
      border-bottom: #ccc solid 1px;
    }
    .label-name{
      padding: 22px 20px 0;
    }
  }
  .part-wrap{
    padding: 0 5px;
    .part-item{
      border-top: #ccc solid 1px;
      margin-bottom: 20px;
      padding:20px;
      position: relative;
      .part-delete{
        position: absolute;
        right: 10px;
        top:10px;
        transition: all .4s;
        line-height: 20px;
        text-align: center;
        width: 20px;
        height: 20px;
        border-radius: 50%;
        cursor: pointer;
        font-size: 12px;
        background: #d1dbe5;
      }
      .part-delete:hover{
        background: #0c77f8;
        color: #fff;
      }
    }
    .btn-part-add{
      width: 100%;
      border: #d1dbe5 dashed 1px;
    }
  }
  .mb20{ margin-bottom: 20px;}
  .field-item{
    padding-right: 20px;
    position: relative;
    .field-item-btns{
      position: absolute;
      right: 28px;
      top:11px;

      .el-button{
        width: 15px;
        height: 15px;
        padding: 0;
        line-height: 15px;
        margin-left:3px;
        border:none;
      }
    }
  }
</style>
