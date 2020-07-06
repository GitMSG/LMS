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
    background:#1C9A2F;
    box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
}
.admin-item{
    color: white;
    padding: 14px 16px;
    text-decoration: none;
}

</style>