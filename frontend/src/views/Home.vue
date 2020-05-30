<template>
  <body>
  <div class="home" :style="image">
    <h1 class="home-header">Working Title</h1>
    <!-- <button class="logout-button" v-on:click="logout">Logout</button> -->
    <div class="homePara">
      <p>Welcome </p>
    
    </div>
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
        
        background: ,
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
body {
 padding: 0;
 position: relative;
 
}
.homePara {
    font-size: large;
    background-color: rgb(207, 207, 211);
    margin: 0px 150px 10px 150px;
    padding: 10px 50px 10px 50px;
    border-radius: 5px;
    border: 1px solid;
    box-shadow: 5px 10px rgb(49, 48, 48);
}
.home{
  background: dimgray;
  padding-bottom: 100%;
  width: 100%;
  max-height: 50%;
  background-size: cover;

}
.background-img {
   z-index: -1;
   background: cover;
}
.home-header{
  font-size: 50px;
  font-family: Lobster;
  color: rgb(240, 201, 73);
  padding-top: 25px;
  padding-left: 25px;
}
.logout-button{
  background-color: rgb(240, 201, 73);
  border: none;
  color: black;
  padding: 10px 40px;
  text-align: center;
  text-decoration: none;
  display: block;
  font-size: 20px;
  border-radius: 8px;
  transition-duration: 0.4s;
  border: 2px solid rgb(240, 201, 73);
}
.logout-button:hover{
  background-color: white;
  border: 2px solid rgb(240, 201, 73);
}

</style>