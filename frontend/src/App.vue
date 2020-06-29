<template>
  <body>
  <nav-bar/>
  <div class='app' >
    
    <router-view/>
  </div>
  </body>
</template>

<script>
import auth from '@/auth.js'
import NavBar from '@/components/NavBar.vue'
export default {
  components: {
    NavBar

  },
  data() {
    return {
        aUser: auth.getUser().sub,
        userRole: auth.getUser().rol
    }
  }
  
}
</script>

<style>
body {
  text-align:center;
  margin:0;
  font-family: 'Roboto Condensed', sans-serif;
  background: radial-gradient(circle, rgba(36,104,143,1) 0%, rgba(37,38,38,1) 100%);
}
.app {
  padding:20px;
}


</style>