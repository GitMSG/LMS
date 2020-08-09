<template>
    <div id="profile" >
    <ul >
        <li v-for="aProfile in profiles" v-bind:key="aProfile.profileId" class="list" >
            <div id='image-div'>
                <img :src="aProfile.profilePic" class="profilePic" height="200px"/>
            </div>
            <div id="profile-detail">
                <h1> {{aProfile.firstname+" "+aProfile.lastname}} </h1>
                <h2><span class="label">Location</span>{{aProfile.campusShortCode}}</h2>
                <h2><span class="label">Position</span>{{aProfile.role}}</h2>
                <h2><span class="label">Start Date</span>  {{aProfile.startDate}}</h2>
            </div>
            <div class="time-div" >
                <p>Compliance Total:<span class="compliance" > {{compSum/60}}</span></p>
                <p>Elective Total:    <span class="elective" >    {{elecSum/60}}</span></p>
                <p>Total:    <span class="total" >    {{elecSum/60 + compSum/60}}</span></p>
            </div>
        </li>
    </ul>
    </div>
</template>

<script>
import auth from "@/auth.js"

    export default {
        name:'allProfiles',
        data(){
           return{
               profiles: [],
                compSum: 0,
                elecSum: 0,
               
           }

        },
        methods:{
            getTotals(arr){
                
                let compTimeArr= []
                let elecTimeArr= []
                if(this.arr.length() > 1){
                     arr.filter( (o)=>{
                  if(o.trainComp === true){
                    compTimeArr.push(o.trainMinutes)   
                       this.compSum = compTimeArr.reduce( (tot, val)=>{
                        return tot+val
                    },0)
                  } else{
                       elecTimeArr.push(o.trainMinutes)   
                       this.elecSum = elecTimeArr.reduce( (tot, val)=>{
                        return tot+val
                    },0)
                  } 
              })
                }else{
                     if(this.profiles.trainComp === true){
                    compTimeArr.push(this.profiles.trainMinutes)   
                       this.compSum = compTimeArr.reduce( (tot, val)=>{
                        return tot+val
                    },0)
                  } else{
                       elecTimeArr.push(this.profiles.trainMinutes)   
                       this.elecSum = elecTimeArr.reduce( (tot, val)=>{
                        return tot+val
                    },0)
                  } 
                }
                /*  arr.filter( (o)=>{
                  if(o.trainComp === true){
                    compTimeArr.push(o.trainMinutes)   
                       this.compSum = compTimeArr.reduce( (tot, val)=>{
                        return tot+val
                    },0)
                  } else{
                       elecTimeArr.push(o.trainMinutes)   
                       this.elecSum = elecTimeArr.reduce( (tot, val)=>{
                        return tot+val
                    },0)
                  } 
              }) */   
                   
           },
           sortProfiles(arr){
    

               console.log(arr)
           }
        },
        created() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/allProfiles`,{
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
            this.profiles = theData;
              this.sortProfiles(this.profiles)
                      
                    })
            .catch((err) => {
                console.log(err);
            })
            /* fetch(`${process.env.VUE_APP_REMOTE_API}/api/training`, {
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
            }) */
            
    }
        
    }
</script>

<style scoped>
#profile{
    height:100vh;
    padding:0px 10px;
    color:white;
}
#image-div{
   
}
.profilePic{
    border-radius:50%;
    
}
.label{
    font-weight:lighter;
    
}
.list{
    display:flex;
    justify-content:space-evenly;
    margin:0px 20px 20px 20px;
    padding:10px;
    background-color:rgba(32, 33, 36, .7 );
    box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
    border: 1px solid #1C9A2F;
    border-radius:3px;
}
ul{
    list-style:none;
    padding: 0;
    
}

</style>