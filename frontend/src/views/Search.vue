<template>
  <div class="search" >
      <div class="search-card" >
        <h1>Search</h1>
        <button class="submit-button" >Submit</button>
     </div>
  </div>
  
</template>

<script>
import auth from "@/auth.js"

export default {
  name: 'search',
  data() {
    return {
     email: auth.getUser().sub,
     permission: auth.getUser().rol,     
    }
  },
  created(){
   if(this.permission != 'admin' ){
     this.$router.push({ path: "/profile", params: this.email });
   }
  },
  methods: {
  
  }
}
</script>

<style scoped>
.search{
  min-height:100vh;
}
.search-card{
  color:white;
  display: block;
  max-width: 500px;
  padding:10px;
  margin: auto;
  background-color: rgba(32, 33, 36, 0.7);
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),
    0 1px 10px 0 rgba(0, 0, 0, 0.12);
}

.submit-button{
  background-color: rgba(36, 104, 143, 1);
  color: white;
  padding: 5px 20px;
  max-width: 25%;
  text-decoration: none;
  font-size: 16px;
  border-radius: 4px;
  border: none;
  margin: 10px;
  cursor: pointer;
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