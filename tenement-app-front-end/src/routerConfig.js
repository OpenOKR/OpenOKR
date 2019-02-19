/* eslint-disable */
// 工具
import UtilIce from '@/libs/util-ice.js'; // 页面和布局
import Blank from './layout/Blank';
import HeaderAside from './layout/HeaderAside';
// 变量名 routerConfig 为 iceworks 检测关键字
import Index from './pages/Index';
import Login from './pages/Login';
import Error401 from './pages/Error401';
import Error404 from './pages/Error404';

import CorpList from './pages/corp/CorpList';
import CustomerLabel from './pages/corp/CustomerLabel';
import OfficeList from './pages/base/OfficeList';
import RegionList from './pages/base/RegionList';
import StationList from './pages/base/StationList';
import ParkingList from './pages/base/ParkingList';
import ParkType from './pages/base/ParkType';
import ParkSummary from './pages/base/ParkSummary';
import EnergyList from './pages/energy/EnergyList';
import EnergyOpen from './pages/energy/EnergyOpen';
import AirUseDaily from './pages/energy/AirUseDaily';
import MeterUnitRel from './pages/energy/MeterUnitRel';
import UseQuantity from './pages/energy/UseQuantity';
import PayRec from './pages/energy/PayRec';
import BillAdd from './pages/energy/billManage/BillAdd';
import BillReview from './pages/energy/billManage/BillReview';
import BillReceive from './pages/energy/billManage/BillReceive';
import InvoiceRecord from './pages/energy/invoice/InvoiceRecord';
import InvoiceConfig from './pages/energy/invoice/InvoiceConfig';
import InvoiceWaitingOpen from './pages/energy/invoice/InvoiceWaitingOpen';
import InvoiceApply from './pages/energy/invoice/InvoiceApply';
import HLSystem from './pages/energy/energyRedirect/HLSystem';
import FeeAudit from './pages/tet/FeeAudit';
import FeeType from './pages/tet/FeeType';
import FeeTypeView from './pages/tet/FeeTypeView';
import OfficeAccount from './pages/tet/OfficeAccount';
import StationCorp from './pages/tet/StationCorp';

import WorkApplyList from './pages/mall/WorkApply/WorkApplyList';
import PassApplyList from './pages/mall/PassApply/PassApplyList';

//项目跟进管理
import ProjectType from './pages/project/TypeList';
import ProjectConfig from './pages/project/Config';
import ProjectModelList from './pages/project/ModelList';


// ice 会自动在这个变量下添加路由数据
// 请不要修改名称
// 备注 ice 自动添加的路由记录是以下格式
// {
//   path: '/page4',
//   layout: d2LayoutMain,
//   component: 4
// }
// 如果不指定 name 字段，会根据 path 生成 name = page-demo1
// 转换规则见 UtilIce.recursiveRouterConfig 中 path2name 方法
// meta 字段会和默认值使用 Object.assign 合并
// 如果不指定 meta.name 的话，name 字段会使用和上面路由 name 一样的取值逻辑
// 下面两个页面就是对比 你可以分别观察两个页面上显示的路由数据差异
const routerConfig = [
  {
    path: '/',
    name: 'index',
    layout: Blank,
    component: Index,
  },
  {
    path: '/corp/customer/:unionId',
    name: 'CorpList',
    layout: Blank,
    component: CorpList,
    meta: {
      title: '园区客户管理',
    }
  },
  {
    path: '/corp/customerLabel/:unionId',
    name: 'CustomerLabel',
    layout: Blank,
    component: CustomerLabel,
    meta: {
      title: '自定义标签',
    }
  },
  {
    path: '/base/office/:unionId',
    name: 'OfficeList',
    layout: Blank,
    component: OfficeList,
    meta: {
      title: '地点管理',
    }
  },
  {
    path: '/base/region/:unionId',
    name: 'RegionList',
    layout: Blank,
    component: RegionList,
    meta: {
      title: '区域管理',
    }
  },
  {
    path: '/base/station/:unionId',
    name: 'StationList',
    layout: Blank,
    component: StationList,
    meta: {
      title: '单元管理',
    }
  },
  {
    path: '/base/parking/:unionId',
    name: 'ParkingList',
    layout: Blank,
    component: ParkingList,
    meta: {
      title: '车位管理',
    }
  },
  {
    path: '/base/parkType/:unionId',
    name: 'ParkType',
    layout: Blank,
    component: ParkType,
    meta: {
      title: '园区类型设置',
    }
  },
  {
    path: '/base/parkSummary/:unionId',
    name: 'ParkSummary',
    layout: Blank,
    component: ParkSummary,
    meta: {
      title: '园区信息概况',
    }
  },
  {
    path: '/energy/energyList/:unionId',
    name: 'EnergyList',
    layout: Blank,
    component: EnergyList,
    meta: {
      title: '能耗系统管理',
    }
  },
  {
    path: '/energy/energyOpen/:unionId',
    name: 'EnergyOpen',
    layout: Blank,
    component: EnergyOpen,
    meta: {
      title: '能耗开通管理',
    }
  },
  {
      path: '/energy/airUseDaily/:unionId',
      name: 'AirUseDaily',
      layout: Blank,
      component: AirUseDaily,
      meta: {
          title: '能耗原始数据',
      }
  },
  {
    path: '/energy/meterUnitRel/:unionId',
    name: 'MeterUnitRel',
    layout: Blank,
    component: MeterUnitRel,
    meta: {
      title: '电表单元关系管理',
    }
  },
  {
    path: '/energy/useQuantity/:unionId',
    name: 'UseQuantity',
    layout: Blank,
    component: UseQuantity,
    meta: {
      title: '电表电量管理',
    }
  },
  {
    path: '/energy/billManage/billAdd/:unionId',
    name: 'BillAdd',
    layout: Blank,
    component: BillAdd,
    meta: {
      title: '账单添加',
    }
  },
  {
    path: '/energy/billManage/billReview/:unionId',
    name: 'BillReview',
    layout: Blank,
    component: BillReview,
    meta: {
      title: '账单审核管理',
    }
  },
  {
    path: '/energy/billManage/billReceive/:unionId',
    name: 'BillReceive',
    layout: Blank,
    component: BillReceive,
    meta: {
      title: '账单收款管理',
    }
  },
  {
    path: '/energy/energyRedirect/HLSystem/:siteId',
    name: 'HLSystem',
    layout: Blank,
    component: HLSystem,
    meta: {
      title: '跳转到HLSystem',
    }
  },
  {
    path: '/energy/payRec/:unionId',
    name: 'PayRec',
    layout: Blank,
    component: PayRec,
    meta: {
      title: '收支明细',
    }
  },
  {
    path: '/tet/feeAudit/:unionId',
    name: 'FeeAudit',
    layout: Blank,
    component: FeeAudit,
    meta: {
      title: '物业费项审核设置',
    }
  },
  {
    path: '/tet/feeType/:unionId',
    name: 'FeeType',
    layout: Blank,
    component: FeeType,
    meta: {
      title: '物业费项管理',
    }
  },
  {
    path: '/tet/feeTypeView/:unionId',
    name: 'FeeTypeView',
    layout: Blank,
    component: FeeTypeView,
    meta: {
      title: '物业费项管理',
    }
  },
  {
    path: '/tet/officeAccount/:unionId',
    name: 'OfficeAccount',
    layout: Blank,
    component: OfficeAccount,
    meta: {
      title: '物业收款账户管理',
    }
  },
  {
    path: '/tet/stationCorp/:unionId',
    name: 'StationCorp',
    layout: Blank,
    component: StationCorp,
    meta: {
      title: '单元企业关系管理',
    }
  },
  {
    path: '/energy/invoice/record/:unionId',
    name: 'InvoiceRecord',
    layout: Blank,
    component: InvoiceRecord,
    meta: {
      title: '开票记录 - 开票管理',
    }
  },
  {
    path: '/energy/invoice/apply/:unionId',
    name: 'InvoiceApply',
    layout: Blank,
    component: InvoiceApply,
    meta: {
      title: '开票申请',
    }
  },
  {
    path: '/energy/invoice/config/:unionId',
    name: 'InvoiceConfig',
    layout: Blank,
    component: InvoiceConfig,
    meta: {
      title: '开票费项设置',
    }
  },
  {
      path: '/energy/invoice/waitingOpen/:unionId',
      name: 'InvoiceWaitingOpen',
      layout: Blank,
      component: InvoiceWaitingOpen,
      meta: {
          title: '待开单据',
      }
  },
  {
    path: '/mall/WorkApply/workApplyList/:siteId',
    name: 'WorkApplyList',
    layout: Blank,
    component: WorkApplyList,
    meta: {
      title: '加班申请',
    }
  },
  {
    path: '/mall/PassApply/passApplyList/:siteId',
    name: 'PassApplyList',
    layout: Blank,
    component: PassApplyList,
    meta: {
      title: '放行申请',
    }
  },
  {
    path: '/project/type/:unionId',
    name: 'ProjectType',
    layout: Blank,
    component: ProjectType,
    meta: {
      title: '项目类型配置',
    }
  },
  {
    path: '/project/config/:unionId',
    name: 'ProjectConfig',
    layout: Blank,
    component: ProjectConfig,
    meta: {
      title: '项目跟进通用配置',
    }
  },
  {
    path: '/project/modelList/:unionId',
    name: 'ProjectModelList',
    layout: Blank,
    component: ProjectModelList,
    meta: {
      title: '项目配置列表',
    }
  },
];

// 不参与菜单显示的
// ice 不会处理这部分
// 但是这部分路由也会被注册
// 处理规则同 routerConfig
const routerConfigMenuOut = [
  {
    path: '/login',
    name: 'login',
    component: Login,
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: '/401',
    name: 'Unauthorized',
    component: Error401,
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: '*',
    component: Error404,
  },
];

// 导出全部路由设置
// 这个数据会在 router.js 中被扁平处理

export default UtilIce.recursiveRouterConfig([
  ...routerConfig,
  ...routerConfigMenuOut,
]);

// 导出参与多标签页处理的路由设置
// 这个数据会在 mian.js 中使用

export const frameInRoutes = UtilIce.recursiveRouterConfig(routerConfig).map(
  e => {
    const route = e.children ? e.children[0] : e;
    return {
      path: e.path,
      name: route.name,
      meta: route.meta,
    };
  }
);
