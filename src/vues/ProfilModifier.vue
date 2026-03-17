<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import NavbarUtilisateur from "../components/NavbarUtilisateur.vue"
import { useRouter ,useRoute} from "vue-router"
import { ref,onMounted } from 'vue'
import usersData from '../bdd/users.json'

const router = useRouter()
const route = useRoute()

const pseudoParam = "robertD"

const user = usersData.find(u => u.pseudo === pseudoParam) 

const pseudo = ref(user ? user.pseudo : "")
const nom = ref(user ? user.nom : "")
const prenom = ref(user ? user.prenom : "")
const mail = ref(user ? user.mail : "")
const error = ref("")

const annuler = async () => {
  if (route.path.startsWith('/private')) {
    router.push("/private/profil")
  } else {
    router.push("/admin/profil")
  }
}
const valider = async () => {
   if (route.path.startsWith('/private')) {
    router.push("/private/profil")
  } else {
    router.push("/admin/profil")
  }
}

const token = localStorage.getItem("jwtToken")
onMounted(() => {
  if (!token) {
    router.push("/connexion")
  }
})
</script>

<template>
  <NavbarUtilisateur v-if="route.path.startsWith('/private')" />
  <Navbar v-else />
  <div class="container">
    <div class="box">
      <h1>Modifier mon profil</h1>

      <input v-model="pseudo" placeholder="Pseudo" />
      <input v-model="nom" placeholder="Nom" />
      <input v-model="prenom" placeholder="Prenom" />
      <input v-model="mail" placeholder="Mail" />
      <div class="nav">
        <button class="boutonsNav" @click="valider">Valider</button> 
        <button class="boutonsNav" @click="annuler">Annuler</button>
      </div>
      <h2 v-if="error" class="error">&nbsp{{ error }}</h2>
    </div>
  </div>
</template>