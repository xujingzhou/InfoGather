import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

export default new Vuex.Store({
  state: { 
      // 存储token
    Authorization: sessionStorage.getItem('Authorization') ? sessionStorage.getItem('Authorization') : ''  
  },
  
  mutations: {
     // 修改token，并将token存入localStorage
    changeLogin (state, user) {
      state.Authorization = user.Authorization;
      sessionStorage.setItem('Authorization', user.Authorization);
    }
  }
})