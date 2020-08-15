<template>
    <div>
  
    <ul class="training-list">
        
            <li v-for="aTrain in training" v-bind:key="aTrain.trainingId" class="training-item"  >
            <div v-if="aTrain.isApproved === false" class="overlay" >
                <div class="compliance-div" >
                    <h2 class="compliance" v-if="aTrain.complianceTime != 0" >Compliance</h2>
                    <h2 class="elective" v-if="aTrain.electiveTime != 0" >Elective</h2>
                </div>
               <h2><span class="label">Training Name: </span><span class="var">{{aTrain.name}}</span></h2>
               <h2><span class="label">Training Provider: </span><span class="var">{{aTrain.provider}}</span></h2>
               <h2><span class="label">Training Date: </span><span class="var">{{aTrain.date}}</span></h2>
               <h2>
                <div v-if="aTrain.complianceTime != 0" ><span class="label">Compliance Time: </span>
                    <span class="var">{{aTrain.complianceTime/60}} hrs</span>
                </div> 
                <div v-if="aTrain.electiveTime != 0" ><span class="label">Elective Time: </span>
                    <span class="var">{{aTrain.electiveTime/60}} hrs</span>
                </div> 
               </h2>

               <h3><span class="label">Training Description: </span><span class="var">{{aTrain.topic}}</span></h3>
               <h2 v-if="aTrain.isApproved === false" class="warn">Waiting on Approval</h2>
                </div>

            <div v-else >
                <div class="compliance-div" >
                    <h2 class="compliance" v-if="aTrain.complianceTime != 0" >Compliance</h2>
                    <h2 class="elective" v-if="aTrain.electiveTime != 0" >Elective</h2>
                </div>
                <h2><span class="label">Training Name: </span><span class="var">{{aTrain.name}}</span></h2>
                <h2><span class="label">Training Provider: </span><span class="var">{{aTrain.provider}}</span></h2>
                <h2><span class="label">Training Date: </span><span class="var">{{aTrain.date}}</span></h2>
                <h2>
                    <div v-if="aTrain.complianceTime != 0" ><span class="label">Compliance Time: </span>
                        <span class="var">{{aTrain.complianceTime/60}} hrs</span>
                    </div> 
                    <div v-if="aTrain.electiveTime != 0" ><span class="label">Elective Time: </span>
                        <span class="var">{{aTrain.electiveTime/60}} hrs</span>
                    </div> 
                </h2>
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