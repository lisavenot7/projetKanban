<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"
import { useRouter, useRoute } from "vue-router"
import { ref, computed } from 'vue'


const router = useRouter()
const route = useRoute()

const idTableau = Number(route.params.id)
const idColonne = Number(route.params.idcolonne)
const idTache = Number(route.params.idtache)

const texte = ref("")
const error = ref("")

const annuler = async () => {
  router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}`)
}
const valider = async () => {
  router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}`)
}

</script>

<template>
<Navbar />
<div class="container">
  <div class="box">
    <h1>Ajouter un commentaire</h1>

    <textarea 
      v-model="texte" 
      placeholder="Texte du commentaire" 
      rows="4" style="resize: none;">
    </textarea>

    <div class="nav">
      <button class="boutonsNav" @click="valider">Valider</button> 
      <button class="boutonsNav" @click="annuler">Annuler</button>
    </div>

    <p v-if="error" class="error">&nbsp{{ error }}</p>
  </div>
</div>
</template>