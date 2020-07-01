<template>
  
  <div class="home" >
    <h1 class="home-header">Tech Elevator LMS</h1>
   
  </div>
  
</template>

<script>
import auth from "@/auth.js"

export default {
  name: 'home',
  data() {
    return {
     
    }
  },
  methods: {
    logout(){
      auth.logout();
      this.$router.go();
    }
  }
}
</script>

<style >
.home{
    width:100vw;
  height:100vh;
}

.home-header{
  font-size: 50px;
  font-weight:lighter;
  color: #add8e6;
  
}


</style>