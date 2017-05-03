package scalafx.ecstock.i18n
import scalafx.Includes._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.{Map, SynchronizedMap, HashMap}

object Messages {
  val data = collection.mutable.Map[String, String]()
  data += ("save" -> "Guardar")
  data += ("cancel" -> "Cancelar")
  data += ("Add Card" -> "Nueva Venta")
  data += ("Report" -> "Report")
  data += ("Add Category" -> "Nueva Categoria")
  data += ("Add Customer" -> "Nuevo Cliente")
  data += ("Add Product" -> "Nuevo Producto")
  data += ("Add Product Inventory" -> "Comprar")
  data += ("Add Product Inventory By Box" -> "Comprar Por Cajas")
  data += ("Add Vendor" -> "Nuevo Proveedor")
  data += ("Border Pane" -> "Border Pane")
  data += ("Grid Pane" -> "Grid Pane")
  data += ("Inventory" -> "Inventario")
  data += ("List Card" -> "Listar Ventas")
  data += ("List Card Item" -> "Listar Ventas/Producto")
  data += ("List Category" -> "Listar Categorias")
  data += ("List Customer" -> "Listar Clientes")
  data += ("List Inventory" -> "Listar Compras")
  data += ("List Product" -> "Listar Productos")
  data += ("List Vendor" -> "Listar Proveedores")
  data += ("Name:" -> "Nombre:")
  data += ("Name" -> "Nombre")
  data += ("Description:" -> "Descripcion:")
  data += ("Description" -> "Descripcion:")
  data += ("Save Data." -> "Guardar Datos.")
  data += ("Save Data" -> "Guardar Datos")
  data += ("Product" -> "Producto")
  data += ("Product:" -> "Producto:")
  data += ("Quantity" -> "Cantidad")
  data += ("Quantity:" -> "Cantidad:")
  data += ("Customer" -> "Cliente")
  data += ("Customer:" -> "Cliente:")
  data += ("Category" -> "Categoria")
  data += ("Category:" -> "Categoria:")
  data += ("Vendor" -> "Proveedor")
  data += ("Vendor:" -> "Proveedor:")
  data += ("Brand" -> "Marca")
  data += ("Brand:" -> "Marca:")
  data += ("Retail Price" -> "Precio por Menor")
  data += ("Retail Price:" -> "Precio por Menor:")
  data += ("Address" -> "Direccion")
  data += ("Address:" -> "Direccion:")
  data += ("Total:" -> "Total:")
  data += ("Total" -> "Total")
  data += ("Limit:" -> "Limite:")
  data += ("Limit" -> "Limite")
  data += ("Cost:" -> "Costo:")
  data += ("Cost" -> "Costo")
  data += ("Price:" -> "Precio:")
  data += ("Price" -> "Precio")
  data += ("Total Price:" -> "Precio Total:")
  data += ("Total Price" -> "Precio Total")
  data += ("Contact:" -> "Contacto:")
  data += ("Contact" -> "Contacto")
  data += ("Contact2:" -> "Contacto2:")
  data += ("Contact2" -> "Contacto2")
  data += ("Make a choice..." -> "Seleccionar ...")
  data += ("Information" -> "Informacion")
  data += ("List" -> "Lista")
  data += ("Unit Cost" -> "Costo Unitario")
  data += ("Total Cost" -> "Costo Total")
  data += ("Box Quantity" -> "Unidades en cajas")
  data += ("Box Size" -> "Unidades por caja")
  data += ("Box Cost" -> "Costo por caja")

  val dataOpposite = collection.mutable.Map[String, String]()
  dataOpposite += ("Guardar" -> "save")
  dataOpposite += ("Cancelar" -> "cancel")
  dataOpposite += ("Nueva Venta" -> "Add Card")
  dataOpposite += ("Reporte" -> "Report")
  dataOpposite += ("Nueva Categoria" -> "Add Category")
  dataOpposite += ("Nuevo Cliente" -> "Add Customer")
  dataOpposite += ("Nuevo Producto" -> "Add Product")
  dataOpposite += ("Comprar" -> "Add Product Inventory")
  dataOpposite += ("Comprar Por Cajas" -> "Add Product Inventory By Box")
  dataOpposite += ("Nuevo Proveedor" -> "Add Vendor")
  dataOpposite += ("Border Pane" -> "Border Pane")
  dataOpposite += ("Grid Pane" -> "Grid Pane")
  dataOpposite += ("Inventario" -> "Inventory")
  dataOpposite += ("Listar Ventas" -> "List Card")
  dataOpposite += ("Listar Ventas/Producto" -> "List Card Item")
  dataOpposite += ("Listar Categorias" -> "List Category")
  dataOpposite += ("Listar Clientes" -> "List Customer")
  dataOpposite += ("Listar Compras" -> "List Inventory")
  dataOpposite += ("Listar Productos" -> "List Product")
  dataOpposite += ("Listar Proveedores" -> "List Vendor")
}
