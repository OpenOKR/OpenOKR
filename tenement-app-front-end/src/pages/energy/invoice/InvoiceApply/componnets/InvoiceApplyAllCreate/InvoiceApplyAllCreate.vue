<template>
  <transition name="edit">
    <d2-container class="edit-panel" v-loading="loading" v-show="isVisible">
      <template slot="header">
        <i @click="back" class="el-icon-back" title="后退"></i>
        批量申请
      </template>
      <div class="total">
        购方企业列表（{{billListShow.length}}）<el-button class="fr" @click="submit" type="primary"><i class="iconfont icon-save"></i>  确认提交</el-button>
      </div>
      <div class="my-tabs" :style="contentStyleObj">
        <div class="my-tabs-title-box">
          <div class="in">
            <div class="list" :class="{active:tabIndex==index}" v-for="(item,index) in billListShow" @click="changeTab(item,index)" :key="'tabTitle'+index">
              <span>{{item.corpName}}</span>
              <a class="remove fr" @click.stop="removeByCorp(item)">移除({{item.billTickType1.length + item.billTickType2.length}})</a>
            </div>
          </div>
        </div>
        <div class="my-tabs-centent-box">
            <!--专用-->
          <div class="w100off" v-for="(item,index) in billListShow" v-show="index==tabIndex" :key="'tabCentent'+index" >
              <div class="tip-text" :data="item.billTickType1">
                  <div class="tip-text-list">
                      生成【增值税专用发票】的费用单据，共{{item.billTickType1.length}}张
                      <span class="ml20">价税合计金额：{{getTotal(item.billTickType1,'invoiceAmt')|NumFormat}}元</span>
                  </div>
                  <div class="tip-text-list">
                      不含税：{{(getTotal(item.billTickType1,'invoiceAmt') - getTotal(item.billTickType1,'speTax'))|NumFormat}}元
                  </div>
                  <div class="tip-text-list">
                      税额：{{getTotal(item.billTickType1,'speTax')|NumFormat}}元
                  </div>
              </div>
              <!--专用-->
              <el-table :data="item.billTickType1">
                  <el-table-column fixed="left" label="操作" width="80">
                      <template slot-scope="{row}">
                          <el-button @click="removeItem(row)" type="text">移除</el-button>
                      </template>
                  </el-table-column>
                  <el-table-column label="业务流水号" prop="billNo" width="160"></el-table-column>
                  <el-table-column label="费项" prop="feeTypeDesc"></el-table-column>
                  <el-table-column label="客户名称" prop="corpName" width="220"></el-table-column>
                  <el-table-column label="不含税金额" width="100">
                      <template slot-scope="{row}">
                          {{row.invoiceAmt - row.speTax}}
                      </template>
                  </el-table-column>
                  <el-table-column label="税额" prop="speTax" ></el-table-column>
                  <el-table-column label="税率" prop="speTaxRate" >
                      <template slot-scope="{row}">
                          {{row.speTaxRate}}%
                      </template>
                  </el-table-column>
                  <el-table-column label="价税合计" prop="invoiceAmt" ></el-table-column>
                  <el-table-column label="应收日期" prop="receivableDate" width="100">
                      <template slot-scope="{row}">
                          <el-button-group>
                              <span v-if="row.receivableDate > 0">{{ new Date(row.receivableDate) | dateFormat('yyyy-MM-dd') }}</span>
                          </el-button-group>
                      </template>
                  </el-table-column>
                  <el-table-column label="收款期间" width="160">
                      <template slot-scope="{row}">
                <span v-if="row.billStartDate && row.billEndDate">
                {{ new Date(row.billStartDate) | dateFormat('yyyy-MM-dd') }} ~
                {{ new Date(row.billEndDate) | dateFormat('yyyy-MM-dd') }}
                </span>
                      </template>
                  </el-table-column>
              </el-table>
            <div class="page-box">
              <!--共{{item.length}}条记录-->
            </div>
              <div class="tip-text" :data="item.billTickType2">
                  <div class="tip-text-list">
                      生成【增值税普通发票】的费用单据，共{{item.billTickType2.length}}张
                      <span class="ml20">价税合计金额：{{getTotal(item.billTickType2,'invoiceAmt')|NumFormat}}元</span>
                  </div>
                  <div class="tip-text-list">
                      不含税：{{(getTotal(item.billTickType2,'invoiceAmt') - getTotal(item.billTickType2,'comTax'))|NumFormat}}元
                  </div>
                  <div class="tip-text-list">
                      税额：{{getTotal(item.billTickType2,'comTax')|NumFormat}}元
                  </div>
              </div>
              <el-table :data="item.billTickType2">
                  <el-table-column fixed="left" label="操作" width="80">
                      <template slot-scope="{row}">
                          <el-button @click="removeItem(row)" type="text">移除</el-button>
                      </template>
                  </el-table-column>
                  <el-table-column label="业务流水号" prop="billNo" width="160"></el-table-column>
                  <el-table-column label="费项" prop="feeTypeDesc"></el-table-column>
                  <el-table-column label="客户名称" prop="corpName" width="220"></el-table-column>
                  <el-table-column label="不含税金额" width="100">
                      <template slot-scope="{row}">
                          {{row.invoiceAmt - row.comTax}}
                      </template>
                  </el-table-column>
                  <el-table-column label="税额" prop="comTax" ></el-table-column>
                  <el-table-column label="税率" prop="comTaxRate" >
                      <template slot-scope="{row}">
                          {{row.comTaxRate}}%
                      </template>
                  </el-table-column>
                  <el-table-column label="价税合计" prop="invoiceAmt" ></el-table-column>
                  <el-table-column label="应收日期" prop="receivableDate" width="100">
                      <template slot-scope="{row}">
                          <el-button-group>
                              <span v-if="row.receivableDate > 0">{{ new Date(row.receivableDate) | dateFormat('yyyy-MM-dd') }}</span>
                          </el-button-group>
                      </template>
                  </el-table-column>
                  <el-table-column label="收款期间" width="160">
                      <template slot-scope="{row}">
                <span v-if="row.billStartDate && row.billEndDate">
                {{ new Date(row.billStartDate) | dateFormat('yyyy-MM-dd') }} ~
                {{ new Date(row.billEndDate) | dateFormat('yyyy-MM-dd') }}
                </span>
                      </template>
                  </el-table-column>
              </el-table>
          </div>

        </div>
      </div>
    </d2-container>
  </transition>
</template>

<script>
  import formMixin from "@/mixins/form.mixin";
  import { cloneDeep, find } from 'lodash';

  export default {
    name: "InvoiceApplyAllCreate",
    components: {},
    mixins: [
      formMixin
    ],
    props: [],
    data() {
      return {
        contentStyleObj:{
          height:''
        },
        siteId:'',
        isVisible: false,
        billVos:[],
        tabIndex:0,
        sellerCorpId:'',
      };
    },
    computed: {
      unionId() {
        return this.$route.params.unionId
      },
      billListShow(){
        let _billListShow = [];
        let corpIds = [];
        this.billVos.map(item => {
          if (corpIds.indexOf(item.corpId) == -1) {
            corpIds.push(item.corpId);
            _billListShow.push({
              billTickType1: [],
              billTickType2: [],
              corpName: item.corpName,
              corpId: item.corpId,
              billNo: item.billNo
            });
          }
          ;
          _billListShow[corpIds.indexOf(item.corpId)]['billTickType' + item.tickType].push(item)
        });
        return _billListShow;
      }

    },
    methods: {
      getHeight(){
        this.contentStyleObj.height=window.innerHeight-165+'px';
      },
      // 打开弹框，初始化数据
      open(vo) {
        console.log(vo);
          this.siteId = vo.siteId;
          this.sellerCorpId = vo.sellerCorpId;
          this.tabIndex = 0;
          this.$api.energy.invoice.apply.queryBatchInfo(this.siteId,vo.billIds).then(res =>{
              console.log(res);
          if(res.code==0){
            this.$nextTick(() => {
              this.billVos = res.data;
            })
          }else{
                  this.$message.error(res.message);
              }
          });
        this.isVisible = true;
        this.loading = false;
      },
      getTotal(billVos,name) {
          let _invoiceAmt=0;
          console.log(billVos);
          billVos.map((item,index)=>{
              _invoiceAmt+=item[name];
          })
          return _invoiceAmt;
      },
      back() {
        this.isVisible = false
        this.$refs.formMain.resetFields();
      },
      changeTab(item,index){
        this.tabIndex = index;
      },
      //通过企业id删除
      removeByCorp(item){
          console.log(item);
        let corpId = item.corpId;
          for(let i=0;i<this.billVos.length;){
              let billItem = this.billVos[i]
              if(billItem.corpId==corpId){
                  this.billVos.splice(i,1)
              }else{
                  i++;
              }
          }
          this.tabIndex=Math.min(this.tabIndex,this.billListShow.length-1)
      },
      //通过billid删除
      removeItem(item){
        let billNo = item.billNo;
        this.billVos.map((billItem,index)=>{
          if(billItem.billNo==billNo){
            this.billVos.splice(index,1)
          }
        })
      },
      /**
       * 提交客户
       */
      submit() {
          let vo={
              siteId: this.siteId,
              billIds:this.billVos.map(bill=>{
                  return bill.billId
              }),
              sellerCorpId: this.sellerCorpId
          };
        this.$api.energy.invoice.apply.batchApply(vo).then(res =>{
              console.log(res);
              if(res.code==0){
                this.$message.success('提交成功！');
                this.$emit("ok");
                this.back()
              }else{
                this.$message.error(res.message);
              }
          });

      } // end submit

        ,
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
        }
    },
    mounted() {
    },
    created(){
      window.addEventListener('resize', this.getHeight);
      this.getHeight()
    },

    destroyed(){
      window.removeEventListener('resize', this.getHeight)
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .form-input{ width: 300px}
</style>

<style type="text/scss" lang="scss">
    .total {
        padding: 10px 15px;
        background: #f9f9f9;
        line-height: 40px;
        border: solid 1px #eee;
        font-size: 15px;
    }

    .my-tabs {
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
    }

    .my-tabs-title-box {
        width: 270px;
        float: left;
        border: solid 1px #eee;
        border-top: none;
        background: #f9f9f9;
        font-size: 14px;
        height: 100%;
        min-height: 400px;
        overflow: auto;
        flex-shrink: 0;
        box-sizing: border-box;
        .in {
            background: #fff;
        }
        .list {
            padding: 10px 15px;
            line-height: 22px;
            background: #f9f9f9;
            border-bottom: solid 2px #fff;
            cursor: pointer;
            span {
                display: inline-block;
                width: 180px;
            }
        }
        .list.active {
            background: #fff;
        }
        .remove {
            color: #409eff;
            font-size: 12px;
            cursor: pointer;
        }
    }

    .my-tabs-centent-box {
        font-size: 14px;
        padding: 18px;margin-left:272px;
        height: 100%;
        box-sizing: border-box;
        overflow: auto;
    }

    .el-table th {
        color: #333;
        background-color: #f9f9f9;
    }

    .el-table td {
        padding: 5px 0;
        font-size: 12px;
        .el-button {
            font-size: 12px;
        }
        .cell {
            line-height: 20px;
        }
    }

    .page-box {
        padding: 15px 10px;
        line-height: 30px;
        margin-bottom: 10px;
        font-size: 12px;
        text-align: center;
    }

    .tip-text {
        margin-bottom: 10px;
        padding: 5px 20px;
        border: 1px solid #b3d8ff;
        background-color: #ecf5ff;
        border-radius: 4px;
        line-height: 20px;
        color: #666;
    }

    .tip-text-list {
        display: inline-block
    }

    .tip-text-list + .tip-text-list {
        margin-left: 15px;
        padding-left: 15px;
        position: relative;
    }

    .tip-text-list + .tip-text-list:before {
        content: '';
        position: absolute;
        top: 50%;
        top: 50%;
        width: 2px;
        left: 0;
        height: 16px;
        margin-top: -8px;
        border-left: 1px solid #cfd7e5;
    }

    .ml20 {
        margin-left: 20px;
    }

    .w100off {
        width: 100%;
    }
</style>