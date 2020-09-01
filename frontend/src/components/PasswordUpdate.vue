<template>
  <div id="register" class="register-text-center">
    
        <div class="alert-success" role="alert" v-if="this.$route.query.registration">Success!!</div>
        <div
          class="alert alert-danger"
          role="alert"
          v-if="registrationErrors"
        >There were problems updating your password. Try again.</div>
        <div class="form-register">
        <h1>Change Password</h1>
        
        <input
          type="password"
          id="password"
          class="register-form-control"
          placeholder="Password"
          v-model="user.password"
          required
          autofocus
        /> <br>
        <input
          type="password"
          id="confirmPassword"
          class="register-form-control"
          placeholder="Confirm Password"
          v-model="user.confirmPassword"
          required
        /> <br>
        <button class="register-submit" @click="checkPassword">Submit</button>
        <button class="register-submit" @click="$emit('toggle-password')">Cancel</button>
    </div>
  </div>
</template>

<script>
import auth from "@/auth.js";
export default {
  name: "PasswordUpdate",
  data() {
    return {
      user: {
        email: "",
        password: "",
        confirmPassword: "",
        permission: "user",
      },
      registrationErrors: false,
    };
  },
  methods: {
     checkPassword(){
       if(this.password === this.confirmPassword){
         this.updatePassword()
       }
       else{
         this.registrationErrors= true;
       }
     },
     updatePassword(){
          fetch(`${process.env.VUE_APP_REMOTE_API}/api/updatePassword`, {
              method: 'PUT',
              headers: {
                  'Content-Type': 'application/json',
                  Authorization: 'Bearer ' + auth.getToken(),     
              },
              body: JSON.stringify(this.user)    
              })
              .then((response) => {
                  if(response.ok) {
                      this.$router.go();
                  }
              })
              .catch((err) => console.error(err));
          
     }
  },
};
</script>

<style >

.form-register {
  min-width: 450px;
  max-width: 35%;
  display: flex;
  flex-direction: column;
  align-content: flex-start;
  margin-top:20px;
  color: white;
  background-color: rgba(32, 33, 36, 0.7);
  border-radius: 4px;
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),
    0 1px 10px 0 rgba(0, 0, 0, 0.12);
  text-align: right;
  padding: 20px;
}

.label {
  padding: 10px;
}
h1{
  font-weight: lighter;
}
.register-submit {
  background-color: #336699;
  border: none;
  color: black;
  padding: 5px 20px;
  margin: auto;
  margin-top: 20px;
  text-align: center;
  text-decoration: none;
  display: block;
  font-size: 20px;
  border-radius: 4px;
}
.register-submit:hover {
  background-color: white;
  border: 1px solid #336699;
  color: #336699;
}
.alert-success {
  font-size: 24px;
  color: #1c9a2f;
}
</style>
