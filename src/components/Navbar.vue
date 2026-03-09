<script setup>

</script>

<template>
  <nav class="navbar">
    <div class="logo">PrimeUBO</div>
    <div class="links">
      <a>Accueil</a>
      <a>Séries</a>
      <a>Films</a>
      <a>Connexion</a>
    </div>
  </nav>
</template>

<style scoped>
.navbar {
  position: fixed;
  width: 100%;
  height: 60px;
  background-color: #0F171E;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 40px;
  color: white;
  z-index: 100;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #00A8E1;
}

.links a {
  margin-left: 20px;
  cursor: pointer;
}

.links a:hover {
  color: #00A8E1;
}

</style>