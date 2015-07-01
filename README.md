# SanteQuery
Utilidad para construir Queries utilizando objetos, eliminando la impedancia que surje al concatenar Cadenas

Ejemplo:

Para generar el siguiente fragmento de Query String ?filter=(isRent==true;adress==a1;adress==a2) donde isRent es un parametro obligatorio y adress tiene una cardinalidad de [0..n] quedaría como sigue:

String listOfFilters=QueryStringBuilder.init()
							.openEnclosingSymbol("$filter=(")
							.addComparison(isRent,"true")
							.ifPrint(adresses.length>0)
								.addDelimitationSymbol()
								.addComparison("adress",adresses)
							.endIfPrint()
							.closeEnclosingSymbol(")")
							.buildQuery();
