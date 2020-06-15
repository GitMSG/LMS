<template>
  <body>
 
    <div id="nav" class="nav">
      <router-link :to="{ name: 'home' }" class="baritem">Home</router-link>
    <!--   <router-link :to="{ name: 'breweries' }" class="baritem">Breweries</router-link>
      <router-link :to="{ name: 'beers' }" class="baritem">Beers</router-link> -->
      <router-link :to="{ name: 'editUserRoles' }" class="baritem" v-if="userRole == 'admin'">Edit User Roles</router-link>
      <h3 id="user-acct" v-if="aUser"> {{aUser}}, Logged in as {{userRole}}</h3>
      <logout />
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
  display: flex;
  list-style-type: none;
  overflow: hidden;
  background-color: #202124;
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
 
 
}
.baritem{
  color: white;
  padding: 14px 16px;
  text-decoration: none;
}
.baritem:hover{
background-color: #111;
 color: #add8e6;
}
#user-acct{  
  font-weight:lighter;
  color: #add8e6;
  padding: 10px 100px;
  margin-top:0px;
  margin-bottom:0px;
  text-decoration: none;
}



</style>