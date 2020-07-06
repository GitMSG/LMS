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
     email: auth.getUser().sub,
          
    }
  },
  created(){
   
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
    width:90vw;
  height:90vh;
}

.home-header{
  font-size: 50px;
  font-weight:lighter;
  color: white;
  
}


</style>