<template>
  <div class="settings-page" >
      <div class="settings-card" >
     <h1>Settings</h1>
     <div>
        <span class="label">Select Location </span>
            <select v-model="campusShortCode" class="dropdown">
                <option value="CLE" >CLE</option>
                <option value="CBUS" >CBUS</option>
                <option value="CINCY" >CINCY</option>
                <option value="PGH" >PGH</option>
                <option value="PHL" >PHL</option>
            </select><br/>
        <span class="label">Certification Length </span> 
        <input type="text" v-model="campus.certLength" class="short-input" placeholder="Months ..." ><br>
        <span class="label">Start Date of Current Period</span>
        <input type="date" v-model="campus.currentPeriod" class="short-input" />
        <br />
        <button class="submit-button" @click="submitDate" >Submit</button>
     </div>
     </div>
  </div>
  
</template>

<script>
import auth from "@/auth.js"

export default {
  name: 'settings',
  data() {
    return {
     email: auth.getUser().sub,
     permission: auth.getUser().rol,
     campusShortCode:'',
      campus:{
        certLength:'',
        currentPeriod:'',
      },          
    }
  },
  created(){
   if(this.permission != 'admin' ){
     this.$router.push({ path: "/profile", params: this.email });
   }
  },
  methods: {
    submitDate(){
       fetch(`${process.env.VUE_APP_REMOTE_API}/api/period/${this.campusShortCode}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + auth.getToken(),     
                },
                body: JSON.stringify(this.campus)    
                })
                .then((response) => {
                    if(response.ok) {
                         this.$router.push({name: 'profile'});
                    }
                })
                .catch((err) => console.error(err));
            },
   
    }
}
</script>

<style scoped>
.settings-page{
    min-height:100vh;
}
.settings-card{
  color:white;
  display: block;
  max-width: 500px;
  padding:10px;
  margin: auto;
  background-color: rgba(32, 33, 36, 0.7);
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),
    0 1px 10px 0 rgba(0, 0, 0, 0.12);
}
.dropdown{
    padding: 5px;
  margin: 10px;
  width: 125px;
  border: none;
  border-radius: 2px;
}
.short-input{
  padding: 5px;
  margin: 10px;
  width: 125px;
  border: none;
  border-radius: 2px;
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