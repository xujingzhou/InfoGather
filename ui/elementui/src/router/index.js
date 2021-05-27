import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/punchinghole/index.html',
      // name: 'HelloWorld',
      // component: HelloWorld
    }
  ],
  mode:"history"
})
