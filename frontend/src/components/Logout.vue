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
      
      this.$router.push({name: 'login'});      
      this.$router.go(); 
    }
  }
}
</script>
 
 <style scoped>
 .logout-button{
  background-color: #336699;
  color: black;
  padding: 5px 20px;
  text-align: center;
  text-decoration: none;
  display: block;
  font-size: 16px;
  border-radius: 4px;
 
}
.logout-button:hover{
  background-color: white;
  color:#336699;
  cursor:pointer;
}
 .logout{
  margin:auto;
}

 </style>