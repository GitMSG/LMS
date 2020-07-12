<template>
    <div id="profile">
            <img :src="profile.profilePic" class="profilePic" height="200px"/>
        <div id="profile-detail">
            <h1> {{profile.firstName+" "+profile.lastName}} </h1>
            <h2><span class="label">Position</span>{{profile.role}}</h2>
            <h2><span class="label">Start Date</span>  {{profile.startDate}}</h2>
            <h3>Compliance Total{{}}</h3>
            <button v-if="!this.formMode" 
                    v-on:click="toggleFormMode" 
                    class="form-button">Add Training</button>
            <hr/>
        </div>
        <div id="training">
          <training v-if="!this.formMode" :fName = profile.firstName  />
          <training-form v-if="this.formMode" />
        </div>
    </div>
</template>

<script>
import auth from "@/auth.js"
import Training from "@/components/Training.vue"
import TrainingForm from "@/components/TrainingForm.vue"
    export default {
        name:'profile',
        components:{
            Training,
            TrainingForm,
        },
        data(){
           return{
                formMode: false,
                profile:{
                     profileId: '',
                     firstName: '',
                     lastName: '',
                     role: '',
                     startDate: '',
                     profilePic: '',
                },
               
           }

        },
        methods: {
            toggleFormMode(){
                if(!this.formMode){
                    this.formMode = true;
                }
                else if(this.formMode){
                    this.formMode = false;
                }
            },
           
            
        },
        mounted() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/profile`, {
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
                    this.profile = theData;
                if(this.profile.firstName === null){
                    this.$router.push('/profileForm')
                }   
                    })
                    .catch((err) => {
                    console.log(err);
            })
             
        },
        
    }
</script>

<style scoped>
#profile{
    height:85vh;
    padding:0px 10px;
    color:rgba(37,38,38,1);
}
#image-div{
    display:block;
    float:left;
}
.profilePic{
    border-radius:50%;
    border: 1px solid #1C9A2F;
}
.label{
    font-weight:lighter;
    
}

</style>