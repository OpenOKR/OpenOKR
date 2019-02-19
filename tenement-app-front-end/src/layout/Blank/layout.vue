<template>
  <div
    class="d2-layout-blank-group"
    :style="styleLayoutMainGroup"
    :class="{grayMode: isGrayMode}">
    <!-- 主体内容 -->
    <div class="d2-layout-header-aside-content">
      <div class="d2-theme-container">
        <!-- 主体 -->
        <div class="d2-theme-container-main">
          <div class="d2-theme-container-main-body">
            <transition name="fade-transverse">
              <keep-alive :include="d2adminKeepAliveInclude">
                <router-view/>
              </keep-alive>
            </transition>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'

export default {
  name: 'd2-layout-blank',
  components: {
  },
  data () {
    return {
    }
  },
  computed: {
    ...mapState({
      isGrayMode: state => state.d2admin.isGrayMode,
      pageOpenedList: state => state.d2admin.pageOpenedList
    }),
    ...mapGetters([
      'd2adminThemeActiveSetting',
      'd2adminKeepAliveInclude'
    ]),
    /**
     * @description 最外层容器的背景图片样式
     */
    styleLayoutMainGroup () {
      return {
        ...this.d2adminThemeActiveSetting.backgroundImage ? {
          backgroundImage: `url('${this.$baseUrl}${this.d2adminThemeActiveSetting.backgroundImage}')`
        } : {}
      }
    }
  },
  methods: {
  }
}
</script>

<style lang="scss">
// 注册主题
@import '~@/assets/style/theme/register.scss';
</style>
