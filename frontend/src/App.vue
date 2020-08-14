<template>
  <body :style="image"  >
  <nav-bar v-if="aUser != null" />
  <admin-nav v-if="this.userRole == 'admin'" />
  <div class='app' > 
    <router-view/>
  </div>
  </body>
</template>

<script>
import auth from '@/auth.js'
import NavBar from '@/components/NavBar.vue'
import AdminNav from '@/components/AdminNav.vue'
export default {
  components: {
    NavBar,
    AdminNav,
  },
  data() {
    return {
        aUser: auth.getUser().sub,
        userRole: auth.getUser().rol,
        image: {
              background: `url(${require('@/assets/TE_LMS.png')})no-repeat` ,
              backgroundSize: "cover",
              backgroundPosition: "center",
              backgroundRepeat: "no-repeat",
              backgroundAttachment:"fixed"
            }
    }
  }
  
}
</script>

<style>
body {
  
  text-align:center;
  margin:0;
  font-family: 'Roboto Condensed', sans-serif; 
  /* background: radial-gradient(circle, rgba(36,104,143,1) 20%, rgba(37,38,38,1) 100%); */
 /*  background: linear-gradient(180deg, rgba(184,187,189,1) 0%, rgba(86,89,86,1) 98%); */
  background: rgba(37,85,112,1);
  
  /* linear-gradient(180deg, rgba(37,85,112,1) 20%, rgba(70,74,74,1) 100%); */


}




</style>