<template>
    <div>
  
    <ul class="training-list">
        
            <li v-for="aTrain in training" v-bind:key="aTrain.trainingId" class="training-item"  >
            <div v-if="aTrain.isApproved === false" class="overlay" >
                <div class="compliance-div" >
                    <h2 class="compliance" v-if="aTrain.isCompliance === true" >Compliance</h2>
                    <h2 class="elective" v-if="aTrain.isCompliance === false" >Elective</h2>
                </div>
               <h2><span class="label">Training Name: </span><span class="var">{{aTrain.name}}</span></h2>
               <h2><span class="label">Training Provider: </span><span class="var">{{aTrain.provider}}</span></h2>
               <h2><span class="label">Training Date: </span><span class="var">{{aTrain.date}}</span></h2>
               <h2><span class="label">Training Time: </span><span class="var">{{aTrain.minutes/60}} hrs</span></h2>
               <h3><span class="label">Training Description: </span><span class="var">{{aTrain.topic}}</span></h3>
               <h2 v-if="aTrain.isApproved === false" class="warn">Waiting on Approval</h2>
                </div>

            <div v-else >
                <div class="compliance-div" >
                    <h2 class="compliance" v-if="aTrain.isCompliance === true" >Compliance</h2>
                    <h2 class="elective" v-if="aTrain.isCompliance === false" >Elective</h2>
                </div>
                <h2><span class="label">Training Name: </span><span class="var">{{aTrain.name}}</span></h2>
                <h2><span class="label">Training Provider: </span><span class="var">{{aTrain.provider}}</span></h2>
                <h2><span class="label">Training Date: </span><span class="var">{{aTrain.date}}</span></h2>
                <h2><span class="label">Training Time: </span><span class="var">{{aTrain.minutes/60}} hrs</span></h2>
                <h3><span class="label">Training Description: </span><span class="var">{{aTrain.topic}}</span></h3>              
            </div>
            </li>
           
        </ul> 

    </div>
</template>

<script>
/* import auth from "@/auth.js" */
    export default {
        props:{
            firstName: String,
            training: Array,
            //profileId: Number,
        },
        data(){
            return{
             
            }
        },
       /*  mounted(){console.log(this.$props.profileId)
            
                fetch(`${process.env.VUE_APP_REMOTE_API}/api/training/${this.$props.profileId}`, {
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
                //this.getTotals(this.trainingArr);           
                    })
                    .catch((err) => {
                    console.log(err);
            })
            
        
            
        } */
        
    }
</script>

<style>

.training-item{
    background-color:rgba(32, 33, 36, .80 );
    border-radius:4px;
    box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
    padding:5px;
    margin:10px;
    list-style:none;
    text-align:left;
}
.label{
    font-size:16px;
    font-weight: lighter;
    padding:10px;
    color:rgb(211, 228, 243);
}
.overlay{
    color:rgb(231, 202, 34);
    background-color: rgba(238, 240, 241, 0.5); 
    z-index: 2;
}
.warn{
    text-align: center;
}
.var{
    color:silver;
    font-weight:lighter;
    text-align:center;
}
.compliance{
    color:#1C9A2F;
    font-size: 20px;
}
.elective{
    color: rgba(36,104,143,1);
    font-size: 20px;
  
}
.total{
    color:#914522;
    font-size: 20px;
}
.compliance-div{
    text-align:center;
    float:right;
    margin:10px;
    padding:10px;
}
ul{
    padding:0;
}
h2{
    margin:10px;
}
h3{
    margin:10px;
}

</style>