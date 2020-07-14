<template>
  <div id="register" class="register-text-center">
  <!--  <h1 class="register-tag">Create Account</h1> -->
      
   <div class="form-register">
    <form  @submit.prevent="register">
      <div class="alert-success" role="alert" v-if="this.$route.query.registration">
        Success!! 
      </div>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        There were problems registering this user.
      </div>
      <label for="username" class="label">Email</label>
      <input
        type="text"
        id="email"
        class="register-form-control"
        placeholder="Username"
        v-model="user.email"
        required
        autofocus
      />
      <br/>
      <label for="password" class="label">Password</label>
      <input
        type="password"
        id="password"
        class="register-form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <input
        type="password"
        id="confirmPassword"
        class="register-form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <button class="register-submit" type="submit">
        Create Account
      </button>
    </form>
    
    </div>
    
  </div>
</template>

<script>
export default {
  name: 'register',
  data() {
    return {
      user: {
        email: '',
        password: '',
        confirmPassword: '',
        permission: 'user',
      },
      registrationErrors: false,
    };
  },
  methods: {
    register() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/register`, {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.user),
      })
        .then((response) => {
          if (response.ok) {
            this.$router.push({ path: '/register', query: { registration: 'success' } });
            this.$router.go();
          } else {
            this.registrationErrors = true;
          }
        })

        .then((err) => console.error(err));
    },
  },
};
</script>

<style>

#register{
  height:99vh;
  display:flex;
  flex-direction: column;
}
.register-form-control{
  align-content:center;
  border-radius:2px;
  padding: 5px ;
  margin: 15px 0;
  border:none;
  text-align:center;
}
.form-register{
  min-width:450px;
  max-width:35%;
  display: flex;
  justify-content:center;
  align-items:center;
  margin:auto;
  color:white;
  background-color:rgba(32, 33, 36, .70 );
  border-radius:4px;
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
  text-align:right;
  padding:20px;
}
form{
 max-width:50%;
 text-align:center;
}
.label {
  padding:20px;
}

.register-submit{
   
    background-color: #336699;
    border: none;
    color: black;
    padding: 5px 20px;
    margin:auto;
    margin-top:20px;
    text-align: center;
    text-decoration: none;
    display: block;
    font-size: 20px;
    border-radius: 4px;
}
.register-submit:hover{
    background-color: white;
    border: 1px solid #336699;
    color:#336699;
}
.alert-success{
  font-size:24px;
  color:#1C9A2F;
}

</style>
