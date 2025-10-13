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

  carrito.forEach((item,index) => {
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

    // Botón Quitar
    const btnQuitar = document.createElement("button");
    btnQuitar.classList.add("btn-quitar");
    btnQuitar.textContent = "Quitar";
    btnQuitar.setAttribute("data-index", index); // Establecer el índice del producto

    // Añadir todo al li
    li.appendChild(img);
    li.appendChild(infoDiv);
    li.appendChild(precioSpan);
    li.appendChild(btnQuitar);

    listaCarrito.appendChild(li);
  });

  // Mostrar total con moneda
  totalElement.textContent = `$${total.toLocaleString()} CLP`;
}

// ====== Eliminar producto del carrito ======

function eliminarProducto(index) {
  const carrito = obtenerCarrito();

  
  carrito.splice(index, 1); // Eliminar producto en el índice especificado
  guardarCarrito(carrito);  // Guardar carrito actualizado en localStorage
  renderCarrito();          // Volver a renderizar el carrito
}

// Añadir evento de "Quitar" al botón
if (listaCarrito) {
  listaCarrito.addEventListener("click", (e) => {
    if (e.target && e.target.classList.contains("btn-quitar")) {
      const index = e.target.getAttribute("data-index"); // Obtener el índice del producto
      eliminarProducto(index); // Eliminar el producto
    }
  });
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
