<template>
  <div>
    <p>跳转中...</p>
    <!--跳转到HLSystem-->
    <form method="POST" id="myForm" style="display: none" action="http://122.112.227.247:16666/multi-meter-fast-api/api/zjn/SSO_Request">
      <input name="sign" :value="formData.sign" />
      <input name="timestamp"  :value="formData.timestamp"/>
      <input name="sysId"  :value="formData.sysId" />
      <input name="sysName"  :value="formData.sysName" />
      <input name="data.userName"  :value="formData.data.userName" />
      <input name="data.userId" :value="formData.data.userId" />
      <input name="data.token" :value="formData.data.token" />
      <input name="data.extInfo" :value="formData.data.extInfo" />
    </form>
  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";
  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "HLSystem",
    components: {},
    mixins: [
      listMixin
    ],
    data() {
      return {
        tableMainSearchModelBase:{
          corpName: ''
        },
        formData: {
        data:{}
        },
      };
    },
    computed: {
    },
    methods: {

    },
    mounted() {
     setTimeout(()=>{
       console.log(this.$route.params.siteId);
       this.$api.energy.energyRedirect.HLSystem(this.$route.params.siteId).then(res=>{
         this.formData = res.info;
         setTimeout(() => {
           console.log(document.getElementById('myForm'));
           document.getElementById('myForm').submit()
         },10)
       })
     },0)
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
</style>
