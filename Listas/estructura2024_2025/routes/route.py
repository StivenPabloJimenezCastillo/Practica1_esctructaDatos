from flask import Blueprint, abort, request, render_template, redirect, flash
import requests
import json
router = Blueprint('router', __name__)

@router.route('/')
def home():
    
    return render_template('template.html')


#Registrar persona
@router.route('/admin/person/register')
def view_register_person():
    r = requests.get("http://localhost:8080/myapp/person/listType")
    data = r.json()
    print(r.json())
    return render_template('familia/registro.html', lista = data["data"])


#Registrar generador
@router.route('/admin/generador/register/<family_id>')
def view_register_generador(family_id):
    return render_template('generador/agregarGenerador.html', lista = ["data"], family_id = family_id)


#Ver lista de Generadores por Familia
@router.route('/admin/generators/view/<id>')
def list_generadores(id):
    r = requests.get("http://localhost:8080/myapp/person/getGeneradores/"+id)
    data = r.json()
    # Asegúrate de incluir el ID de la familia en el contexto
    return render_template('generador/listaGeneradores.html', lista=data["data"], family_id=id)

# lista de personas
@router.route('/admin/person/list')
def list_person():
    r = requests.get("http://localhost:8080/myapp/person/list/familia")
    #print(type(r.json()))
    #print(r.json())
    data = r.json()
    return render_template('familia/lista.html', lista = data["data"])

#Editar generador
@router.route('/admin/generador/edit/<id>')
def view_edit_generador(id):
    r = requests.get("http://localhost:8080/myapp/person/getGeneradores/" + id)
    data = r.json()
    if r.status_code == 200 and data["data"]:  # Verifica que hay datos
        generador = data["data"][0]  # Asumiendo que es un solo generador
        return render_template('generador/editarGenerador.html', lista=generador)
    else:
        flash(data["data"], category='error')
        return redirect("/admin/person/list")
    
    
#Editar persona
@router.route('/admin/person/edit/<id>')
def view_edit_person(id):
    r = requests.get("http://localhost:8080/myapp/person/listType")
    data = r.json()
    r1 = requests.get("http://localhost:8080/myapp/person/get/"+id)
    data1 = r1.json()
    if(r1.status_code == 200):
        return render_template('familia/editar.html', lista = data["data"], person = data1["data"])
    else:
        flash(data1["data"], category='error')
        return redirect("/admin/person/list")
    


# Guardar generador
@router.route('/save/generador/<family_id>', methods=["POST"])
def save_generador(family_id):
    headers = {'Content-type': 'application/json'}
    form = request.form
    dataF = {
        "coste": form["coste"],
        "consumo": form["consumo"],
        "potencia": form["potencia"],
        "uso": form["uso"]
    }
    try:
        r = requests.post(f"http://localhost:8080/myapp/person/save/generador/{family_id}", data=json.dumps(dataF), headers=headers)
        if r.status_code == 200:
            flash("Generador guardado correctamente", category='info')
            # Redirige a la lista de generadores para la familia actual
            return redirect(f"/admin/generators/view/{family_id}")
        else:
            flash(f"Error al guardar generador: {r.json().get('data', 'Error desconocido')}", category='error')
            return redirect(f"/admin/generators/view/{family_id}")
    except Exception as e:
        flash(f"Error al guardar generador: {str(e)}", category='error')
        return redirect(f"/admin/generators/view/{family_id}")
    

#Metodo de guardar Familias
@router.route('/admin/person/save', methods=["POST"])
def save_person():
    headers = {'Content-type': 'application/json'}
    form = request.form
    
    dataF = {"tipo":form["tipo"], "apellido":form["ape"], "nombre":form["nom"], "identificacion":form["iden"], "direccion":form["dir"],"fono":form["fono"]}
    r = requests.post("http://localhost:8080/myapp/person/save", data = json.dumps(dataF), headers=headers)
    dat = r.json()
    if r.status_code==200:
        flash("Se ha guardado correctamente", category='info')
        return redirect("/admin/person/list")
    else:
        flash(str(dat["data"],category='error'))
        return redirect("/admin/person/list")
    
#Metodo de actualizar
@router.route('/admin/person/update', methods=["POST"])
def update_person():
    headers = {'Content-type': 'application/json'}
    form = request.form
    
    dataF = {"id":form["id"], "tipo":form["tipo"], "apellido":form["ape"], "nombre":form["nom"], "direccion":form["dir"],"fono":form["fono"]}
    r = requests.post("http://localhost:8080/myapp/person/update", data = json.dumps(dataF), headers=headers)
    dat = r.json()
    if r.status_code==200:
        return redirect("/admin/person/list")
    else:
        return redirect("/admin/person/list")
    
#Metodo de actualizar generador
@router.route('/admin/person/update/generador', methods=["POST"])
def update_generador():
    headers = {'Content-type': 'application/json'}
    form = request.form
    
    dataF = {"id":form["id"], "coste": form["coste"], "consumo": form["consumo"], "potencia": form["potencia"], "uso": form["uso"]}
    r = requests.post("http://localhost:8080/myapp/person/update/generador", json = dataF)
    dat = r.json()
    print("#########################################################")
    print(dat)
    if r.status_code==200:
        return redirect("/admin/person/list")
    else:
        return redirect("/admin/person/list")


# Ruta para ver familias con generadores
@router.route('/admin/person/list/familiasConGeneradores')
def list_familias_con_generadores():
    r = requests.get("http://localhost:8080/myapp/person/list/familiasConGeneradores")
    data = r.json()
    return render_template('familia/listaConGeneradores.html', lista=data["data"])

