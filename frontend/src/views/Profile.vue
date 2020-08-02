<template>
    <div id="profile" >
        <div id="detail-container">
        <div id="profile-detail">
            <div id="image-div" >
                <img :src="profile.profilePic" class="profilePic" height="200px"/>
            </div>
            <div id="profile-text" >
                <h1> {{profile.firstName+" "+profile.lastName}} </h1>
                <h2><span class="label">Location</span>{{profile.campusShortCode}}</h2>
                <h2><span class="label">Position</span>{{profile.role}}</h2>
                <h2><span class="label">Start Date</span>  {{profile.startDate}}</h2>
            </div>
            <div class="time-div" >
                <p>Compliance Total:<span class="compliance" > {{compSum/60}}</span></p>
                <p>Elective Total:    <span class="elective" >    {{elecSum/60}}</span></p>
                <p>Total:    <span class="total" >    {{elecSum/60 + compSum/60}}</span></p>
            </div>
        </div>
            <button v-if="!this.formMode" 
                    v-on:click="toggleFormMode" 
                    class="form-button">Add Training
            </button>
            </div>
           
      
    <div id="training">
          <training v-if="!this.formMode" :profileId="profile.profileId" :training="trainingArr" :firstName="profile.firstName" />
          <training-form v-if="this.formMode" :profileId="profile.profileId"/>
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
                
                email: auth.getUser().sub,
                formMode: false,
                profile:{
                     profileId: '',
                     firstName: '',
                     lastName: '',
                     campusShortCode: '',
                     role: '',
                     startDate: '',
                     profilePic: '',
                },
                trainingArr:[],
                compSum: 0,
                elecSum: 0,

           }

        },
        methods: {
            toggleFormMode(){
                if(!this.formMode){
                    this.formMode = true;
                }
              
            },
            
           getTotals(arr){
               let compTimeArr= []
               let elecTimeArr= []
                 arr.filter( (o)=>{
                  if(o.isCompliance === true && o.isApproved === true){
                    compTimeArr.push(o.minutes)   
                       this.compSum = compTimeArr.reduce( (tot, val)=>{
                        return tot+val
                    },0)
                  } else if(o.isApproved === true){
                       elecTimeArr.push(o.minutes)   
                       this.elecSum = elecTimeArr.reduce( (tot, val)=>{
                        return tot+val
                    },0)
                  } 
              })
                   
           }
        },
        created() {
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
                    this.$router.go()
                }   
                
                    })
                    .catch((err) => {
                    console.log(err);
            })
            /* fetch(`${process.env.VUE_APP_REMOTE_API}/api/training/${profileId}`, {
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
            })    */
        }
    }
</script>

<style scoped>
#profile{
    height: 100vh;
    padding:20px;
    border-radius:3px;
    color:silver;
}
#detail-container{
    background-color:rgba(233, 235, 241, 0.9);
    box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
}
#profile-detail{
    display: flex;
    justify-content:space-around;
    padding:5px 0px;
}
.profilePic{
    float:left;
    border-radius:50%;
    border: 1px solid #1c9a2f;
}
#profile-text{
    background-color:rgba(32, 33, 36, .80 );
    box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
    align-self:center;
}
.time-div{
    background-color:rgba(32, 33, 36, .80 );
    box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
    align-self:center;
    text-align:right;
    padding:10px;
}
p{
    color:silver;
    
}
.form-button{
  background-color: rgba(36,104,143,1);
  color:white;
  padding: 5px 20px;
  text-decoration: none;
  font-size: 16px;
  border-radius: 4px;
  border:none;
  margin:10px;
  cursor:pointer
}
.label{
    font-weight:lighter;
}


</style>