<template>
  <nav>
    <v-toolbar flat app>
      <v-toolbar-title class="text-uppercase grey-text" style="font-weight: bold;">
        <span class="naslovnica">Planer</span>
      </v-toolbar-title>

      <v-btn flat color="black" @click="pushTermini">
        <span>TERMINI</span>
      </v-btn>

      <v-btn flat color="black" @click="pushZadaci">
        <span>ZADACI</span>
      </v-btn>

      <v-btn flat color="black" @click="pushStatistika">
        <span>STATISTIKA</span>
      </v-btn>
    </v-toolbar>
  </nav>

</template>

<script>
import router from "@/router";

export default {

  methods:{
    pushTermini(){
      router.push('/termini')
    },
    pushZadaci(){
      router.push('/zadaci')
    },
    pushStatistika(){
      router.push('/statistika')
    },
  }

}

</script>

<style>

.naslovnica {
  text-transform: uppercase;
  color: #333;
  font-weight: bold;
  font-size: 38px;
  text-shadow: 1px 1px 2px rgba(0,0,0,0.3);
}

</style>