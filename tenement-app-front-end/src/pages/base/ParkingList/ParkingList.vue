<template>
  <station-list ref="stationList" :is-car="1" :site-id="siteId" :office-id="officeId" :uid="uid"></station-list>
</template>

<script>
  import StationList from "../StationList";

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "ParkingList",
    components: {StationList},
    props: ['siteId', 'officeId', 'uid'],
    data() {
      return {};
    },
    methods: {
      refresh(){
        this.$refs.stationList.refresh();
      }
    },
    mounted() {
    }
  };
</script>
