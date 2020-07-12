<template>
    <div id="profile">
        <div id="profile-detail">
            <div id="image-div" >
                <img :src="profile.profilePic" class="profilePic" height="200px"/>
            </div>
            <div id="profile-text" >
                <h1> {{profile.firstName+" "+profile.lastName}} </h1>
                <h2><span class="label">Position</span>{{profile.role}}</h2>
                <h2><span class="label">Start Date</span>  {{profile.startDate}}</h2>
            </div>
            <div class="time-div" >
                <p>Compliance Total:<span class="compliance" > {{compSum/60}}</span></p>
                <p>Elective Total:    <span class="elective" >    {{elecSum/60}}</span></p>
                <p>Combined:    <span class="total" >    {{elecSum/60 + compSum/60}}</span></p>
            </div>
        </div>
            <button v-if="!this.formMode" 
                    v-on:click="toggleFormMode" 
                    class="form-button">Add Training</button>
            <hr/>
      
    <div id="training">
          <training v-if="!this.formMode" :training="trainingArr" :firstName="profile.firstName" />
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
                trainingArr:[],
                compSum: 0,
                elecSum: 0
           }

        },
        methods: {
            toggleFormMode(){
                if(!this.formMode){
                    this.formMode = true;
                }
               /*  else if(this.formMode){
                    this.formMode = false;
                } */
            },
           getTotals(arr){
               let compTimeArr= []
               let elecTimeArr= []
                 arr.filter( (o)=>{
                  if(o.isCompliance === true){
                    compTimeArr.push(o.minutes)   
                       this.compSum = compTimeArr.reduce( (tot, val)=>{
                        return tot+val
                    },0)
                  } else{
                       elecTimeArr.push(o.minutes)   
                       this.elecSum = elecTimeArr.reduce( (tot, val)=>{
                        return tot+val
                    },0)
                  } 
              })
                   
           }
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
            }),
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/training`, {
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
                this.trainingArr = theData; 
                this.getTotals(this.trainingArr);           
                    })
                    .catch((err) => {
                    console.log(err);
            })
             
        },
        
    }
</script>

<style scoped>
#profile{
    padding:0px 5px;
    color:rgba(37,38,38,1);
}
#image-div{
    margin-top:20px;
    float:left;
}
#profile-detail{
    display: flex;
    justify-content:space-between;
    padding:5px 0px;
}
.profilePic{
    float:left;
    border-radius:50%;
    border: 1px solid #1C9A2F;
}
#profile-text{
    align-self:center;
}
.time-div{
align-self:center;
text-align:right;
padding:10px;
}
p{
    color:silver;
}
.label{
    font-weight:lighter;
    
}

</style>