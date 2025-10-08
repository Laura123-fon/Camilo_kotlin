// ====== Funciones de almacenamiento ======

// Obtener carrito desde localStorage
function obtenerCarrito() {
  return JSON.parse(localStorage.getItem("carrito")) || [];
}

// Guardar carrito en localStorage
function guardarCarrito(carrito) {
  localStorage.setItem("carrito", JSON.stringify(carrito));
}

// ====== Elementos del DOM ======

const listaCarrito = document.getElementById("carrito-lista");
const totalElement = document.getElementById("total");
const finalizarBtn = document.getElementById("finalizar-btn");

// ====== Función para renderizar carrito ======

function renderCarrito() {
  if (!listaCarrito || !totalElement) return; // Validación de existencia

  const carrito = obtenerCarrito();
  listaCarrito.innerHTML = ""; // Limpiar lista
  let total = 0;

  carrito.forEach(item => {
    total += item.precio * item.cantidad;

    const li = document.createElement("li");
    li.classList.add("cart-item");

    // Crear imagen de manera segura
    const img = document.createElement("img");
    img.setAttribute("src", item.imagen);
    img.setAttribute("alt", item.nombre);

    // Información del producto
    const infoDiv = document.createElement("div");
    infoDiv.classList.add("cart-info");

    const nombre = document.createElement("h3");
    nombre.textContent = item.nombre;

    const cantidad = document.createElement("p");
    cantidad.textContent = `Cantidad: ${item.cantidad}`;

    infoDiv.appendChild(nombre);
    infoDiv.appendChild(cantidad);

    // Precio total por producto
    const precioSpan = document.createElement("span");
    precioSpan.classList.add("cart-price");
    precioSpan.textContent = `$${(item.precio * item.cantidad).toLocaleString()} CLP`;

    // Añadir todo al li
    li.appendChild(img);
    li.appendChild(infoDiv);
    li.appendChild(precioSpan);

    listaCarrito.appendChild(li);
  });

  // Mostrar total con moneda
  totalElement.textContent = `$${total.toLocaleString()} CLP`;
}

// ====== Finalizar compra ======

if (finalizarBtn) {
  finalizarBtn.addEventListener("click", () => {
    const carrito = obtenerCarrito();
    if (carrito.length === 0) {
      alert("Tu carrito está vacío.");
      return;
    }

    alert("¡Gracias por tu compra! 🛍️");
    guardarCarrito([]); // Vaciar carrito
    renderCarrito();    // Actualizar vista
  });
}

// ====== Render inicial ======
renderCarrito();
