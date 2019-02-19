<template>
  <transition name="edit">
    <d2-container class="edit-panel" v-loading="loading" v-show="isVisible">
      <template slot="header">
        <i @click="back" class="el-icon-back" title="后退"></i>
        查看发票
      </template>
      <div class="invoice-list-item" v-for="item in invoiceList">
        <div class="tip-text">
          <span v-if="item.createTs">开票日期：{{ new Date(item.createTs)|dateFormat('yyyy-MM-dd hh:mm:ss')}}</span>
          <span>开票金额：{{(item.noTaxMoney+item.tax)| NumFormat }}元</span>
          <span>开票人： {{item.operUserName}}</span>
        </div>
        <iframe
          :src="item.viewInvoiceUrl"
          frameborder="0"
          id="contentIframe"
          style="width: 100%;height:400px;">
        </iframe>
      </div>

      <template slot="footer">
        <el-button @click="back">关 闭</el-button>
      </template>
    </d2-container>
  </transition>
</template>

<script>
  import formMixin from "@/mixins/form.mixin";
  import { cloneDeep, find } from 'lodash';

  export default {
    name: "InvoiceApplyCreate",
    components: {},
    mixins: [
      formMixin
    ],
    props: [],
    data() {
      return {
          isVisible: false,
          invoiceList:[]
      };
    },
    computed: {

      unionId() {
        return this.$route.params.unionId
      },
    },
    methods: {
      // 打开弹框，初始化数据
      open(billVo) {
        console.log(billVo);
        this.$api.energy.invoice.apply.getInvoiceList(billVo.id).then(res => {
            if(res.code==0){
              this.invoiceList = res.data;
              this.isVisible = true;
            }else{
              this.$message.error(res.message);
              this.isVisible = false;
            }
          this.loading = false;
        });
      },
      back() {
        this.isVisible = false;
      },
    },
    mounted() {

    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .form-input{ width: 300px}
  .invoice-list-item{
    margin: 10px 0 20px 0;
  }
  .tip-text {
    margin-bottom: 10px;
    padding: 5px 20px;
    border: 1px solid #b3d8ff;
    background-color: #ecf5ff;
    border-radius: 4px;
    line-height: 20px;
    color: #666;
    span{
      margin-right: 40px;
    }
  }
</style>
