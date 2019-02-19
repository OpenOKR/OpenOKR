<template>
  <div>
    <d2-container>
      <template slot="header">通用配置</template>
      <div style="margin:0 0 20px 0">
        请选择站点：
        <el-select
          v-model="siteId"
          filterable
          placeholder="请选择站点"
          @change="changeSite"
          style="width: 300px; margin-right: 10px">
          <el-option
            v-for="item in siteOptions"
            :key="item.siteId"
            :label="item.name"
            :value="item.siteId">
          </el-option>
        </el-select>
      </div>
      <!---->
      <template>
        <el-tabs v-model="activeName">

          <el-tab-pane label="填报时间限制" name="first">
            <div class="form-wrap">
              <el-form ref="timeForm" :model="timeForm" :rules="rules"  label-width="120px">
                <div class="form-cent">
                  <h2 class="tab-sub-title" style="margin-bottom: 20px">填报时间方案</h2>
                  <el-form-item label="填写时间：">
                      <el-radio v-model="timeForm.type" label="0">无时间限制填写</el-radio>
                      <el-radio v-model="timeForm.type" label="1">每个月1日至规定日期内填写</el-radio>
                  </el-form-item>
                </div>
                <template v-if="timeForm.type=='1'">
                  <div class="form-cent">
                    <h2 class="tab-sub-title">填报规定时间设置</h2>
                    <p class="tab-cont">请填写选择年份并填写月份的填报截止日期。举例：选择了2019年1月1日，系统将在1月1日至1月3日允许查看和编辑。1月4日至1月31日只允许查看，不允许编辑。</p>
                    <el-form-item label="年份：">
                      <el-select v-model="timeForm.yearIndex" style="width: 250px">
                        <el-option
                          v-for="(item, index) in timeForm.fillInTimes"
                          :key="item.fillYear"
                          :label="item.fillYear"
                          :value="index">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    <el-row style="width: 80%">
                      <el-col v-if="currentYear" v-for="(monthItem,index) in currentYear.monthDataList" :span="12">
                        <el-form-item :label="monthStr[index]">
                          <el-date-picker
                            type="daterange"
                            style="width: 250px"
                            v-model="monthItem.startEndDate"
                            :default-value="new Date(currentYear.fillYear+'/'+ monthItem.fillMonth )"
                            start-placeholder="开始日期"
                            value-format="yyyy/MM/dd"
                            end-placeholder="结束日期">
                          </el-date-picker>
                          <span v-if="monthItem.startEndDate.length==0" style="color:#E6A23C; margin-left: 10px">未填写</span>
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                  <div class="form-cent">
                    <h2 class="tab-sub-title">项目管理时间限制未填写通知</h2>
                    <p class="tab-cont">当未填写下个月填写限制日期时，系统将提前7天，3天，1天通知以下联系方式。</p>
                    <el-form-item label="通知手机号：" prop="noticePhone">
                      <el-input placeholder="请输入" style="width: 200px;" v-model="timeForm.noticePhone" ></el-input>
                    </el-form-item>
                    <el-form-item label="通知邮箱：" prop="noticeEmail">
                      <el-input placeholder="请输入" style="width: 200px;" v-model="timeForm.noticeEmail" ></el-input>
                    </el-form-item>
                  </div>
                </template>
              <el-form-item>
                <el-button type="primary" @click="saveOrUpdateFillInType">保存</el-button>
              </el-form-item>
            </el-form>
            </div>
          </el-tab-pane>
          <el-tab-pane label="全局权限人员" name="second">
            <div class="form-wrap">
              <div class="form-cent">
                <p class="tab-cont" style="margin: 0; padding-top: 20px">全局权限人员可查看所有项目，比如领导角色，设置后，无需再在每个项目中设置。</p>
                <div style="padding:20px 40px" class="transfer-class">
                  <el-transfer
                    filterable
                    :titles="['所有人员', '全局权限人员']"
                    filter-placeholder="请输入"
                    :button-texts="['移除','加入', ]"
                    v-model="userListChecked"
                    :data="userList">
                  </el-transfer>
                </div>
              </div>
              <el-button type="primary">保存</el-button>
            </div>

          </el-tab-pane>

        </el-tabs>
      </template>

      <template slot="footer"></template>
    </d2-container>

  </div>
</template>

<script>
  import { find,cloneDeep } from "lodash";
  import listMixin from "@/mixins/list.mixin";
  import { phone,email } from '@/libs/rules';
  const MONTH_STR_AYYAY =[
    '一月',
    '二月',
    '三月',
    '四月',
    '五月',
    '六月',
    '七月',
    '八月',
    '九月',
    '十月',
    '十一月',
    '十二月',
  ];

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "ProjectConfig",
    components: {},
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
        siteId:'',
        siteOptions: [],
        activeName: 'first',
        monthStr:MONTH_STR_AYYAY,
        timeForm:{
          type:"1",
          yearIndex:1,
          fillInTimes:[],
          noticePhone :'',
          noticeEmail :''
        },
        rules:{
          noticePhone:phone,
          noticeEmail: email,
        },
        userList:userListData(),
        userListChecked:[],
      };
    },
    computed: {
      unionId() {
        return this.$route.params.unionId
      },
      currentYear(){
        if(this.timeForm.fillInTimes.length>0){
          return this.timeForm.fillInTimes[this.timeForm.yearIndex]
        }else{
          return {
            fillYear:'',
            monthDataList:[]
          };
        }
      }
    },
    watch:{
      activeName(){
        this.changeSite()
      },
    },
    methods: {
      //切换站点
      changeSite(siteId){
        if(this.activeName==='first'){
          //第一个页签 查询填报时间设置
          let vo = {
            siteId,
            unionId:this.unionId,
          };
          //获取站点数据
          this.$api.pim.config.queryFillInType(vo).then(res => {
            //无数据
            if(!res.data){
              let year = new Date().getFullYear();
              this.timeForm.year = year;
              let fillInTimes=[year-1,year,year+1];

              let getMonthData = (year)=>{
                let data = [];
                for(let i=0; i<12;i++){
                  data.push({
                    fillMonth:i+1,
                    startEndDate:[]
                  })
                }
                return data
              };
              fillInTimes = fillInTimes.map(item =>{
                return {
                  fillYear:item,
                  monthDataList:getMonthData(item)
                }
              });
              this.timeForm = {
                type:"1",
                yearIndex:1,
                fillInTimes,
                noticePhone :'',
                noticeEmail :''
              };
              return;
            };
            //已经有数据时
            if(res.data.fillInTimes){
              res.data.fillInTimes.map(item=>{
                item.monthDataList = item.monthDataList.map(item2=>{
                  let newItem2 = {
                    fillMonth:item2.fillMonth,
                    startEndDate:[]
                  };
                  if(item2.startDate && item2.endDate){
                    newItem2.startEndDate = [item2.startDate,item2.endDate];
                  }
                  return newItem2;

                })
              });
            }
            this.timeForm ={
              ...this.timeForm,
              ...res.data
            };
          });
        }else{
          //第一个页签 全局权限人员设置
        }
        setTimeout(()=>{
          this.isEditing = false
        },300)
      },
      //保存时间段
      saveOrUpdateFillInType(){
        this.$refs.timeForm.validate((valid) => {
          if (!valid) {
            return false;
          }
          let timeForm = cloneDeep(this.timeForm);

          let vo = {
            siteId:this.siteId,
            unionId:this.unionId,
            type:timeForm.type,
          };
          if(timeForm.type==1){
            timeForm.fillInTimes.map(item=>{
              //过滤掉空的数据
              item.monthDataList = item.monthDataList.map(item2=>{
                let startDate ='',endDate='';
                if(item2.startEndDate.length==2){
                  startDate = item2.startEndDate[0];
                  endDate = item2.startEndDate[1];
                }
                return {
                  fillMonth:item2.fillMonth,
                  startDate,
                  endDate
                }
              })
            });

            vo = {
              ...vo,
              noticePhone:timeForm.noticePhone,
              noticeEmail :timeForm.noticeEmail ,
              fillInTimes:timeForm.fillInTimes,
            }
          }

          this.$api.pim.config.saveOrUpdateFillInType(vo).then(res => {
            if(res.code==0){
              let message = '保存成功！';
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

        });
      }
    },
    mounted() {
      this.$api.merch.region.siteOptions(this.unionId).then(res => {
        let siteId = res.data[0].siteId
        this.siteId = siteId;
        this.siteOptions = res.data;
        this.changeSite(siteId);
      });
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .mb10{margin-bottom: 10px}
  .form-wrap{
    background:rgba(240, 242, 245, 1);
    padding: 20px;
    border-radius: 5px;
  }
  .form-cent{
    background: #fff;
    margin-bottom:20px;
    padding-bottom: 20px;
  }
  .tab-sub-title{
    font-size:14px;
    padding-left: 20px;
    margin: 0;
    border-bottom: #ddd solid 1px;
    line-height: 50px;
  }
  .tab-cont{
    font-size:14px;
    line-height: 40px;
    padding-left: 20px;
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