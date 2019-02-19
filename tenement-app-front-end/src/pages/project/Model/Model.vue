<template>
  <transition name="edit">
      <d2-container v-show="isVisible" v-loading="loading" class="edit-panel">
          <template slot="header">
              <i title="后退" class="el-icon-back" @click="back"></i>
              项目配置详情
          </template>
          <el-steps :active="active" simple finish-status="success" style="width:80%; margin: 25px auto">
              <el-step title="项目名称"></el-step>
              <el-step title="项目表格配置"></el-step>
              <el-step title="项目人员分配"></el-step>
          </el-steps>
          <div class="form-wrap">
              <div v-show="active==0" style="padding:40px 0 30px 150px">
                  <el-form label-width="100px" :model="pageVo" :rules="rules" ref="saveForm1">
                      <el-form-item label="项目名称：" prop="name">
                          <el-input
                            placeholder="请输入项目类型名称"
                            style="width: 300px;"
                            v-model="pageVo.name" >
                          </el-input>
                      </el-form-item>
                      <el-form-item label="项目类型：" prop="type">
                          <el-select
                              v-model="pageVo.type"
                              placeholder="请选择"
                              style="width: 300px;"
                              filterable>
                              <el-option
                                  v-for="item in typeOptions"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value">
                              </el-option>
                          </el-select>
                      </el-form-item>
                  </el-form>
              </div>
              <div v-show="active==1" style="padding:20px">
                  <form-edit v-model="pageVo"></form-edit>
              </div>
              <div v-show="active==2" style="padding:0 20px 20px 20px">
                  <p class="tab-cont">请将负责该项目的人员在左侧表格中勾选，并点击“加入" 按钮添加至右侧表格</p>
                  <div style="padding:20px 0 0 0" class="transfer-class">
                      <el-transfer
                          filterable
                          :titles="['所有人员', '项目人员']"
                          filter-placeholder="请输入"
                          :button-texts="['移除','加入', ]"
                          v-model="userListChecked"
                          :data="userList">
                      </el-transfer>
                  </div>
              </div>
          </div>
          <template slot="footer" >
              <div style="text-align: center">
                  <el-button v-show="active !=0 " @click="prevPage">上一步</el-button>
                  <el-button v-show="active !=2 " type="primary" @click="nextPage">下一步</el-button>
                  <el-button v-show="active ==2 " type="primary" @click="submit"><i class="iconfont icon-save"></i> 保存</el-button>
                  <el-button @click="back">取消</el-button>
              </div>
          </template>
      </d2-container>
  </transition>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";
  import formEdit from "./componnets/FormEdit";

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "Edit",
    components: {formEdit},
    mixins: [
      listMixin
    ],
    data() {
      const userListData = _ => {
        const data = [];
        for (let i = 1; i <= 15; i++) {
          data.push({
            key: i,
            label: `管理员 ${ i }`,
            disabled: i % 4 === 0
          });
        }
        return data;
      };
      return {
        isVisible:true,
        loading: false,
        active:1,
        typeOptions:[],
        pageVo:{
          name:'',
          id:'',
          labels:[
            {
              name:'标签1标签1标签1标签1标签1标签1标签1标签1标签1',
              parts:[
                {
                  name:'模块名1',
                  fields:[
                    {name:'公司名称',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业法人',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业类型',type:'2',required:true,placeholder:'请选择',size:1,options:[{label:'国企',id:'1',active:true},{label:'私企',id:'1',active:false}]},
                    {name:'企业标签',type:'3',required:true,placeholder:'请选择',size:1,options:[{label:'国家扶持',id:'1',active:true},{label:'明星企业',id:'1',active:true}]},
                    {name:'企业简介',type:'4',required:false,placeholder:'请输入',size:2, maxLength:200}
                  ]
                },
                {
                  name:'模块名2',
                  fields:[
                    {name:'公司名称',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业法人',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业类型',type:'2',required:true,placeholder:'请选择',size:1,options:[{label:'国企',id:'1',active:true},{label:'私企',id:'1',active:false}]},
                    {name:'企业标签',type:'3',required:true,placeholder:'请选择',size:1,options:[{label:'国家扶持',id:'1',active:true},{label:'明星企业',id:'1',active:true}]},
                    {name:'企业简介',type:'4',required:false,placeholder:'请输入',size:2, maxLength:200}
                  ]
                }
              ]
            },
            {
              name:'标签2',
              parts:[
                {
                  name:'标签2模块名1',
                  fields:[
                    {name:'公司名称',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业法人',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业类型',type:'2',required:true,placeholder:'请选择',size:1,options:[{label:'国企',id:'1',active:true},{label:'私企',id:'1',active:false}]},
                    {name:'企业标签',type:'3',required:true,placeholder:'请选择',size:1,options:[{label:'国家扶持',id:'1',active:true},{label:'明星企业',id:'1',active:true}]},
                    {name:'企业简介',type:'4',required:false,placeholder:'请输入',size:2, maxLength:200}
                  ]
                },
                {
                  name:'标签2模块名2',
                  fields:[
                    {name:'公司名称',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业法人',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业类型',type:'2',required:true,placeholder:'请选择',size:1,options:[{label:'国企',id:'1',active:true},{label:'私企',id:'1',active:false}]},
                    {name:'企业标签',type:'3',required:true,placeholder:'请选择',size:1,options:[{label:'国家扶持',id:'1',active:true},{label:'明星企业',id:'1',active:true}]},
                    {name:'企业简介',type:'4',required:false,placeholder:'请输入',size:2, maxLength:200}
                  ]
                }
              ]
            },
            {
              name:'标签3',
              parts:[
                {
                  name:'标签2模块名1',
                  fields:[
                    {name:'公司名称',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业法人',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业类型',type:'2',required:true,placeholder:'请选择',size:1,options:[{label:'国企',id:'1',active:true},{label:'私企',id:'1',active:false}]},
                    {name:'企业标签',type:'3',required:true,placeholder:'请选择',size:1,options:[{label:'国家扶持',id:'1',active:true},{label:'明星企业',id:'1',active:true}]},
                    {name:'企业简介',type:'4',required:false,placeholder:'请输入',size:2, maxLength:200}
                  ]
                },
                {
                  name:'标签2模块名2',
                  fields:[
                    {name:'公司名称',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业法人',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业类型',type:'2',required:true,placeholder:'请选择',size:1,options:[{label:'国企',id:'1',active:true},{label:'私企',id:'1',active:false}]},
                    {name:'企业标签',type:'3',required:true,placeholder:'请选择',size:1,options:[{label:'国家扶持',id:'1',active:true},{label:'明星企业',id:'1',active:true}]},
                    {name:'企业简介',type:'4',required:false,placeholder:'请输入',size:2, maxLength:200}
                  ]
                }
              ]
            },
            {
              name:'标签4',
              parts:[
                {
                  name:'标签2模块名1',
                  fields:[
                    {name:'公司名称',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业法人',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业类型',type:'2',required:true,placeholder:'请选择',size:1,options:[{label:'国企',id:'1',active:true},{label:'私企',id:'1',active:false}]},
                    {name:'企业标签',type:'3',required:true,placeholder:'请选择',size:1,options:[{label:'国家扶持',id:'1',active:true},{label:'明星企业',id:'1',active:true}]},
                    {name:'企业简介',type:'4',required:false,placeholder:'请输入',size:2, maxLength:200}
                  ]
                },
                {
                  name:'标签2模块名2',
                  fields:[
                    {name:'公司名称',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业法人',type:'1',required:true,placeholder:'请输入',size:1,},
                    {name:'企业类型',type:'2',required:true,placeholder:'请选择',size:1,options:[{label:'国企',id:'1',active:true},{label:'私企',id:'1',active:false}]},
                    {name:'企业标签',type:'3',required:true,placeholder:'请选择',size:1,options:[{label:'国家扶持',id:'1',active:true},{label:'明星企业',id:'1',active:true}]},
                    {name:'企业简介',type:'4',required:false,placeholder:'请输入',size:2, maxLength:200}
                  ]
                }
              ]
            }
          ],
        },
        userList:userListData(),
        userListChecked:[],
      };
    },
    computed: {
      unionId() {
        return this.$route.params.unionId
      },
    },
    methods: {
      open(vo) {
        if(vo){
          this.getPageVo(vo.id);
        }else{
          //this.pageVo = {};
          this.active=0;
        }
        this.$nextTick(() => {
          this.isVisible = true;
          this.loading = false;
        })
      },
      //编辑时，获取项目数据
      getPageVo(id){
        this.pageVo={};
      },
      //上一页
      prevPage(){
        this.active--;
      },
      //下一页
      nextPage(){
        //判断验证当前步骤数据有效性
        this.active++;
      },
      submit(){
        console.log(this.pageVo)
      },
      back(){
        this.isVisible = false;
        this.$emit("ok");
      }
    },
    mounted() {

    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
    .tab-cont{
        font-size:14px;
        line-height: 40px;
        padding:0;
        margin: 0;
        color: #999;
    }
</style>
<style>
    .transfer-class .el-transfer-panel{
        width: 320px;
        height: 450px;
    }
    .transfer-class .el-transfer-panel__list.is-filterable{
        height: 346px;
    }
</style>