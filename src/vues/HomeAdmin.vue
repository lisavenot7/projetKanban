<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import Bandeau from "../components/BandeauAdmin.vue"

import { useRouter } from "vue-router"
import { onMounted } from "vue"

const router = useRouter()

onMounted(() => {
  const token = localStorage.getItem("jwtToken")
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "0") {
    router.push("/private")
  }
})
</script>

<template>
<Navbar />
  <div class="home">
    <Bandeau />
  </div>
</template>
