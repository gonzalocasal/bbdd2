package ar.com.grupo27.service;

import ar.com.grupo27.model.Pasajero;
import ar.com.grupo27.model.Viaje;
import ar.com.grupo27.persistence.ViajeDAO;

public class ViajeService {

	private PasajeroService pasajeroService;
	private ViajeDAO dao;
	
	public ViajeService(){
		this.dao = new ViajeDAO();
		this.pasajeroService = new PasajeroService();
	}
	
	public void registrarViaje(Viaje viaje){
		dao.openCurrentSessionwithTransaction();
		dao.persist(viaje);
		dao.closeCurrentSessionwithTransaction();
	}

	public void actualizarViaje(Viaje viaje){
		dao.openCurrentSessionwithTransaction();
		dao.actualizarViaje(viaje);
		dao.closeCurrentSessionwithTransaction();
	}
	
	public Viaje obtenerViaje (int id){
		dao.openCurrentSessionwithTransaction();
		Viaje viaje = dao.obtenerViaje(id);
		dao.closeCurrentSessionwithTransaction();
		return viaje;
	}
	
	public void registrarPasajero(Viaje viajeParam, Pasajero pasajero) {
		Viaje viaje = obtenerViaje(viajeParam.getId());
		viaje.registrarPasajero(pasajero);
		actualizarViaje(viaje);
	}

	public void finalizarViaje(Viaje viajerParam) {
		Viaje viaje = obtenerViaje(viajerParam.getId());
		viaje.setAbierto(false);
		actualizarViaje(viaje);
		pasajeroService.cobrarViaje(viaje);
	}
	
	
}
