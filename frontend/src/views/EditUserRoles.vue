<template>
    <div id="editUsersPage" class="permission-page">
        <h1 class="permission-header">Edit User</h1>
        <ul class="permission-group">
            <li v-for="user in users" v-bind:key="user.id" class="permission-item">
               <!--  <User :email = user.email :currentpermission = user.permission :id = user.id /> -->
            </li>
        </ul>
    </div>
</template>

<script>
import auth from '@/auth.js'


export default {
   /*  components: {
        User
    }, */
    data() {
        return {
            users: []
        }
    },

    created() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/users`,
        {
        method: 'GET',
        headers: {
        Authorization: 'Bearer ' + auth.getToken(),
        },
        credentials: 'same-origin',
        })
        .then ((response) => {     
            return response.json()
            })  
        .then ((theData) => {   
        this.users = theData;
        })
        .catch((err) => {
        console.log(err);
        })
    }
}
</script>

<style>
#editUsersPage{
  width:100vw;
  height:100vh;
}
.permission-header{
  font-weight:lighter;
  color: white;

}
.permission-group{
    display:flex;
    flex-direction:column;
    align-items:center;
}
.permission-item{
   
    list-style-type: none;
    border: none;
    background-color:rgba(37,38,38,1);
    box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
    width: 60%;
    margin: 20px;
    padding:20px;
   
}
.permission-form{
    display:flex;
    flex-direction:row;
    justify-content:space-between;
}
.permission-email{
    font-size:20px;
    color: white;
    
}
.permission-select{
    width:100px;
    height:30px;
    border:none;
    
}
.permission-submit{
    width:125px;
    height:30px;
    border-radius:2px;
    border:none;
    background-color: #336699;
}
.permission-submit:hover{
    background-color: white;
    border: 1px solid #336699;
    color:#336699;
    cursor:pointer;
}
.delete-button{
    width:100px;
    height:30px;
    border-radius:2px;
    border:none;
    background-color: #336699;
}
.delete-button:hover{
    background-color: white;
    border: 1px solid #336699;
    color:#336699;
    cursor:pointer;
}

 
</style>