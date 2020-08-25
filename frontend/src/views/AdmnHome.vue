<template>
  <div class="home" >
    <div class="tab">
     <h1> Admin Settings</h1>
      </div>
    <div class="tab">
     <router-link :to="{ name: 'allProfiles' }" > <h1> All Profiles</h1></router-link>
      </div>
    <div class="tab">
     <h1> Search</h1>
      </div>
    <div class="tab">
      <router-link :to="{ name: 'allProfiles' }" ><h1> My Profile</h1></router-link>
      </div>
  </div>
  
</template>

<script>
import auth from "@/auth.js"

export default {
  name: 'admnhome',
  data() {
    return {
     email: auth.getUser().sub,     
    }
  },
  created(){
   
  },
  methods: {
    logout(){
      auth.logout();
      this.$router.go();
    }
  }
}
</script>

<style scoped>
.home{
  display:flex;
  flex-direction: column;
  min-height:100vh;
}
.tab{
  background-color: rgba(32, 33, 36, 0.9);
  box-shadow: 10px 10px 5px 0px rgba(0,0,0,0.75);
  border: 1px solid #1c9a2f;
  border-radius: 3px;
  color: white;
  margin:30px auto;
  width: 400px;
}
.tab:hover{
  cursor: pointer;
  color:#1c9a2f;
  transform: scale(1.2);
  transition-duration: 500ms;
}
h1{
  font-weight: lighter;
}

</style>