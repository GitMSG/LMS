import Vue from 'vue'
import App from './App.vue'
import router from './router'
import * as Cloudinary from 'cloudinary-vue';

Vue.config.productionTip = false

Vue.use(Cloudinary, {
  configuration: {
    cloudName: 'goshorn'
  }
})

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
