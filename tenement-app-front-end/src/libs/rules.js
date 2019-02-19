
const required =  { required: true, message: '请填写', trigger: 'blur' };


//手机号码
export const phone =  [
  required,
  { pattern: /^1\d{10}$/, message: '手机号码错误',trigger: 'blur' }
];
//邮箱
export const email =  [
  required,
  { pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message: '邮箱格式有误',trigger: 'blur' }
];