<template>
  <div id="nav" class="nav">
    <router-link :to="{ name: 'profile' }" class="baritem">Profile</router-link>
    <logout id="logout" />
  </div>
</template>
<script>
import auth from "@/auth.js";
import Logout from "@/components/Logout.vue";
export default {
  components: {
    Logout,
  },
  data() {
    return {
      aUser: auth.getUser().sub,
      userRole: auth.getUser().rol,
    };
  },
};
</script>

<style >
.nav {
  display: flex;
  list-style-type: none;
  justify-content: space-between;
  padding:0px 10px;
  background-color: rgba(37, 38, 38, 1);
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),
    0 1px 10px 0 rgba(0, 0, 0, 0.12);
}
#logout {
  align-self: center;
  
}
.baritem {
  color: white;
  padding: 14px 16px;
  text-decoration: none;
}
.baritem:hover {
  background-color: #111;
  color: #add8e6;
}

</style>