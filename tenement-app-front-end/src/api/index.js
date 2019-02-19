import common from './common'
import region from './merch/region'
import office from './merch/office'
import station from './merch/station'
import corp from './corp/corp'
import parkType from './base/parkType'
import parkSummary from './base/parkSummary'
//能耗管理
import ecaSystemSiteRel from './energy/ecaSystemSiteRel'
import meterUnitRel from './energy/meterUnitRel'
import useQuantity from './energy/useQuantity'
import billManage from './energy/billManage'
import payRec from './energy/payRec'
import energyRedirect from './energy/energyRedirect'
//物业费用管理
import feeAudit from './tet/feeAudit'
import feeType from './tet/feeType'
import officeAccount from './tet/officeAccount'
//单元企业关系
import stationCorp from './tet/stationCorp'
// 开票管理
import invoiceRecord from './energy/invoiceRecord'
import invoiceApply from './energy/apply'
import invoiceConfig from './energy/invoiceConfig'
import invoiceWaitingOpen from './energy/invoiceWaitingOpen'

import workApply from './mall/workApply'
import passApply from './mall/passApply'

//项目跟进
import pimType from './pim/type'
import pimConfig from './pim/config'

export default {
  common,
  corp,
  merch: {
    region,
    office,
    station
  },
  base:{
    parkType,
    parkSummary
  },
  tet:{
    feeAudit,
    officeAccount,
    feeType,
    stationCorp
  },
  energy:{
    ecaSystemSiteRel,
    meterUnitRel,
    useQuantity,
    billManage,
    payRec,
    energyRedirect,
    invoice: {
      record: invoiceRecord,
      apply: invoiceApply,
      config: invoiceConfig,
      invoiceWaitingOpen: invoiceWaitingOpen
    },

  },
  mall:{
    workApply,
    passApply,
  },
  pim:{
    type:pimType,
    config:pimConfig
  }
}
