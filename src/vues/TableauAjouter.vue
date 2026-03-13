<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"

import { useRouter} from "vue-router"
import { ref} from 'vue'

const router = useRouter()

const nom = ref("")
const error = ref("")


const annuler = async () => {
  router.push("/private/tableaux")
}
const valider = async () => {
  
  router.push("/private/tableaux")
}

</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1>Ajouter un tableau</h1>
      <input v-model="nom" placeholder="Nom" />

        
      
      <div class="nav">
        <button class="boutonsNav" @click="valider">Valider</button> 
        <button class="boutonsNav" @click="annuler">Annuler</button>
      </div>
      <p v-if="error" class="error">&nbsp{{ error }}</p>
    </div>
  </div>
</template>

