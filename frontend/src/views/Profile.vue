<template>
    <div id="profile">
    <div id='image-div'>
        <img :src="profile.profilePic" class="profilePic" height="200px"/>
    </div>
    <div id="profile-detail">
        <h1> {{profile.firstName+" "+profile.lastName}} </h1>
        
        <h2>{{profile.role}}</h2>
        <h2>Start Date: {{profile.startDate}}</h2>
    </div>
    
    
    </div>
</template>

<script>
import auth from "@/auth.js"

    export default {
        name:'profile',
        data(){
           return{
                profile:{
                    
                     firstName: '',
                     lastName: '',
                     role: '',
                     startDate: '',
                     profilePic: '',
                }
           }

        },
        created() {
        fetch(`${process.env.VUE_APP_REMOTE_API}/api/profile`,
            {
            method: 'GET',
            headers: {
            Authorization: 'Bearer ' + auth.getToken(),
            },
            credentials: 'same-origin',
            })
            .then ((response) => {  
                console.log(response)   
                return response.json()
                })  
            .then ((theData) => {   
            this.profile = theData;
            })
            .catch((err) => {
            console.log(err);
            })
    }
        
    }
</script>

<style scoped>
#profile{
    width:90vw;
    height:90vh;
    padding:0px 10px;
}
#image-div{
    display:block;
    float:left;
}
.profilePic{
    border-radius:50%;
    border: 1px solid #1C9A2F;
}
#profile-detail{
    
}

</style>