<template>
    <div>
    <h1>{{fName}}'s Training</h1>
    <ul class="training-list">
            <li v-for="aTrain in training" v-bind:key="aTrain.trainingId" class="training-item">
            <div class="compliance-div" >
                <h2 class="compliance" v-if="aTrain.isCompliance === true" >Compliance</h2>
                <h2 class="elective" v-if="aTrain.isCompliance === false" >Elective</h2>
            </div>
               <h2><span class="label">Training Name: </span><span class="var">{{aTrain.name}}</span></h2>
               <h2><span class="label">Training Provider: </span><span class="var">{{aTrain.provider}}</span></h2>
               <h2><span class="label">Training Date: </span><span class="var">{{aTrain.date}}</span></h2>
               <h2><span class="label">Training Time: </span><span class="var">{{aTrain.minutes/60}} hrs</span></h2>
               <h3><span class="label">Training Description: </span><span class="var">{{aTrain.topic}}</span></h3>
               <hr class="training-seperator" />
            </li>
        </ul> 

    </div>
</template>

<script>
import auth from '@/auth.js'
    export default {
        props:{
            fName: String,
        },
        data(){
            return{
                training: [],
               
            }
        },
        mounted(){
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
                this.training = theData; 
                console.log(this.training)
                    })
                    .catch((err) => {
                    console.log(err);
            })
            
                
            }
        
    }
</script>

<style scoped>
.training-list{
    list-style:none;
    text-align:left;
}
.label{
    font-size:20px;
    color:rgba(37,38,38,1);
}
.var{
    color:silver;
    font-weight:lighter;
    text-align:center;
}
.compliance{
    color:#1C9A2F;
}
.elective{
    color:#CB9908;
}
.compliance-div{
    text-align:center;
}
.training-seperator{
   width:75%;
}

</style>