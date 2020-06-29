<template>
  
  <div class="home" :style="image">
    <h1 class="home-header">Tech Elevator LMS</h1>
    
  </div>
  
</template>

<script>
import auth from "@/auth.js"

export default {
  name: 'home',
  data() {
    return {
      image: {
        
       
        backgroundSize: "cover",
        backgroundPosition: "center",
        height: "10px", 
        maxWidth: "100%"

        
      }
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


.home-header{
  font-size: 50px;
  font-weight:lighter;
  color: #add8e6;
  
}


</style>