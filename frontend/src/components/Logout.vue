<template>
  <div class="logout">
    <a class="logout-button" v-on:click="logout">Logout</a>
  </div>
</template>

<script>
import auth from "@/auth.js"

export default {
  name: 'logout',
  methods: {
    logout(){
      auth.logout();
      this.$router.push({name: 'home'});
       this.$router.go(); 
    }
  }
}
</script>
 
 <style scoped>
 .logout{
  float: right;
  display: block;
  color: white;
  text-align: center;
  padding: 5px 10px;
  text-decoration: none;
}
.logout-button {
    padding:5px 10px;
}
 </style>