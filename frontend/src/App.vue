<template>
  <body>
  <div id="app">
    <div id="nav" class="nav">
      <router-link :to="{ name: 'home' }" class="baritem">Home</router-link>
      <router-link :to="{ name: 'breweries' }" class="baritem">Breweries</router-link>
      <router-link :to="{ name: 'beers' }" class="baritem">Beers</router-link>
      <router-link :to="{ name: 'editUserRoles' }" class="baritem" v-if="userRole == 'admin'">Edit User Roles</router-link>
      <h3 id="user-acct" v-if="aUser"> {{aUser}}, Logged in as {{userRole}}</h3>
      <logout />
    </div>
    <router-view/>
  </div>
  </body>
</template>

<script>
import auth from '@/auth.js'
import Logout from '@/components/Logout.vue'
export default {
  components: {
    Logout
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

.nav{
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: #333;
  position: fixed;
  top: 0;
  overflow: hidden;
  width: 100%;
  z-index: 1;
  float: left;
}
.baritem{
  float: left;
  display: block;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}
.baritem:hover{
background-color: #111;
}
#user-acct{
  display: inline-block;
  font-weight:lighter;
  color: rgb(240, 201, 73);
  padding: 10px 100px;
  margin-top:0px;
  margin-bottom:0px;
  text-decoration: none;
}

body {
  margin-right: 0;
  margin-left: 0;
  margin-top: 45px;
  font-family: 'Roboto Condensed', sans-serif;
}
body{
  background: dimgray;
}


</style>