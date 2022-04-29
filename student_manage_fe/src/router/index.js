import Vue from 'vue'
import VueRouter from 'vue-router'
import{request} from "../network/request";
import store from "../store/index"

Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
// 修改 原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err=>err)
}

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('../views/Home.vue'),
    redirect: '/home/leaveRecord',
    children: [
      {
        path: 'leaveRecord',
        name: 'LeaveRecord', 
        component: () => import('../views/LeaveRecordManage.vue')
      },
      {
        path: 'userManage',
        name: 'UserManage',
        component: () => import('../views/UserManage/index.vue')
      },
      {
        path: 'displayBoard',
        name: 'DisplayBoard',
        component: () => import('../views/DisplayBoard.vue')
      },
    ]
  },

  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  }
]

const router = new VueRouter({
  routes
})
router.beforeEach((to, from, next) => {
  if (to.path !== '/login'){
    request({
      url: '/auth/checkLogin'
    }).then( res => {
      if( res.code === '200'){
        console.log('login success');
        store.commit('updateLoginState', {isLogin: true, role: res.data})
        next();
      } else {
        next('/login')
      }
    }).catch( err => {next('/login')})
  } else {
    next();
  }
})
export default router
