package bd2.Muber.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import bd2.Muber.dto.AgregarPasajeroDTO;
import bd2.Muber.dto.CalificacionDTO;
import bd2.Muber.dto.PasajeroDTO;
import bd2.Muber.dto.ViajeDTO;
import bd2.Muber.model.Calificacion;
import bd2.Muber.model.Conductor;
import bd2.Muber.model.Pasajero;
import bd2.Muber.model.Viaje;
import bd2.Muber.service.ConductorService;
import bd2.Muber.service.PasajeroService;
import bd2.Muber.service.ViajeService;

@ControllerAdvice
@RequestMapping("/services")
@ResponseBody
@EnableWebMvc
public class ViajeController {

	@Autowired
	private ConductorService conductorService;
	
	@Autowired
	private PasajeroService pasajeroService;
	
	@Autowired
	private ViajeService service;
	
	@RequestMapping(value = "/viajes/abiertos", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	public Map<String, Object> viajesAbiertos() {
		Map<String, Object> aMap = new HashMap<String, Object>();
		aMap.put("result", "OK");
		aMap.put("Viajes Abiertos", service.obtenerTodosLosViajesAbiertosDTO());
		return aMap;
	}
	
	@RequestMapping(value = "/viajes/nuevo", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public Map<String, Object> nuevoViaje(@RequestBody  ViajeDTO viajeDTO ) {
		Conductor conductor = conductorService.obternerConductor(viajeDTO.getConductor());
		Viaje nuevoViaje = new Viaje (viajeDTO.getOrigen(),viajeDTO.getDestino(),conductor,viajeDTO.getCosto(),viajeDTO.getCapacidad());
		service.registrarViaje(nuevoViaje);
		Map<String, Object> aMap = new HashMap<String, Object>();
		aMap.put("result", "OK");
		aMap.put("Viajes Creado", new ViajeDTO(nuevoViaje));
		return aMap;
	}
	
	@RequestMapping(value = "/viajes/agregarPasajero", method = RequestMethod.PUT, produces = "application/json", headers = "Accept=application/json")
	public Map<String, Object> agregarPasajero(@RequestBody AgregarPasajeroDTO pasajeroViajeDTO 	) {
		Viaje viaje = service.obtenerViaje(pasajeroViajeDTO.getViajeId());
		Pasajero pasajero = pasajeroService.obtenerPasajero(pasajeroViajeDTO.getPasajeroId());
		service.registrarPasajero(viaje, pasajero);
		Map<String, Object> aMap = new HashMap<String, Object>();
		aMap.put("result", "OK");
		aMap.put("Pasajero Agregado", new PasajeroDTO(pasajero));
		return aMap;
	}
	
	@RequestMapping(value = "/viajes/calificar", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public Map<String, Object> calificar( 	@RequestBody CalificacionDTO paramCalificacion){
		Viaje viaje = service.obtenerViaje(paramCalificacion.getViajeID());
		Pasajero pasajero = pasajeroService.obtenerPasajero(paramCalificacion.getPasajero());
		Calificacion calificacion = new Calificacion(viaje, pasajero, paramCalificacion.getPuntaje(), paramCalificacion.getComentario());
		service.calificar(calificacion);
		Map<String, Object> aMap = new HashMap<String, Object>();
		aMap.put("result", "OK");
		aMap.put("Calificación Agregada", new CalificacionDTO(calificacion));
		return aMap;
	}
	
	@RequestMapping(value = "/viajes/finalizar", method = RequestMethod.PUT , produces = "application/json", headers = "Accept=application/json")
	public Map<String, Object> finalizar( 	@RequestBody Integer viajeId){
		Viaje viaje = service.obtenerViaje(viajeId);
		String result = (conductorService.finalizarViaje(viaje)) ? "OK" : "Error, el viaje fue cerrado previamente";
		Map<String, Object> aMap = new HashMap<String, Object>();
		aMap.put("result", result);
		aMap.put("Viaje Finalizado", new ViajeDTO(viaje));
		return aMap;
	}
	
}


