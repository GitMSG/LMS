<template>
  <body>
  <div class="home" :style="image">
    <h1 class="home-header">Tech Elevator LMS</h1>
    
  </div>
  </body>
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
/* body {
 padding: 0;
 position: relative;
 
} */

.home{
  text-align:center;
  background: dimgray;
  padding-bottom: 100%;
  width: 100%;
  max-height: 50%;
  background-size: cover;

}

.home-header{
  font-size: 50px;
  font-weight:lighter;
  color: #add8e6;
  padding-top: 25px;
  padding-left: 25px;
}


</style>