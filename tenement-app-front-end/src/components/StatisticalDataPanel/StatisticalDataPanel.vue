<template>
  <el-row class="statistical-data-panel">
      <el-col :span="spanValue" class="item" v-for="(item, index) in dataList" :key="index">
        <div class="name">{{item.name}}</div>
        <div class="value" v-if="typeof item.value === 'number'"><d2-count-up :end="item.value" /> {{item.unit}}</div>
        <div class="value" v-else>{{item.value}}</div>
      </el-col>
    </el-row>
</template>

<script>
export default {
  name: "StatisticalDataPanel",
  props: {
    dataList: {
      type: Array,
      default: []
    }
  },
  computed: {
    spanValue() {
      return parseInt((24 / this.dataList.length).toFixed(0));
    }
  }
};
</script>

<style type="text/scss" lang="scss" scoped>
.statistical-data-panel {
  background-color: #f2f9ff;
  border-radius: 3px;
  .item {
    position: relative;
    text-align: center;
    &:after {
      content: "";
      position: absolute;
      right: 0;
      top: 20px;
      bottom: 20px;
      width: 0;
      border-left: 1px solid #ddd;
    }
    &:last-child:after {
      display: none;
    }
    .name {
      color: #999;
      font-size: 14px;
      padding-top: 20px;
      padding-bottom: 10px;
    }
    .value {
      color: #008dff;
      font-size: 24px;
      font-weight: bold;
      padding-bottom: 20px;
    }
  }
}
</style>
