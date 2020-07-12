<template>
    <div>
   <!--  <h1>{{firstName}}'s Training</h1> -->
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
               
            </li>
        </ul> 

    </div>
</template>

<script>
    export default {
        props:{
            firstName: String,
            training: Array,
        },
        data(){
            return{
                //training: [],
               
            }
        },
        
    }
</script>

<style>
.training-list{
   
}
.training-item{
    background-color:rgba(32, 33, 36, .5 );
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
.total{
    color:#B53A0F
}
.compliance-div{
    text-align:center;
    float:right;
    margin:10px;
    padding:10px;
}
.training-seperator{
   width:75%;
}
h2{
    margin:10px;
}
h3{
    margin:10px;
}

</style>