<template>
    <div id="admin-nav">
     <router-link :to="{ name: 'register' }" class="admin-item">Add User</router-link>
     <router-link :to="{ name: 'allProfiles' }" class="admin-item" v-if="userRole == 'admin'"> All Profiles</router-link>
     <router-link :to="{ name: 'unApprovedList' }" class="admin-item" v-if="userRole == 'admin'">Needs Approval</router-link>
     <router-link :to="{ name: 'settings' }" class="admin-item" v-if="userRole == 'admin'">Settings</router-link>
     <router-link :to="{ name: 'search' }" class="admin-item" v-if="userRole == 'admin'">Search</router-link>
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
    background:rgba(32,133,47,1);
    border-bottom: 2px solid rgba(32,133,47,1);    
    box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
}
.admin-item{
    color: white;
    font-weight: lighter;
    padding: 14px 16px;
    text-decoration: none;
}
.admin-item:hover{
    font-weight:bolder;
    
}

</style>