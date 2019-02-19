import originAddress from './address-origin'

// 数组转对象
const toTreeData = function (arrayData, parentId) {
  let treeObejct = []
  let remainderArrayData = []

  arrayData.forEach(function (item) {
    if (item.parentId === parentId) {
      treeObejct.push(item)
    } else {
      remainderArrayData.push(item)
    }
  })
  treeObejct.forEach(function (item) {
    let _childrenData = toTreeData(remainderArrayData, item.value)
    _childrenData && _childrenData.length > 0 && (item.children = _childrenData)
  })
  return treeObejct
}

// 先转换一次数据
const addressTemp = originAddress.map(item => {
  let res = {
    label: item.codename,
    value: item.codevalue
  }
  if (item.codetype === '2' || item.codetype === '3') {
    res.parentId = item.codevalue.substr(0, item.codetype === '2' ? 5 : 7)
  }
  return res
})
const addressOptions = toTreeData(addressTemp)

export {
  originAddress,
  addressOptions
}
