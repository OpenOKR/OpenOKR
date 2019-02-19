<template>
  <div>
    <d2-container>
      <template slot="header">能耗原始数据</template>
        <br>
        <el-form label-width="100px" :model="formData" :rules="rules" ref="pageForm">
            <el-form-item label="站点：" prop="siteId">
                <el-select
                    v-model="formData.siteId"
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
            </el-form-item>
            <el-form-item label="开始时间：" prop="startDateStr">
                <el-date-picker
                    v-model="formData.startDateStr"
                    type="date"
                    style="width: 300px;"
                    :picker-options="startDateStrOptions"
                    value-format="yyyy-MM-dd"
                    placeholder="选择日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="结束时间：" prop="endDateStr">
                <el-date-picker
                    v-model="formData.endDateStr"
                    style="width: 300px;"
                    type="date"
                    value-format="yyyy-MM-dd"
                    :picker-options="endDateStrOptions"
                    placeholder="选择日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="制冷单价：" prop="coldMeterPrice">
                <el-input
                    style="width: 300px;"
                    v-model="formData.coldMeterPrice">
                </el-input>
            </el-form-item>
            <el-form-item label="制热单价：" prop="heatMeterPrice">
                <el-input
                    style="width: 300px;"
                    v-model="formData.heatMeterPrice">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="exportExcel">Excel导出</el-button>
                <el-button @click="resetForm">重 置</el-button>
            </el-form-item>
        </el-form>

    </d2-container>

  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";
  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "AirUseDaily",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      const checkAmt = (rule, value, callback)=>{
        console.log(rule)
        let isCash=/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
        if(value ===''){
           callback(new Error('请输入金额'));
        }else if(!isCash.test(value)){
          callback(new Error('输入金额有误，最多保留两位小数'));
        }else if(parseFloat(value)==0){
           callback(new Error('输入不能少于0.01元'));
        }else{
          return callback();
        }
      };
      return {
        formData:{
          "coldMeterPrice": "",
          "endDateStr": "",
          "heatMeterPrice": "",
          "siteId": "",
          "startDateStr": "",
        },
        //开始时间可选区域
        startDateStrOptions:{
          disabledDate: (time) => {
            let endDateStr = this.formData.endDateStr;
            if (endDateStr) {
              return time.getTime() > endDateStr;
            }
          }
        },
        //结束时间可选区域
        endDateStrOptions:{
          disabledDate: (time) => {
            let startDateStr = this.formData.startDateStr;
            if (startDateStr) {
              return time.getTime() < startDateStr;
            }
          }
        },
        siteOptions: [],
        rules:{
          siteId:[
            {required: true, trigger: 'change', message: '站点不能为空', }
          ],
          startDateStr:[
            {required: true, trigger: 'change', message: '开始时间必填', }
          ],
          endDateStr:[
            {required: true, trigger: 'change', message: '结束时间必填', }
          ],
          coldMeterPrice:[
            {required: true, validator: checkAmt, trigger: 'blur' }
          ],
          heatMeterPrice:[
            {required: true, validator: checkAmt, trigger: 'blur' }
          ],
        }
      };
    },
    computed: {
      unionId() {
        return this.$route.params.unionId
      },
    },
    methods: {
      //切换站点
      changeSite(siteId){
        this.formData.siteId = siteId;
      },
      resetForm(){
        this.$refs.pageForm.resetFields();
      },
      //导出
      exportExcel(){
        this.$refs.pageForm.validate((valid) => {
          if (valid) {
            this.$api.energy.ecaSystemSiteRel.exportAirUseDailyDataExcel({
              ...this.formData,
              unionId:this.unionId
            })
          }else {
            return false;
          }
        });
      },
    },
    mounted() {
      //获取站点列表
      setTimeout(()=>{
        this.$api.merch.region.siteOptions(this.unionId).then(res => {
          let siteId = res.data[0].siteId;
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
