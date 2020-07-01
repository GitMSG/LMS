<template>
    <div id="admin-nav">
     <router-link :to="{ name: 'register' }" class="admin-item">Add a new user</router-link>
     <router-link :to="{ name: 'editUserRoles' }" class="admin-item" v-if="userRole == 'admin'">Edit User Roles</router-link>
    </div>
</template>

<script>
import auth from '@/auth.js'
    export default {
        data() {
    return {
        aUser: auth.getUser().sub,
        userRole: auth.getUser().rol
    }
  }
        
    }
</script>

<style  scoped>
#admin-nav{
    display:flex;
    height:50px;
    background:#1CCA29;
}
.admin-item{
    color: white;
    padding: 14px 16px;
    text-decoration: none;
}

</style>